package com.teammanco.securenotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.teammanco.securenotes.Conexión.Controller;
import com.teammanco.securenotes.model.Note;

public class ConsultaEditaNota extends AppCompatActivity {

    private Note note;
    private int idNote;
    private Controller db;
    private TextInputEditText etTitulo;
    private TextInputEditText etContenido;
    private CheckBox chkSeguridad;
    private FloatingActionButton editbtn;
    private boolean flagAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_edita_nota);
        note = (Note) getIntent().getSerializableExtra("Note");
        db = new Controller(this);

        etTitulo = findViewById(R.id.etConTitulo);
        etContenido = findViewById(R.id.etConConcepto);
        chkSeguridad = findViewById(R.id.chkSeguridadC);
        editbtn = findViewById(R.id.fabEditNote);

        etTitulo.setText(note.getTitle());
        etContenido.setText(note.getContent());
        if(note.getSecurity() == 1){
            chkSeguridad.setChecked(true);
        }else{
            chkSeguridad.setChecked(false);
        }

        editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!flagAction){
                    etTitulo.setEnabled(true);
                    etContenido.setEnabled(true);
                    chkSeguridad.setEnabled(true);
                    editbtn.setImageResource(R.drawable.ic_baseline_save_24);
                    flagAction = true;
                }else{
                    note.setTitle(etTitulo.getText().toString());
                    note.setContent(etContenido.getText().toString());
                    note.setSecurity((chkSeguridad.isChecked()? 1 : 0));
                    db.updateNote(note);
                    Toast.makeText(getApplicationContext(),
                                    "Nota editada con éxito",
                                    Toast.LENGTH_LONG).show();
                    editbtn.setImageResource(R.drawable.ic_baseline_edit_24);
                    flagAction = false;
                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.deleteButton){
            db.deleteNote(note);
            Toast.makeText(getApplicationContext(),"Nota eliminada con éxito",Toast.LENGTH_LONG).show();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}