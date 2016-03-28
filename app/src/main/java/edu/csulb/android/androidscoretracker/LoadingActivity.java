package edu.csulb.android.androidscoretracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LoadingActivity extends AppCompatActivity {

    private DatabaseInterface db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DatabaseInterface();
        //SQLiteDatabase.deleteDatabase(new File("/data/data/edu.csulb.android.androidscoretracker/ScoreTracker.db")); -> to delete all the database
    }
}
