package com.example.apptcc.Adapters;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apptcc.Firebase.ConfFireBase;
import com.example.apptcc.Fragments.FavoritosFragment;
import com.example.apptcc.Models.DTOPratosFavoritos;
import com.example.apptcc.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdapterPratosFavoritos extends RecyclerView.Adapter<AdapterPratosFavoritos.myviewholder> {
    List<DTOPratosFavoritos> dtoPratosFavoritosArrayList;
    StorageReference storageReference;
    DatabaseReference databaseReference;

    public AdapterPratosFavoritos(ArrayList<DTOPratosFavoritos> dtoPratosFavoritosArrayList) {
        this.dtoPratosFavoritosArrayList = dtoPratosFavoritosArrayList;
    }

    @Override
    public AdapterPratosFavoritos.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_pratos, parent, false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
        holder.textViewNomePratoFav.setText(dtoPratosFavoritosArrayList.get(position).getNomePrato());
        holder.textViewValorPratoFav.setText("Valor: "+dtoPratosFavoritosArrayList.get(position).getValorPrato());
        holder.textViewData.setText("Adicionado em: " + dtoPratosFavoritosArrayList.get(position).getDataAdd());

        String imagem = dtoPratosFavoritosArrayList.get(position).getImagem();

        storageReference = ConfFireBase.getFirebaseStorage().child("imagens").child("pratos").child(imagem);
        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {

            public void onSuccess(Uri uri) {
                Picasso.get().load(uri.toString()).into(holder.imageViewPratoFav);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //
            }
        });

        //excluir prato favorito
        holder.imageViewPratoFav.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                DTOPratosFavoritos dtoPratosFavoritos = dtoPratosFavoritosArrayList.get(position);
                databaseReference = ConfFireBase.getFirebaseDatabase().child("pratosFavoritos").child(dtoPratosFavoritos.getIdPrato());
                databaseReference.removeValue();
                if(getItemCount() <= 1){
                    AppCompatActivity activity = (AppCompatActivity)v.getContext();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout1, new FavoritosFragment()).addToBackStack(null).commit();
                }
                else{

                }
                return false;
            }
        });
    }

    public int getItemCount() {
        return dtoPratosFavoritosArrayList.size();
    }

    public class myviewholder extends RecyclerView.ViewHolder {
        ImageView imageViewPratoFav;
        TextView textViewNomePratoFav, textViewValorPratoFav, textViewData;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            imageViewPratoFav = itemView.findViewById(R.id.imageViewPratoFav);
            textViewNomePratoFav = itemView.findViewById(R.id.textViewNomePratoFav);
            textViewValorPratoFav = itemView.findViewById(R.id.textViewValorPratoFav);
            textViewData = itemView.findViewById(R.id.textViewData);
        }
    }
}
