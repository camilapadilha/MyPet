package com.camila.mypet.ENUMS;


public enum TipoDePet {
    CACHORRO("Cachorro"),
    GATO("Gato"),
    COELHO("Coelho"),
    PASSARO("Pássaro"),
    ROEDOR("Roedor"),
    OUTRO("Outro");

    private String descricao;

    TipoDePet(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
