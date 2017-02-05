package com.tesis.autentic.clases;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Sole on 24/01/2017.
 */
public class Cuenta {

    String usuario;
    String nombre;
    String apellido;
    String lector ;


    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getLector() {
        return lector;
    }

    public void setIdLector(String lector) {
        this.lector = lector;
    }

    public static ArrayList<Cuenta> obtenerCuentas (String json){
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Cuenta>>(){}.getType();
        return gson.fromJson(json, type);
    }

    public static Cuenta obtenerCuenta (String json){
        Gson gson = new Gson();
         return gson.fromJson(json, Cuenta.class);
    }

}
