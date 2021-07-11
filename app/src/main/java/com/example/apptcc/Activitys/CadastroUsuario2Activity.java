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
import com.example.apptcc.Models.DTOCliente;
import com.example.apptcc.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;

public class CadastroUsuario2Activity extends AppCompatActivity {
    EditText editTextNome, editTextCPF, editTextCelular;
    Button buttonCadastrarUser;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario2);

        editTextNome = findViewById(R.id.editTextNomeCad);
        editTextCPF = findViewById(R.id.editTextCPF);
        editTextCelular = findViewById(R.id.editTextCelular);
        buttonCadastrarUser = findViewById(R.id.buttonCadastrarUser);

        //máscaras
        MaskEditTextChangedListener maskCPF = new MaskEditTextChangedListener("###.###.###-##", editTextCPF);
        MaskEditTextChangedListener maskTEL = new MaskEditTextChangedListener("(##)#####-####", editTextCelular);

        editTextCPF.addTextChangedListener(maskCPF);
        editTextCelular.addTextChangedListener(maskTEL);

        //cadastrar usuário no realtime database
        buttonCadastrarUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth = ConfFireBase.getFirebaseAuth();
                DTOCliente dtoCliente = new DTOCliente(editTextNome.getText().toString(),
                        editTextCelular.getText().toString(),
                        editTextCPF.getText().toString(),
                        firebaseAuth.getCurrentUser().getEmail(),
                         ("Sem Imagem.JPEG"));
                databaseReference = ConfFireBase.getFirebaseDatabase().child("clientes").child(firebaseAuth.getCurrentUser().getUid());
                databaseReference.setValue(dtoCliente).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Intent intent = new Intent(CadastroUsuario2Activity.this, ConsultaActivity.class);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(CadastroUsuario2Activity.this, "Erro Inesperado", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}