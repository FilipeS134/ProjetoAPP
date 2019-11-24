package com.example.trocalivre.config;

import com.example.trocalivre.helper.Base64Custom;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ConfiguracaoFirebase {

    private static FirebaseAuth autenticacao;
    private static DatabaseReference firebase;
    private static StorageReference storage;

    public static String getIdUser(){
        String emailUser = autenticacao.getCurrentUser().getEmail();
        String idUser = Base64Custom.codificarBase64(emailUser);

        return idUser;

    }

    public static FirebaseAuth getAutenticação(){
        if (autenticacao == null){
            autenticacao = FirebaseAuth.getInstance();
        }
        return autenticacao;
    }

    public static DatabaseReference getDatabase(){
        if (firebase == null){
            firebase = FirebaseDatabase.getInstance().getReference();
        }
        return firebase;
    }

    public static StorageReference getStorage(){
        if (storage == null){
            storage = FirebaseStorage.getInstance().getReference();
        }
        return storage;
    }
}
