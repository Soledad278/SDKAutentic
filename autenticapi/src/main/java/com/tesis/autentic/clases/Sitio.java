package com.tesis.autentic.clases;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Sole on 31/01/2017.
 */
public class Sitio {

    int id;
    String descripcion ;
    String url;
    String nivel;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public static ArrayList<Sitio> obtenerSitios (String json){
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Sitio>>(){}.getType();
        return gson.fromJson(json, type);
    }
}
