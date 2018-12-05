package movil.tesis.miguel.opuestos;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Principal extends AppCompatActivity {

    public Button n1, n2, n3, act, descargar;
    public String[] arrayNombres = new String[10];
    public String[] arrayURL = new String[10];
    String texto = "";
    public ProgressDialog pd, pDialog;
    ;
    public String[] imagen = new String[10];
    private ProgressBar progressBar;

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
        n3 = (Button) findViewById(R.id.nivel3);
        n3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cambiarnivel3 = new Intent(Principal.this, nivel3.class);
                startActivity(cambiarnivel3);
            }
        });
        act = (Button) findViewById(R.id.actualizar);
        act.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizar();
            }
        });
        descargar = (Button) findViewById(R.id.descargarImg);
        descargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    for (int i = 0; i < arrayURL.length; i++) {
                        Picasso.get().load(arrayURL[i]).into(picassoImageTarget(getApplicationContext(), "picasso", arrayNombres[i] + ".png"));
                    }
                } catch (Exception e) {
                    Log.e("descarga: ", "error en picasso");

                }


            }
        });

    }

    public void actualizar() {

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

                //***************************************************primer descarga JSON del server
                new JsonTask().execute("http://opuestos-miguel-94.c9users.io:8081/api/opuestos");

                //*****************************ahora con el JSON descargado separa los nombres y las URL y los guarda en arreglos
                try {
                    BufferedReader fin = new BufferedReader(new InputStreamReader(openFileInput("opuestos.JSON")));
                    texto = fin.readLine();
                    fin.close();
                } catch (Exception ex) {
                    Log.e("Ficheros", "Error al leer fichero jsonweb.JSON desde memoria interna");
                    Log.e("ReadPlacesFeedTask", ex.getLocalizedMessage());
                }
                try {
                    JSONObject obj = new JSONObject("{\"opuestos\":" + texto + "}");
                    JSONArray opuesto = obj.getJSONArray("opuestos");
                    int n = opuesto.length();
                    for (int i = 0; i < n*2; i++) {
                        JSONObject img = opuesto.getJSONObject(i);
                        arrayURL[i] = img.getString("url_im1");
                        arrayURL[i + 1] = img.getString("url_im2");
                        arrayNombres[i] = img.getString("opuesto_im1");
                        arrayNombres[i + 1] = img.getString("opuesto_im2");
                        i++;
                    }
                } catch (Exception e) {
                    Log.d("Parsear", "Error al parsear  " + e.getLocalizedMessage());
                }

            }
        } else {
            Toast.makeText(this, "NO tiene internet", Toast.LENGTH_SHORT).show();
        }
    }

    private class JsonTask extends AsyncTask<String, String, String> {
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(Principal.this);
            pd.setMessage("Descargando... por favor espere");
            pd.setCancelable(false);
            pd.show();
        }

        protected String doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }
                return buffer.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (pd.isShowing()) {
                pd.dismiss();
            }
            String filename = "opuestos.JSON";
            String string = result;
            FileOutputStream outputStream;
            try {
                outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
                outputStream.write(string.getBytes());
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Target picassoImageTarget(Context context, final String imageDir, final String imageName) {
        Log.d("picassoImageTarget", " picassoImageTarget");
        ContextWrapper cw = new ContextWrapper(context);
        final File directory = cw.getDir(imageDir, Context.MODE_PRIVATE); // path to /data/data/yourapp/app_imageDir
        return new Target() {
            @Override
            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final File myImageFile = new File(directory, imageName); // Create image file
                        FileOutputStream fos = null;
                        try {
                            fos = new FileOutputStream(myImageFile);
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                fos.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        Log.i("image", "image saved to >>>" + myImageFile.getAbsolutePath());

                    }
                }).start();
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }


            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                if (placeHolderDrawable != null) {
                }
            }
        };
    }


}