package edu.csulb.android.androidscoretracker;

import java.util.ArrayList;

public class Game {

    private int gameId;
    private String gameName;
    private ArrayList<GameSession> sessions;

    public Game() {
        gameId = 0;
        gameName = null;
        sessions = null;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getGameName() {
        return gameName;
    }

    public void setSessions(ArrayList<GameSession> sessions) {
        this.sessions = sessions;
    }

    public ArrayList<GameSession> getSessions() {
        return sessions;
    }

    public void addSession(GameSession session) {
        if (sessions == null)
            sessions = new ArrayList<>();
        sessions.add(session);
    }
}
