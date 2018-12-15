package movil.tesis.miguel.opuestos;

import android.content.ClipData;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
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

public class nivel3 extends AppCompatActivity {
    ImageView imagenizquierda, imagenderecha;
    TextView txtuno, txtdos, txttres, txtcuatro, txtcinco, txtcambio, txtcambio2;
    Button cargar;
    String texto = "";
    private TextToSpeech mTTS;
    public String[] arrayNombres1 = new String[20];
    public String[] arrayNombres2 = new String[20];
    public MediaPlayer felicitaciones;
    public int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_nivel3);

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
                        Toast.makeText(nivel3.this, "Idioma no Válido", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
        imagenizquierda = (ImageView) findViewById(R.id.imgcambio);
        imagenderecha = (ImageView) findViewById(R.id.imgcambio2);
        txtuno = (TextView) findViewById(R.id.txtuno);
        txtdos = (TextView) findViewById(R.id.txtdos);
        txttres = (TextView) findViewById(R.id.txttres);
        txtcuatro = (TextView) findViewById(R.id.txtcuatro);
        txtcinco = (TextView) findViewById(R.id.txtcinco);
        txtcambio = (TextView) findViewById(R.id.txtcambio);
        txtcambio2 = (TextView) findViewById(R.id.txtcambio2);

        cargar = (Button) findViewById(R.id.btncambio);
        cargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarImg();
            }
        });


        txtuno.setOnLongClickListener(longClickListener);
        txtdos.setOnLongClickListener(longClickListener);
        txttres.setOnLongClickListener(longClickListener);
        txtcuatro.setOnLongClickListener(longClickListener);
        txtcinco.setOnLongClickListener(longClickListener);
        txtcambio.setOnDragListener(dragListener);
        txtcambio2.setOnDragListener(dragListener2);

    }

    View.OnLongClickListener longClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            ClipData clipData = ClipData.newPlainText("", "");
            View.DragShadowBuilder myShadowbuilder = new View.DragShadowBuilder(v);
            v.startDrag(clipData, myShadowbuilder, v, 0);
            return true;
        }
    };

    View.OnDragListener dragListener = new View.OnDragListener() {
        @Override
        public boolean onDrag(View v, DragEvent event) {

            int dragevent = event.getAction();

            switch (dragevent) {
                case DragEvent.ACTION_DRAG_ENTERED:

                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    break;
                case DragEvent.ACTION_DROP:
                    final View view = (View) event.getLocalState();
                    if (view.getId() == R.id.txtuno) {
                        txtcambio.setText(txtuno.getText().toString());
                        mTTS.speak(txtuno.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
                    }
                    break;
            }


            return true;
        }
    };

    View.OnDragListener dragListener2 = new View.OnDragListener() {
        @Override
        public boolean onDrag(View v, DragEvent event) {

            int dragevent = event.getAction();

            switch (dragevent) {
                case DragEvent.ACTION_DRAG_ENTERED:

                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    break;
                case DragEvent.ACTION_DROP:
                    final View view = (View) event.getLocalState();
                    if (view.getId() == R.id.txtdos) {
                        txtcambio2.setText(txtdos.getText().toString());
                        mTTS.speak(arrayNombres2[i], TextToSpeech.QUEUE_FLUSH, null);
                    }
                    break;
            }


            return true;
        }
    };

    public void cargarImg() {
        cargar.setText("CAMBIAR");
        i++;
        if (arrayNombres1[i] != null) {

            Picasso.get().load(new File("/data/data/movil.tesis.miguel.opuestos/app_picasso/" + arrayNombres1[i] + ".png")).into(imagenizquierda);
            txtuno.setText(arrayNombres1[i]);
            Picasso.get().load(new File("/data/data/movil.tesis.miguel.opuestos/app_picasso/" + arrayNombres2[i] + ".png")).into(imagenderecha);
            txtdos.setText(arrayNombres2[i]);
            txttres.setText(arrayNombres1[randomico(i)]);
            txtcuatro.setText(arrayNombres1[randomico(i)]);
            txtcinco.setText(arrayNombres1[randomico(i)]);
            txtcambio.setText("");
            txtcambio2.setText("");
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

    private void termino() {
        imagenizquierda.setImageResource(R.color.blanco);
        imagenizquierda.setEnabled(false);
        imagenderecha.setEnabled(false);
        imagenderecha.setImageResource(R.color.blanco);
        txtuno.setText("");
        txtdos.setText("");
        txttres.setText("");
        txtcuatro.setText("");
        txtcinco.setText("");
        cargar.setText("TERMINÓ EL NIVEL 3");
        LayoutInflater myInflator = getLayoutInflater();
        View myLayout = myInflator.inflate(R.layout.felicitaciones, (ViewGroup) findViewById(R.id.lay_felicitaciones));
        felicitaciones = MediaPlayer.create(getApplicationContext(), R.raw.felicitaciones);
        felicitaciones.start();
        Toast myToast = new Toast(getApplicationContext());
        myToast.setDuration(Toast.LENGTH_LONG);
        myToast.setGravity(Gravity.FILL, 0, 0);
        //algo
        myToast.setView(myLayout);
        myToast.show();
        cargar.setEnabled(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent regresarhome = new Intent(nivel3.this, Principal.class);
                startActivity(regresarhome);
            }
        }, 3500);
    }
}
