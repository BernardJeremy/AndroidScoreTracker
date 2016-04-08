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

    private GameDatabaseManager dbGame = new GameDatabaseManager();

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

        TextView sessionNameText = (TextView) rowView.findViewById(R.id.session_name);
        TextView gameNameText = (TextView) rowView.findViewById(R.id.game_name);
        TextView scoresLabelsText = (TextView) rowView.findViewById(R.id.scores_labels);
        TextView scoresValuesText = (TextView) rowView.findViewById(R.id.scores_values);

        sessionNameText.setText(gameSessions[position].getName());
        gameNameText.setText(dbGame.getGame(gameSessions[position].getGameId()).getName());
        scoresLabelsText.setText(gameSessions[position].getNbDraw() >= 0 ? "W / D / L" : "W / L");

        String scoreValues = String.valueOf(gameSessions[position].getNbWin()) + " / ";
        if (gameSessions[position].getNbDraw() >= 0) {
            scoreValues += gameSessions[position].getNbDraw() + " / ";
        }
        scoreValues += gameSessions[position].getNbLoose();

        scoresValuesText.setText(scoreValues);

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
