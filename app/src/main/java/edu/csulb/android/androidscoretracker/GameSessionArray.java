package edu.csulb.android.androidscoretracker;

import java.util.ArrayList;
import java.util.Iterator;

public class GameSessionArray implements Iterable<GameSession> {

    private ArrayList<GameSession> gameSessions;

    public GameSessionArray() {
        gameSessions = null;
    }

    public GameSessionArray(ArrayList<GameSession> gameSessions) {
      this.setGameSessions(gameSessions);
    }

    public Iterator<GameSession> iterator() {
        return gameSessions.iterator();
    }

    public void setGameSessions(ArrayList<GameSession> gameSessions) {
        this.gameSessions = gameSessions;
    }

    public void addGameSessions(GameSession gameSession) {
        this.gameSessions.add(gameSession);
    }

    public ArrayList<GameSession> getGameSessions() {
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
