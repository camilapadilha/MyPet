package com.camila.mypet.entities;

import com.camila.mypet.ENUMS.Sexo;
import com.camila.mypet.ENUMS.TipoDePet;

import java.util.Date;

public class Pet {

    private String chave;
    private String nome;
    private Double peso;
    private String raca;
    private Date dataNascimento;
    private Sexo sexo;
    private TipoDePet tipoDePet;

    public Pet(String chave, String nome, Double peso, String raca, Date dataNascimento, Sexo sexo, TipoDePet tipoDePet) {
        this.chave = chave;
        this.nome = nome;
        this.peso = peso;
        this.raca = raca;
        this.dataNascimento = dataNascimento;
        this.sexo = sexo;
        this.tipoDePet = tipoDePet;
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

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public TipoDePet getTipoDePet() {
        return tipoDePet;
    }

    public void setTipoDePet(TipoDePet tipoDePet) {
        this.tipoDePet = tipoDePet;
    }
}
