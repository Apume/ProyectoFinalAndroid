package com.example.adrian.proyectofinal;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;

public class SonidosActivity extends AppCompatActivity implements View.OnClickListener{
    private Button bPlay;
    private Button bPause;
    private Button bStop;
    private MediaPlayer mediaplayer;
    private ListView lvSonidos;
    private ArrayList<String> ArraySonidos;
    private ArrayAdapter adaptador;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sonidos);
        //meter las canciones en el listview
        lvSonidos= findViewById(R.id.lvSonidos);
        ArraySonidos = new ArrayList<>();
        ArraySonidos.add("caillou");
        ArraySonidos.add("lonely");

        adaptador = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,ArraySonidos);
        lvSonidos.setAdapter(adaptador);

        //Inicializamos la clase MediaPlayer asociandole el fichero de Audio
        mediaplayer = MediaPlayer.create(this, R.raw.caillou);

        //Obtenemos los tres botones de la interfaz
        bPlay= findViewById(R.id.bPlay);
        bStop= findViewById(R.id.bStop);
        bPause= findViewById(R.id.bPause);

        //Y les asignamos el controlador de eventos
        bPlay.setOnClickListener(this);
        bStop.setOnClickListener(this);
        bPause.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.bPlay:
                //Iniciamos el audio
                mediaplayer.start();
                break;
            case R.id.bPause:
                //Pausamos el audio
                mediaplayer.pause();
                break;
            case R.id.bStop:
                //Paramos el audio y volvemos a inicializar
                try {
                    mediaplayer.stop();
                    mediaplayer.prepare();
                    mediaplayer.seekTo(0);
                } catch (IOException e) {
                    e.printStackTrace();
                }


                break;

        }
    }
}
