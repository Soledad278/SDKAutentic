package com.tesis.autentic.clases;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tesis.autentic.R;

import java.util.ArrayList;

/**
 * Created by Sole on 15/01/2017.
 */
  public class PersonaAdapter extends BaseAdapter {
    Context context;
    ArrayList<Persona> personaArrayList;

    public PersonaAdapter (Context context, ArrayList<Persona> personaArrayList){
        this.context = context;
        this.personaArrayList = personaArrayList;
    }

    @Override
    public int getCount() {
        return this.personaArrayList.size();
    }

    @Override
    public Object getItem(int position) {

        return this.personaArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Creacion de vista
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.layout_persona, parent, false);

//Objeto del formulario
        TextView id = (TextView) view.findViewById(R.id.textView5);
        TextView fullname = (TextView) view.findViewById(R.id.textView6);
        TextView age = (TextView) view.findViewById(R.id.textView7);

        Persona persona = this.personaArrayList.get(position);
        if(persona != null) {
            id.setText("id: " + persona.getId());
            fullname.setText("fullname: "+ persona.getFullname());
            age.setText("age: " + persona.getAge());
        }
        return view;
    }

}
