package com.teammanco.securenotes.Conexión;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.teammanco.securenotes.R;
import com.teammanco.securenotes.model.ItemList;
import com.teammanco.securenotes.model.Note;

import java.util.ArrayList;
import java.util.List;

public class Controller {
    private Conexion dbHelper;
    private final String TABLE_NAME = "nota", DB_NAME = "db";

    public Controller(Context context) {
        dbHelper = new Conexion(context, DB_NAME, null, 1);

    }

    public long insertNote(Note n){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        //values.put("id", n.getID());
        values.put("title", n.getTitle());
        values.put("content", n.getContent());
        values.put("security", n.getSecurity());
        values.put("category", n.getCategory());
        long state = db.insert(TABLE_NAME, null, values);
        return state;
    }

    public int deleteNote(Note n){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int state = db.delete(TABLE_NAME, "id = " + n.getID(), null);
        return state;
    }

    public int updateNote(Note n){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        //values.put("id", n.getID());
        values.put("title", n.getTitle());
        values.put("content", n.getContent());
        values.put("security", n.getSecurity());
        values.put("category", n.getCategory());
        int state = db.update(TABLE_NAME, values, "id = " + n.getID(), null);
        db.close();
        return state;
    }

    public ArrayList<Note> getNotes() {
        ArrayList<Note> notes = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = {"id", "title", "content", "security", "category"};
        Cursor cursor = db.query(TABLE_NAME, columns, null, null, null,
                null, null);
        //Si hay un error regresa lista vacia
        if (cursor == null) {
            db.close();
            return notes;
        }
        //Si no hay datos regresa lista vacia
        if (!cursor.moveToFirst()){
            db.close();
        return notes;
        }
        //Cuando si hay datos
        do{
            Note auxNote = new Note(cursor.getInt(0),cursor.getString(1), cursor.getString(2),
                    cursor.getInt(3), cursor.getInt(4));
            notes.add(auxNote);
        }while(cursor.moveToNext());
        db.close();
        cursor.close();
        return notes;
    }

    public List<ItemList> getNotesItems(){
        List<ItemList> notes = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = {"id", "title", "content", "security", "category"};
        Cursor cursor = db.query(TABLE_NAME, columns, null, null, null,
                null, null);
        //Si hay un error regresa lista vacia
        if (cursor == null) {
            db.close();
            return notes;
        }
        //Si no hay datos regresa lista vacia
        if (!cursor.moveToFirst()) {
            db.close();
            return notes;
        }
        //Cuando si hay datos
        do{
            Note auxNote = new Note(cursor.getInt(0),cursor.getString(1), cursor.getString(2),
                    cursor.getInt(3), cursor.getInt(4));
            notes.add(new ItemList(auxNote, R.drawable.icon_note));
        }while(cursor.moveToNext());
        db.close();
        cursor.close();
        return notes;
    }
}
