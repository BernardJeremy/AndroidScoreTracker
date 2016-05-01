package edu.csulb.android.androidscoretracker;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class GameDialog extends DialogFragment implements OnClickListener {

    private EditText gameNameTxt;
    private Button addButton;
    private GameDatabaseManager dbGame = new GameDatabaseManager();

    private Game game = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        this.getDialog().setTitle(R.string.add_game);

        View view = inflater.inflate(R.layout.game_dialog_fragment, container,
                false);

        gameNameTxt = (EditText) view.findViewById(R.id.game_name);
        addButton = (Button) view.findViewById(R.id.button_add);
        addButton.setOnClickListener(this);

        if (game != null) {
            gameNameTxt.setText(game.getName());
            addButton.setText(R.string.update);
        }

        return view;
    }

    @Override
    public void onResume() {
        getActivity().setTitle(R.string.add_game);
        super.onResume();
    }

    @Override
    public void onClick(View view) {
        String gameName = gameNameTxt.getText().toString();

        if (gameName.isEmpty()) {
            Toast.makeText(getActivity(), "Please enter a game name", Toast.LENGTH_LONG).show();
        } else {
            if (game == null) {
                dbGame.addGame(new Game(0, gameName, null));
            } else {
                dbGame.updateGame(new Game(this.game.getId(), gameName, null));
            }
            this.dismiss();
        }
        ((LoadingActivity)getActivity()).updateNavigationDrawer();
    }

    public static void showDialog(Activity context) {
        GameDialog.showDialog(context, null);
    }

    public static void showDialog(Activity context, Game game) {
        FragmentTransaction ft = context.getFragmentManager().beginTransaction();
        Fragment prev = context.getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        GameDialog addDialog = new GameDialog();
        addDialog.setGame(game);
        addDialog.show(ft, "dialog");
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}


