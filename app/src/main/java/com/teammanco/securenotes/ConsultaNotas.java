package com.teammanco.securenotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import androidx.biometric.BiometricPrompt;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.teammanco.securenotes.Conexión.Conexion;
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
    private FloatingActionButton fabAdd;
    private int idNota;
    private Intent intentNuevaNota;
    private Intent intentConsultaNota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_notas);
        autheticateMobile();
        initViews();
        // Objeto controlador de la base de datos
        db = new Controller(this);

        intentNuevaNota = new Intent(this, InsertaNota.class);
        intentConsultaNota = new Intent(this, ConsultaEditaNota.class);


        initValues();

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intentNuevaNota);
            }
        });
        //biometricPrompt.authenticate(promptInfo);
    }

    private void initViews(){
        rvLista = findViewById(R.id.rvLista);
        fabAdd = findViewById(R.id.fabAdd);
    }

    private void initValues(){
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rvLista.setLayoutManager(manager);

        //Se crea un adaptador usando el metodo getNotesItems() de la clase controller
        List<ItemList> lista = db.getNotesItems();
        adapter = new RecyclerAdapter(lista);
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Note note = lista.get(rvLista.getChildAdapterPosition(v)).getNote();
                idNota = note.getID();
                intentConsultaNota.putExtra("Note",note);
                if (note.getSecurity() == 1){
                    biometricPrompt.authenticate(promptInfo);
                }else{
                    startActivity(intentConsultaNota);
                }
            }
        });
        //Se le pasa el adaptador al recyclerView
        rvLista.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initValues();
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
                            launchActivity();
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

    private void launchActivity(){
        startActivity(intentConsultaNota);
    }
}