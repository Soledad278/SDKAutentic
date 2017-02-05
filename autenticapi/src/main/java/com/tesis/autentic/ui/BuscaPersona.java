package com.tesis.autentic.ui;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.tesis.autentic.R;
import com.tesis.autentic.clases.HttpRequest;
import com.tesis.autentic.clases.Persona;
import com.tesis.autentic.clases.PersonaAdapter;

import java.util.ArrayList;

public class BuscaPersona extends AppCompatActivity {
    Spinner spinnerParametro;
    EditText dato;
    ListView listViewPersona;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busca_persona);

        inicializar();

    }

    public void inicializar(){
        this.spinnerParametro = (Spinner) findViewById(R.id.spinnerPersonParametros);
        this.dato = (EditText) findViewById(R.id.EditTextDato);
        this.listViewPersona = (ListView) findViewById(R.id.ListViewPersona);

        new getPersonas().execute("http://192.168.0.3:8080/AutenticRest/api/personas");
    }
    // Get personas

    private class getPersonas extends AsyncTask<String, Void, String> {
        public String doInBackground(String... params) {
            try {
                return HttpRequest.get(params[0]).accept("application/json").body();
            } catch (Exception e) {
                return "";
            }
        }


         public void onPostExecute (String result) {
             if (result.isEmpty()) {
                  Toast.makeText(BuscaPersona.this, "No se generaron resultado", Toast.LENGTH_LONG).show();
              } else {
                 ArrayList<Persona> personas =  Persona.obtenerPersonas(result);
                 ArrayList<Persona> personas_aux = new ArrayList<>();

            if (spinnerParametro.getSelectedItem().equals("Listar Todo")){
                personas_aux = personas;
            } else{
                for (int i = 0; i < personas.size() ; i++) {
                    switch (spinnerParametro.getSelectedItem().toString()){
                        case "id":
                            if (personas.get(i).getId().equals(dato.getText().toString().trim())){
                               personas_aux.add(personas.get(i));
                        } break;
                        case "fullname":
                            if (personas.get(i).getFullname().equals(dato.getText().toString().trim())){
                                personas_aux.add(personas.get(i));
                        } break;

                        case "age":
                            if (personas.get(i).getAge().equals(dato.getText().toString().trim())){
                                personas_aux.add(personas.get(i));
                            } break;
                    }

                }
            }
            if (personas_aux.size() != 0){
                PersonaAdapter adapter = new PersonaAdapter(BuscaPersona.this, personas_aux);
                listViewPersona.setAdapter(adapter);
                }
            }
        }

    }
}

