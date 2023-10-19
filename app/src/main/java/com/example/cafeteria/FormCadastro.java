package com.example.cafeteria;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class FormCadastro extends AppCompatActivity {

    private EditText edit_nome,edit_nascimento, edit_cpf, edit_telefone, edit_email, edit_senha;
    private Button bt_cadastrar;
    private String[] mensagens = {"Preencha todos os campos", "Cadastro com sucesso"};
    String usuarioID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_cadastro);

        getSupportActionBar().hide();
        iniciarComponentes();

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
                    cadastrarUsuario(v);

                }
            }
        });
    }

    private void cadastrarUsuario(View v) {
        String email = edit_email.getText().toString();
        String senha = edit_senha.getText().toString();

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    salvarDadosUsuario();
                    mostrarMensagem(v, mensagens[1]);
                    telaMinhaConta();
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

    private void telaMinhaConta() {
        Intent intent = new Intent(FormCadastro.this, MinhaConta.class);
        startActivity(intent);
        finish();
    }

    private void mostrarMensagem(View v, String msg) {
        Snackbar snackbar = Snackbar.make(v, msg, Snackbar.LENGTH_SHORT);
        snackbar.setBackgroundTint(Color.WHITE);
        snackbar.setTextColor(Color.BLACK);
        snackbar.show();
    }

    private void salvarDadosUsuario() {
        String nome = edit_nome.getText().toString();
        String nascimento = edit_nascimento.getText().toString();
        String cpf = edit_cpf.getText().toString();
        String telefone = edit_telefone.getText().toString();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> usuarios = new HashMap<>();
        usuarios.put("nome", nome);
        usuarios.put("nascimento", nascimento);
        usuarios.put("cpf", cpf);
        usuarios.put("telefone", telefone);

        usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference documentReference = db.collection("Usuarios").document(usuarioID);
        documentReference.set(usuarios).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d("db", "Sucesso ao salvar os dados");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("db_error", "Erro ao salvar os dados" + e.toString());
            }
        });
    }

    private void iniciarComponentes() {
        edit_nome = findViewById(R.id.edit_nome);
        edit_nascimento = findViewById(R.id.edit_nascimento);
        edit_cpf = findViewById(R.id.edit_cpf);
        edit_telefone = findViewById(R.id.edit_telefone);
        edit_email = findViewById(R.id.edit_email);
        edit_senha = findViewById(R.id.edit_senha);
        bt_cadastrar = findViewById(R.id.bt_cadastrar);
    }

}