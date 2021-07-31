package com.example.apptcc.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.apptcc.Firebase.ConfFireBase;
import com.example.apptcc.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class CadastroUsuarioActivity extends AppCompatActivity {
    EditText editTextEmailCad, editTextSenhaCad;
    Button buttonCadastrar;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        editTextEmailCad = findViewById(R.id.editTextEmailCad);
        editTextSenhaCad = findViewById(R.id.editTextSenhaCad);
        buttonCadastrar = findViewById(R.id.buttonAvancarCadastro);

        //cadastrar e logar usuário no auth
        buttonCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextEmailCad.getText().toString().isEmpty() || editTextSenhaCad.getText().toString().isEmpty()) {
                    Toast.makeText(CadastroUsuarioActivity.this, "Dados preenchidos incorretamente", Toast.LENGTH_SHORT).show();
                }
                else {
                    firebaseAuth = ConfFireBase.getFirebaseAuth();
                    firebaseAuth.createUserWithEmailAndPassword(editTextEmailCad.getText().toString(), editTextSenhaCad.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    firebaseAuth.signInWithEmailAndPassword(editTextEmailCad.getText().toString(), editTextSenhaCad.getText().toString());
                                    if (task.isSuccessful()) {
                                        Intent intent = new Intent(CadastroUsuarioActivity.this, CadastroUsuario2Activity.class);
                                        startActivity(intent);
                                    } else {
                                        try {
                                            throw task.getException();
                                        } catch (FirebaseAuthWeakPasswordException ex) {
                                            Toast.makeText(CadastroUsuarioActivity.this, "Senha fraca", Toast.LENGTH_SHORT).show();
                                        } catch (FirebaseAuthEmailException ex) {
                                            Toast.makeText(CadastroUsuarioActivity.this, "Email incorreto", Toast.LENGTH_SHORT).show();
                                        } catch (FirebaseAuthUserCollisionException ex) {
                                            Toast.makeText(CadastroUsuarioActivity.this, "Usuário já existe", Toast.LENGTH_SHORT).show();
                                        } catch (Exception ex) {
                                            Toast.makeText(CadastroUsuarioActivity.this, "Erro ao cadastrar", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            });
                        }
                    }
                });
            }
        }