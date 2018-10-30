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

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Locale;

public class nivel2 extends AppCompatActivity {

    public ImageView par1, par2, distractor1;
    public TextView txtpar1, txtpar2, txtdistractor1;
    private TextToSpeech mTTS;
    public MediaPlayer felicitaciones;
    public String texto="";
    public String[] imagen = new String[10];
    public String[] arrayNombres = new String[10] ;
    //public String[] vectorimg ;
    public int i=0, id1,id2,idd;
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
            Log.e("Ficheros", "Error al leer fichero opuestos.JSON desde memoria interna");
        }
        try {
            JSONArray opuesto = new JSONArray(texto);
            String prueba = "";

            for (int i = 0; i < opuesto.length(); i++) {
                JSONObject img = opuesto.getJSONObject(i);
                imagen[i] = img.getString("nombre_im1");
                imagen[i]=imagen[i].substring(0,imagen[i].lastIndexOf("."));
                imagen[i + 1] = img.getString("nombre_im2");
                imagen[i+1]=imagen[i+1].substring(0,imagen[i+1].lastIndexOf("."));
                arrayNombres[i]=img.getString("opuesto_im1");
                arrayNombres[i+1]=img.getString("opuesto_im2");
                i++;
            }

        } catch (Exception e) {
            Log.d("ReadPlacesFeedTask", e.getLocalizedMessage());
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
                i=i+3;
                if (i < imagen.length) {
                    par1 = (ImageView) findViewById(R.id.par1);
                    par2 = (ImageView) findViewById(R.id.par2);
                    distractor1 = (ImageView)findViewById(R.id.distractor1);
                    txtpar1 = (TextView) findViewById(R.id.txtpar1);
                    txtpar2 = (TextView) findViewById(R.id.txtpar2);
                    txtdistractor1 = (TextView)findViewById(R.id.txtdistractor1);
                    id1 = getResources().getIdentifier(imagen[i], "drawable", getPackageName());
                    id2 = getResources().getIdentifier(imagen[i+1], "drawable", getPackageName());
                    idd =  getResources().getIdentifier(imagen[i+2], "drawable", getPackageName());
                    par1.setImageResource(id1);
                    txtpar1.setText(arrayNombres[i]);
                    par2.setImageResource(id2);
                    txtpar2.setText(arrayNombres[i+1]);
                    distractor1.setImageResource(idd);
                    txtdistractor1.setText(arrayNombres[i+2]);

                } else {
                    termino();
                }
            }

        });

        txtpar1 = (TextView)findViewById(R.id.txtpar1) ;
        txtpar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTTS.speak(arrayNombres[i], TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        txtpar2 = (TextView)findViewById(R.id.txtpar2);
        txtpar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTTS.speak(arrayNombres[i+1],TextToSpeech.QUEUE_FLUSH,null);
            }
        });
        juntar();

        txtdistractor1 = (TextView)findViewById(R.id.txtdistractor1) ;
        txtdistractor1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTTS.speak(arrayNombres[i+2], TextToSpeech.QUEUE_FLUSH, null);
            }
        });
    }

    private void juntar(){

    }
    private void termino(){
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
