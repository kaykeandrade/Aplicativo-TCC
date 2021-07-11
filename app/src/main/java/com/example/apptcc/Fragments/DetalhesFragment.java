package com.example.apptcc.Fragments;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.apptcc.Firebase.ConfFireBase;
import com.example.apptcc.Models.DTOPratosFavoritos;
import com.example.apptcc.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DetalhesFragment extends Fragment {

    private String nomePrato, descricao, categoria, valorPrato, imagem;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    StorageReference storageReference;

    public DetalhesFragment() {

    }

    //construtor que recebe os dados do adapter
    public DetalhesFragment(String nomePrato, String descricao, String categoria, String valorPrato, String imagem) {
        this.nomePrato = nomePrato;
        this.descricao = descricao;
        this.categoria = categoria;
        this.valorPrato = valorPrato;
        this.imagem = imagem;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detalhes, container, false);

        ImageView produto = view.findViewById(R.id.imageViewDetalhesFrag);
        TextView nome = view.findViewById(R.id.textViewNomeDetFrag);
        TextView desc = view.findViewById(R.id.textViewDescricaoDetFrag);
        TextView valor = view.findViewById(R.id.textViewValorDetFrag);
        TextView cat = view.findViewById(R.id.textViewCategoriaDetFrag);
        Button buttonPratosFav = view.findViewById(R.id.buttonPratosFav);

        nome.setText(nomePrato);
        desc.setText(descricao);
        valor.setText(valorPrato);
        cat.setText(categoria);

        storageReference = ConfFireBase.getFirebaseStorage().child("imagens").child("pratos").child(imagem);
        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {

            public void onSuccess(Uri uri) {
                Picasso.get().load(uri.toString()).into(produto);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //
            }
        });

        //formatação da data e hora para o id
        SimpleDateFormat id = new SimpleDateFormat("yyyy-MM-ddhh:mm:ss");
        Date dataId = new Date();
        String idPratoFav = id.format(dataId);

        //formatação de data
        SimpleDateFormat formatarData = new SimpleDateFormat("dd/MM/yyyy");
        Date data = new Date();
        String dataFormatada = formatarData.format(data);

        //inserir pratos favoritos
        buttonPratosFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth = ConfFireBase.getFirebaseAuth();
                DTOPratosFavoritos dtoPratosFavoritos = new DTOPratosFavoritos(nome.getText().toString(), desc.getText().toString(), valor.getText().toString(),cat.getText().toString(), imagem, firebaseAuth.getCurrentUser().getEmail(), dataFormatada, idPratoFav);
                databaseReference = ConfFireBase.getFirebaseDatabase().child("pratosFavoritos").child(dtoPratosFavoritos.getIdPrato());
                databaseReference.setValue(dtoPratosFavoritos).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getActivity(), "Adicionado aos pratos favoritos", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getActivity(), "Erro Inesperado", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        return view;
    }
}