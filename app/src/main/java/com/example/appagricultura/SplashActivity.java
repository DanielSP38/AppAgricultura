package com.example.appagricultura;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                abrirJanela();
            }
        },3000);
    }
    public void abrirJanela(){
        Intent abrir = new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(abrir);
        finish();
    }

}


