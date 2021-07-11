package com.example.apptcc.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.apptcc.Firebase.ConfFireBase;
import com.example.apptcc.Models.DTOEnderecos;
import com.example.apptcc.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EnderecoListaActivity extends AppCompatActivity {
    ListView listViewEndereco;
    TextView textView2;
    Button buttonNovoEndereco;
    DatabaseReference databaseReference;
    ArrayList<DTOEnderecos> arrayListEndereco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_endereco_lista);

        listViewEndereco = findViewById(R.id.listViewEndereco);
        buttonNovoEndereco = findViewById(R.id.buttonNovoEndereco);
        textView2 = findViewById(R.id.textView2);

        //excluir endereço
        listViewEndereco.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                DTOEnderecos dtoEnderecos = arrayListEndereco.get(position);
                databaseReference = ConfFireBase.getFirebaseDatabase().child("enderecos").child(dtoEnderecos.getIdEndereco());
                databaseReference.removeValue();
                return false;
            }
        });

        //abrir a activity de cadastro de endereços
        buttonNovoEndereco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EnderecoListaActivity.this, CadastrarEnderecoActivity.class);
                startActivity(intent);
            }
        });

        carregarEndereco();

    }
    private void carregarEndereco() {
        arrayListEndereco = new ArrayList<>();
        FirebaseAuth firebaseAuth = ConfFireBase.getFirebaseAuth();
        databaseReference = ConfFireBase.getFirebaseDatabase().child("enderecos");
        Query query = databaseReference.orderByChild("emailCliente").equalTo(firebaseAuth.getCurrentUser().getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayListEndereco.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    DTOEnderecos dtoEnderecos = new DTOEnderecos();
                    dtoEnderecos.setLogradouro(dataSnapshot.child("logradouro").getValue().toString());
                    dtoEnderecos.setNumero(dataSnapshot.child("numero").getValue().toString());
                    dtoEnderecos.setComplemento(dataSnapshot.child("complemento").getValue().toString());
                    dtoEnderecos.setCidade(dataSnapshot.child("cidade").getValue().toString());
                    dtoEnderecos.setCEP(dataSnapshot.child("cep").getValue().toString());
                    dtoEnderecos.setBairro(dataSnapshot.child("bairro").getValue().toString());
                    dtoEnderecos.setUF(dataSnapshot.child("uf").getValue().toString());
                    dtoEnderecos.setIdEndereco(dataSnapshot.child("idEndereco").getValue().toString());

                    arrayListEndereco.add(dtoEnderecos);
                }
                if (arrayListEndereco.size() == 0){
                    textView2.setVisibility(View.VISIBLE);
                }
                else{
                    textView2.setVisibility(View.INVISIBLE);
                }
                ArrayAdapter adapter = new ArrayAdapter(EnderecoListaActivity.this, android.R.layout.simple_list_item_1, arrayListEndereco);
                listViewEndereco.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}