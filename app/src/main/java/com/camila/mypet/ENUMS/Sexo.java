package com.camila.mypet.ENUMS;

public enum Sexo {
    FEMEA("Fêmea"),
    MACHO("Macho");

    private String descricao;

    Sexo(String descricao) {
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
