package edu.csulb.android.androidscoretracker;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class GameSessionDatabaseManager {

    private SQLiteDatabase db;
    private GameDatabaseManager dbGame;

    private SimpleDateFormat dateFormat;

    private final String TABLE_NAME = "gameSession";
    private final String COLUMN_ID = "id";
    private final String COLUMN_NAME = "name";
    private final String COLUMN_GAME_ID = "gameId";
    private final String COLUMN_NB_WIN = "nbWin";
    private final String COLUMN_NB_LOOSE = "nbLoose";
    private final String COLUMN_NB_DRAW = "nbDraw";
    private final String COLUMN_START_DATE = "startDate";
    private final String COLUMN_END_DATE = "endDate";
    private final String COLUMN_IS_ACTIVE = "isActive";
    private final String COLUMN_COMMENT = "comment";

    public GameSessionDatabaseManager() {
        db = DatabaseManager.getInstance().getDb();
        dbGame = new GameDatabaseManager();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }

    //add a game in the database
    public void addGameSession(GameSession gameSession) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, gameSession.getName());
        values.put(COLUMN_GAME_ID, gameSession.getGameId());
        values.put(COLUMN_NB_WIN, gameSession.getNbWin());
        values.put(COLUMN_NB_LOOSE, gameSession.getNbLoose());
        values.put(COLUMN_NB_DRAW, gameSession.getNbDraw());
        values.put(COLUMN_START_DATE, (gameSession.getStartDate() == null ? null : dateFormat.format(gameSession.getStartDate())));
        values.put(COLUMN_END_DATE, (gameSession.getEndDate() == null ? null : dateFormat.format(gameSession.getEndDate())));
        values.put(COLUMN_IS_ACTIVE, gameSession.getIsActive());
        values.put(COLUMN_COMMENT, gameSession.getComment());
        db.insert(TABLE_NAME, null, values);
    }

    //Get a game from database by the name
    public GameSession getGameSession(String name) {
        Cursor res = db.rawQuery("select * from " + TABLE_NAME + " where " + COLUMN_NAME + " = '" + name + "';", null);
        res.moveToFirst();

        GameSession gameSession = new GameSession();
        gameSession.setId(res.getInt(res.getColumnIndex(COLUMN_ID)));
        gameSession.setName(res.getString(res.getColumnIndex(COLUMN_NAME)));
        gameSession.setGameId(res.getInt(res.getColumnIndex(COLUMN_GAME_ID)));
        gameSession.setNbWin(res.getInt(res.getColumnIndex(COLUMN_NB_WIN)));
        gameSession.setNbLoose(res.getInt(res.getColumnIndex(COLUMN_NB_LOOSE)));
        gameSession.setNbDraw(res.getInt(res.getColumnIndex(COLUMN_NB_DRAW)));
        gameSession.setIsActive(res.getInt(res.getColumnIndex(COLUMN_IS_ACTIVE)) == 1);
        gameSession.setComment(res.getString(res.getColumnIndex(COLUMN_COMMENT)));
        try {
            gameSession.setStartDate(dateFormat.parse(res.getString(res.getColumnIndex(COLUMN_START_DATE))));
            gameSession.setEndDate(dateFormat.parse(res.getString(res.getColumnIndex(COLUMN_END_DATE))));
        } catch (Exception e) {
            Log.d("date-parser", "Fail to parse date : " + e.toString());
        }
        res.close();

        return gameSession;
    }

    //Get a game from database by the id
    public GameSession getGameSession(int id) {
        Cursor res = db.rawQuery("select * from " + TABLE_NAME + " where " + COLUMN_ID + " = " + id + ";", null);
        res.moveToFirst();

        GameSession gameSession = new GameSession();
        gameSession.setId(res.getInt(res.getColumnIndex(COLUMN_ID)));
        gameSession.setName(res.getString(res.getColumnIndex(COLUMN_NAME)));
        gameSession.setGameId(res.getInt(res.getColumnIndex(COLUMN_GAME_ID)));
        gameSession.setNbWin(res.getInt(res.getColumnIndex(COLUMN_NB_WIN)));
        gameSession.setNbLoose(res.getInt(res.getColumnIndex(COLUMN_NB_LOOSE)));
        gameSession.setNbDraw(res.getInt(res.getColumnIndex(COLUMN_NB_DRAW)));
        gameSession.setIsActive(res.getInt(res.getColumnIndex(COLUMN_IS_ACTIVE)) == 1);
        gameSession.setComment(res.getString(res.getColumnIndex(COLUMN_COMMENT)));
        try {
            gameSession.setStartDate(dateFormat.parse(res.getString(res.getColumnIndex(COLUMN_START_DATE))));
            gameSession.setEndDate(dateFormat.parse(res.getString(res.getColumnIndex(COLUMN_END_DATE))));
        } catch (Exception e) {
            Log.d("date-parser", "Fail to parse date : " + e.toString());
        }
        res.close();

        return gameSession;
    }

    //Get all games from the database
    public ArrayList<GameSession> getAllGameSessions() {
        return getAllGameSessions(false);
    }

    //Get all games from the database
    public ArrayList<GameSession> getAllGameSessions(boolean isActive) {
        ArrayList<GameSession> gameSessions = new ArrayList<>();
        String whereClause = "1 = 1";
        if (isActive) {
            whereClause  = COLUMN_IS_ACTIVE + " = 1";
        }
        Cursor res = db.rawQuery("select * from " + TABLE_NAME + " WHERE " + whereClause + ";", null);
        res.moveToFirst();

        while(!res.isAfterLast()){
            GameSession gameSession = new GameSession();
            gameSession.setId(res.getInt(res.getColumnIndex(COLUMN_ID)));
            gameSession.setName(res.getString(res.getColumnIndex(COLUMN_NAME)));
            gameSession.setGameId(res.getInt(res.getColumnIndex(COLUMN_GAME_ID)));
            gameSession.setNbWin(res.getInt(res.getColumnIndex(COLUMN_NB_WIN)));
            gameSession.setNbLoose(res.getInt(res.getColumnIndex(COLUMN_NB_LOOSE)));
            gameSession.setNbDraw(res.getInt(res.getColumnIndex(COLUMN_NB_DRAW)));
            String startDate = res.getString(res.getColumnIndex(COLUMN_START_DATE));
            String endDate = res.getString(res.getColumnIndex(COLUMN_END_DATE));
            gameSession.setIsActive(res.getInt(res.getColumnIndex(COLUMN_IS_ACTIVE)) == 1);
            gameSession.setComment(res.getString(res.getColumnIndex(COLUMN_COMMENT)));
            try {
                gameSession.setStartDate((dateFormat.parse(startDate)));
                gameSession.setEndDate((dateFormat.parse(endDate)));
            } catch (Exception e) {
                Log.d("date-parser", "Fail to parse date : " + e.toString());
            }
            Date toomorow = new Date();
            toomorow.setTime(toomorow.getTime() + 86400000);
            if (gameSession.getStartDate().before(toomorow)) {
                if (gameSession.getEndDate() == null || (gameSession.getEndDate() != null && gameSession.getEndDate().after(new Date())))
                gameSessions.add(gameSession);
            }
            res.moveToNext();
        }
        res.close();

        return gameSessions;
    }

    //Get all games from the database with a given game name
    public ArrayList<GameSession> getAllGameSessionsFromGameName(String gameName) {
        return this.getAllGameSessionsFromGameId(dbGame.getGame(gameName).getId());
    }

    //Get all games from the database with a given gameId
    public ArrayList<GameSession> getAllGameSessionsFromGameId(int gameId) {
        ArrayList<GameSession> gameSessions = new ArrayList<>();

        Cursor res = db.rawQuery("select * from " + TABLE_NAME + " WHERE " + COLUMN_GAME_ID + " = " + gameId+ ";", null);
        res.moveToFirst();

        while(!res.isAfterLast()){
            GameSession gameSession = new GameSession();
            gameSession.setId(res.getInt(res.getColumnIndex(COLUMN_ID)));
            gameSession.setName(res.getString(res.getColumnIndex(COLUMN_NAME)));
            gameSession.setGameId(res.getInt(res.getColumnIndex(COLUMN_GAME_ID)));
            gameSession.setNbWin(res.getInt(res.getColumnIndex(COLUMN_NB_WIN)));
            gameSession.setNbLoose(res.getInt(res.getColumnIndex(COLUMN_NB_LOOSE)));
            gameSession.setNbDraw(res.getInt(res.getColumnIndex(COLUMN_NB_DRAW)));
            String startDate = res.getString(res.getColumnIndex(COLUMN_START_DATE));
            String endDate = res.getString(res.getColumnIndex(COLUMN_END_DATE));
            gameSession.setIsActive(res.getInt(res.getColumnIndex(COLUMN_IS_ACTIVE)) == 1);
            gameSession.setComment(res.getString(res.getColumnIndex(COLUMN_COMMENT)));
            try {
                gameSession.setStartDate((dateFormat.parse(startDate)));
                gameSession.setEndDate((dateFormat.parse(endDate)));
            } catch (Exception e) {
                Log.d("date-parser", "Fail to parse date : " + e.toString());
            }
            gameSessions.add(gameSession);
            res.moveToNext();
        }
        res.close();

        return gameSessions;
    }

    //Update a game in the database
    public void updateGameSession(GameSession gameSession) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, gameSession.getName());
        values.put(COLUMN_GAME_ID, gameSession.getGameId());
        values.put(COLUMN_NB_WIN, gameSession.getNbWin());
        values.put(COLUMN_NB_LOOSE, gameSession.getNbLoose());
        values.put(COLUMN_NB_DRAW, gameSession.getNbDraw());
        values.put(COLUMN_IS_ACTIVE, gameSession.getIsActive());
        values.put(COLUMN_COMMENT, gameSession.getComment());
        values.putNull(COLUMN_START_DATE);
        if (gameSession.getStartDate() != null) {
            values.put(COLUMN_START_DATE, dateFormat.format(gameSession.getStartDate()));
        }
        values.putNull(COLUMN_END_DATE);
        if (gameSession.getEndDate() != null) {
            values.put(COLUMN_END_DATE, dateFormat.format(gameSession.getEndDate()));
        }
        db.update(TABLE_NAME, values, "id = ?", new String[] {Integer.toString(gameSession.getId())});
    }

    //Delete a game from a database
    public void deleteGameSession(GameSession gameSession) {
        db.delete(TABLE_NAME, "id = ?", new String[]{Integer.toString(gameSession.getId())});
    }

    public SimpleDateFormat getDateFormat() {
        return dateFormat;
    }
}
