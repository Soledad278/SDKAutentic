package com.tesis.autentic.clases;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
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
public class SitioAdapter extends BaseAdapter {
    Context context;
    ArrayList<Sitio> SitioArrayList;

    public SitioAdapter (Context context, ArrayList<Sitio> SitioArrayList){
        this.context = context;
        this.SitioArrayList = SitioArrayList;
    }

    @Override
    public int getCount() {
        return this.SitioArrayList.size();
    }

    @Override
    public Object getItem(int position) {

        return this.SitioArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Creacion de vista
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.layout_sitio, parent, false);

        if(position %2 == 1)
        {
            // Set a background color for ListView regular row/item
            view.setBackgroundColor(Color.parseColor("#f7fcf5"));
        }
        else
        {
            // Set the background color for alternate row/item
            view.setBackgroundColor(Color.parseColor("#e4f4dc"));
        }
//Objeto del formulario

        TextView descripcion = (TextView) view.findViewById(R.id.textView5);
        TextView url = (TextView) view.findViewById(R.id.textView6);
        descripcion.setTextSize(TypedValue.COMPLEX_UNIT_DIP,20);
        url.setTextSize(TypedValue.COMPLEX_UNIT_DIP,15);

        Sitio sitio = this.SitioArrayList.get(position);
        if(sitio != null) {
            descripcion.setText(sitio.getDescripcion());
            url.setText("URL: "+ sitio.getUrl());

        }
        return view;
    }

}

