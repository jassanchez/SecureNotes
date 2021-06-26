package com.teammanco.securenotes.model;

import java.io.Serializable;

public class Note implements Serializable {
    private int    ID;
    private String title;
    private String content;

    public Note(int ID, String title, String content, int security, int category) {
        this.ID = ID;
        this.title = title;
        this.content = content;
        this.security = security;
        this.category = category;
    }

    private int    security;
    private int    category;

    public Note(String title, String content, int security, int category) {
        this.title = title;
        this.content = content;
        this.security = security;
        this.category = category;
    }

    public Note(){}

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
