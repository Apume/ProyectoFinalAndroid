package com.example.adrian.proyectofinal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EditorNotasActivity extends AppCompatActivity {
    private EditText etTitulo,contenidoNota;
    private String elTitulo, elContenido;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor_notas);
        etTitulo = findViewById(R.id.ptTituloNota);
        contenidoNota = findViewById(R.id.etContenidoNota);

        String elTitulo = getIntent().getStringExtra("tituloNota");
        String elContenido = getIntent().getStringExtra("contenidoNota");

        comprobarCampos(elTitulo, elContenido);

    }

    public void modificar(View v){
        Intent i = new Intent(this, NotasActivity.class);
        elTitulo = etTitulo.getText().toString();
        elContenido = contenidoNota.getText().toString();
        i.putExtra("titulo",elTitulo);
        i.putExtra("contenido", elContenido);
        startActivity(i);

    }

    public void borrar(View v){
        etTitulo.setText(null);
        contenidoNota.setText(null);
    }

    public void cancelar(View v){
        Intent i = new Intent(this, NotasActivity.class);
        startActivity(i);
    }

    public void comprobarCampos(String elTitulo, String elContenido){
        if(elTitulo == null){
            etTitulo.setText("No hay titulo");
        }else{
            etTitulo.setText(elTitulo);
        }

        if(elContenido == null){
            contenidoNota.setText("No hay contenido");
        }else{
            contenidoNota.setText(elContenido);
        }
    }
}
