package com.teammanco.securenotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SystemClock.sleep(1500);
        Intent intent = new Intent(this, ConsultaNotas.class);
        startActivity(intent);
        finish();
    }
}