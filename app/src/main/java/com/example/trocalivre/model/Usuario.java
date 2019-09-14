package com.example.trocalivre.model;

import com.example.trocalivre.config.ConfiguracaoFirebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

public class Usuario {
    private String nome, idUsuario;
    private String email;
    private String senha;
    private int imagem;
    private String telefone;

    public Usuario() {

    }

    public void salvar(){
        DatabaseReference usuario = ConfiguracaoFirebase.getDatabase();
        usuario.child("usuarios")
                .child(this.idUsuario)
                .setValue(this);
    }
    @Exclude
    public String getidUsuario() {
        return idUsuario;
    }


    public void setidUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Exclude
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public int getImagem() {
        return imagem;
    }

    public void setImagem(int imagem) {
        this.imagem = imagem;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
