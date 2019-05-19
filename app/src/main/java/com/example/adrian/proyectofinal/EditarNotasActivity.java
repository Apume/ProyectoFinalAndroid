package com.example.adrian.proyectofinal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

public class EditarNotasActivity extends AppCompatActivity {

    private Button bGuardar, bCancelar, bBorrar;
    private EditText etNombreNota, etContenidoNota;
    private String nombreNota, contenidoNota;
    private int opcionActivity, contadorEditarNota;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_notas);

        etNombreNota = findViewById(R.id.etNombreNota);
        etContenidoNota = findViewById(R.id.etContenidoNota);

        bGuardar = findViewById(R.id.bGuardar);
        bCancelar = findViewById(R.id.bCancelar);
        bBorrar = findViewById(R.id.bBorrar);

        bCancelar.setVisibility(View.INVISIBLE);
        bBorrar.setVisibility(View.INVISIBLE);

        contadorEditarNota = getIntent().getIntExtra("contadorEditarNota", contadorEditarNota);

        opcionActivity = getIntent().getIntExtra("opcionActivity", opcionActivity);

        if(opcionActivity!=0){
            bCancelar.setVisibility(View.VISIBLE);
        }else{
            bBorrar.setVisibility(View.VISIBLE);
        }

        if(contadorEditarNota!=0){
            nombreNota = getIntent().getStringExtra("nombreNota");
            contenidoNota = getIntent().getStringExtra("contenidoNota");

            etNombreNota.setText(nombreNota);
            etContenidoNota.setText(contenidoNota);

        }


    }

    public void bGuardar(View v) // Guarda los cambios hechos en la nota
    {

        nombreNota = etNombreNota.getText().toString();
        contenidoNota = etContenidoNota.getText().toString();

        if(!TextUtils.isEmpty(nombreNota) && !TextUtils.isEmpty(contenidoNota)) {
            Intent i = new Intent(this, NotasActivity.class);
            int contadorAgregarNota = 1;

            i.putExtra("nombreNota", nombreNota);
            i.putExtra("contenidoNota", contenidoNota);

            i.putExtra("contadorAgregarNota", contadorAgregarNota);


            finish();
            startActivity(i);

        }else{
            if(TextUtils.isEmpty(nombreNota)) {
                etNombreNota.setError("Introduce un nombre");
            }
            if(TextUtils.isEmpty(contenidoNota)){
                etContenidoNota.setError("La nota no puede estar vacia");
            }
        }
    }

    public void bCancelar(View v) // Vuelve a la ventana anterior
    {
        Intent i = new Intent(this, NotasActivity.class);
        finish();
        startActivity(i);
    }

    public void bBorrar(View v) // Borra la nota del HashMap
    {
        Intent i = new Intent(this, NotasActivity.class);
        Toast toast = Toast.makeText(getApplicationContext(), "Nota "+nombreNota+" borrada", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER|Gravity.CENTER_HORIZONTAL,0,0);
        toast.show();
        finish();
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        //anulado
    }
}
