package com.example.cafeteria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class MinhaConta extends AppCompatActivity {

    private TextView nomeUsuario, emailUsuario, telefoneUsuario, nascimentoUsuario;
    private Button bt_deslogar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minha_conta);

        getSupportActionBar().hide();
        iniciarComponentes();

        bt_deslogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MinhaConta.this, FormLogin.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void iniciarComponentes() {
        nomeUsuario = findViewById(R.id.textNomeUsuario);
        emailUsuario = findViewById(R.id.textEmailUsuario);
        telefoneUsuario = findViewById(R.id.textPhoneUsuario);
        nascimentoUsuario = findViewById(R.id.textDataUsuario);
        bt_deslogar = findViewById(R.id.bt_deslogar);
    }
}