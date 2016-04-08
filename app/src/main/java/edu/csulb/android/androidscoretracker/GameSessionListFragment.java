package edu.csulb.android.androidscoretracker;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class GameSessionListFragment extends ListFragment {

    private GameDatabaseManager dbGame = new GameDatabaseManager();
    private GameSessionDatabaseManager dbSession = new GameSessionDatabaseManager();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.game_session_list_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayList<GameSession> gameSessions = dbSession.getAllGameSessions();
        GameSession[] values = gameSessions.toArray(new GameSession[gameSessions.size()]);
        GameSessionArray adapter = new GameSessionArray(getActivity(), values);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // TODO implement some logic
    }
}
