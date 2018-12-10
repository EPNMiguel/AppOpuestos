package movil.tesis.miguel.opuestos;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Locale;

public class nivel1 extends AppCompatActivity {

    ImageView imagenizquierda, imagenderecha;
    TextView txtizquierda, txtderecha;
    Button cargar;
    String texto = "";
    public String[] arrayNombres1 = new String[20];
    public String[] arrayNombres2 = new String[20];
    public MediaPlayer felicitaciones;
    public int i = 0;

    private TextToSpeech mTTS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_nivel1);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
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
            for (int i = 1; i < n; i++) {
                JSONObject img = opuesto.getJSONObject(i);
                arrayNombres1[i] = img.getString("opuesto_im1");
                arrayNombres2[i] = img.getString("opuesto_im2");
            }
        } catch (Exception e) {
            Log.d("Parsear", "Error al parsear  " + e.getLocalizedMessage());
        }


        mTTS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = mTTS.setLanguage(new Locale("spa", "MEX"));
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Toast.makeText(nivel1.this, "Idioma no Válido", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
        imagenizquierda = (ImageView) findViewById(R.id.imgcambio);
        imagenderecha = (ImageView) findViewById(R.id.imgcambio2);
        txtizquierda = (TextView) findViewById(R.id.txtcambio);
        txtderecha = (TextView) findViewById(R.id.txtcambio2);

        cargar = (Button) findViewById(R.id.btncambio);
        cargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarImg();
            }
        });

        imagenizquierda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTTS.speak(arrayNombres1[i], TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        imagenderecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTTS.speak(arrayNombres2[i], TextToSpeech.QUEUE_FLUSH, null);
            }
        });

    }

    public void cargarImg() {
        cargar.setText("CAMBIAR");
        i++;
        if (arrayNombres1[i] != null) {
            Picasso.get().load(new File("/data/data/movil.tesis.miguel.opuestos/app_picasso/" + arrayNombres1[i] + ".png")).into(imagenizquierda);
            txtizquierda.setText(arrayNombres1[i]);
            Picasso.get().load(new File("/data/data/movil.tesis.miguel.opuestos/app_picasso/" + arrayNombres2[i] + ".png")).into(imagenderecha);
            txtderecha.setText(arrayNombres2[i]);
        } else {
            termino();
        }
    }

    private void termino() {
        imagenizquierda.setImageResource(R.color.blanco);
        imagenizquierda.setEnabled(false);
        imagenderecha.setEnabled(false);
        imagenderecha.setImageResource(R.color.blanco);
        txtizquierda.setText("");
        txtizquierda.setEnabled(false);
        txtderecha.setText("");
        txtderecha.setEnabled(false);

        cargar.setText("TERMINÓ EL NIVEL 1");
        LayoutInflater myInflator = getLayoutInflater();
        View myLayout = myInflator.inflate(R.layout.felicitaciones, (ViewGroup) findViewById(R.id.lay_felicitaciones));
        felicitaciones = MediaPlayer.create(getApplicationContext(), R.raw.felicitaciones);
        felicitaciones.start();
        Toast myToast = new Toast(getApplicationContext());
        myToast.setDuration(Toast.LENGTH_LONG);
        myToast.setView(myLayout);
        myToast.show();
        cargar.setEnabled(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent regresarhome = new Intent(nivel1.this, Principal.class);
                startActivity(regresarhome);
            }
        }, 3500);
    }
}
