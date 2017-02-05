package com.tesis.autentic.clases;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Sole on 28/01/2017.
 */
public class RestriccionLector {

    int id;
    String lector;
    Long fechaD;
    Long fechaH;
    String horaD;
    String horaH;
    int posicion;
    int metros;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLector() {
        return lector;
    }

    public void setLector(String lector) {
        this.lector = lector;
    }

    public Long getFechaD() { return fechaD;
    }

    public void setFechaD(Long fechaD) {
        this.fechaD = fechaD;
    }

    public Long getFechaH() {
        return fechaH;
    }

    public void setFechaH(Long fechaH) {
        this.fechaH = fechaH;
    }

    public String getHoraD() {
        return horaD;
    }

    public void setHoraD(String horaD) {
        this.horaD = horaD;
    }

    public String getHoraH() {
        return horaH;
    }

    public void setHoraH(String horaH) {
        this.horaH = horaH;
    }

    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    public int getMetros() {
        return metros;
    }

    public void setMetros(int metros) {
        this.metros = metros;
    }

    public static ArrayList<RestriccionLector> obtenerRestriccion (String json){
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<RestriccionLector>>(){}.getType();
        return gson.fromJson(json, type);
    }
}
