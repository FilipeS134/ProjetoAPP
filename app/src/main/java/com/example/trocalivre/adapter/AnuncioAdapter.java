package com.example.trocalivre.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trocalivre.model.Anuncio;

import java.util.ArrayList;
import java.util.List;

public class AnuncioAdapter extends RecyclerView.Adapter<AnuncioAdapter.AnuncioViewHolder> {

    private List<Anuncio> anuncios = new ArrayList();

    public AnuncioAdapter(List<Anuncio> anuncios) {
        this.anuncios = anuncios;
    }

    @NonNull
    @Override
    public AnuncioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull AnuncioViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return anuncios.size();
    }

    public class AnuncioViewHolder extends RecyclerView.ViewHolder{

        public AnuncioViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }
}
