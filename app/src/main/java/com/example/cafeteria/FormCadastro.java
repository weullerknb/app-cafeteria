package com.example.cafeteria;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class FormCadastro extends AppCompatActivity {

    private EditText edit_nome,edit_nascimento, edit_cpf, edit_telefone, edit_email, edit_senha;
    private Button bt_cadastrar;
    private String[] mensagens = {"Preencha todos os campos", "Cadastro com sucesso"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_cadastro);

        getSupportActionBar().hide();
        IniciarComponentes();

        bt_cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = edit_nome.getText().toString();
                String nascimento = edit_nascimento.getText().toString();
                String cpf = edit_cpf.getText().toString();
                String telefone = edit_telefone.getText().toString();
                String email = edit_email.getText().toString();
                String senha = edit_senha.getText().toString();

                if (nome.isEmpty() || nascimento.isEmpty() || cpf.isEmpty() ||
                        telefone.isEmpty() || email.isEmpty() || senha.isEmpty()) {
                    mostrarMensagem(v, mensagens[0]);
                } else {
                    CadastrarUsuario(v);
                }
            }
        });
    }

    private void CadastrarUsuario(View v) {
        String email = edit_email.getText().toString();
        String senha = edit_senha.getText().toString();

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    mostrarMensagem(v, mensagens[1]);
                } else {
                    String erro;
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e) {
                        erro = "Digite uma senha com no mínimo 6 caracteres";
                    } catch (FirebaseAuthUserCollisionException e) {
                        erro = "Este e-mail já foi cadastrado";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        erro = "E-mail inválido";
                    } catch (Exception e) {
                        erro = "Erro ao cadastrar usuário";
                    }
                    mostrarMensagem(v, erro);
                }
            }
        });
    }

    private void mostrarMensagem(View v, String msg) {
        Snackbar snackbar = Snackbar.make(v, msg, Snackbar.LENGTH_SHORT);
        snackbar.setBackgroundTint(Color.WHITE);
        snackbar.setTextColor(Color.BLACK);
        snackbar.show();
    }

    private void IniciarComponentes() {
        edit_nome = findViewById(R.id.edit_nome);
        edit_nascimento = findViewById(R.id.edit_nascimento);
        edit_cpf = findViewById(R.id.edit_cpf);
        edit_telefone = findViewById(R.id.edit_telefone);
        edit_email = findViewById(R.id.edit_email);
        edit_senha = findViewById(R.id.edit_senha);
        bt_cadastrar = findViewById(R.id.bt_cadastrar);
    }

}