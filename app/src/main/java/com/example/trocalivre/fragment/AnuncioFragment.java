package com.example.trocalivre.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.trocalivre.R;
import com.example.trocalivre.activity.AddAnuncioActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class AnuncioFragment extends Fragment {

    private FloatingActionButton addAnuncio;

    public AnuncioFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_anuncio, container, false);

        addAnuncio = view.findViewById(R.id.add_anuncio);
        addAnuncio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), AddAnuncioActivity.class));
            }
        });


        return view;
    }

}
