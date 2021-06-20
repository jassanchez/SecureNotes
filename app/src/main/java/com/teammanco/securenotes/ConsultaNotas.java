package com.teammanco.securenotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import androidx.biometric.BiometricPrompt;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.teammanco.securenotes.model.Note;

import java.util.concurrent.Executor;

public class ConsultaNotas extends AppCompatActivity {

    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo  promptInfo;
    private boolean authorized = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_notas);
        autheticateMobile();
        //biometricPrompt.authenticate(promptInfo);
        Button btn = findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNota(v,new Note("","",1,2));
            }
        });
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