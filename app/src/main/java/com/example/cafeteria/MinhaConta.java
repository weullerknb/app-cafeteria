package com.example.cafeteria;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MinhaConta extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minha_conta);

        getSupportActionBar().hide();
    }
}