package com.example.trocalivre.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.trocalivre.R;
import com.example.trocalivre.config.ConfiguracaoFirebase;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 */
public class PerfilFragment extends Fragment {

    private Button sair;
    FirebaseAuth auth;


    public PerfilFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        sair = view.findViewById(R.id.button_sair);

        sair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth = ConfiguracaoFirebase.getAutenticação();
                auth.getCurrentUser();
                auth.signOut();
                getActivity().onBackPressed();
            }
        });

        return view;
    }

}
