package edu.csulb.android.androidscoretracker;

import java.util.Date;

public class GameSession {
    private int id;
    private int gameId;
    private String name;
    private int nbWin;
    private int nbLoose;
    private int nbDraw;
    private Date startDate;
    private Date endDate;

    public GameSession(int id, int gameId, String name, int nbWin, int nbLoose, int nbDraw, Date startDate, Date endDate) {
        this.id = id;
        this.gameId = gameId;
        this.name = name;
        this.nbWin = nbWin;
        this.nbLoose = nbLoose;
        this.nbDraw = nbDraw;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public GameSession() {
        this.id = 0;
        this.gameId = 0;
        this.name = "";
        this.nbWin = 0;
        this.nbLoose = 0;
        this.nbDraw = -1;
        this.startDate = null;
        this.endDate = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNbWin() {
        return nbWin;
    }

    public void setNbWin(int nbWin) {
        this.nbWin = nbWin;
    }

    public int getNbLoose() {
        return nbLoose;
    }

    public void setNbLoose(int nbLoose) {
        this.nbLoose = nbLoose;
    }

    public int getNbDraw() {
        return nbDraw;
    }

    public void setNbDraw(int nbDraw) {
        this.nbDraw = nbDraw;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
