package com.example.apptcc.Models;

public class DTOCardapio {
    private String nomePrato;
    private String valorPrato;
    private String descricao;
    private String imagem;
    private String categoria;

    public DTOCardapio(String nomePrato, String valorPrato, String descricao, String imagem, String categoria) {
        this.nomePrato = nomePrato;
        this.valorPrato = valorPrato;
        this.descricao = descricao;
        this.imagem = imagem;
        this.categoria = categoria;
    }

    public DTOCardapio() {
    }

    public String getNomePrato() {
        return nomePrato;
    }

    public void setNomePrato(String nomePrato) {
        this.nomePrato = nomePrato;
    }

    public String getValorPrato() {
        return "R$: " +valorPrato;
    }

    public void setValorPrato(String valorPrato) {
        this.valorPrato = valorPrato;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getCategoria() { return categoria; }

    public void setCategoria(String categoria) { this.categoria = categoria; }
}
