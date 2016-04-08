package edu.csulb.android.androidscoretracker;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

public class LoadingActivity extends FragmentActivity {

    private GameSessionDatabaseManager dbSession;
    private GameDatabaseManager dbGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbSession = new GameSessionDatabaseManager();
        dbGame = new GameDatabaseManager();

        GameSessionListFragment fragmentS1 = new GameSessionListFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.test, fragmentS1).commit();


        //SQLiteDatabase.deleteDatabase(new File("/data/data/edu.csulb.android.androidscoretracker/ScoreTracker.db")); //-> to delete all the database
        //GameSessionDialog.showDialog(this);
        //GameDialog.showDialog(this);
    }


}
