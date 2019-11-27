package com.example.trocalivre.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trocalivre.R;

import com.example.trocalivre.model.Anuncio;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

public class DetalheAnuncioActivity extends AppCompatActivity {

    private CarouselView carouselView;
    private TextView titulo;
    private TextView descricao;
    private TextView cidade;
    private TextView trocoPor;
    private Anuncio anuncioSlected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_anuncio);

        carouselView = findViewById(R.id.carouselView);
        titulo = findViewById(R.id.textView_tituloDetalhe);
        descricao = findViewById(R.id.textView_descriçãoDetalhe);
        cidade = findViewById(R.id.textView_cidadeDetalhe);
        trocoPor = findViewById(R.id.textView_trocoDetalhe);



        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            anuncioSlected = (Anuncio) getIntent().getSerializableExtra("anuncio");


            titulo.setText(anuncioSlected.getTituloAnuncio());
            descricao.setText(anuncioSlected.getDescricao());
            cidade.setText(anuncioSlected.getCidade());
            trocoPor.setText(anuncioSlected.getTrocoPor());

            ImageListener imageListener = new ImageListener() {
                @Override
                public void setImageForPosition(int position, ImageView imageView) {
                    String urlString = anuncioSlected.getFotos().get(position);
                    Picasso.get().load(urlString).into(imageView);
                }
            };

            carouselView.setPageCount(anuncioSlected.getFotos().size());
            carouselView.setImageListener(imageListener);

        }

    }

    public void visualizarTelefone(View view){
        Intent i = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", anuncioSlected.getCelular(), null));
        startActivity(i);

    }

}
