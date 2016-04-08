package edu.csulb.android.androidscoretracker;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;


public class GameSessionDialog extends DialogFragment implements OnClickListener {

    private EditText sessionNameTxt;
    private Button addButton;
    private Spinner gameSpinner;
    private CheckBox hasDrawButton;
    private EditText startDate;
    private EditText endDate;

    private static GameSessionDatabaseManager dbSession = new GameSessionDatabaseManager();
    private static GameDatabaseManager dbGame = new GameDatabaseManager();

    private GameSession gameSession = null;

    private ArrayAdapter<String> gameNameAdapter;

    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        this.getDialog().setTitle(R.string.add_game_session);

        View view = inflater.inflate(R.layout.game_session_dialog_fragment, container,
                false);

        this.initLayout(view);
        this.fillLayout();

        return view;
    }

    private void initLayout(View view) {
        sessionNameTxt = (EditText) view.findViewById(R.id.session_name);

        addButton = (Button) view.findViewById(R.id.button_add);
        addButton.setOnClickListener(this);

        gameSpinner = (Spinner) view.findViewById(R.id.game_spinner);
        List<String> gamesStringList = new ArrayList<String>();
        ArrayList<Game> gamesList = dbGame.getAllGames();
        for(Game game: gamesList){
            gamesStringList.add(game.getName());
        }
        gameNameAdapter = new ArrayAdapter<String>(view.getContext(),
                android.R.layout.simple_spinner_item, gamesStringList);
        gameSpinner.setAdapter(gameNameAdapter);

        hasDrawButton = (CheckBox) view.findViewById(R.id.has_draw);

        startDate = (EditText) view.findViewById(R.id.start_date);
        endDate = (EditText) view.findViewById(R.id.end_date);
    }

    private void fillLayout() {
        if (gameSession != null) {
            sessionNameTxt.setText(gameSession.getName());
            gameSpinner.setSelection(gameNameAdapter.getPosition(
                    dbGame.getGame(gameSession.getId()).getName()));
            hasDrawButton.setChecked(gameSession.getNbDraw() != -1);

            if (gameSession.getStartDate() != null) {
                startDate.setText(dateFormat.format(gameSession.getStartDate()));
            }
            if (gameSession.getEndDate() != null) {
                endDate.setText(dateFormat.format(gameSession.getEndDate()));
            }

            addButton.setText(R.string.update);
        }
    }

    @Override
    public void onResume() {
        getActivity().setTitle(R.string.add_game);
        super.onResume();
    }

    @Override
    public void onClick(View view) {
        if (sessionNameTxt.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), "Please enter a session name", Toast.LENGTH_LONG).show();
        } else {
            GameSession newGameSession = this.buildGameSession();
            if (gameSession == null) {
                dbSession.addGameSession(newGameSession);
            } else {
                newGameSession.setId(this.gameSession.getId());
                dbSession.updateGameSession(newGameSession);
            }
            this.dismiss();
        }
    }

    private GameSession buildGameSession() {
        GameSession newGameSession = new GameSession();
        try {
            newGameSession.setName(sessionNameTxt.getText().toString());
            newGameSession.setGameId(dbGame.getGame(gameSpinner.getSelectedItem().toString()).getId());
            if (this.gameSession == null) {
                newGameSession.setNbDraw(hasDrawButton.isChecked() ? 0 : -1);
            } else if (this.gameSession.getNbDraw() < 0 && hasDrawButton.isChecked()) {
                newGameSession.setNbDraw(0);
            }

            if (!startDate.getText().toString().isEmpty()) {
                newGameSession.setStartDate(dateFormat.parse(startDate.getText().toString()));
            } else {
                newGameSession.setStartDate(new Date());
            }
            if (!endDate.getText().toString().isEmpty()) {
                newGameSession.setEndDate(dateFormat.parse(endDate.getText().toString()));
            } else {
                newGameSession.setEndDate(null);
            }
        } catch (Exception e) {
            Log.d("date-parser", "Fail to parse date : " + e.toString());
        }

        return newGameSession;
    }

    public static void showDialog(Activity context) {
        GameSessionDialog.showDialog(context, null);
    }

    public static void showDialog(Activity context, GameSession gameSession) {

        if (dbGame.countGame() == 0) {
            Toast.makeText(context, "Please add a game before.", Toast.LENGTH_LONG).show();
            return;
        }

        FragmentTransaction ft = context.getFragmentManager().beginTransaction();
        Fragment prev = context.getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        GameSessionDialog addDialog = new GameSessionDialog();
        addDialog.setGame(gameSession);
        addDialog.show(ft, "dialog");
    }

    public GameSession getGame() {
        return gameSession;
    }

    public void setGame(GameSession gameSession) {
        this.gameSession = gameSession;
    }
}


