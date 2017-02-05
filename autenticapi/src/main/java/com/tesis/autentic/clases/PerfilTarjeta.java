package com.tesis.autentic.clases;

import com.google.gson.Gson;

/**
 * Created by Sole on 31/01/2017.
 */
public class PerfilTarjeta {
    int  id ;
    String numero;
    String bin;
    String lector;
    String nivel;
    int flag_habilitado;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getBin() {
        return bin;
    }

    public void setBin(String bin) {
        this.bin = bin;
    }

    public String getLector() {
        return lector;
    }

    public void setLector(String lector) {
        this.lector = lector;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public int getFlag_habilitado() {
        return flag_habilitado;
    }

    public void setFlag_habilitado(int flag_habilitado) {
        this.flag_habilitado = flag_habilitado;
    }

    public static PerfilTarjeta obtenerPerfil (String json){
        Gson gson = new Gson();
        return gson.fromJson(json, PerfilTarjeta.class);
    }
}
