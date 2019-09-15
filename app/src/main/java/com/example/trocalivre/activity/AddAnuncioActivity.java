package com.example.trocalivre.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.trocalivre.R;
import com.example.trocalivre.model.Anuncio;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

public class AddAnuncioActivity extends AppCompatActivity {
    private EditText titulo,desc,trocoPor,cep;
    private ImageView imagem;
    private CircularProgressButton botaoSalvar;
    private Anuncio anuncio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_anuncio);

        titulo = findViewById(R.id.editText_titulo);
        desc = findViewById(R.id.editText_desc);
        trocoPor = findViewById(R.id.editText_trocoPor);
        cep = findViewById(R.id.editText_cep);
        imagem = findViewById(R.id.imageView_imagens);
        botaoSalvar = findViewById(R.id.button_salvarAnuncio);

        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                botaoSalvar.startAnimation();
                criarAnuncio();
            }
        });



    }

    String teste;
    public void criarAnuncio(){
        String titulo = this.titulo.getText().toString();
        String desc = this.desc.getText().toString();
        String trocoPor = this.trocoPor.getText().toString();
        String cep = this.cep.getText().toString();

        if (!titulo.isEmpty()){
            if (!desc.isEmpty()){
                if (!trocoPor.isEmpty()){
                    if (!cep.isEmpty()){
                        anuncio = new Anuncio();
                        anuncio.setTituloAnuncio(titulo);
                        anuncio.setDescricao(desc);
                        anuncio.setTrocoPor(trocoPor);
                        anuncio.setCepCidade(cep);
                        anuncio.salvar();
                        Toast.makeText(this, "Anúncio salvo com sucesso!", Toast.LENGTH_SHORT).show();
                        finish();
                    }else {
                        botaoSalvar.revertAnimation();
                        Toast.makeText(this, "Digite um CEP", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    botaoSalvar.revertAnimation();
                    Toast.makeText(this, "O campo de Interesses está vazio", Toast.LENGTH_SHORT).show();
                }
            }else {
                botaoSalvar.revertAnimation();
                Toast.makeText(this, "A descrição está vazia", Toast.LENGTH_SHORT).show();
            }
        }else {
            botaoSalvar.revertAnimation();
            Toast.makeText(this, "O Título está vazio", Toast.LENGTH_SHORT).show();
        }
    }

}
