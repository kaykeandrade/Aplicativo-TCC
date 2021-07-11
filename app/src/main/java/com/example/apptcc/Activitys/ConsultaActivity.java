package com.example.apptcc.Activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.apptcc.Fragments.BuscarFragment;
import com.example.apptcc.Fragments.FavoritosFragment;
import com.example.apptcc.Fragments.HomeFragment;
import com.example.apptcc.Fragments.PerfilFragment;
import com.example.apptcc.R;

public class ConsultaActivity extends AppCompatActivity {
    Button buttonHome, buttonBuscar, buttonPedidos, buttonPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta);

        buttonHome = findViewById(R.id.buttonHome);
        buttonBuscar = findViewById(R.id.buttonBuscar);
        buttonPedidos = findViewById(R.id.buttonFavoritos);
        buttonPerfil = findViewById(R.id.buttonPerfil);

            buttonBuscar.setElevation(50);
            buttonPedidos.setElevation(40);
            buttonPerfil.setElevation(40);

            HomeFragment homeFragment = new HomeFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frameLayout1, homeFragment);
            transaction.commit();

        //abrir o fragment home
        buttonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonBuscar.setElevation(40);
                buttonPedidos.setElevation(40);
                buttonPerfil.setElevation(40);

                HomeFragment homeFragment = new HomeFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout1, homeFragment);
                transaction.commit();
            }
        });

        //abrir o fragment de busca de pedidos
        buttonBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                buttonHome.setElevation(40);
                buttonPedidos.setElevation(40);
                buttonPerfil.setElevation(40);

                BuscarFragment buscarFragment = new BuscarFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout1, buscarFragment);
                transaction.commit();
            }
        });

        //abrir o fragment de pratos favoritos
        buttonPedidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               buttonHome.setElevation(40);
               buttonBuscar.setElevation(40);
               buttonPerfil.setElevation(40);

                FavoritosFragment favoritosFragment = new FavoritosFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout1, favoritosFragment);
                transaction.commit();
            }
        });

        //abrir o fragment de perfil
        buttonPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                buttonBuscar.setElevation(40);
                buttonPedidos.setElevation(40);
                buttonHome.setElevation(40);

                PerfilFragment perfilFragment = new PerfilFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout1, perfilFragment);
                transaction.commit();
            }
        });
    }
}



