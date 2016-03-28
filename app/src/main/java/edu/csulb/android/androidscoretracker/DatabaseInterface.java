package edu.csulb.android.androidscoretracker;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class DatabaseInterface {

    private SQLiteDatabase db;
    private final String DATABASE_NAME = "ScoreTracker.db";
    private final String DATABASE_PATH = "/data/data/edu.csulb.android.androidscoretracker/";
    private final String CREATE_GAME_TABLE = "CREATE TABLE IF NOT EXISTS game(gameId INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR);";
    private final String CREATE_SESSION_TABLE = "CREATE TABLE IF NOT EXISTS gameSession(sessionId INTEGER PRIMARY KEY AUTOINCREMENT, gameId INTEGER, nbWin VARCHAR, nbLose VARCHAR, isDrawPossible BOOLEAN, nbDraw VARCHAR, startDate DATE, endDate DATE, FOREIGN KEY(gameId) REFERENCES game(gameId));";
    private final String GAME_TABLE_NAME = "game";
    private final String GAME_COLUMN_NAME = "name";
    private final String GAME_COLUMN_ID = "gameId";

    //open or create the database, if it does not exist
    public DatabaseInterface() {
        db = SQLiteDatabase.openOrCreateDatabase(DATABASE_PATH + DATABASE_NAME, null);
        db.execSQL(CREATE_GAME_TABLE);
        db.execSQL(CREATE_SESSION_TABLE);
    }

    //add a game in the database
    public void addGame(String name) {
        ContentValues values = new ContentValues();
        values.put(GAME_COLUMN_NAME, name);
        db.insert(GAME_TABLE_NAME, null, values);
    }

    //Get a game from database by the name
    public Game getGame(String name) {
        Cursor res = db.rawQuery("select * from game where name = '" + name + "';", null);
        res.moveToFirst();

        Game game = new Game();
        game.setGameId(res.getInt(res.getColumnIndex(GAME_COLUMN_ID)));
        game.setGameName(res.getString(res.getColumnIndex(GAME_COLUMN_NAME)));
        return game;
    }

    //Get a game from database by the id
    public Game getGame(int id) {
        Cursor res = db.rawQuery("select * from game where gameId = " + id + ";", null);
        res.moveToFirst();

        Game game = new Game();
        game.setGameId(res.getInt(res.getColumnIndex(GAME_COLUMN_ID)));
        game.setGameName(res.getString(res.getColumnIndex(GAME_COLUMN_NAME)));
        return game;
    }

    //Get all games from the database
    public ArrayList<Game> getGames() {
        ArrayList<Game> games = new ArrayList<>();

        Cursor res = db.rawQuery("select * from game;", null);
        res.moveToFirst();

        while(!res.isAfterLast()){
            Game game = new Game();
            game.setGameId(res.getInt(res.getColumnIndex(GAME_COLUMN_ID)));
            game.setGameName(res.getString(res.getColumnIndex(GAME_COLUMN_NAME)));
            games.add(game);
            res.moveToNext();
        }
        return games;
    }

    //Update a game in the database
    public void updateGame(Game game) {
        ContentValues values = new ContentValues();
        values.put(GAME_COLUMN_NAME, game.getGameName());
        db.update(GAME_TABLE_NAME, values, "id = ?", new String[] {Integer.toString(game.getGameId())});
    }

    //Delete a game from a database
    public void deleteGame(Game game) {
        db.delete(GAME_TABLE_NAME, "id = ?", new String[] {Integer.toString(game.getGameId())});
    }
}
