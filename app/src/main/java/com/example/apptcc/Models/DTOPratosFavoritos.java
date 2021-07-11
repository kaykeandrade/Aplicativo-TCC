package com.example.apptcc.Models;

public class DTOPratosFavoritos {
    private String nomePrato;
    private String descricaoPrato;
    private String valorPrato;
    private String categoria;
    private String imagem;
    private String emailCliente;
    private String dataAdd;
    private String idPrato;

    public DTOPratosFavoritos() {
    }

    public DTOPratosFavoritos(String nomePrato, String descricaoPrato, String valorPrato, String categoria, String imagem, String emailCliente, String dataAdd, String idPrato) {
        this.nomePrato = nomePrato;
        this.descricaoPrato = descricaoPrato;
        this.valorPrato = valorPrato;
        this.categoria = categoria;
        this.imagem = imagem;
        this.emailCliente = emailCliente;
        this.dataAdd = dataAdd;
        this.idPrato = idPrato;
    }

    public String getNomePrato() {
        return nomePrato;
    }

    public void setNomePrato(String nomePrato) {
        this.nomePrato = nomePrato;
    }

    public String getDescricaoPrato() {
        return descricaoPrato;
    }

    public void setDescricaoPrato(String descricaoPrato) {
        this.descricaoPrato = descricaoPrato;
    }

    public String getValorPrato() {
        return valorPrato;
    }

    public void setValorPrato(String valorPrato) {
        this.valorPrato = valorPrato;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getEmailCliente() {
        return emailCliente;
    }

    public void setEmailCliente(String emailCliente) {
        this.emailCliente = emailCliente;
    }

    public String getDataAdd() {
        return dataAdd;
    }

    public void setDataAdd(String dataAdd) {
        this.dataAdd = dataAdd;
    }

    public String getIdPrato() {
        return idPrato;
    }

    public void setIdPrato(String idPrato) {
        this.idPrato = idPrato;
    }
}