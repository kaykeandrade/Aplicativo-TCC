package com.example.apptcc.Fragments;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.apptcc.Classes.Permissoes;
import com.example.apptcc.Firebase.ConfFireBase;
import com.example.apptcc.Models.DTOCliente;
import com.example.apptcc.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;

public class AlterarPerfilFragment extends Fragment {
    private String nomeCliente, celular, CPF, emailCliente, imagemCliente;
    ImageView imageViewFoto;
    Button buttonAlterar;
    private final int CAMERA = 1;
    private final int GALERIA = 2;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    Bitmap bitmap;

    String[] permissoes = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE};
    AlertDialog.Builder msg;

    //construtor que recebe os dados do adapter
    public AlterarPerfilFragment (String nomeCliente, String celular, String CPF, String emailCliente, String imagemCliente) {
        this.nomeCliente = nomeCliente;
        this.CPF = CPF;
        this.imagemCliente = imagemCliente;
        this.emailCliente = emailCliente;
        this.celular = celular;
    }

    public AlterarPerfilFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alterar_perfil, container, false);

        msg = new AlertDialog.Builder(getActivity());

        buttonAlterar = view.findViewById(R.id.buttonAlterar);

        imageViewFoto = view.findViewById(R.id.imageViewPerfilAlt);
        EditText name = view.findViewById(R.id.editTextNomeAlt);
        EditText number = view.findViewById(R.id.editTextCelularAlt);
        EditText cpf = view.findViewById(R.id.editTextCPFAlt);
        EditText mail = view.findViewById(R.id.editTextEmailAlt);

        //máscaras
        MaskEditTextChangedListener maskCPF = new MaskEditTextChangedListener("###.###.###-##", cpf);
        MaskEditTextChangedListener maskTEL = new MaskEditTextChangedListener("(##)#####-####", number);

        cpf.addTextChangedListener(maskCPF);
        number.addTextChangedListener(maskTEL);

        //strings com dados do construtor
        name.setText(nomeCliente);
        number.setText(celular);
        cpf.setText(CPF);
        mail.setText(emailCliente);
        imageViewFoto.setImageURI(Uri.parse(imagemCliente));

        //tornando a editText de email apenas para leitura
        mail.setEnabled(false);

        storageReference = ConfFireBase.getFirebaseStorage().child("imagens").child("clientes").child(imagemCliente);
        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {

            public void onSuccess(Uri uri) {
                Picasso.get().load(uri.toString()).into(imageViewFoto);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //
            }
        });

        //alterar perfil do usuário
        buttonAlterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth firebaseAuth = ConfFireBase.getFirebaseAuth();
                String enderecoImagem = uploadImagem(mail.getText().toString());
                DTOCliente dtoCliente = new DTOCliente(name.getText().toString(), number.getText().toString(), cpf.getText().toString(),
                        firebaseAuth.getCurrentUser().getEmail(), firebaseAuth.getCurrentUser().getEmail()+".JPEG");
                databaseReference = ConfFireBase.getFirebaseDatabase().child("clientes").child(firebaseAuth.getCurrentUser().getUid());
                databaseReference.setValue(dtoCliente).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            PerfilFragment perfilFragment = new PerfilFragment();
                            FragmentTransaction transaction = getFragmentManager().beginTransaction();
                            transaction.replace(R.id.frameLayout1, perfilFragment);
                            transaction.commit();
                        } else{
                            Toast.makeText(getActivity(), "Ocorreu um erro inesperado", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        //abrir câmera ou galeria
        imageViewFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Permissoes.validarPermissoes(permissoes, getActivity(), 1);
                int permissionCamera = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA);
                int permissionGaleria = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);
                if (permissionCamera == PackageManager.PERMISSION_GRANTED) {
                    msg.setPositiveButton("Câmera", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intentCamera, CAMERA);
                        }
                    });
                }
                if (permissionGaleria == PackageManager.PERMISSION_GRANTED) {
                    msg.setNeutralButton("Galeria", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                                Intent intentGaleria = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(intentGaleria, GALERIA);
                        }
                    });
                }
                if (permissionGaleria == PackageManager.PERMISSION_GRANTED && permissionCamera == PackageManager.PERMISSION_GRANTED) {
                    msg.show();
                }
            }
        });
        return view;
    }
    //método de upload de imagem
    private String uploadImagem(String emailCliente) {
        storageReference = ConfFireBase.getFirebaseStorage().child("imagens").child("clientes/"+emailCliente+".JPEG");
        if (bitmap != null) {
            imageViewFoto.setImageBitmap(bitmap);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] bytes = byteArrayOutputStream.toByteArray();

            UploadTask uploadTask = storageReference.putBytes(bytes);
        } else {
            //
        }
        return "";
    }

    //seta uma imagem no imageView a partir da câmera ou galeria
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            bitmap = null;
            if(requestCode==CAMERA){
                bitmap = (Bitmap) data.getExtras().get("data");
            }
            else if(requestCode==GALERIA){
                Uri uri = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else{
                Toast.makeText(getActivity(), "Erro ao carregar imagem.", Toast.LENGTH_SHORT).show();
            }
            if(bitmap != null) {
                imageViewFoto.setImageBitmap(bitmap);
            }
        }
        else{
            Toast.makeText(getActivity(), "Erro ao carregar imagem.", Toast.LENGTH_SHORT).show();
        }
    }

    //método de validação de permissões
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for(int i = 0; i<permissions.length; i++){
            if(permissions[i].equals("android.permission.CAMERA") && grantResults[i]==0){ ;
                msg.setPositiveButton("Câmera", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intentCamera, CAMERA);
                    }
                });
            }
            if(permissions[i].equals("android.permission.READ_EXTERNAL_STORAGE") && grantResults[i]==0){
                msg.setNeutralButton("Galeria", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        Intent intentGaleria = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intentGaleria, GALERIA);
                    }
                });
            }
        }
        msg.show();
    }
}