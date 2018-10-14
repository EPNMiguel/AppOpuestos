package movil.tesis.miguel.opuestos;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class leerJSON extends AppCompatActivity {

    public TextView txt;
     String texto ="";
     String cadenaDeOpuestos;
     Button cargar,mostrar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leer_json);
        txt = findViewById(R.id.json);
        try{
            BufferedReader fin = new BufferedReader( new InputStreamReader(openFileInput("opuestos.JSON")));
            texto = fin.readLine();
            fin.close();
            txt.setText(texto);
        }
        catch (Exception ex){
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
            //JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray opuesto = new JSONArray(texto);

            String result = "";
            for (int i = 0; i < opuesto.length(); i++) {
                JSONObject city = opuesto.getJSONObject(i);
                result += "URL_IMAGEN_1: " + city.getString("url_im1") + "\n" +
                        "URL_IMAGEN_1: " + city.getString("url_im2") + "\n" ;
            }
            txt.setText(result);
        } catch (Exception e) {
            Log.d("ReadPlacesFeedTask", e.getLocalizedMessage());
        }


    }

}
