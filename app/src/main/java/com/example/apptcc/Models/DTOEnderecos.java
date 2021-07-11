package com.example.apptcc.Models;

public class DTOEnderecos {
    private String logradouro;
    private String numero;
    private String complemento;
    private String CEP;
    private String bairro;
    private String cidade;
    private String UF;
    private String emailCliente;
    private String idEndereco;

    @Override
    public String toString() {
        return "Logradouro: " +logradouro+
                "\nNúmero: " +numero+
                "\nComplemento: " +complemento+
                "\nBairro: " +bairro+
                "\nCidade: " +cidade+
                "\nCep: " +CEP+
                "\nUF: " +UF;
    }

    public DTOEnderecos() {
    }

    public DTOEnderecos(String logradouro, String numero, String complemento, String CEP, String bairro, String cidade, String UF, String emailCliente, String idEndereco) {
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento = complemento;
        this.CEP = CEP;
        this.bairro = bairro;
        this.cidade = cidade;
        this.UF = UF;
        this.emailCliente = emailCliente;
        this.idEndereco = idEndereco;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getCEP() {
        return CEP;
    }

    public void setCEP(String CEP) {
        this.CEP = CEP;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUF() {
        return UF;
    }

    public void setUF(String UF) {
        this.UF = UF;
    }

    public String getEmailCliente() {
        return emailCliente;
    }

    public void setEmailCliente(String emailCliente) {
        this.emailCliente = emailCliente;
    }

    public String getIdEndereco() {
        return idEndereco;
    }

    public void setIdEndereco(String idEndereco) {
        this.idEndereco = idEndereco;
    }
}
