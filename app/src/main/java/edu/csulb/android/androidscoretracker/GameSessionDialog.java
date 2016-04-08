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
import android.widget.EditText;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;


public class GameSessionDialog extends DialogFragment implements OnClickListener {

    private EditText sessionNameTxt;
    private Button addButton;
    private Spinner gameSpinner;
    private CheckBox hasDrawButton;
    private CalendarViewScrollable startDate;
    private CalendarViewScrollable endDate;

    private static GameSessionDatabaseManager dbSession = new GameSessionDatabaseManager();
    private static GameDatabaseManager dbGame = new GameDatabaseManager();

    private GameSession gameSession = null;

    private ArrayAdapter<String> gameNameAdapter;

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

        startDate = (CalendarViewScrollable) view.findViewById(R.id.start_date);
        endDate = (CalendarViewScrollable) view.findViewById(R.id.end_date);
    }

    private void fillLayout() {
        if (gameSession != null) {
            sessionNameTxt.setText(gameSession.getName());
            gameSpinner.setSelection(gameNameAdapter.getPosition(
                    dbGame.getGame(gameSession.getId()).getName()));
            hasDrawButton.setChecked(gameSession.getNbDraw() != -1);
            startDate.setDate(gameSession.getStartDate().getTime(), true, true);
            endDate.setDate(gameSession.getEndDate().getTime(), true, true);

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
        GameSession newGameSession = this.buildGameSession();
        if (gameSession == null) {
            dbSession.addGameSession(newGameSession);
        } else {
            newGameSession.setId(this.gameSession.getId());
            dbSession.updateGameSession(newGameSession);
        }
        this.dismiss();
    }

    private GameSession buildGameSession() {
        GameSession newGameSession = new GameSession();
        newGameSession.setName(sessionNameTxt.getText().toString());
        newGameSession.setGameId(dbGame.getGame(gameSpinner.getSelectedItem().toString()).getId());
        if (this.gameSession == null) {
            newGameSession.setNbDraw(hasDrawButton.isChecked() ? 0 : -1);
        } else if (this.gameSession.getNbDraw() < 0 && hasDrawButton.isChecked()) {
            newGameSession.setNbDraw(0);
        }

        if (startDate.updated) {
            newGameSession.setStartDate(new GregorianCalendar(startDate.year, startDate.month, startDate.day).getTime());
        } else if (this.gameSession != null) {
            newGameSession.setStartDate(this.gameSession.getStartDate());
        }
        if (endDate.updated) {
            newGameSession.setEndDate(new GregorianCalendar(endDate.year, endDate.month, endDate.day).getTime());
        } else if (this.gameSession != null) {
            newGameSession.setEndDate(this.gameSession.getEndDate());
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


