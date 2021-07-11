package com.example.apptcc.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apptcc.Adapters.AdapterPerfil;
import com.example.apptcc.Firebase.ConfFireBase;
import com.example.apptcc.Models.DTOCliente;
import com.example.apptcc.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class PerfilFragment extends Fragment {

    Button buttonDeslogar;
    RecyclerView recyclerViewPerfil;
    AdapterPerfil adapterPerfil;
    ImageView imageViewPerfil;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        recyclerViewPerfil = view.findViewById(R.id.recyclerViewPerfil);
        imageViewPerfil = view.findViewById(R.id.imageViewPerfil);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerViewPerfil.setLayoutManager(layoutManager);

        //recyclerview do perfil do usuário
        FirebaseAuth firebaseAuth = ConfFireBase.getFirebaseAuth();
        FirebaseRecyclerOptions<DTOCliente> opcoes = new FirebaseRecyclerOptions.Builder<DTOCliente>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("clientes").orderByChild("emailCliente").equalTo(firebaseAuth.getCurrentUser().getEmail()).limitToFirst(1), DTOCliente.class).build();
        adapterPerfil = new AdapterPerfil(opcoes);
        recyclerViewPerfil.setAdapter(adapterPerfil);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapterPerfil.startListening();
    }

    @Override
    public void onStop(){
        super.onStop();
        adapterPerfil.stopListening();
    }
}