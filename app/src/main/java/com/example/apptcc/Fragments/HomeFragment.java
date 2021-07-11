package com.example.apptcc.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apptcc.Adapters.AdapterCardapio;
import com.example.apptcc.Models.DTOCardapio;
import com.example.apptcc.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;


public class HomeFragment extends Fragment {
    RecyclerView recyclerViewCardapio;
    AdapterCardapio adapterCardapio;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, viewGroup, false);

        recyclerViewCardapio = view.findViewById(R.id.recyclerViewCardapio);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerViewCardapio.setLayoutManager(layoutManager);

        //recyclerview do cardápio
        FirebaseRecyclerOptions<DTOCardapio> opcoes = new FirebaseRecyclerOptions.Builder<DTOCardapio>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("pratos").orderByChild("categoria"), DTOCardapio.class).build();

        adapterCardapio = new AdapterCardapio(opcoes);
        recyclerViewCardapio.setAdapter(adapterCardapio);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapterCardapio.startListening();
    }

    @Override
    public void onStop(){
        super.onStop();
        adapterCardapio.stopListening();
    }
}

