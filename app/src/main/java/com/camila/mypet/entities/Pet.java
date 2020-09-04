package com.camila.mypet.entities;

import java.util.Date;

public class Pet {

    private String chave;
    private String nome;
    private Double peso;
    private String raca;
    private Date dataNascimento;
    private String sexo;
    private String tipoDePet;
    private String fotoPet;

    public Pet() {

    }

    public Pet(String chave, String nome, Double peso, String raca, Date dataNascimento, String sexo, String tipoDePet, String fotoPet) {
        this.chave = chave;
        this.nome = nome;
        this.peso = peso;
        this.raca = raca;
        this.dataNascimento = dataNascimento;
        this.sexo = sexo;
        this.tipoDePet = tipoDePet;
        this.fotoPet = fotoPet;
    }

    public String getChave() {
        return chave;
    }

    public void setChave(String chave) {
        this.chave = chave;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getTipoDePet() {
        return tipoDePet;
    }

    public void setTipoDePet(String tipoDePet) {
        this.tipoDePet = tipoDePet;
    }

    public String getFotoPet() {
        return fotoPet;
    }

    public void setFotoPet(String fotoPet) {
        this.fotoPet = fotoPet;
    }
}
