package edu.csulb.android.androidscoretracker;

import android.database.sqlite.SQLiteDatabase;


public class DatabaseManager {

    private static DatabaseManager instance = null;

    private SQLiteDatabase db;
    private final String DATABASE_NAME = "ScoreTracker.db";
    private final String DATABASE_PATH = "/data/data/edu.csulb.android.androidscoretracker/";
    private final String CREATE_GAME_TABLE = "CREATE TABLE IF NOT EXISTS game(id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR);";
    private final String CREATE_SESSION_TABLE = "CREATE TABLE IF NOT EXISTS gameSession(id INTEGER PRIMARY KEY AUTOINCREMENT, gameId INTEGER, name VARCHAR, nbWin INTEGER, nbLoose INTEGER, nbDraw INTEGER, startDate DATE, endDate DATE, FOREIGN KEY(gameId) REFERENCES game(gameId));";

    //open or create the database, if it does not exist
    private DatabaseManager() {
        db = SQLiteDatabase.openOrCreateDatabase(DATABASE_PATH + DATABASE_NAME, null);
        db.execSQL(CREATE_GAME_TABLE);
        db.execSQL(CREATE_SESSION_TABLE);
    }

    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }

        return instance;
    }

    public SQLiteDatabase getDb() {
        return db;
    }
}