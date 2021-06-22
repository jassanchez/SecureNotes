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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_notas);
        autheticateMobile();
        initViews();
        // Objeto controlador de la base de datos
        db = new Controller(this);

        Intent intent = new Intent(this, InsertaNota.class);

        initValues();

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
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
        adapter = new RecyclerAdapter(db.getNotesItems());
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