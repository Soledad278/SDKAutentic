    package com.tesis.autentic.ui;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tesis.autentic.R;
import com.tesis.autentic.clases.HttpRequest;
import com.tesis.autentic.clases.PerfilTarjeta;
import com.tesis.autentic.clases.Sitio;
import com.tesis.autentic.clases.SitioAdapter;

import java.util.ArrayList;


    public class BuscaSitios extends AppCompatActivity {

        EditText dato;
        ListView listViewSitio;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_busca_sitios);

            inicializar();

        }

        public void inicializar(){

            this.dato = (EditText) findViewById(R.id.EditTextDato);
            this.listViewSitio = (ListView) findViewById(R.id.ListViewSitio);
            String tarjeta = "4517616967708018";
            new getNivel().execute("http://192.168.0.3:8080/AutenticRest/api/tarjeta/perfil/" + (tarjeta) + "/");
        }


        private class getNivel extends AsyncTask<String, Void, String> {
            public String doInBackground(String... params) {
                try {
                    return HttpRequest.get(params[0]).accept("application/json").body();
                } catch (Exception e) {
                    return "";
                }
            }

            public void onPostExecute(String result) {
                TextView tv = (TextView) findViewById(R.id.mensaje);
                if (result.isEmpty()) {
                    tv.setText("Nook");
                } else {
                    PerfilTarjeta perfilT = PerfilTarjeta.obtenerPerfil(result);
                    if (perfilT != null) {
                        String nivel = perfilT.getNivel();
                        buscarSitios(nivel);
//                            tv.setText("ok");
                     } else {
                            tv.setText("No tiene sitios disponibles");
                     }

                    }
                }
            }

            public void buscarSitios (String nivel) {
                new getSitios ().execute("http://192.168.0.3:8080/AutenticRest/api/sitio/nivel/" + (nivel) + "/");

            }

            private class getSitios  extends AsyncTask<String, Void, String> {
                public String doInBackground(String... params) {
                    try {
                        return HttpRequest.get(params[0]).accept("application/json").body();
                    } catch (Exception e) {
                        return "";
                    }
                }
                public void onPostExecute (String result) {
                if (result.isEmpty()) {
                    Toast.makeText(BuscaSitios.this, "No se generaron resultado", Toast.LENGTH_LONG).show();
                } else {
                  ArrayList<Sitio> sitios =  Sitio.obtenerSitios(result);
                  SitioAdapter adapter = new SitioAdapter(BuscaSitios.this, sitios);
                  listViewSitio.setAdapter(adapter);
                    }
                }
            }


    }

