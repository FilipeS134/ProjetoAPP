package com.example.trocalivre.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.trocalivre.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenssagemFragment extends Fragment {


    public MenssagemFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menssagem, container, false);
    }

}
