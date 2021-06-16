package com.teammanco.securenotes.Conexión;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.teammanco.securenotes.model.Note;

import java.util.ArrayList;

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
        return db.insert(TABLE_NAME, null, values);
    }

    public int deleteNote(Note n){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.delete(TABLE_NAME, "id = " + n.getID(), null);
    }

    public int updateNote(Note n){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        //values.put("id", n.getID());
        values.put("title", n.getTitle());
        values.put("content", n.getContent());
        values.put("security", n.getSecurity());
        values.put("category", n.getCategory());
        return db.update(TABLE_NAME, values, "id = " + n.getID(), null);
    }

    public ArrayList<Note> getNotes(){
        ArrayList<Note> notes = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = {"id", "title", "content", "security", "category"};
        Cursor cursor = db.query(TABLE_NAME, columns, null, null, null,
                null, null);
        //Si hay un error regresa lista vacia
        if (cursor == null)
            return notes;

        //Si no hay datos regresa lista vacia
        if (!cursor.moveToFirst())
            return notes;

        //Cuando si hay datos
        do{
            Note auxNote = new Note(cursor.getString(1), cursor.getString(2),
                    cursor.getInt(3), cursor.getInt(4));
            notes.add(auxNote);
        }while(cursor.moveToNext());

        cursor.close();
        return notes;
    }
}
