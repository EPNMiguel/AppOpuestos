package movil.tesis.miguel.opuestos;

import android.content.Intent;
import android.content.pm.ActivityInfo;
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

public class nivel2 extends AppCompatActivity {

    public ImageView par1, par2, distractor1;
    public TextView txtpar1, txtpar2, txtdistractor1;
    private TextToSpeech mTTS;
    public MediaPlayer felicitaciones;
    public String texto = "";
    public String[] arrayNombres1 = new String[20];
    public String[] arrayNombres2 = new String[20];
    public int i = 0;
    public Button bt1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_nivel2);

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
                        Toast.makeText(nivel2.this, "Idioma no Válido", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        bt1 = (Button) findViewById(R.id.btncambio);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarImg();
            }

        });

        txtpar1 = (TextView) findViewById(R.id.txtpar1);
        txtpar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTTS.speak(arrayNombres1[i], TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        txtpar2 = (TextView) findViewById(R.id.txtpar2);
        txtpar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTTS.speak(arrayNombres2[i], TextToSpeech.QUEUE_FLUSH, null);
            }
        });


        txtdistractor1 = (TextView) findViewById(R.id.txtdistractor1);
        txtdistractor1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTTS.speak(arrayNombres1[randomico(i)], TextToSpeech.QUEUE_FLUSH, null); //random excepto i
            }
        });
    }


    public void cargarImg() {
        bt1.setText("CAMBIAR");
        i++;
        if (arrayNombres1[i] != null) {
            Picasso.get().load(new File("/data/data/movil.tesis.miguel.opuestos/app_picasso/" + arrayNombres1[i] + ".png")).into(par1);
            txtpar1.setText(arrayNombres1[i]);
            Picasso.get().load(new File("/data/data/movil.tesis.miguel.opuestos/app_picasso/" + arrayNombres2[i] + ".png")).into(par2);
            txtpar2.setText(arrayNombres2[i]);
            Picasso.get().load(new File("/data/data/movil.tesis.miguel.opuestos/app_picasso/" + arrayNombres2[i] + ".png")).into(distractor1);
            txtpar2.setText(arrayNombres1[3]);
        } else {
            termino();
        }
    }

    public int randomico(int indice) {

        int ran = 0;
        ran = (int) (Math.random() * arrayNombres1.length) + 1;
        if (ran == indice) {
            randomico(indice);
        } else {
            return ran;
        }return  ran;
    }

    private void termino() {
        par1.setImageResource(R.color.blanco);
        par1.setEnabled(false);
        par2.setEnabled(false);
        par2.setImageResource(R.color.blanco);
        distractor1.setImageResource(R.color.blanco);
        distractor1.setEnabled(false);
        txtpar1.setText(null);
        txtpar2.setText(null);
        txtdistractor1.setText(null);
        bt1.setText("TERMINÓ EL NIVEL 2");
        LayoutInflater myInflator = getLayoutInflater();
        View myLayout = myInflator.inflate(R.layout.felicitaciones, (ViewGroup) findViewById(R.id.lay_felicitaciones));
        felicitaciones = MediaPlayer.create(getApplicationContext(), R.raw.felicitaciones);
        felicitaciones.start();
        Toast myToast = new Toast(getApplicationContext());
        myToast.setDuration(Toast.LENGTH_LONG);
        myToast.setView(myLayout);
        myToast.show();
        bt1.setEnabled(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent regresarhome = new Intent(nivel2.this, Principal.class);
                startActivity(regresarhome);
            }
        }, 3500);
    }

}
