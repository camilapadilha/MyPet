package com.camila.mypet.entities;

import java.sql.Time;
import java.util.Date;

public class Lembrete {
    private String nome;
    private Date data;
    private Time horario;
    private String comentario;
    private String chave;

    public Lembrete() {

    }

    public Lembrete(String nome, Date data, Time horario, String comentario, String chave) {
        this.nome = nome;
        this.data = data;
        this.horario = horario;
        this.comentario = comentario;
        this.chave = chave;
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

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Time getHorario() {
        return horario;
    }

    public void setHorario(Time horario) {
        this.horario = horario;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}
