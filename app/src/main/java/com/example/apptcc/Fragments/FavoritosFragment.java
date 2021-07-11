package com.example.apptcc.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apptcc.Adapters.AdapterPratosFavoritos;
import com.example.apptcc.Firebase.ConfFireBase;
import com.example.apptcc.Models.DTOPratosFavoritos;
import com.example.apptcc.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FavoritosFragment extends Fragment {
    RecyclerView recyclerViewPratosFav;
    TextView textViewPedido1, textViewPedido2;
    ImageView imageViewPedidos;
    ArrayList<DTOPratosFavoritos> arrayListFavoritos;
    AdapterPratosFavoritos adapterPratosFavoritos;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    int count;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_favoritos, container, false);

        imageViewPedidos = view.findViewById(R.id.imageViewPedidos);
        textViewPedido1 = view.findViewById(R.id.textViewPedido1);
        textViewPedido2 = view.findViewById(R.id.textViewPedido2);
        recyclerViewPratosFav = view.findViewById(R.id.recyclerViewPratosFav);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerViewPratosFav.setLayoutManager(layoutManager);
        recyclerViewPratosFav.setHasFixedSize(true);

        carregarPratosFavoritos();

        return view;
    }
    public void carregarPratosFavoritos(){
        arrayListFavoritos = new ArrayList<>();
        firebaseAuth = ConfFireBase.getFirebaseAuth();
        databaseReference = ConfFireBase.getFirebaseDatabase().child("pratosFavoritos");
        Query query = databaseReference.orderByChild("emailCliente").equalTo(firebaseAuth.getCurrentUser().getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                arrayListFavoritos.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    DTOPratosFavoritos dtoPratosFavoritos = new DTOPratosFavoritos();
                    dtoPratosFavoritos.setNomePrato(dataSnapshot.child("nomePrato").getValue().toString());
                    dtoPratosFavoritos.setValorPrato(dataSnapshot.child("valorPrato").getValue().toString());
                    dtoPratosFavoritos.setDataAdd(dataSnapshot.child("dataAdd").getValue().toString());
                    dtoPratosFavoritos.setImagem(dataSnapshot.child("imagem").getValue().toString());
                    dtoPratosFavoritos.setIdPrato(dataSnapshot.child("idPrato").getValue().toString());

                    arrayListFavoritos.add(dtoPratosFavoritos);
                }
                adapterPratosFavoritos = new AdapterPratosFavoritos(arrayListFavoritos);

                count = adapterPratosFavoritos.getItemCount();
                if(count >= 1){
                    imageViewPedidos.setVisibility(View.INVISIBLE);
                    textViewPedido1.setVisibility(View.INVISIBLE);
                    textViewPedido2.setVisibility(View.INVISIBLE);
                    recyclerViewPratosFav.setAdapter(adapterPratosFavoritos);
                }else{
                    imageViewPedidos.setVisibility(View.VISIBLE);
                    textViewPedido1.setVisibility(View.VISIBLE);
                    textViewPedido2.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });
    }
}