package com.example.adrian.proyectofinal;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

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
    private String nombreCancion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sonidos);

        this.setTitle(R.string.titulo_reproductor); //titulo del actionBar


        //meter las canciones en el listview
        lvSonidos= findViewById(R.id.lvSonidos);
        ArraySonidos = new ArrayList<>();
        ArraySonidos.add("caillou");
        ArraySonidos.add("lonely");
        adaptador = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,ArraySonidos);
        lvSonidos.setAdapter(adaptador);

        //Obtenemos los tres botones de la interfaz
        bPlay= findViewById(R.id.bPlay);
        bStop= findViewById(R.id.bStop);
        bPause= findViewById(R.id.bPause);
        bPause.setVisibility(View.INVISIBLE);

        //Y les asignamos el controlador de eventos
        bPlay.setOnClickListener(this);
        bStop.setOnClickListener(this);
        bPause.setOnClickListener(this);

        //--------------------------------------------------------------------------------------------------------------------------------------
        lvSonidos.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
                // TODO Auto-generated method stub
                String itemval = (String)lvSonidos.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(), "Has elegido " + itemval, Toast.LENGTH_SHORT).show();
                if(mediaplayer!=null){
                    mediaplayer.stop();
                    mediaplayer.release();
                }

                nombreCancion = itemval;

                if(nombreCancion.equals("caillou")){
                    bPlay.setVisibility(View.VISIBLE);
                    bPause.setVisibility(View.INVISIBLE);
                    mediaplayer = MediaPlayer.create(getBaseContext(), R.raw.caillou);
                }
                if(nombreCancion.equals("lonely")){
                    bPlay.setVisibility(View.VISIBLE);
                    bPause.setVisibility(View.INVISIBLE);
                    mediaplayer = MediaPlayer.create(getBaseContext(), R.raw.lonely);
                }
            }
        });
        //--------------------------------------------------------------------------------------------------------------------------------------
    }

    //----------------------------------------------
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.bPlay:
                //Iniciamos el audio
                bPlay.setVisibility(View.INVISIBLE);
                bPause.setVisibility(View.VISIBLE);
                mediaplayer.start();
                break;

            case R.id.bPause:
                //Pausamos el audio
                bPlay.setVisibility(View.VISIBLE);
                bPause.setVisibility(View.INVISIBLE);
                mediaplayer.pause();
                break;

            case R.id.bStop:
                //Paramos el audio
                try {
                    mediaplayer.stop();
                    mediaplayer.prepare();
                    mediaplayer.seekTo(0);
                    bPlay.setVisibility(View.VISIBLE);
                    bPause.setVisibility(View.INVISIBLE);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void onBackPressed(){ //metodo del boton fisico atrás del telefono
        //dejar en blanco esto inutilizara el botón
        if(mediaplayer!=null){
            mediaplayer.stop();
            mediaplayer.release();
        }
        Intent i = new Intent(this, NavigationActivity.class);
        startActivity(i);
        this.finish();
    }
}
