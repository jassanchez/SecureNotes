package com.teammanco.securenotes.model;

public class Note {
    private int    ID;
    private String title;
    private String content;
    private int    security;
    private int    category;

    public Note(String title, String content, int security, int category) {
        this.title = title;
        this.content = content;
        this.security = security;
        this.category = category;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getSecurity() {
        return security;
    }

    public void setSecurity(int security) {
        this.security = security;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }
}
