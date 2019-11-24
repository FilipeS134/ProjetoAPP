package com.example.trocalivre.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trocalivre.R;
import com.example.trocalivre.model.Anuncio;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AnuncioAdapter extends RecyclerView.Adapter<AnuncioAdapter.AnuncioViewHolder> {

    private List<Anuncio> anuncios;
    private Context context;

    public AnuncioAdapter(List<Anuncio> anuncios, Context context) {
        this.anuncios = anuncios;
        this.context = context;
    }

    @NonNull
    @Override
    public AnuncioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_anuncio, parent, false);
        return new AnuncioViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull AnuncioViewHolder holder, int position) {

        Anuncio anuncio = anuncios.get(position);

        holder.titulo.setText(anuncio.getTituloAnuncio());
        holder.desc.setText(anuncio.getDescricao());
        holder.cepCidade.setText(anuncio.getCepCidade());

        //pegar a primeira imagen da lista
        List<String> urlFotos = anuncio.getFotos();
        String urlCapa = urlFotos.get(0);

        Picasso.get().load(urlCapa).into(holder.imagen);



    }

    @Override
    public int getItemCount() {
        return anuncios.size();
    }

    public class AnuncioViewHolder extends RecyclerView.ViewHolder{

        ImageView imagen;
        TextView titulo, desc, cepCidade;

        public AnuncioViewHolder(@NonNull View itemView) {
            super(itemView);

            imagen = itemView.findViewById(R.id.imageAnuncioAdpter);
            titulo = itemView.findViewById(R.id.textTituloAdpter);
            desc = itemView.findViewById(R.id.textDescAdpter);
            cepCidade = itemView.findViewById(R.id.textCidadeAdpter);
        }
    }
}
