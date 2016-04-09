package edu.csulb.android.androidscoretracker;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class LoadingActivity extends AppCompatActivity {

    private GameSessionDatabaseManager dbSession;
    private GameDatabaseManager dbGame;
    private Toolbar toolbar;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private DrawerLayout drawerLayout;
    private ListView listMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbSession = new GameSessionDatabaseManager();
        dbGame = new GameDatabaseManager();

        toolbar = (Toolbar) findViewById(R.id.customToolbar);
        toolbar.setTitleTextColor(0xFFFFFFFF);

        drawerLayout = (DrawerLayout) findViewById(R.id.navigation_drawer);
        setSupportActionBar(toolbar);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.app_name,R.string.app_name);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        listMenu = (ListView)findViewById(R.id.left_drawer);
        ArrayList<NavigationItem> list = new ArrayList<>();
        list.add(new NavigationItem(getString(R.string.session_list), R.mipmap.ic_list_black));
        list.add(new NavigationItem(getString(R.string.add_new_game), R.mipmap.ic_add_circle_outline_black));
        list.add(new NavigationItem(getString(R.string.add_new_session), R.mipmap.ic_add_circle_outline_black));
        listMenu.setAdapter(new MenuListAdapter(getApplicationContext(), list));
        listMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        drawerLayout.closeDrawer(Gravity.LEFT);
                        GameSessionListFragment fragmentS1 = GameSessionListFragment.newInstance();
                        getFragmentManager().beginTransaction().replace(R.id.main_activity, fragmentS1).commit();
                        break;
                    case 1:
                        drawerLayout.closeDrawer(Gravity.LEFT);
                        GameDialog.showDialog(LoadingActivity.this);
                        break;
                    case 2:
                        drawerLayout.closeDrawer(Gravity.LEFT);
                        GameSessionDialog.showDialog(LoadingActivity.this);
                        break;
                }
            }
        });

        GameSessionListFragment fragmentS1 = GameSessionListFragment.newInstance();
        getFragmentManager().beginTransaction().replace(R.id.main_activity, fragmentS1).commit();

        //SQLiteDatabase.deleteDatabase(new File("/data/data/edu.csulb.android.androidscoretracker/ScoreTracker.db")); //-> to delete all the database
    }
}
