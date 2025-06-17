package com.tonala.mabeth.entities;

public class Respuestas {
    private String Respuesta;
    private String Creador;
    private String IDCreador;
    private String IDForo;
    private String IDRespuesta;

    public String getRespuesta() {
        return Respuesta;
    }

    public void setRespuesta(String respuesta) {
        Respuesta = respuesta;
    }

    public String getCreador() {
        return Creador;
    }

    public void setCreador(String creador) {
        Creador = creador;
    }

    public String getIDCreador() {
        return IDCreador;
    }

    public void setIDCreador(String IDCreador) {
        this.IDCreador = IDCreador;
    }

    public String getIDForo() {
        return IDForo;
    }

    public void setIDForo(String IDForo) {
        this.IDForo = IDForo;
    }

    public String getIDRespuesta() {
        return IDRespuesta;
    }

    public void setIDRespuesta(String IDRespuesta) {
        this.IDRespuesta = IDRespuesta;
    }
}
