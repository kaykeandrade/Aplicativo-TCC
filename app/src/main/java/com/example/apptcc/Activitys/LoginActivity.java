package com.example.apptcc.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.apptcc.Firebase.ConfFireBase;
import com.example.apptcc.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    EditText editTextEmailLogin, editTextSenhaLogin;
    TextView textViewCadastrar;
    Button buttonLogar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmailLogin = findViewById(R.id.editTextEmailLogin);
        editTextSenhaLogin = findViewById(R.id.editTextSenhaLogin);
        textViewCadastrar = findViewById(R.id.textViewCadastrar);
        buttonLogar = findViewById(R.id.buttonLogar);

        //abrir a tela de cadastro de usuário
        textViewCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, CadastroUsuarioActivity.class);
                startActivity(intent);
            }
        });

        //login de usuário pelo auth
        buttonLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(editTextEmailLogin.getText().toString().isEmpty() || editTextSenhaLogin.getText().toString().isEmpty()){
                        Toast.makeText(LoginActivity.this, "Dados preenchidos incorretamente", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        FirebaseAuth firebaseAuth = ConfFireBase.getFirebaseAuth();
                        firebaseAuth.signInWithEmailAndPassword(editTextEmailLogin.getText().toString(), editTextSenhaLogin.getText().toString())
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if(task.isSuccessful()){
                                            Intent intent = new Intent(LoginActivity.this, ConsultaActivity.class);
                                            startActivity(intent);
                                        }
                                        else{
                                            Toast.makeText(LoginActivity.this, "Usuário ou senha incorretos", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        }
                    });
                }
            }