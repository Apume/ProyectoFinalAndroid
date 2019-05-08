package com.example.adrian.proyectofinal;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class PantallaAdmin extends AppCompatActivity {

    EditText etGestionarUs;
    AdminSQLiteOpenHelper admin;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_admin);

        etGestionarUs = findViewById(R.id.etGestionUsers);
        //bd
        admin = new AdminSQLiteOpenHelper(this,"administracion",null,1);
        db = admin.getWritableDatabase();
        justDoIt();
    }



    public void borrar(View v){
        String unNombre = etGestionarUs.getText().toString();
        //Cursor filaBorar = db.rawQuery("delete from usuarios where nombre = " + unNombre, null);
        //filaBorar.close();

        //justDoIt();

    }

    public void modificar(View v){

    }

    public void agregar(View v){

    }

    public void cerrarSesion(View v){
        Intent i = new Intent(this, MainActivity.class);
        finish();
        startActivity(i);
    }

    @Override
    public void onBackPressed(){ //metodo del boton fisico atrás del telefono
        //dejar en blanco esto inutilizara el botón
    }

    public void justDoIt(){
        //creamos los objetos necesarios
        ScrollView svUsuarios = findViewById(R.id.svListaNombres);

        TextView tv = null;
        
        //creamos un linear layout para pasarselo al scrollView
        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);

        //obtenemos la consulta de todos los usuarios y los añadimos a text view
//        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion",null,1);
//        SQLiteDatabase db = admin.getWritableDatabase();
        Cursor fila = db.rawQuery("select nombre, clave from usuarios", null);


        if (fila.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                String codigo = fila.getString(0);
                String nombre = fila.getString(1);

                tv = new TextView(this);
                tv.setText("Nombre de usuario: " + codigo + "\tContraseña: " + nombre);
                ll.addView(tv);

            } while (fila.moveToNext());
        }
        fila.close();
        db.close();

        //finalmente añadimos el layout al scrollView
        svUsuarios.addView(ll);
    }
}
