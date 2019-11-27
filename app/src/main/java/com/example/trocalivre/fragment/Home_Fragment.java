package com.example.trocalivre.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.trocalivre.R;
import com.example.trocalivre.activity.DetalheAnuncioActivity;
import com.example.trocalivre.adapter.AnuncioAdapter;
import com.example.trocalivre.config.ConfiguracaoFirebase;
import com.example.trocalivre.helper.RecyclerItemClickListener;
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
    private Button buttonEstado, buttonCategoria;
    private String filtroEstado = "";
    private String filtroCategoria = "";
    private Boolean filtrandoPorEstado = false;

    public Home_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // config Inicial
        buttonCategoria = view.findViewById(R.id.button_Categoria);
        buttonEstado = view.findViewById(R.id.button_Estado);
        recyclerViewFeed = view.findViewById(R.id.recyclerView_home);
        autenticacao = ConfiguracaoFirebase.getAutenticação();
        anuncioRef = ConfiguracaoFirebase.getDatabase().child("anuncios");

        // configuração RecyclerView
        adapterAnuncio = new AnuncioAdapter(listaAnuncios, getContext());
        recyclerViewFeed.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewFeed.setHasFixedSize(true);
        recyclerViewFeed.setAdapter(adapterAnuncio);

        recuperarAnuncioFeed();

        buttonEstado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               recuperarAnuncioPorEstado();
            }
        });

        buttonCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recuperarAnuncioPorCategoria();
            }
        });

        recyclerViewFeed.addOnItemTouchListener(new RecyclerItemClickListener(
                view.getContext(),
                recyclerViewFeed,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        Anuncio anuncioSelecionado = listaAnuncios.get(position);
                        Intent i = new Intent(getContext(), DetalheAnuncioActivity.class);
                        i.putExtra("anuncio", anuncioSelecionado);
                        startActivity(i);

                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                }));
        
        return view;
    }
    private void FiltrarPorEstado() {

        alertDialog = new SpotsDialog.Builder()
                .setContext(getContext())
                .setCancelable(false)
                .setMessage("Buscando anúncios")
                .build();
        alertDialog.show();

        anuncioRef = ConfiguracaoFirebase.getDatabase()
                .child("anuncios")
                .child(filtroEstado);

        anuncioRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                listaAnuncios.clear();

                for (DataSnapshot categorias : dataSnapshot.getChildren()){
                    for (DataSnapshot anuncios : categorias.getChildren()){
                        Anuncio anuncio = anuncios.getValue(Anuncio.class);
                        listaAnuncios.add(anuncio);

                    }
                }

                Collections.reverse(listaAnuncios);
                adapterAnuncio.notifyDataSetChanged();
                alertDialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                alertDialog.dismiss();
            }
        });

    }

    private void FiltrarPorCategoria() {

        alertDialog = new SpotsDialog.Builder()
                .setContext(getContext())
                .setCancelable(false)
                .setMessage("Buscando anúncios")
                .build();
        alertDialog.show();

        anuncioRef = ConfiguracaoFirebase.getDatabase()
                .child("anuncios")
                .child(filtroEstado)
                .child(filtroCategoria);

        anuncioRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                listaAnuncios.clear();

                for (DataSnapshot anuncios : dataSnapshot.getChildren()){
                    Anuncio anuncio = anuncios.getValue(Anuncio.class);
                    listaAnuncios.add(anuncio);
                }

                Collections.reverse(listaAnuncios);
                adapterAnuncio.notifyDataSetChanged();
                alertDialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                alertDialog.dismiss();
            }
        });

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

                    alertDialog.dismiss();
            }
        });
    }

    public void recuperarAnuncioPorEstado(){
        AlertDialog.Builder dialogEstado = new AlertDialog.Builder(getContext());
        dialogEstado.setTitle("Selecione o estado desejado");

        //Configurar Spinner
        View viewSpinner = getLayoutInflater().inflate(R.layout.dialog_spinner, null);

        //Configuração Spinner estados
        final Spinner spinnerEstados = viewSpinner.findViewById(R.id.spinner_filtro);
        String[] estados = getResources().getStringArray(R.array.estados);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, estados);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEstados.setAdapter(adapter);


        dialogEstado.setView(viewSpinner);

        dialogEstado.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                filtroEstado = spinnerEstados.getSelectedItem().toString();
                FiltrarPorEstado();
                filtrandoPorEstado = true;
            }
        });
        dialogEstado.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog dialog = dialogEstado.create();
        dialog.show();
    }

    public void recuperarAnuncioPorCategoria() {

        if (filtrandoPorEstado == true) {

            AlertDialog.Builder dialogEstado = new AlertDialog.Builder(getContext());
            dialogEstado.setTitle("Selecione uma categoria desejada");

            //Configurar Spinner
            View viewSpinner = getLayoutInflater().inflate(R.layout.dialog_spinner, null);

            //Configuração Spinner estados
            final Spinner spinnerCategoria = viewSpinner.findViewById(R.id.spinner_filtro);
            String[] categorias = getResources().getStringArray(R.array.categoria);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, categorias);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerCategoria.setAdapter(adapter);


            dialogEstado.setView(viewSpinner);

            dialogEstado.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    filtroCategoria = spinnerCategoria.getSelectedItem().toString();

                    FiltrarPorCategoria();
                }
            });
            dialogEstado.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            AlertDialog dialog = dialogEstado.create();
            dialog.show();

        } else {
            Toast.makeText(getContext(), "Escolha primeiro uma região!", Toast.LENGTH_SHORT).show();
        }

    }
}
