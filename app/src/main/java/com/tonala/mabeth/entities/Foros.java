package com.tonala.mabeth.entities;

public class Foros {
    private String Creador;
    private String Cuerpo;
    private String URLimagen;
    private String IDForo;
    private String Tema;
    private String Titulo;

    public String getIDForo() {
        return IDForo;
    }

    public void setIDForo(String IDForo) {
        this.IDForo = IDForo;
    }

    public String getCreador() {
        return Creador;
    }

    public void setCreador(String creador) {
        Creador = creador;
    }

    public String getCuerpo() {
        return Cuerpo;
    }

    public void setCuerpo(String cuerpo) {
        Cuerpo = cuerpo;
    }

    public String getURLimagen() {
        return URLimagen;
    }

    public void setURLimagen(String URLimagen) {
        this.URLimagen = URLimagen;
    }

    public String getTema() {
        return Tema;
    }

    public void setTema(String tema) {
        Tema = tema;
    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String titulo) {
        Titulo = titulo;
    }
}
