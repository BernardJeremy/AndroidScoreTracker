package edu.csulb.android.androidscoretracker;

//import android.app.Fragment;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Scoreboard extends Fragment {

    private GameDatabaseManager dbGame = new GameDatabaseManager();
    private GameSessionDatabaseManager dbSession = new GameSessionDatabaseManager();
    private GameSession gameSession;

    public static Scoreboard newInstance(int idSession) {
        Scoreboard scoreboard = new Scoreboard();
        Bundle bundle = new Bundle();
        bundle.putInt("idSession", idSession);
        scoreboard.setArguments(bundle);
        return scoreboard;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.scoreboard_fragment, container, false);

        Integer idSession = getArguments().getInt("idSession");
        gameSession = dbSession.getGameSession(idSession);
        Game game = dbGame.getGame(gameSession.getGameId());

        TextView gameName = (TextView) view.findViewById(R.id.scoreboard_game_name);
        gameName.setText(game.getName());
        TextView sessionName = (TextView) view.findViewById(R.id.scoreboard_session_name);
        sessionName.setText(gameSession.getName());
        TextView startDate = (TextView) view.findViewById(R.id.session_date_start);
        startDate.setText(gameSession.getStartDate().toString());
        TextView endDate = (TextView) view.findViewById(R.id.session_date_end);
        endDate.setText(gameSession.getStartDate().toString());
        final Button winButton = (Button) view.findViewById(R.id.win_score_button);
        winButton.setText(String.valueOf(gameSession.getNbWin()));
        final Button drawButton = (Button) view.findViewById(R.id.draw_score_button);
        drawButton.setText(String.valueOf(gameSession.getNbDraw()));
        final Button looseButton = (Button) view.findViewById(R.id.loose_score_button);
        looseButton.setText(String.valueOf(gameSession.getNbLoose()));

        winButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                gameSession.setNbWin(gameSession.getNbWin() + 1);
                dbSession.updateGameSession(gameSession);
                winButton.setText(String.valueOf(gameSession.getNbWin()));
            }
        });
        winButton.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
                Toast.makeText(getContext(), "Long Click", Toast.LENGTH_LONG).show();
                return true;
            }
        });

        drawButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                gameSession.setNbDraw(gameSession.getNbDraw() + 1);
                dbSession.updateGameSession(gameSession);
                drawButton.setText(String.valueOf(gameSession.getNbDraw()));
            }
        });
        drawButton.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
                Toast.makeText(getContext(), "Long Click", Toast.LENGTH_LONG).show();
                return true;
            }
        });

        looseButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                gameSession.setNbLoose(gameSession.getNbLoose() + 1);
                dbSession.updateGameSession(gameSession);
                looseButton.setText(String.valueOf(gameSession.getNbLoose()));
            }
        });
        looseButton.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
                Toast.makeText(getContext(), "Long Click", Toast.LENGTH_LONG).show();
                return true;
            }
        });

        return view;
    }
}
