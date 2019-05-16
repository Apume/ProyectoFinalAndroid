package com.example.adrian.proyectofinal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class EditorNotasActivity extends AppCompatActivity {
    private EditText etTitulo,contenidoNota;
    private String elTitulo, elContenido;
    private File FILENAME = new File("/storage/emulated/0/Android/data/com.example.adrian.proyectofinal/files/notas.txt");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor_notas);
        etTitulo = findViewById(R.id.ptTituloNota);
        contenidoNota = findViewById(R.id.etContenidoNota);

        String elTitulo = getIntent().getStringExtra("tituloNota");
        String elContenido = getIntent().getStringExtra("contenidoNota");

        comprobarCampos(elTitulo,elContenido);

    }

    public void modificar(View v){
        Intent i = new Intent(this, NotasActivity.class);
        elTitulo = etTitulo.getText().toString();
        elContenido = contenidoNota.getText().toString();
        if(comprobarCampos(elTitulo,elContenido)== false){
            Toast.makeText(this,"Rellene los campos",Toast.LENGTH_SHORT).show();
        }else{
            i.putExtra("titulo",elTitulo);
            i.putExtra("contenido", elContenido);
            startActivity(i);
        }


    }

    public void borrar(View v){
        etTitulo.setText(null);
        contenidoNota.setText(null);
    }

    public void cancelar(View v){
        Intent i = new Intent(this, NotasActivity.class);
        startActivity(i);
    }


    public boolean comprobarCampos(String elTitulo, String elContenido){
        if(elTitulo == null || elContenido == null){
            Toast.makeText(this,"Rellene los campos",Toast.LENGTH_SHORT).show();
            return true;
        }else{
            return false;
        }

    }
}
