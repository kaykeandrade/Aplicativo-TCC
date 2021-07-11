package com.example.apptcc.Fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apptcc.Adapters.AdapterCardapio;
import com.example.apptcc.Models.DTOCardapio;
import com.example.apptcc.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class BuscarFragment extends Fragment {
    RecyclerView recyclerView;
    AdapterCardapio adapterCardapio;
    EditText editTextBuscar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_buscar, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewBuscar);
        editTextBuscar = view.findViewById(R.id.editTextBuscarFrag);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);

        //recyclerview dos pratos
        FirebaseRecyclerOptions<DTOCardapio> opcoes = new FirebaseRecyclerOptions.Builder<DTOCardapio>()
                .setQuery(FirebaseDatabase.getInstance().getReference("pratos").orderByChild("nomePrato"), DTOCardapio.class).build();
        adapterCardapio = new AdapterCardapio(opcoes);
        recyclerView.setAdapter(adapterCardapio);

        //método para buscar prato após alterar o texto
        editTextBuscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable pesquisa) {
                pesquisar(pesquisa.toString());
            }
        });

        return view;
    }

    //método para pesquisar os pratos
    private void pesquisar(String pesquisa){

        FirebaseRecyclerOptions<DTOCardapio> opcoes = new FirebaseRecyclerOptions.Builder<DTOCardapio>()
                .setQuery(FirebaseDatabase.getInstance().getReference("pratos").orderByChild("nomePrato").startAt(pesquisa).endAt(pesquisa+"utf8"), DTOCardapio.class).build();
        adapterCardapio = new AdapterCardapio(opcoes);
        recyclerView.setAdapter(adapterCardapio);
        adapterCardapio.startListening();
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