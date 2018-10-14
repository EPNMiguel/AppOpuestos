//PRINCIPAL CON DESCARGAR DE ARCHIVOS


package movil.tesis.miguel.opuestos;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;

public class Principal extends AppCompatActivity {

    public Button n1, n2, act, leer;
    ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_principal);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
         n1 = (Button) findViewById(R.id.nivel1);
        n1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cambiarnivel1 = new Intent(Principal.this, nivel1.class);
                startActivity(cambiarnivel1);
            }
        });
        n2 = (Button) findViewById(R.id.nivel2);
        n2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cambiarnivel2 = new Intent(Principal.this, nivel2.class);
                startActivity(cambiarnivel2);
            }
        });
        act = (Button) findViewById(R.id.actualizar);

        leer = (Button) findViewById(R.id.leer);
        leer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cambiarleer = new Intent(Principal.this, leerJSON.class);
                startActivity(cambiarleer);
            }
        });

    }

    public void actualizar(View view) {
        ConnectivityManager cm;
        NetworkInfo ni;
        cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        ni = cm.getActiveNetworkInfo();
        boolean tipoConexion1 = false;
        boolean tipoConexion2 = false;

        if (ni != null) {
            ConnectivityManager connManager1 = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWifi = connManager1.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

            ConnectivityManager connManager2 = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mMobile = connManager2.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if (mWifi.isConnected()) {
                tipoConexion1 = true;
            }
            if (mMobile.isConnected()) {
                tipoConexion2 = true;
            }
            if (tipoConexion1 == true || tipoConexion2 == true) {
             //   new JsonTask().execute("http://opuestos-miguel-94.c9users.io:8081/api/usuarios");

                    //BORRAR cuando ya se carguen los archivos desde la base, para eso debo descomentar  la línea de arriba
                String filename1 = "usuarios.JSON";
                String filename2 = "opuestos.JSON";
                String string1 = "[{\"opuestos\":[{\"_id\":\"5b9e8f5c1b841230486c154b\",\"url_im1\":\"http://opuestos-miguel-94.c9users.io:8081/upload/photo_1539460772749.jpg\",\"url_im2\":\"http://opuestos-miguel-94.c9users.io:8081/upload/photo_1539460772787.png\",\"nombre_im1\":\"photo_1539460772749.jpg\",\"nombre_im2\":\"photo_1539460772787.png\",\"opuesto_im1\":\"noche\",\"opuesto_im2\":\"día\",\"usuario\":\"5b9e8157602e1e4304bebaec\",\"__v\":0},{\"_id\":\"5b9ec5dd173811241c839385\",\"url_im1\":\"http://opuestos-miguel-94.c9users.io:8081/upload/photo_1539460809873.jpg\",\"url_im2\":\"http://opuestos-miguel-94.c9users.io:8081/upload/photo_1539460809883.png\",\"nombre_im1\":\"photo_1539460809873.jpg\",\"nombre_im2\":\"photo_1539460809883.png\",\"opuesto_im1\":\"gato\",\"opuesto_im2\":\"perro\",\"usuario\":\"5b9e8157602e1e4304bebaec\",\"__v\":0},{\"_id\":\"5bc24f30da4edc1a6449e1c2\",\"url_im1\":\"http://opuestos-miguel-94.c9users.io:8081/upload/photo_1539460912650.png\",\"url_im2\":\"http://opuestos-miguel-94.c9users.io:8081/upload/photo_1539460912748.png\",\"nombre_im1\":\"photo_1539460912650.png\",\"nombre_im2\":\"photo_1539460912748.png\",\"opuesto_im1\":\"ángel\",\"opuesto_im2\":\"demonio\",\"usuario\":\"5b9e8157602e1e4304bebaec\",\"__v\":0}],\"_id\":\"5b9e8157602e1e4304bebaec\",\"username\":\"lein11\",\"email\":\"lennyesteban@hotmail.com\",\"password\":\"epn123\",\"__v\":3},{\"opuestos\":[{\"_id\":\"5b9e904e3633804294f1d1c1\",\"url_im1\":\"http://opuestos-miguel-94.c9users.io:8081/upload/photo_1539461125331.png\",\"url_im2\":\"http://opuestos-miguel-94.c9users.io:8081/upload/photo_1539461125359.jpg\",\"nombre_im1\":\"photo_1539461125331.png\",\"nombre_im2\":\"photo_1539461125359.jpg\",\"opuesto_im1\":\"pareja\",\"opuesto_im2\":\"singular\",\"usuario\":\"5b9e90263633804294f1d1c0\",\"__v\":0},{\"_id\":\"5b9e90fc3633804294f1d1c3\",\"url_im1\":\"http://opuestos-miguel-94.c9users.io:8081/upload/photo_1539461198012.jpg\",\"url_im2\":\"http://opuestos-miguel-94.c9users.io:8081/upload/photo_1539461198068.png\",\"nombre_im1\":\"photo_1539461198012.jpg\",\"nombre_im2\":\"photo_1539461198068.png\",\"opuesto_im1\":\"azul\",\"opuesto_im2\":\"rojo\",\"usuario\":\"5b9e90263633804294f1d1c0\",\"__v\":0}],\"_id\":\"5b9e90263633804294f1d1c0\",\"username\":\"lein2\",\"email\":\"lennydan2@gmail.com\",\"password\":\"qwerty\",\"__v\":2},{\"opuestos\":[{\"_id\":\"5ba448824a13292174d42d99\",\"url_im1\":\"http://opuestos-miguel-94.c9users.io:8081/upload/photo_1539461352529.jpg\",\"url_im2\":\"http://opuestos-miguel-94.c9users.io:8081/upload/photo_1539461352555.png\",\"nombre_im1\":\"photo_1539461352529.jpg\",\"nombre_im2\":\"photo_1539461352555.png\",\"opuesto_im1\":\"animal\",\"opuesto_im2\":\"persona\",\"usuario\":\"5ba07172d84b8f0a9484e354\",\"__v\":0},{\"_id\":\"5ba463304877863ab4b0313f\",\"url_im1\":\"http://opuestos-miguel-94.c9users.io:8081/upload/photo_1539461375303.jpg\",\"url_im2\":\"http://opuestos-miguel-94.c9users.io:8081/upload/photo_1539461375343.png\",\"nombre_im1\":\"photo_1539461375303.jpg\",\"nombre_im2\":\"photo_1539461375343.png\",\"opuesto_im1\":\"dos\",\"opuesto_im2\":\"uno\",\"usuario\":\"5ba07172d84b8f0a9484e354\",\"__v\":0},{\"_id\":\"5bc2516bda4edc1a6449e1c4\",\"url_im1\":\"http://opuestos-miguel-94.c9users.io:8081/upload/photo_1539461483489.png\",\"url_im2\":\"http://opuestos-miguel-94.c9users.io:8081/upload/photo_1539461483513.jpg\",\"nombre_im1\":\"photo_1539461483489.png\",\"nombre_im2\":\"photo_1539461483513.jpg\",\"opuesto_im1\":\"muchos\",\"opuesto_im2\":\"pocos\",\"usuario\":\"5ba07172d84b8f0a9484e354\",\"__v\":0},{\"_id\":\"5bc252bada4edc1a6449e1c5\",\"url_im1\":\"http://opuestos-miguel-94.c9users.io:8081/upload/photo_1539461818486.jpg\",\"url_im2\":\"http://opuestos-miguel-94.c9users.io:8081/upload/photo_1539461818511.jpg\",\"nombre_im1\":\"photo_1539461818486.jpg\",\"nombre_im2\":\"photo_1539461818511.jpg\",\"opuesto_im1\":\"sentado\",\"opuesto_im2\":\"parado\",\"usuario\":\"5ba07172d84b8f0a9484e354\",\"__v\":0}],\"_id\":\"5ba07172d84b8f0a9484e354\",\"username\":\"lein93\",\"email\":\"lennydan@yahoo.com\",\"password\":\"asdf123\",\"__v\":16}]";
                String string2 = "[{\"_id\":\"5b9e8f5c1b841230486c154b\",\"url_im1\":\"http://localhost:3000/upload/photo_1539460772749.jpg\",\"url_im2\":\"http://localhost:3000/upload/photo_1539460772787.png\",\"nombre_im1\":\"photo_1539460772749.jpg\",\"nombre_im2\":\"photo_1539460772787.png\",\"opuesto_im1\":\"noche\",\"opuesto_im2\":\"día\",\"usuario\":\"5b9e8157602e1e4304bebaec\",\"__v\":0},{\"_id\":\"5b9e904e3633804294f1d1c1\",\"url_im1\":\"http://localhost:3000/upload/photo_1539461125331.png\",\"url_im2\":\"http://localhost:3000/upload/photo_1539461125359.jpg\",\"nombre_im1\":\"photo_1539461125331.png\",\"nombre_im2\":\"photo_1539461125359.jpg\",\"opuesto_im1\":\"pareja\",\"opuesto_im2\":\"singular\",\"usuario\":\"5b9e90263633804294f1d1c0\",\"__v\":0},{\"_id\":\"5b9e90fc3633804294f1d1c3\",\"url_im1\":\"http://localhost:3000/upload/photo_1539461198012.jpg\",\"url_im2\":\"http://localhost:3000/upload/photo_1539461198068.png\",\"nombre_im1\":\"photo_1539461198012.jpg\",\"nombre_im2\":\"photo_1539461198068.png\",\"opuesto_im1\":\"azul\",\"opuesto_im2\":\"rojo\",\"usuario\":\"5b9e90263633804294f1d1c0\",\"__v\":0},{\"_id\":\"5b9ec5dd173811241c839385\",\"url_im1\":\"http://localhost:3000/upload/photo_1539460809873.jpg\",\"url_im2\":\"http://localhost:3000/upload/photo_1539460809883.png\",\"nombre_im1\":\"photo_1539460809873.jpg\",\"nombre_im2\":\"photo_1539460809883.png\",\"opuesto_im1\":\"gato\",\"opuesto_im2\":\"perro\",\"usuario\":\"5b9e8157602e1e4304bebaec\",\"__v\":0},{\"_id\":\"5ba448824a13292174d42d99\",\"url_im1\":\"http://localhost:3000/upload/photo_1539461352529.jpg\",\"url_im2\":\"http://localhost:3000/upload/photo_1539461352555.png\",\"nombre_im1\":\"photo_1539461352529.jpg\",\"nombre_im2\":\"photo_1539461352555.png\",\"opuesto_im1\":\"animal\",\"opuesto_im2\":\"persona\",\"usuario\":\"5ba07172d84b8f0a9484e354\",\"__v\":0},{\"_id\":\"5ba463304877863ab4b0313f\",\"url_im1\":\"http://localhost:3000/upload/photo_1539461375303.jpg\",\"url_im2\":\"http://localhost:3000/upload/photo_1539461375343.png\",\"nombre_im1\":\"photo_1539461375303.jpg\",\"nombre_im2\":\"photo_1539461375343.png\",\"opuesto_im1\":\"dos\",\"opuesto_im2\":\"uno\",\"usuario\":\"5ba07172d84b8f0a9484e354\",\"__v\":0},{\"_id\":\"5bc24f30da4edc1a6449e1c2\",\"url_im1\":\"http://localhost:3000/upload/photo_1539460912650.png\",\"url_im2\":\"http://localhost:3000/upload/photo_1539460912748.png\",\"nombre_im1\":\"photo_1539460912650.png\",\"nombre_im2\":\"photo_1539460912748.png\",\"opuesto_im1\":\"ángel\",\"opuesto_im2\":\"demonio\",\"usuario\":\"5b9e8157602e1e4304bebaec\",\"__v\":0},{\"_id\":\"5bc2516bda4edc1a6449e1c4\",\"url_im1\":\"http://localhost:3000/upload/photo_1539461483489.png\",\"url_im2\":\"http://localhost:3000/upload/photo_1539461483513.jpg\",\"nombre_im1\":\"photo_1539461483489.png\",\"nombre_im2\":\"photo_1539461483513.jpg\",\"opuesto_im1\":\"muchos\",\"opuesto_im2\":\"pocos\",\"usuario\":\"5ba07172d84b8f0a9484e354\",\"__v\":0},{\"_id\":\"5bc252bada4edc1a6449e1c5\",\"url_im1\":\"http://localhost:3000/upload/photo_1539461818486.jpg\",\"url_im2\":\"http://localhost:3000/upload/photo_1539461818511.jpg\",\"nombre_im1\":\"photo_1539461818486.jpg\",\"nombre_im2\":\"photo_1539461818511.jpg\",\"opuesto_im1\":\"sentado\",\"opuesto_im2\":\"parado\",\"usuario\":\"5ba07172d84b8f0a9484e354\",\"__v\":0},{\"_id\":\"5bc28385da4edc1a6449e1c7\",\"url_im1\":\"http://localhost:3000/upload/photo_1539474309528.png\",\"url_im2\":\"http://localhost:3000/upload/photo_1539474309553.jpg\",\"nombre_im1\":\"photo_1539474309528.png\",\"nombre_im2\":\"photo_1539474309553.jpg\",\"opuesto_im1\":\"ssss\",\"opuesto_im2\":\"ffff\",\"usuario\":\"5b9e8157602e1e4304bebaec\",\"__v\":0}]";
                FileOutputStream outputStream;

                try {
                    outputStream = openFileOutput(filename1, Context.MODE_PRIVATE);
                    outputStream.write(string1.getBytes());
                    outputStream = openFileOutput(filename2, Context.MODE_PRIVATE);
                    outputStream.write(string2.getBytes());
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }




                Toast.makeText(this, "SÍ tiene internet, Y SE DESCARGA EL ARCHIVO", Toast.LENGTH_SHORT).show();
                 }
        } else {
             Toast.makeText(this, "NO tiene internet", Toast.LENGTH_SHORT).show();
        }
    }

    {
    }

//    private class JsonTask extends AsyncTask <String,String,String>{
//        protected void onPreExecute() {
//            super.onPreExecute();
//
//            pd = new ProgressDialog(Principal.this);
//            pd.setMessage("Descargando... por favor espere");
//            pd.setCancelable(false);
//            pd.show();
//        }
//
//        protected String doInBackground(String... params) {
//
//
//            HttpURLConnection connection = null;
//            BufferedReader reader = null;
//
//            try {
//                URL url = new URL(params[0]);
//                connection = (HttpURLConnection) url.openConnection();
//                connection.connect();
//                InputStream stream = connection.getInputStream();
//                reader = new BufferedReader(new InputStreamReader(stream));
//
//                StringBuffer buffer = new StringBuffer();
//                String line = "";
//
//                while ((line = reader.readLine()) != null) {
//                    buffer.append(line+"\n");
//                    Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)
//
//                }
//
//                return buffer.toString();
//
//
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            } finally {
//                if (connection != null) {
//                    connection.disconnect();
//                }
//                try {
//                    if (reader != null) {
//                        reader.close();
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            super.onPostExecute(result);
//            if (pd.isShowing()){
//                pd.dismiss();
//            }
//            String filename = "jsonweb.JSON";
//            String string = result;
//            FileOutputStream outputStream;
//
//            try {
//                outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
//                outputStream.write(string.getBytes());
//                outputStream.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
}