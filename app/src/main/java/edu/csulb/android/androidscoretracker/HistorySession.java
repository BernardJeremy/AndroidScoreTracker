package edu.csulb.android.androidscoretracker;

import java.util.Date;

public class HistorySession {
    private int id;
    private int sessionId;
    private int type;
    private String comment;
    private Date date;

    public static final int TYPE_UNDEFINED = 0;
    public static final int TYPE_WIN = 1;
    public static final int TYPE_DRAW = 2;
    public static final int TYPE_LOOSE = 3;

    public HistorySession(int id, int sessionId, int type, String comment, Date date) {
        this.id = id;
        this.date = date;
        this.sessionId = sessionId;
        this.type = type;
        this.comment = comment;
    }

    public HistorySession(int id, int sessionId, int type, String comment) {
        this.id = id;
        this.date = new Date();
        this.sessionId = sessionId;
        this.type = type;
        this.comment = comment;
    }

    public HistorySession() {
        this.id = 0;
        this.date = new Date();
        this.sessionId = 0;
        this.type = HistorySession.TYPE_UNDEFINED;
        this.comment = "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
