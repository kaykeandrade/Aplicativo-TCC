package com.example.apptcc.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apptcc.Activitys.EnderecoListaActivity;
import com.example.apptcc.Activitys.LoginActivity;
import com.example.apptcc.Firebase.ConfFireBase;
import com.example.apptcc.Fragments.AlterarPerfilFragment;
import com.example.apptcc.Models.DTOCliente;
import com.example.apptcc.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class AdapterPerfil extends FirebaseRecyclerAdapter<DTOCliente, AdapterPerfil.MyViewHolder> {

    StorageReference storageReference;
    Context mContext;

    private static Activity desembrulhar (Context context) {
        while (!(context instanceof Activity) && context instanceof ContextWrapper) {
            context = ((ContextWrapper) context).getBaseContext();
        }
        return (Activity) context;
    }

    public AdapterPerfil(@NonNull FirebaseRecyclerOptions<DTOCliente> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull AdapterPerfil.MyViewHolder holder, int i, DTOCliente dtoCliente) {
        holder.textViewNome.setText("Nome: "+dtoCliente.getNomeCliente());
        holder.textViewCelular.setText("Celular: " +dtoCliente.getCelular());
        holder.textViewCPF.setText("CPF: "+dtoCliente.getCPF());
        holder.textViewEmail.setText("Email: "+dtoCliente.getEmailCliente());
        holder.textViewAlterarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) desembrulhar(view.getContext());
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout1, new AlterarPerfilFragment(dtoCliente.getNomeCliente(), dtoCliente.getCelular(), dtoCliente.getCPF(), dtoCliente.getEmailCliente(),dtoCliente.getImagemCliente())).addToBackStack(null).commit();
            }
        });

        holder.textViewEndereco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) desembrulhar(v.getContext());
                activity.startActivity(new Intent(activity, EnderecoListaActivity.class));

            }
        });

        String imagem = dtoCliente.getImagemCliente();

        storageReference = ConfFireBase.getFirebaseStorage().child("imagens").child("clientes").child(imagem);
        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {

            public void onSuccess(Uri uri) {
                Picasso.get().load(uri.toString()).into(holder.imageViewPerfil);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //
            }
        });

        holder.buttonExcluirUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String id = FirebaseAuth.getInstance().getUid();
                String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();

                //excluir os dados do usuário no realtime database
                DatabaseReference dbRef1 = ConfFireBase.getFirebaseDatabase().child("clientes").child(id);
                dbRef1.removeValue();

                //excluir todos os endereços cadastrados pelo usuário logado
                DatabaseReference dbRef2 = FirebaseDatabase.getInstance().getReference();
                Query query2 = dbRef2.child("enderecos").orderByChild("emailCliente").equalTo(email);
                query2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot enderecoSnapshot: dataSnapshot.getChildren()) {
                            enderecoSnapshot.getRef().removeValue();
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                //excluir todos os pratos favoritos do usuário logado ao excluir o usuário
                DatabaseReference dbRef3 = FirebaseDatabase.getInstance().getReference();
                Query query3 = dbRef2.child("pratosFavoritos").orderByChild("emailCliente").equalTo(email);
                query3.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot favoritoSnapshot: dataSnapshot.getChildren()) {
                            favoritoSnapshot.getRef().removeValue();
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                //excluir a imagem do usuário no storage
                storageReference = ConfFireBase.getFirebaseStorage();
                StorageReference imagemRef = storageReference.child("imagens").child("clientes/"+email+".JPEG");
                imagemRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Imagem deletada
                    }
                });

                //excluir usuário no auth
                user.delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    AppCompatActivity activity = (AppCompatActivity) desembrulhar(v.getContext());
                                    activity.startActivity(new Intent(activity, LoginActivity.class));
                                }
                                else{
                                    Toast.makeText(mContext, "Ocorreu um erro inesperado", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });

                holder.buttonDeslogar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AppCompatActivity activity = (AppCompatActivity) desembrulhar(v.getContext());
                        activity.startActivity(new Intent(activity, LoginActivity.class));
                    }
                });
            }

        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_perfil, parent, false);
            return new AdapterPerfil.MyViewHolder(view);
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            ImageView imageViewPerfil;
            TextView textViewNome, textViewCelular, textViewCPF, textViewEmail, textViewAlterarPerfil, textViewEndereco;
            Button buttonExcluirUser, buttonDeslogar;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                imageViewPerfil = itemView.findViewById(R.id.imageViewPerfil);
                textViewNome = itemView.findViewById(R.id.textViewNomePerfil);
                textViewCelular = itemView.findViewById(R.id.textViewCelularPerfil);
                textViewCPF = itemView.findViewById(R.id.textViewCPFPerfil);
                textViewEmail = itemView.findViewById(R.id.textViewEmailPerfil);
                textViewAlterarPerfil = itemView.findViewById(R.id.textViewAlterarPerfil);
                textViewEndereco = itemView.findViewById(R.id.textViewEndereco);
                buttonExcluirUser = itemView.findViewById(R.id.buttonExcluirUser);
                buttonDeslogar = itemView.findViewById(R.id.buttonDeslogar);
            }
        }
    }
