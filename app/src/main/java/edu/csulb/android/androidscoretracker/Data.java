package edu.csulb.android.androidscoretracker;

import java.util.ArrayList;

public class Data {

    private ArrayList<Game> games;

    public Data() {
        games = null;
    }

    public void setGames(ArrayList<Game> games) {
        this.games = games;
    }

    public ArrayList<Game> getGames() {
        return games;
    }

    public Game getOneGame(String gameName) {
        for (Game game:games) {
            if (game.getGameName() != null && game.getGameName().equals(gameName))
                return game;
        }
        return null;
    }

    public Game getOneGame(int gameId) {
        for (Game game:games) {
            if (game.getGameId() == gameId)
                return game;
        }
        return null;
    }
}
