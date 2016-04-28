package edu.csulb.android.androidscoretracker;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class SessionHistoryDatabaseManager {

    private SQLiteDatabase db;
    private SimpleDateFormat dateFormat;

    private final String TABLE_NAME = "sessionHistory";
    private final String COLUMN_ID = "id";
    private final String COLUMN_SESSION_ID = "sessionId";
    private final String COLUMN_DATE = "date";
    private final String COLUMN_COMMENT = "comment";
    private final String COLUMN_TYPE = "type";

    public SessionHistoryDatabaseManager() {
        db = DatabaseManager.getInstance().getDb();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    //add an history in the database
    public void addHistory(SessionHistory history) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_SESSION_ID, history.getSessionId());
        values.put(COLUMN_COMMENT, history.getComment());
        values.put(COLUMN_TYPE, history.getType());
        values.put(COLUMN_DATE, dateFormat.format(history.getDate()));
        db.insert(TABLE_NAME, null, values);
    }

    public int countHistory(int sessionId) {
        Cursor res= db.rawQuery("select COUNT(*) from " + TABLE_NAME + " where " + COLUMN_SESSION_ID + " = '" + sessionId + "';", null);
        res.moveToFirst();
        int count= res.getInt(0);
        res.close();

        return count;
    }

    //Get all history from the database
    public ArrayList<SessionHistory> getAllHistory(int sessionId) {
        ArrayList<SessionHistory> histories = new ArrayList<>();

        Cursor res = db.rawQuery("select * from " + TABLE_NAME + " where " + COLUMN_SESSION_ID + " = '" + sessionId + "';", null);
        res.moveToFirst();

        while(!res.isAfterLast()){
            SessionHistory history = new SessionHistory();
            history.setId(res.getInt(res.getColumnIndex(COLUMN_ID)));
            history.setSessionId(res.getInt(res.getColumnIndex(COLUMN_SESSION_ID)));
            history.setComment(res.getString(res.getColumnIndex(COLUMN_COMMENT)));
            history.setType(res.getInt(res.getColumnIndex(COLUMN_TYPE)));
            try {
                history.setDate(dateFormat.parse(res.getString(res.getColumnIndex(COLUMN_DATE))));
            } catch (Exception e) {
                Log.d("date-parser", "Fail to parse date : " + e.toString());
            }
            histories.add(history);
            res.moveToNext();
        }
        res.close();

        return histories;
    }

    //Delete an history from a database
    public void deleteHistory(SessionHistory history) {
        db.delete(TABLE_NAME, "id = ?", new String[]{Integer.toString(history.getId())});
    }
}
