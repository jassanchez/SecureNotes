package com.teammanco.securenotes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.teammanco.securenotes.Conexión.Conexion;
import com.teammanco.securenotes.Conexión.Controller;
import com.teammanco.securenotes.model.Note;

public class InsertaNota extends AppCompatActivity {

    private FloatingActionButton btnAdd;
    private Controller db;

    private EditText etTitulo;
    private EditText etContenido;
    private CheckBox chkSeguridad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inserta_nota);

        etTitulo = findViewById(R.id.etTitulo);
        etContenido = findViewById(R.id.etConcepto);
        chkSeguridad = findViewById(R.id.chkSeguridad);

        db = new Controller(this);

        btnAdd = findViewById(R.id.fabAddNote);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.insertNote(new Note(etTitulo.getText().toString(),
                                        etContenido.getText().toString(),
                                        (chkSeguridad.isChecked()? 1 : 0),
                                1));
                finish();
            }
        });
    }
}