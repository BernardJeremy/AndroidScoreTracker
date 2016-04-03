package edu.csulb.android.androidscoretracker;

import java.util.ArrayList;
import java.util.Iterator;

public class GameArray implements Iterable<Game> {

    private ArrayList<Game> games;

    public GameArray() {
        games = null;
    }

    public GameArray(ArrayList<Game> games) {
      this.setGames(games);
    }

    public Iterator<Game> iterator() {
        return games.iterator();
    }

    public void setGames(ArrayList<Game> games) {
        this.games = games;
    }

    public void addGame(Game game) {
        this.games.add(game);
    }

    public ArrayList<Game> getGames() {
        return games;
    }

    public Game getOneGame(String gameName) {
        for (Game game:games) {
            if (game.getName() != null && game.getName().equals(gameName))
                return game;
        }
        return null;
    }

    public Game getOneGame(int gameId) {
        for (Game game:games) {
            if (game.getId() == gameId)
                return game;
        }
        return null;
    }
}
