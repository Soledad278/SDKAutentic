package com.tesis.autentic.ui;

        import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.imagpay.MessageHandler;
import com.imagpay.Settings;
import com.imagpay.SwipeEvent;
import com.imagpay.SwipeHandler;
import com.imagpay.SwipeListener;
import com.tesis.autentic.R;
import com.tesis.autentic.clases.Cuenta;
import com.tesis.autentic.clases.HttpRequest;
import com.tesis.autentic.clases.RestriccionLector;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class M1CardActivity extends MyActivity {
    private SwipeHandler _handler;
    private Settings _settings;
    private MessageHandler _msg;
    private Handler _ui;
    private boolean _testFlag = false;
    private String sn;
    private boolean noCheck = true;
    private int tieneRes;
    private ProgressDialog pDialog;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
//		 requestWindowFeature(Window.FEATURE_NO_TITLE);
//		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_m1card);
//		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
//				R.layout.title_m1card);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        _handler = new SwipeHandler(this);
        _settings = new Settings(_handler);
//		_msg = new MessageHandler((TextView) findViewById(R.id.status3));
        _ui = new Handler(Looper.myLooper());


        ImageButton btnback = (ImageButton) findViewById(R.id.btnReadCard);
        btnback.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(M1CardActivity.this, BuscaSitios.class));

            }
        });

        _handler.addSwipeListener(new SwipeListener() {
            @Override
            public void onReadData(SwipeEvent event) {
                Log.d("autentic", "onreaddata");
            }

            @Override
            public void onParseData(SwipeEvent event) {
                if (_testFlag)
                    return;
                String result = event.getValue();
                // hex string message
//				sendMessage("Final(16)=>% " + result);
                String[] tmps = event.getValue().split(" ");
                StringBuffer sbf = new StringBuffer();
                for (String str : tmps) {
                    sbf.append((char) Integer.parseInt(str, 16));
                    sbf.append(" ");
                }
                // char message: b{card number}^{card holder}^{exp date}{other track1 data}?;{track2 data}
                // or            b{card number}&{card holder}&{exp date}{other track1 data}?;{track2 data}
                final String data = sbf.toString().replaceAll(" ", "");
                int idx = data.indexOf("^");
                // plain text of card data
                if (data.toUpperCase().startsWith("B") && idx > 0 && data.indexOf("?") > 0) {
//					sendMessage("Final(10)=>% " + data);

                    _ui.post(new Runnable() {
                        @Override
                        public void run() {

                            int idx = data.indexOf("^");
                            String cardNo = data.substring(1, idx);
                            String cardHolder = "";
                            String expDate = "";
                            int idx1 = data.indexOf("^", idx + 1);
                            if (idx1 > 0 && idx1 < data.length() - 4) {
                                cardHolder = data.substring(idx + 1, idx1);
                                expDate = data.substring(idx1 + 1, idx1 + 1 + 4);
                            }
                            Log.d("myApp", cardNo);
                            Log.d("myApp", cardHolder);
                            Log.d("myApp", expDate);
                            TextView et = (TextView) findViewById(R.id.cardno);
                            et.setText("Taejeta:" + cardNo);
                            et = (TextView) findViewById(R.id.holder);
                            et.setText("Titular: " + cardHolder);
                            et = (TextView) findViewById(R.id.expdate);
                            et.setText("Fecha Vto: " + expDate);

                        }
                    });
                }
                // encryption data of card data
                else if (data.length() > 20 + 5 + 4) {
                    _ui.post(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("myApp", "*****");
                            TextView et = (TextView) findViewById(R.id.cardno);
                            et.setText("****************");
                            et = (TextView) findViewById(R.id.holder);
                            et.setText("********");
                            et = (TextView) findViewById(R.id.expdate);
                            et.setText("****");
                        }
                    });
                } else {
                    _ui.post(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("myApp", "---");
                            TextView et = (TextView) findViewById(R.id.cardno);
                            et.setText("");
                            et = (TextView) findViewById(R.id.holder);
                            et.setText("");
                            et = (TextView) findViewById(R.id.expdate);
                            et.setText("");
                        }
                    });

                }
            }

            @Override
            public void onDisconnected(SwipeEvent event) {
//				 "Device is disconnected!"
                toggleConnectStatus();
            }

            @Override
            public void onConnected(SwipeEvent event) {
//				 "Device is connected!
                checkDevice();
            }

            @Override
            public void onStarted(SwipeEvent event) {
                if (_testFlag)
                    return;
//				sendMessage("Device is started");
                toggleConnectStatus();
            }

            @Override
            public void onStopped(SwipeEvent event) {
                if (_testFlag)
                    return;
//				sendMessage("Device is stopped");
                toggleConnectStatus();
            }

            @Override
            public void onICDetected(SwipeEvent arg0) {
                // TODO Auto-generated method stub

            }
        });

    }


    public void checkDevice() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (!_handler.isConnected()) {
                    toggleConnectStatus();
                    return;
                }
                if (_handler.isPowerOn()) {
                    toggleConnectStatus();
                    return;
                }
                _handler.setWritable(true);
                _handler.setReadable(true);
                _handler.powerOn(16000, 1, Short.MIN_VALUE, Short.MAX_VALUE, 22050);
                toggleConnectStatus();


            }
        }).start();
    }


    private void toggleConnectStatus() {
        _ui.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (_handler.isConnected() && _handler.isPowerOn()
                        && _handler.isReadable()) {
                    ImageView iv = (ImageView) findViewById(R.id.connect);
                    iv.setVisibility(View.VISIBLE);
                    iv = (ImageView) findViewById(R.id.disconnect);
                    iv.setVisibility(View.INVISIBLE);
                    if (noCheck) {
                        checkCardReader();
                        noCheck = false;
                    }
                } else {
                    ImageView iv = (ImageView) findViewById(R.id.connect);
                    iv.setVisibility(View.INVISIBLE);
                    iv = (ImageView) findViewById(R.id.disconnect);
                    iv.setVisibility(View.VISIBLE);
                    TextView tv = (TextView) findViewById(R.id.mensaje);
                    //                  tv.setText("Lector/Usuario no habilitado para operar...");
                    noCheck = true;
                    //         tv = (TextView) findViewById(R.id.status );
                    //           tv.setText(" ");

                }
            }
        }, 500);
    }

    private void sendMessage(String msg) {
        _msg.sendMessage(msg);
    }

    public void onStart() {
        super.onStart();
        checkDevice();
    }

    public void onStop() {
        _handler.powerOff();
        super.onStop();
    }

    public void onDestroy() {
        _handler.onDestroy();
        super.onDestroy();
    }

    public void checkCardReader() {
        sn = getSN1();
  //     ImageView iv = (ImageView) findViewById(R.id.iconUsrOK);
//        iv.setVisibility(View.INVISIBLE);
  //      iv = (ImageView) findViewById(R.id.iconUsr);
 //       iv.setVisibility(View.VISIBLE);
        validarLector(sn, "soledad");

    }

    public String getSN1() {
        return "3829232510";
    }

// ********  validacion lector  ****************//

    public void validarLector(String sn, String usuario) {
        new getCuentas().execute("http://192.168.0.3:8080/AutenticRest/api/cuentas/" + usuario + "/");

    }

    private class getCuentas extends AsyncTask<String, Void, String> {
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
                Cuenta cuenta = Cuenta.obtenerCuenta(result);
                if (cuenta != null) {
                    long sncuenta = Long.parseLong(cuenta.getLector());
                    if (sncuenta == Long.parseLong(sn)) {
                        buscarRestriccion(sn);
                        tv.setText("ok");
                    } else {
                        tv.setText("Nook");
                    }

                }
            }
        }

        public void buscarRestriccion(String sn) {
            new getResticcionlector().execute("http://192.168.0.3:8080/AutenticRest/api/Rlector/lector/" + (sn) + "/");

        }

        private class getResticcionlector extends AsyncTask<String, Void, String> {

            protected void onPreExecute() {
                super.onPreExecute();
                // Showing progress dialog
                pDialog = new ProgressDialog(M1CardActivity.this);
                pDialog.setMessage("Please wait...");
                pDialog.setCancelable(false);
                pDialog.show();

            }

            public String doInBackground(String... params) {
                try {
                    return HttpRequest.get(params[0]).accept("application/json").body();
                } catch (Exception j) {
                    return "";
                }
            }

            public void onPostExecute(String result) {
                TextView tv = (TextView) findViewById(R.id.error);
                if (result.isEmpty()) {
                    tv.setText("Error en coneccion...");
                } else {
                    ArrayList<RestriccionLector> restriccionL = RestriccionLector.obtenerRestriccion(result);

                    Calendar c = Calendar.getInstance();
                    Date hoy = c.getTime();
                    final long timestamp = new Date().getTime();
                    int hA = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
                    String hActual = String.format("%02d", hA);
                    int mA = Calendar.getInstance().get(Calendar.MINUTE);
                    String mActual = String.format("%02d", mA);
                    String horaActual = hActual + ":" + mActual;
                    tieneRes = 0;
                    boolean test = true;
                    for (int i = 0; i < restriccionL.size() && test; i++) {

                        if (restriccionL.get(i).getFechaD() == null) {
                            tieneRes = 0;
                        } else {
                            Date diaD = new Date((restriccionL.get(i).getFechaD()));
                            if (hoy.before(diaD)) {
                                tieneRes = 0;
                            } else {
                                if (restriccionL.get(i).getFechaH() == null) {
                                    tieneRes = 1;
                                } else {
                                    Date diaH = new Date((restriccionL.get(i).getFechaH()));
                                    if (hoy.after(diaH)) {
                                        tieneRes = 0;
                                    } else {
                                        tieneRes = 1;
                                    }
                                }
                            }
                        }

                        if (restriccionL.get(i).getHoraD() == null) {
                            tieneRes = tieneRes;
                        } else {
                            String horaD = restriccionL.get(i).getHoraD();
                            if (horaActual.compareTo(horaD) < 0) {
                                tieneRes = 0;
                            } else {
                                if (restriccionL.get(i).getHoraH() == null) {
                                    tieneRes = 2;
                                } else {
                                    String horaH = restriccionL.get(i).getHoraH();
                                    if (horaActual.compareTo(horaH) > 0) {
                                        tieneRes = 0;
                                    } else {
                                        tieneRes = 2;
                                    }
                                }
                            }
                        }

                        if (tieneRes == 0) {
                            tv.setText(" ");
                            visible();
                        } else

                        {
                            if (tieneRes == 1) {
                                test = false;
                                tv.setText("restriccion fecha");
                                visible();
                            } else {
                                test = false;
                                tv.setText("restriccion hora");
                                visible();
                            }
                        }
                    }
                }
                if (pDialog.isShowing())
                    pDialog.dismiss();

            }

        }

    }

    public void visible() {

        if(tieneRes==0)
        {
            ImageView iv = (ImageView) findViewById(R.id.iconUsrOk);
            iv.setVisibility(View.VISIBLE);
            iv = (ImageView) findViewById(R.id.iconUsr);
            iv.setVisibility(View.INVISIBLE);
            TextView tv = (TextView) findViewById(R.id.mensaje);
            tv.setText("Deslice la tarjeta por el lector para autenticarse..");
        }
        else

        {
            ImageView iv = (ImageView) findViewById(R.id.iconUsrOk);
            iv.setVisibility(View.INVISIBLE);
            iv = (ImageView) findViewById(R.id.iconUsr);
            iv.setVisibility(View.VISIBLE);
            TextView tv = (TextView) findViewById(R.id.mensaje);
            tv.setText("Lector/Usuario no habilitado para operar...");
        }
        TextView tv = (TextView) findViewById(R.id.serial);
        tv.setText(sn);
        tv=(TextView)  findViewById(R.id.usuario);
        tv.setText("usurario@gmail.com");
    }



}

