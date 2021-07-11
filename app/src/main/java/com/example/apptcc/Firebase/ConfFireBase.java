package com.example.apptcc.Firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ConfFireBase {
    private static StorageReference firebaseStorage;
    private static DatabaseReference firebaseDatabase;
    private static FirebaseAuth firebaseAuth;

    public static StorageReference getFirebaseStorage(){
        if(firebaseStorage==null){
            firebaseStorage = FirebaseStorage.getInstance().getReference();
        }
        return firebaseStorage;
    }

    public static FirebaseAuth getFirebaseAuth(){
        if(firebaseAuth==null){
            firebaseAuth = FirebaseAuth.getInstance();
        }
        return firebaseAuth;
    }

    public static DatabaseReference getFirebaseDatabase(){
        if(firebaseDatabase==null){
            firebaseDatabase = FirebaseDatabase.getInstance().getReference();
        }
        return firebaseDatabase;
    }
}