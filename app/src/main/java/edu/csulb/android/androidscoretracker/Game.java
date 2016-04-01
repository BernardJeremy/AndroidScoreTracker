package edu.csulb.android.androidscoretracker;

import java.util.ArrayList;

public class Game {

    private int id;
    private String name;
    private ArrayList<GameSession> sessions;

    public Game(int id, String name, ArrayList<GameSession> sessions) {
        this.id = id;
        this.name = name;
        this.sessions = sessions;
    }

    public Game() {
        id = 0;
        name = null;
        sessions = null;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
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
