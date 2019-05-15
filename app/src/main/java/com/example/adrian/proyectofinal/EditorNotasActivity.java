package com.example.adrian.proyectofinal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class EditorNotasActivity extends AppCompatActivity {
    private EditText etTitulo,contenidoNota;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor_notas);
        etTitulo = findViewById(R.id.ptTituloNota);
        contenidoNota = findViewById(R.id.etContenidoNota);

        String elTitulo = getIntent().getStringExtra("tituloNota");
        String elContenido = getIntent().getStringExtra("contenidoNota");

    }

    public void modificar(){

    }

    public void borrar(){

    }

}
