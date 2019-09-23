package com.example.trocalivre.fragment;


import android.os.Bundle;

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
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Home_Fragment extends Fragment {

    private RecyclerView recyclerView;
    private AnuncioAdapter adapterAnuncio;
    private List<Anuncio> anuncios = new ArrayList<>();
    private DatabaseReference anuncioRef;

    public Home_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        
        return view;
    }


}
