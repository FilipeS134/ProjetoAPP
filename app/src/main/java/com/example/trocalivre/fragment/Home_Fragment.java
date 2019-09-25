package com.example.trocalivre.fragment;


import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.trocalivre.R;
import com.example.trocalivre.adapter.AnuncioAdapter;
import com.example.trocalivre.config.ConfiguracaoFirebase;
import com.example.trocalivre.model.Anuncio;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dmax.dialog.SpotsDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class Home_Fragment extends Fragment {
    private RecyclerView recyclerViewFeed;
    private AnuncioAdapter adapterAnuncio;
    private FirebaseAuth autenticacao;
    private List<Anuncio> listaAnuncios = new ArrayList<>();
    private DatabaseReference anuncioRef;
    private AlertDialog alertDialog;

    public Home_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // config Inicial
        recyclerViewFeed = view.findViewById(R.id.recyclerView_home);
        autenticacao = ConfiguracaoFirebase.getAutenticação();
        anuncioRef = ConfiguracaoFirebase.getDatabase().child("anuncios");

        // configuração RecyclerView
        adapterAnuncio = new AnuncioAdapter(listaAnuncios, getContext());
        recyclerViewFeed.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewFeed.setHasFixedSize(true);
        recyclerViewFeed.setAdapter(adapterAnuncio);

        recuperarAnuncioFeed();

        
        return view;
    }

    public void recuperarAnuncioFeed(){

        alertDialog = new SpotsDialog.Builder()
                .setContext(getContext())
                .setCancelable(false)
                .setMessage("Buscando anúncios")
                .build();
        alertDialog.show();

        listaAnuncios.clear();
        anuncioRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot estados : dataSnapshot.getChildren()){
                    for (DataSnapshot categorias : estados.getChildren()){
                        for (DataSnapshot anuncios : categorias.getChildren()){
                            Anuncio anuncio = anuncios.getValue(Anuncio.class);
                            listaAnuncios.add(anuncio);

                        }
                    }

                }

                Collections.reverse(listaAnuncios);
                adapterAnuncio.notifyDataSetChanged();
                alertDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
