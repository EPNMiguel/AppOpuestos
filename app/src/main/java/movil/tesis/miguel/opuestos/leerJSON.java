package movil.tesis.miguel.opuestos;

import android.app.ProgressDialog;
import java.net.URL;
import java.io.InputStream;
import java.io.InputStreamReader;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.OutputStream;
import java.net.URLConnection;
import org.json.JSONArray;
import org.json.JSONObject;


public class leerJSON extends AppCompatActivity {

    public TextView txt;
    private ProgressDialog pDialog;
    String texto = "";
    String[] URL;
    public static String[] imagen = new String[10];
    Button cargar, mostrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leer_json);
        txt = findViewById(R.id.json);
        try {
            BufferedReader fin = new BufferedReader(new InputStreamReader(openFileInput("opuestos.JSON")));
            texto = fin.readLine();
            fin.close();
            txt.setText(texto);
        } catch (Exception ex) {
            Log.e("Ficheros", "Error al leer fichero opuestos.JSON desde memoria interna");
        }
        cargar = findViewById(R.id.cargar);
        mostrar = findViewById(R.id.cargar2);
        mostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               mostrarJson();
            }
        });
    }

    public void mostrarJson() {

        try {
            JSONArray opuesto = new JSONArray(texto);
            String prueba = "";

            for (int i = 0; i < opuesto.length(); i++) {
                JSONObject img = opuesto.getJSONObject(i);
                imagen[i] = img.getString("url_im1");
                imagen[i + 1] = img.getString("url_im1");
                i++;
            }

            for (int i = 0; i < imagen.length; i++) {
                prueba += imagen[i] + "\n";
            }
            txt.setText(prueba);

        } catch (Exception e) {
            Log.d("ReadPlacesFeedTask", e.getLocalizedMessage());
        }


    }

}