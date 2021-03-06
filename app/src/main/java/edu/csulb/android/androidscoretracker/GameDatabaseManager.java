package edu.csulb.android.androidscoretracker;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class GameDatabaseManager {

    private SQLiteDatabase db;
    private GameSessionDatabaseManager dbSession;

    private final String TABLE_NAME = "game";
    private final String COLUMN_ID = "id";
    private final String COLUMN_NAME = "name";

    public GameDatabaseManager () {
        db = DatabaseManager.getInstance().getDb();
        dbSession = new GameSessionDatabaseManager(this);
    }

    //add a game in the database
    public void addGame(Game game) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, game.getName());
        db.insert(TABLE_NAME, null, values);
    }

    //Get a game from database by the name
    public Game getGame(String name) {
        Cursor res = db.rawQuery("select * from " + TABLE_NAME + " where " + COLUMN_NAME + " = '" + name + "';", null);
        res.moveToFirst();

        Game game = new Game();
        game.setId(res.getInt(res.getColumnIndex(COLUMN_ID)));
        game.setName(res.getString(res.getColumnIndex(COLUMN_NAME)));
        game.setSessions(dbSession.getAllGameSessionsFromGameId(game.getId()));
        res.close();

        return game;
    }

    //Get a game from database by the id
    public Game getGame(int id) {
        Cursor res = db.rawQuery("select * from " + TABLE_NAME + " where " + COLUMN_ID + " = " + id + ";", null);
        res.moveToFirst();

        Game game = new Game();
        game.setId(res.getInt(res.getColumnIndex(COLUMN_ID)));
        game.setName(res.getString(res.getColumnIndex(COLUMN_NAME)));
        game.setSessions(dbSession.getAllGameSessionsFromGameId(game.getId()));
        res.close();

        return game;
    }

    public int countGame() {
        Cursor res= db.rawQuery("select count(*) from " + TABLE_NAME + ";", null);
        res.moveToFirst();
        int count= res.getInt(0);
        res.close();

        return count;
    }

    public Game getLastGame() {
        Cursor res = db.rawQuery("select * from " + TABLE_NAME + " order by " + COLUMN_ID + " DESC LIMIT 1;", null);
        res.moveToFirst();

        Game game = new Game();
        game.setId(res.getInt(res.getColumnIndex(COLUMN_ID)));
        game.setName(res.getString(res.getColumnIndex(COLUMN_NAME)));
        game.setSessions(dbSession.getAllGameSessionsFromGameId(game.getId()));
        res.close();

        return game;
    }

    //Get all games from the database
    public ArrayList<Game> getAllGames() {
        ArrayList<Game> games = new ArrayList<>();

        Cursor res = db.rawQuery("select * from " + TABLE_NAME + ";", null);
        res.moveToFirst();

        while(!res.isAfterLast()){
            Game game = new Game();
            game.setId(res.getInt(res.getColumnIndex(COLUMN_ID)));
            game.setName(res.getString(res.getColumnIndex(COLUMN_NAME)));
            game.setSessions(dbSession.getAllGameSessionsFromGameId(game.getId()));
            games.add(game);
            res.moveToNext();
        }
        res.close();

        return games;
    }

    //Update a game in the database
    public void updateGame(Game game) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, game.getName());
        db.update(TABLE_NAME, values, "id = ?", new String[]{Integer.toString(game.getId())});
    }

    //Delete a game from a database
    public void deleteGame(Game game) {
        db.delete(TABLE_NAME, "id = ?", new String[]{Integer.toString(game.getId())});
    }
}
