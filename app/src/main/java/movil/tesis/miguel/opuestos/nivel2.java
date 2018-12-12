package movil.tesis.miguel.opuestos;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
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

    public ImageView imgpar1, imgpar2, imgdistractor;
    public TextView txtpar1, txtpar2, txtdistractor1;
    private TextToSpeech mTTS;
    public MediaPlayer felicitaciones;
    public String texto = "";
    public String[] arrayNombres1 = new String[20];
    public String[] arrayNombres2 = new String[20];
    public int i = 0;
    public Button bt1;
    public int random;

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

        imgpar1 = (ImageView) findViewById(R.id.par1);
        imgpar2 = (ImageView) findViewById(R.id.par2);
        imgdistractor = (ImageView) findViewById(R.id.distractor1);
        txtpar1 = (TextView) findViewById(R.id.txtpar1);
        txtpar2 = (TextView) findViewById(R.id.txtpar2);
        txtdistractor1 = (TextView) findViewById(R.id.txtdistractor1);

        imgpar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTTS.speak(arrayNombres1[i], TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        imgpar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTTS.speak(arrayNombres2[i], TextToSpeech.QUEUE_FLUSH, null);
                acerto();
            }
        });
        imgdistractor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTTS.speak(arrayNombres2[random], TextToSpeech.QUEUE_FLUSH, null);
                fallo();
            }
        });

        bt1 = (Button) findViewById(R.id.btncambio);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarImg();
            }

        });

    }


    public void cargarImg() {
        bt1.setText("CAMBIAR");
        i++;
        if (arrayNombres1[i] != null) {
            random = randomico(i);
            Picasso.get().load(new File("/data/data/movil.tesis.miguel.opuestos/app_picasso/" + arrayNombres1[i] + ".png")).into(imgpar1);
            txtpar1.setText(arrayNombres1[i]);
            Picasso.get().load(new File("/data/data/movil.tesis.miguel.opuestos/app_picasso/" + arrayNombres2[i] + ".png")).into(imgpar2);
            txtpar2.setText(arrayNombres2[i]);
            Picasso.get().load(new File("/data/data/movil.tesis.miguel.opuestos/app_picasso/" + arrayNombres2[random] + ".png")).into(imgdistractor);
            txtdistractor1.setText(arrayNombres2[random]);
        } else {
            termino();
        }
    }

    public static int randomico(int indice) {

        int ran = 0;
        ran = (int) (Math.random() * 4) + 1;
        if (ran == indice) {
            return randomico(indice);
        } else {
            return ran;
        }

    }

    public void acerto() {
        LayoutInflater myInflator = getLayoutInflater();
        View myLayout = myInflator.inflate(R.layout.bien, (ViewGroup) findViewById(R.id.lay_bien));
        Toast myToast = new Toast(getApplicationContext());
        myToast.setGravity(Gravity.FILL, 0, 0);
        myToast.setDuration(Toast.LENGTH_SHORT);
        myToast.setView(myLayout);
        myToast.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
            }
        }, 3500);

    }

    public void fallo() {
        LayoutInflater myInflator = getLayoutInflater();
        View myLayout = myInflator.inflate(R.layout.mal, (ViewGroup) findViewById(R.id.ly_mal));
        Toast myToast = new Toast(getApplicationContext());
        myToast.setDuration(Toast.LENGTH_SHORT);
        myToast.setGravity(Gravity.FILL, 0, 0);
        myToast.setView(myLayout);
        myToast.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
            }
        }, 3500);
    }

    private void termino() {
        imgpar1.setImageResource(R.color.blanco);
        imgpar1.setEnabled(false);
        imgpar2.setEnabled(false);
        imgpar2.setImageResource(R.color.blanco);
        imgdistractor.setImageResource(R.color.blanco);
        imgdistractor.setEnabled(false);
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
        myToast.setGravity(Gravity.FILL, 0, 0);
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