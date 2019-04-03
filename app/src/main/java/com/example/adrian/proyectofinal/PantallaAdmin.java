package com.example.adrian.proyectofinal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class PantallaAdmin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_admin);
    }
    public void cerrarSesion(View v)
    {
        Intent i = new Intent(this, MainActivity.class);
        finish();
        startActivity(i);
    }

    public void borrar(View v)
    {

    }

    public void modificar(View v)
    {

    }

    public void agregar(View v)
    {

    }

    @Override
    public void onBackPressed(){
        //dejar en blanco esto inutilizara el botón
    }
}
