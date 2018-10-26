package movil.tesis.miguel.opuestos;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import movil.tesis.miguel.opuestos.leerJSON;


import java.util.Locale;

public class nivel1 extends AppCompatActivity {

    public Button bt1;
    public ImageView image1, image2;
    public TextView txtimg1, txtimg2;
    public String[] vectorimg ;
            //= {"photo_1539460772749","photo_1539460772787", "photo_1539460809873", "photo_1539460809883", "photo_1539460912650", "photo_1539460912748", "photo_1539461125331", "photo_1539461125359", "photo_1539461198012", "photo_1539461198068"  };
    public int nombreimg, i=0, id, id2;
    private TextToSpeech mTTS;
    public MediaPlayer felicitaciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_nivel1);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        int largo = leerJSON.imagen.length;
        vectorimg = new String[largo];
        for (int k=0;k<5;k++){
            vectorimg[i]= leerJSON.imagen[k];
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

        bt1 = (Button) findViewById(R.id.btncambio);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=i+2;
                if (i < vectorimg.length) {
                    image1 = (ImageView) findViewById(R.id.imgcambio);
                    image2 = (ImageView) findViewById(R.id.imgcambio2);
                    txtimg1 = (TextView) findViewById(R.id.txtcambio);
                    txtimg2 = (TextView) findViewById(R.id.txtcambio2);
                    id = getResources().getIdentifier(vectorimg[i], "drawable", getPackageName());
                    id2 = getResources().getIdentifier(vectorimg[i+1], "drawable", getPackageName());
                    image1.setImageResource(id);
                    txtimg1.setText(vectorimg[i]);
                    image2.setImageResource(id2);
                    txtimg2.setText(vectorimg[i+1]);

                } else {
                    termino();
                }
            }
        });

        image1 = (ImageView) findViewById(R.id.imgcambio);
        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTTS.speak(vectorimg[i], TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        image2 = (ImageView) findViewById(R.id.imgcambio2);
        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTTS.speak(vectorimg[i+1], TextToSpeech.QUEUE_FLUSH, null);
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (mTTS != null) {
            mTTS.stop();
            mTTS.shutdown();
            super.onDestroy();
        }
    }

    private void termino(){
        image1.setImageResource(R.color.blanco);
        image1.setEnabled(false);
        image2.setEnabled(false);
        image2.setImageResource(R.color.blanco);
        txtimg1.setText(null);
        txtimg2.setText(null);
        bt1.setText("TERMINÓ EL NIVEL 1");
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
                Intent regresarhome = new Intent(nivel1.this, Principal.class);
                startActivity(regresarhome);
            }
        }, 3500);
    }
}
