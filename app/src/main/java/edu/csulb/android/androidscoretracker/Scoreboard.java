package edu.csulb.android.androidscoretracker;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class Scoreboard extends Fragment {

    public static final int COMMENT = 1;

    private View view;
    private Button winButton;
    private Button drawButton;
    private Button looseButton;

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.scoreboard_fragment, container, false);

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

        winButton = (Button) view.findViewById(R.id.win_score_button);
        winButton.setText(String.valueOf(gameSession.getNbWin()));
        winButton.setOnClickListener(winButtonClickListener);
        winButton.setOnLongClickListener(winButtonLongClickListener);

        drawButton = (Button) view.findViewById(R.id.draw_score_button);
        drawButton.setText(String.valueOf(gameSession.getNbDraw()));
        drawButton.setOnClickListener(drawButtonClickListener);
        drawButton.setOnLongClickListener(drawButtonLongClickListener);

        looseButton = (Button) view.findViewById(R.id.loose_score_button);
        looseButton.setText(String.valueOf(gameSession.getNbLoose()));
        looseButton.setOnClickListener(looseButtonClickListener);
        looseButton.setOnLongClickListener(looseButtonLongClickListener);

        return view;
    }

    // Result of Comment Dialog
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode == COMMENT) {
            gameSession.setComment(data.getExtras().get("Comment").toString());
            dbSession.updateGameSession(gameSession);
        }
    }

    private void updateWin() {
        gameSession.setNbWin(gameSession.getNbWin() + 1);
        dbSession.updateGameSession(gameSession);
        winButton.setText(String.valueOf(gameSession.getNbWin()));
    }

    private void updateDraw() {
        gameSession.setNbDraw(gameSession.getNbDraw() + 1);
        dbSession.updateGameSession(gameSession);
        drawButton.setText(String.valueOf(gameSession.getNbDraw()));
    }

    private void updateLoose() {
        gameSession.setNbLoose(gameSession.getNbLoose() + 1);
        dbSession.updateGameSession(gameSession);
        looseButton.setText(String.valueOf(gameSession.getNbLoose()));
    }

    // Listener click for win button
    private View.OnClickListener winButtonClickListener = new View.OnClickListener(){
        public void onClick(View v){
            updateWin();
        }
    };

    // Listener long click for win button
    private View.OnLongClickListener winButtonLongClickListener = new View.OnLongClickListener() {
        public boolean onLongClick(View v) {
            ScoreboardCommentDialog dialog = ScoreboardCommentDialog.newInstance();
            dialog.setTargetFragment(Scoreboard.this, COMMENT);
            dialog.show(getFragmentManager(), "Scoreboard comment");
            updateWin();
            return true;
        }
    };

    // Listener click for draw button
    private View.OnClickListener drawButtonClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            updateDraw();
        }
    };

    // Listener long click for draw button
    private View.OnLongClickListener drawButtonLongClickListener = new View.OnLongClickListener() {
        public boolean onLongClick(View v) {
            ScoreboardCommentDialog dialog = ScoreboardCommentDialog.newInstance();
            dialog.show(getFragmentManager(), "Scoreboard comment");
            updateDraw();
            return true;
        }
    };

    // Listener click for loose button
    private View.OnClickListener looseButtonClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            updateLoose();
        }
    };

    // Listener long click for loose button
    private View.OnLongClickListener looseButtonLongClickListener = new View.OnLongClickListener() {
        public boolean onLongClick(View v) {
            ScoreboardCommentDialog dialog = ScoreboardCommentDialog.newInstance();
            dialog.show(getFragmentManager(), "Scoreboard comment");
            updateLoose();
            return true;
        }
    };
}
