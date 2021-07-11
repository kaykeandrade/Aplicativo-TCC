package com.example.apptcc.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.apptcc.Classes.AsyncCEP;
import com.example.apptcc.Firebase.ConfFireBase;
import com.example.apptcc.Models.DTOEnderecos;
import com.example.apptcc.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;

public class CadastrarEnderecoActivity extends AppCompatActivity {

    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_endereco);

        final EditText etLogradouro = findViewById(R.id.etLogradouro);
        final EditText etNumero = findViewById(R.id.etNumero);
        final EditText etComplemento = findViewById(R.id.etComplemento);
        final EditText etCep = findViewById(R.id.etCep);
        final EditText etBairro = findViewById(R.id.etBairro);
        final EditText etCidade = findViewById(R.id.etCidade);
        final EditText etUf = findViewById(R.id.etUF);
        final Button buttonCadEnd = findViewById(R.id.buttonCadastrarEndereco);

        MaskEditTextChangedListener maskCep = new MaskEditTextChangedListener("#####-###", etCep);
        etCep.addTextChangedListener(maskCep);

        //formatação da data para criar o ID do endereço
        SimpleDateFormat id = new SimpleDateFormat("yyyy-MM-ddhh:mm:ss");
        Date dataId = new Date();
        String idEnd = id.format(dataId);

        //método pra pegar o cep digitado após digitar
        etCep.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String cep = s.toString();
                AsyncCEP async = new AsyncCEP(etLogradouro, etCidade, etUf, etBairro, cep);
                async.execute();
                etLogradouro.setEnabled(false);
                etBairro.setEnabled(false);
                etCidade.setEnabled(false);
                etUf.setEnabled(false);
            }
        });

        //cadastro de endereço no realtime database
        buttonCadEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth = ConfFireBase.getFirebaseAuth();
                DTOEnderecos dtoEnderecos = new DTOEnderecos(etLogradouro.getText().toString(), etNumero.getText().toString(),
                        etComplemento.getText().toString(),
                        etCep.getText().toString(),
                        etBairro.getText().toString(),
                        etCidade.getText().toString(),
                        etUf.getText().toString(),
                        firebaseAuth.getCurrentUser().getEmail(), idEnd);
                databaseReference = ConfFireBase.getFirebaseDatabase().child("enderecos").child(dtoEnderecos.getIdEndereco());
                databaseReference.setValue(dtoEnderecos).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Intent intent = new Intent(CadastrarEnderecoActivity.this, EnderecoListaActivity.class);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(CadastrarEnderecoActivity.this, "Erro Inesperado", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}