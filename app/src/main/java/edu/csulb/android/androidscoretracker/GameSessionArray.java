package edu.csulb.android.androidscoretracker;

import android.app.ListFragment;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class GameSessionArray extends ArrayAdapter<GameSession>{
    private final Context context;
    private final GameSession[] gameSessions;

    public GameSessionArray(Context context, GameSession[] values) {
        super(context, -1, values);
        this.context = context;
        gameSessions =values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.game_session_row, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.name);
        textView.setText(gameSessions[position].getName());
        return rowView;
    }


    public GameSession[] getGameSessions() {
        return gameSessions;
    }

    public GameSession getOneGameSession(String name) {
        for (GameSession gameSession:gameSessions) {
            if (gameSession.getName() != null && gameSession.getName().equals(name))
                return gameSession;
        }
        return null;
    }

    public GameSession getOneGameSession(int id) {
        for (GameSession gameSession:gameSessions) {
            if (gameSession.getId() == id)
                return gameSession;
        }
        return null;
    }
}
