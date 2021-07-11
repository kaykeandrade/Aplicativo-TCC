package com.example.apptcc.Models;

public class DTOCliente {
    private String nomeCliente;
    private String CPF;
    private String imagemCliente;
    private String emailCliente;
    private String celular;

    public DTOCliente(String nomeCliente, String celular, String CPF, String emailCliente, String imagemCliente) {
        this.nomeCliente = nomeCliente;
        this.CPF = CPF;
        this.imagemCliente = imagemCliente;
        this.emailCliente = emailCliente;
        this.celular = celular;
    }

    public DTOCliente() {
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public String getImagemCliente() {
        return imagemCliente;
    }

    public void setImagemCliente(String imagemCliente) {
        this.imagemCliente = imagemCliente;
    }

    public String getEmailCliente() {
        return emailCliente;
    }

    public void setEmailCliente(String emailCliente) {
        this.emailCliente = emailCliente;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }
}

