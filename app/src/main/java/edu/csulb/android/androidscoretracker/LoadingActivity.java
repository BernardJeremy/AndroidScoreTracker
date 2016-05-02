package edu.csulb.android.androidscoretracker;

import android.app.FragmentManager;
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
    private GameDatabaseManager gameDb = new GameDatabaseManager();
    private ArrayList<NavigationItem> itemsList;
    private MenuListAdapter menuAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbGame = new GameDatabaseManager();
        dbSession = new GameSessionDatabaseManager(dbGame);

        toolbar = (Toolbar) findViewById(R.id.customToolbar);
        toolbar.setTitleTextColor(0xFFFFFFFF);

        drawerLayout = (DrawerLayout) findViewById(R.id.navigation_drawer);
        setSupportActionBar(toolbar);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.app_name,R.string.app_name);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        ArrayList<Game> gameList = gameDb.getAllGames();
        itemsList = new ArrayList<>();
        itemsList.add(new NavigationItem(getString(R.string.session_list), R.mipmap.ic_list_black));
        itemsList.add(new NavigationItem(getString(R.string.add_new_game), R.mipmap.ic_add_circle_outline_black));
        itemsList.add(new NavigationItem(getString(R.string.add_new_session), R.mipmap.ic_add_circle_outline_black));
        for (Game game : gameList) {
            itemsList.add(new NavigationItem(game.getName(), R.mipmap.ic_games_black));
        }
        listMenu = (ListView)findViewById(R.id.left_drawer);
        menuAdapter = new MenuListAdapter(getApplicationContext(), itemsList);
        listMenu.setAdapter(menuAdapter);
        listMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        drawerLayout.closeDrawer(Gravity.LEFT);
                        GameSessionListFragment fragmentS1 = GameSessionListFragment.newInstance();
                        getFragmentManager().beginTransaction().replace(R.id.main_activity, fragmentS1).addToBackStack(null).commit();
                        break;
                    case 1:
                        drawerLayout.closeDrawer(Gravity.LEFT);
                        GameDialog.showDialog(LoadingActivity.this);
                        break;
                    case 2:
                        drawerLayout.closeDrawer(Gravity.LEFT);
                        GameSessionDialog.showDialog(LoadingActivity.this);
                        break;
                    default:
                        Game chosenGame = gameDb.getGame(itemsList.get(position).getTitle());
                        drawerLayout.closeDrawer(Gravity.LEFT);
                        GameSessionListFragment fragmentS2 = GameSessionListFragment.newInstance(chosenGame.getName());
                        getFragmentManager().beginTransaction().replace(R.id.main_activity, fragmentS2).addToBackStack(null).commit();
                        break;
                }
            }
        });

        GameSessionListFragment fragmentS1 = GameSessionListFragment.newInstance();
        getFragmentManager().beginTransaction().add(R.id.main_activity, fragmentS1).addToBackStack(null).commit();

        //SQLiteDatabase.deleteDatabase(new File("/data/data/edu.csulb.android.androidscoretracker/ScoreTracker.db")); //-> to delete all the database
    }

    public void updateNavigationDrawer() {
        Game lastGame = gameDb.getLastGame();
        itemsList.add(new NavigationItem(lastGame.getName(), R.mipmap.ic_games_black));
        menuAdapter.setNewData(itemsList);
        menuAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed(){
        FragmentManager fm = getFragmentManager();
        if (fm.getBackStackEntryCount() > 1) {
            fm.popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}
