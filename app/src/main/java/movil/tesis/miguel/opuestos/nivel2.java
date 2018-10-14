package movil.tesis.miguel.opuestos;

import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class nivel2 extends AppCompatActivity {

    public ImageView par1, par2, distractor1;
    public TextView text1, text2, text3, idb;
    private TextToSpeech mTTS;
    public MediaPlayer felicitaciones;
    public String[] vectorimg = {"uno", "dos", "tres", "cuatro"};
    public int nombreimg, i=0, id, id2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_nivel2);

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

        text1 = (TextView)findViewById(R.id.txtcambio) ;
        text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTTS.speak(vectorimg[i], TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        text2 = (TextView)findViewById(R.id.txtcambio2);
        text2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTTS.speak(vectorimg[i],TextToSpeech.QUEUE_FLUSH,null);
            }
        });
        juntar();
    }

    private void juntar(){

    }

}
