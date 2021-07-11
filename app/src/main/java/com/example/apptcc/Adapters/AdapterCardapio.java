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
import com.example.apptcc.Fragments.DetalhesFragment;
import com.example.apptcc.Models.DTOCardapio;
import com.example.apptcc.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class AdapterCardapio extends FirebaseRecyclerAdapter<DTOCardapio, AdapterCardapio.myviewholder> {

     StorageReference storageReference;

    public AdapterCardapio(@NonNull  FirebaseRecyclerOptions<DTOCardapio> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull  myviewholder holder, int position, @NonNull DTOCardapio dtoCardapio) {
        holder.textViewProdutosCard.setText(dtoCardapio.getNomePrato());

        String imagem = dtoCardapio.getImagem();

        storageReference = ConfFireBase.getFirebaseStorage().child("imagens").child("pratos").child(imagem);
        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {

            public void onSuccess(Uri uri) {
                Picasso.get().load(uri.toString()).into(holder.imageViewCardapio);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //
            }
        });

        holder.imageViewCardapio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity)view.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout1, new DetalhesFragment(dtoCardapio.getNomePrato()
                        ,dtoCardapio.getDescricao(), dtoCardapio.getCategoria(), dtoCardapio.getValorPrato(), dtoCardapio.getImagem())).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_cardapio, parent, false);
        return new myviewholder(view);
    }

    public class myviewholder extends RecyclerView.ViewHolder {
        ImageView imageViewCardapio;
        TextView textViewProdutosCard;

        public myviewholder (@NonNull View itemView) {
            super(itemView);
            imageViewCardapio = itemView.findViewById(R.id.imageViewCardapio);
            textViewProdutosCard = itemView.findViewById(R.id.textViewProdutosCard);
        }
    }
}
