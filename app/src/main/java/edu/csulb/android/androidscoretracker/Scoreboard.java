package edu.csulb.android.androidscoretracker;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Scoreboard extends Fragment {

    public static final int COMMENT = 1;

    private View view;
    private Button closeSessionButton;
    private Button winButton;
    private Button drawButton;
    private Button looseButton;

    private GameDatabaseManager dbGame = new GameDatabaseManager();
    private GameSessionDatabaseManager dbSession = new GameSessionDatabaseManager(dbGame);
    private GameSession gameSession;
    private SessionHistoryDatabaseManager dbHistory = new SessionHistoryDatabaseManager();
    private SessionHistory sessionHistory;

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
        SpannableString gameNameSpan = new SpannableString(game.getName());
        gameNameSpan.setSpan(new UnderlineSpan(), 0, gameNameSpan.length(), 0);
        gameName.setText(gameNameSpan);

        TextView sessionName = (TextView) view.findViewById(R.id.scoreboard_session_name);
        sessionName.setText(gameSession.getName());

        TextView startDateLabel = (TextView) view.findViewById(R.id.label_session_date_start);
        SpannableString startDateLabelSpan = new SpannableString("Start date : ");
        startDateLabelSpan.setSpan(new UnderlineSpan(), 0, startDateLabelSpan.length(), 0);
        startDateLabel.setText(startDateLabelSpan);
        TextView startDate = (TextView) view.findViewById(R.id.session_date_start);
        if (gameSession.getStartDate() != null) {
            startDate.setText(new SimpleDateFormat("MM-dd-yyyy").format(gameSession.getStartDate()));
        }
        TextView endDate = (TextView) view.findViewById(R.id.session_date_end);
        if (gameSession.getEndDate() != null) {
            TextView endDateLabel = (TextView) view.findViewById(R.id.label_session_date_end);
            SpannableString endDateLabelSpan = new SpannableString("End date : ");
            endDateLabelSpan.setSpan(new UnderlineSpan(), 0, endDateLabelSpan.length(), 0);
            endDateLabel.setText(endDateLabelSpan);

            endDate.setText(new SimpleDateFormat("MM-dd-yyyy").format(gameSession.getEndDate()));
            Date dateEnd = gameSession.getEndDate();
            Date dateNow = new Date();
            String dateEndFormat = new SimpleDateFormat("MM-dd-yyyy").format(dateEnd);
            String dateNowFormat = new SimpleDateFormat("MM-dd-yyyy").format(dateNow);
            if (dateEndFormat.contentEquals(dateNowFormat) || dateEnd.before(dateNow)) {
                gameSession.setIsActive(false);
                dbSession.updateGameSession(gameSession);
            }
        }

        closeSessionButton = (Button) view.findViewById(R.id.close_session);
        if (gameSession.getIsActive()) {
            closeSessionButton.setOnClickListener(closeSessionButtonClickListener);
        } else {
            closeSessionButton.setVisibility(View.GONE);
        }

        winButton = (Button) view.findViewById(R.id.win_score_button);
        winButton.setText(String.valueOf(gameSession.getNbWin()));
        winButton.setOnClickListener(winButtonClickListener);
        winButton.setOnLongClickListener(winButtonLongClickListener);

        drawButton = (Button) view.findViewById(R.id.draw_score_button);
        if (gameSession.getNbDraw() > -1) {
            drawButton.setText(String.valueOf(gameSession.getNbDraw()));
            drawButton.setOnClickListener(drawButtonClickListener);
            drawButton.setOnLongClickListener(drawButtonLongClickListener);
        } else {
            TextView drawTitleButton = (TextView) view.findViewById(R.id.title_draw);
            drawTitleButton.setVisibility(View.GONE);
            drawButton.setVisibility(View.GONE);
        }

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
            sessionHistory.setComment(data.getExtras().get("Comment").toString());
            dbHistory.updateHistorySession(sessionHistory);
            SessionHistory history = dbHistory.getAllHistory(gameSession.getId()).get(dbHistory.getAllHistory(gameSession.getId()).size() - 1);
            Log.d("HISTORY", "ID : " + history.getId());
            Log.d("HISTORY", "COMMENT : " + history.getComment());
            Log.d("HISTORY", "TYPE : " + history.getType());
            Log.d("HISTORY", "DATE : " + history.getDate().toString());
        }
    }

    private void updateWin() {
        if (gameSession.getIsActive()) {
            gameSession.setNbWin(gameSession.getNbWin() + 1);
            dbSession.updateGameSession(gameSession);
            winButton.setText(String.valueOf(gameSession.getNbWin()));
            createSessionHistory(SessionHistory.TYPE_WIN);
        }
    }

    private void updateDraw() {
        if (gameSession.getIsActive()) {
            gameSession.setNbDraw(gameSession.getNbDraw() + 1);
            dbSession.updateGameSession(gameSession);
            drawButton.setText(String.valueOf(gameSession.getNbDraw()));
            createSessionHistory(SessionHistory.TYPE_DRAW);
        }
    }

    private void updateLoose() {
        if (gameSession.getIsActive()) {
            gameSession.setNbLoose(gameSession.getNbLoose() + 1);
            dbSession.updateGameSession(gameSession);
            looseButton.setText(String.valueOf(gameSession.getNbLoose()));
            createSessionHistory(SessionHistory.TYPE_LOOSE);
        }
    }

    private void createSessionHistory(int sessionType) {
        sessionHistory = new SessionHistory(0, gameSession.getId(), sessionType, "");
        dbHistory.addHistory(sessionHistory);


        SessionHistory history = dbHistory.getAllHistory(gameSession.getId()).get(dbHistory.getAllHistory(gameSession.getId()).size() - 1);
        Log.d("HISTORY", "ID : " + history.getId());
        Log.d("HISTORY", "COMMENT : " + history.getComment());
        Log.d("HISTORY", "TYPE : " + history.getType());
        Log.d("HISTORY", "DATE : " + history.getDate().toString());
    }

    private View.OnClickListener closeSessionButtonClickListener = new View.OnClickListener() {
        public void onClick(View v){
            gameSession.setIsActive(false);
            gameSession.setEndDate(new Date());
            dbSession.updateGameSession(gameSession);
        }
    };

    // Listener click for win button
    private View.OnClickListener winButtonClickListener = new View.OnClickListener(){
        public void onClick(View v){
            updateWin();
        }
    };

    // Listener long click for win button
    private View.OnLongClickListener winButtonLongClickListener = new View.OnLongClickListener() {
        public boolean onLongClick(View v) {
            if (gameSession.getIsActive()) {
                ScoreboardCommentDialog dialog = ScoreboardCommentDialog.newInstance();
                dialog.setTargetFragment(Scoreboard.this, COMMENT);
                dialog.show(getFragmentManager(), "Scoreboard comment");
                updateWin();
            }
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
            if (gameSession.getIsActive()) {
                ScoreboardCommentDialog dialog = ScoreboardCommentDialog.newInstance();
                dialog.setTargetFragment(Scoreboard.this, COMMENT);
                dialog.show(getFragmentManager(), "Scoreboard comment");
                updateDraw();
            }
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
            if (gameSession.getIsActive()) {
                ScoreboardCommentDialog dialog = ScoreboardCommentDialog.newInstance();
                dialog.setTargetFragment(Scoreboard.this, COMMENT);
                dialog.show(getFragmentManager(), "Scoreboard comment");
                updateLoose();
            }
            return true;
        }
    };
}
