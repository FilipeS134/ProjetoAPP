package com.example.trocalivre.fragment;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.trocalivre.R;
import com.example.trocalivre.activity.AddAnuncioActivity;
import com.example.trocalivre.adapter.AnuncioAdapter;
import com.example.trocalivre.config.ConfiguracaoFirebase;
import com.example.trocalivre.helper.RecyclerItemClickListener;
import com.example.trocalivre.model.Anuncio;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dmax.dialog.SpotsDialog;


public class AnuncioFragment extends Fragment {

    private FloatingActionButton addAnuncio;
    private RecyclerView recyclerViewAnuncios;
    private List<Anuncio> anuncios = new ArrayList<>();
    private AnuncioAdapter anuncioAdapter;
    private DatabaseReference anuncioUserRef;
    private AlertDialog alertDialog;

    public AnuncioFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_anuncio, container, false);

        anuncioUserRef = ConfiguracaoFirebase.getDatabase().child("meus_anuncios")
                .child(ConfiguracaoFirebase.getIdUser());

        recyclerViewAnuncios = view.findViewById(R.id.recyclerView_MeusAnuncios);

        addAnuncio = view.findViewById(R.id.add_anuncio);
        addAnuncio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), AddAnuncioActivity.class));
            }
        });


        anuncioAdapter = new AnuncioAdapter(anuncios, getContext());

        recyclerViewAnuncios.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewAnuncios.setHasFixedSize(true);
        recyclerViewAnuncios.setAdapter(anuncioAdapter);

        recuperarAnuncio();

        recyclerViewAnuncios.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), recyclerViewAnuncios, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }

            @Override
            public void onLongItemClick(View view, int position) {
                Anuncio anuncioSelecionado = anuncios.get(position);

                anuncioSelecionado.excluir();
                anuncioAdapter.notifyDataSetChanged();
            }

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        }));

        return view;
    }

    private void recuperarAnuncio() {

        alertDialog = new SpotsDialog.Builder()
                .setContext(getContext())
                .setCancelable(false)
                .setMessage("Buscando anúncios")
                .build();
        alertDialog.show();

        anuncioUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                anuncios.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    anuncios.add(ds.getValue(Anuncio.class));
                }
                Collections.reverse(anuncios);
                anuncioAdapter.notifyDataSetChanged();
                alertDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
