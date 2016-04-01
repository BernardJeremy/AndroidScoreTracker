package edu.csulb.android.androidscoretracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LoadingActivity extends AppCompatActivity {

    private GameSessionDatabaseManager dbSession;
    private GameDatabaseManager dbGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbSession = new GameSessionDatabaseManager();
        dbGame = new GameDatabaseManager();
        //SQLiteDatabase.deleteDatabase(new File("/data/data/edu.csulb.android.androidscoretracker/ScoreTracker.db")); //-> to delete all the database
    }


}
