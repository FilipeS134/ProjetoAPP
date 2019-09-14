package com.example.trocalivre.model;

import com.example.trocalivre.config.ConfiguracaoFirebase;
import com.example.trocalivre.helper.Base64Custom;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import java.util.Random;

public class Anuncio {
    private String tituloAnuncio, descricao, trocoPor, cepCidade, idAnuncio;
    private int imagem;

    public Anuncio() {
    }

    public void salvar(){
        FirebaseAuth auth = ConfiguracaoFirebase.getAutenticação();
        String idUsuario = Base64Custom.codificarBase64(auth.getCurrentUser().getEmail());
        DatabaseReference anuncio = ConfiguracaoFirebase.getDatabase();
        anuncio.child("anuncios")
                .child(idUsuario)
                .push()
                .setValue(this);

    }

    @Exclude
    public String getIdAnuncio() {
        return idAnuncio;
    }

    public void setIdAnuncio(String idAnuncio) {
        this.idAnuncio = idAnuncio;
    }

    public String getTituloAnuncio() {
        return tituloAnuncio;
    }

    public void setTituloAnuncio(String tituloAnuncio) {
        this.tituloAnuncio = tituloAnuncio;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTrocoPor() {
        return trocoPor;
    }

    public void setTrocoPor(String trocoPor) {
        this.trocoPor = trocoPor;
    }

    public String getCepCidade() {
        return cepCidade;
    }

    public void setCepCidade(String cepCidade) {
        this.cepCidade = cepCidade;
    }

    public int getImagem() {
        return imagem;
    }

    public void setImagem(int imagem) {
        this.imagem = imagem;
    }
}
