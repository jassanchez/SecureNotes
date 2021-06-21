package com.teammanco.securenotes.model;

public class ItemList {
    private Note note;
    private int imgResource;

    public ItemList(Note note, int imgResource) {
        this.note = note;
        this.imgResource = imgResource;
    }

    public ItemList() {
    }

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }

    public int getImgResource() {
        return imgResource;
    }

    public void setImgResource(int imgResource) {
        this.imgResource = imgResource;
    }

}
