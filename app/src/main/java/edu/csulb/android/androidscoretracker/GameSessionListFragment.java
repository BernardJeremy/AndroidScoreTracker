package edu.csulb.android.androidscoretracker;

import android.os.Bundle;
import android.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

public class GameSessionListFragment extends ListFragment {

    private GameSession[] gameSessions;
    private GameSessionDatabaseManager dbSession = new GameSessionDatabaseManager();

    public static GameSessionListFragment newInstance() {
        return new GameSessionListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.game_session_list_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayList<GameSession> gameSessions = dbSession.getAllGameSessions();
        this.gameSessions = gameSessions.toArray(new GameSession[gameSessions.size()]);
        GameSessionArray adapter = new GameSessionArray(getActivity(), this.gameSessions);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Scoreboard scoreboard = Scoreboard.newInstance(this.gameSessions[position].getId());
        getFragmentManager().beginTransaction().replace(R.id.main_activity, scoreboard).addToBackStack(null).commit();
    }
}
