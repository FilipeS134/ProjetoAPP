package com.example.trocalivre.model;

import com.example.trocalivre.config.ConfiguracaoFirebase;
import com.example.trocalivre.helper.Base64Custom;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import java.util.List;


public class Anuncio {
    private String tituloAnuncio, descricao, trocoPor, cepCidade, idAnuncio, celular, estado, categoria;
    private List<String> fotos;

    //construtor
    public Anuncio() {
        DatabaseReference anuncioRef = ConfiguracaoFirebase.getDatabase()
                .child("meus_anuncios");
        setIdAnuncio(anuncioRef.push().getKey());
    }

    // salvando no fire base
    public void salvar(){
            // pegando id do User
            String idUsuario = ConfiguracaoFirebase.getIdUser();

            // salvando Anuncio
            DatabaseReference anuncio = ConfiguracaoFirebase.getDatabase().child("meus_anuncios");

            anuncio.child(idUsuario)
                    .child(getIdAnuncio())
                    .setValue(this);
            salvarAnuncioFeed();
    }

    public void excluir(){

        String idUsuario = ConfiguracaoFirebase.getIdUser();
        DatabaseReference anuncioRef = ConfiguracaoFirebase.getDatabase().child("meus_anuncios").child(idUsuario);

        anuncioRef.child(getIdAnuncio());

        anuncioRef.removeValue();

        excluirAnuncioPublico();
    }

    public void excluirAnuncioPublico(){

        DatabaseReference anuncioRef = ConfiguracaoFirebase.getDatabase().child("anuncios").child(getEstado())
                .child(getCategoria());

        anuncioRef.child(getIdAnuncio());

                anuncioRef.removeValue();
    }

    public void salvarAnuncioFeed(){

        // salvando Anuncio
        DatabaseReference anuncio = ConfiguracaoFirebase.getDatabase().child("anuncios");

        anuncio.child(getEstado())
                .child(getCategoria())
                .child(getIdAnuncio())
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

    public List<String> getFotos() {
        return fotos;
    }

    public void setFotos(List<String> fotos) {
        this.fotos = fotos;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
