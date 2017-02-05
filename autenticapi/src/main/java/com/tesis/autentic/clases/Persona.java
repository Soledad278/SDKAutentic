package com.tesis.autentic.clases;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Sole on 15/01/2017.
 */
public class Persona {
    Integer id;
    String fullname;
    Integer age;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public static ArrayList <Persona> obtenerPersonas (String json){
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Persona>>(){}.getType();
        return gson.fromJson(json, type);
    }

}
