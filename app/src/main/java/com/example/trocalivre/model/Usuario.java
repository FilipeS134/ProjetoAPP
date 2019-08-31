package com.example.trocalivre.model;

public class Usuario {
    private String nome;
    private String email;
    private String senha;
    private int imagem;
    private Integer telefone;

    public Usuario() {
    }

    public Usuario(String nome, String email, String senha, int imagem, Integer telefone) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.imagem = imagem;
        this.telefone = telefone;
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

    public Integer getTelefone() {
        return telefone;
    }

    public void setTelefone(Integer telefone) {
        this.telefone = telefone;
    }
}
