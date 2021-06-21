package com.teammanco.securenotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import androidx.biometric.BiometricPrompt;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.teammanco.securenotes.Conexión.Controller;
import com.teammanco.securenotes.adapters.RecyclerAdapter;
import com.teammanco.securenotes.model.ItemList;
import com.teammanco.securenotes.model.Note;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public class ConsultaNotas extends AppCompatActivity {

    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo  promptInfo;
    private boolean authorized = false;
    private RecyclerView rvLista;
    private RecyclerAdapter adapter;
    private List<ItemList> items;
    private Controller db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_notas);
        autheticateMobile();
        db = new Controller(this);
        initViews();
        initValues();
        //biometricPrompt.authenticate(promptInfo);
        /*Button btn = findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNota(v,new Note("","",1,2));
            }
        });*/
    }

    private void initViews(){
       rvLista = findViewById(R.id.rvLista);
    }

    private void initValues(){
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rvLista.setLayoutManager(manager);

        //db.insertNote(new Note("Nota 1","Contenido chido",1,2));
        //db.insertNote(new Note("Test","Contenido test",0,2));

        //items = consultaNotas();
        adapter = new RecyclerAdapter(db.getNotesItems());
        rvLista.setAdapter(adapter);
    }

    private List<ItemList> consultaNotas(){
        List<ItemList> itemLists = new ArrayList<>();

        itemLists.add(new ItemList(new Note("Nota 1","Contenido chido",1,2),R.drawable.icon_note));
        itemLists.add(new ItemList(new Note("Nota 2","Contenido chido",0,2),R.drawable.icon_note));
        itemLists.add(new ItemList(new Note("Nota 3","Contenido chido",0,2),R.drawable.icon_note));

        return  itemLists;
    }
    private void showNota(View v, Note note){
           authorized = false;
        if (note.getSecurity() == 1)
             biometricPrompt.authenticate(promptInfo);
        else{
            //Rutina para notas sin seguridad
        }


    }

    private void autheticateMobile(){
        executor = ContextCompat.getMainExecutor(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            biometricPrompt = new BiometricPrompt(
                    ConsultaNotas.this,
                    executor,
                    new BiometricPrompt.AuthenticationCallback(){
                        @Override
                        public void onAuthenticationError(int errorCode, CharSequence errString) {
                            super.onAuthenticationError(errorCode, errString);
                            authorized = false;
                            Snackbar.make(findViewById(android.R.id.content).getRootView(),
                                    "¡Autenticación fallida!",
                                    Snackbar.LENGTH_LONG).show();
                        }

                        @Override
                        public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
                            super.onAuthenticationSucceeded(result);
                            authorized = true;
                        }

                        @Override
                        public void onAuthenticationFailed() {
                            super.onAuthenticationFailed();
                            authorized = false;
                        }
                    });
            promptInfo = new BiometricPrompt.PromptInfo.Builder()
                    .setTitle("Autenticación")
                    .setSubtitle("Identificate para ver la nota")
                    .setNegativeButtonText("Cancelar")
                    .build();

        }
    }
}