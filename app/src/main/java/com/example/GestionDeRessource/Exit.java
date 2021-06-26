package com.example.GestionDeRessource;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Exit extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exit);
        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
            android.os.Process.killProcess(android.os.Process.myPid());
        }

    }
}