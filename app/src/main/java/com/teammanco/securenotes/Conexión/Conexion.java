package com.teammanco.securenotes.Conexión;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Conexion extends SQLiteOpenHelper {

    final String table_nota =
            "CREATE TABLE nota (id INTEGER, title VARCHAR(50), contet TEXT,  security INTEGER, category INTEGER)";

    //Constructor de clase
    public Conexion(@Nullable Context context,
                    @Nullable String name,
                    @Nullable SQLiteDatabase.CursorFactory factory,
                    int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(table_nota);
    }

    //Verificación de versiones antigua vs nueva
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
