package com.example.adrian.proyectofinal;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class PantallaAdmin extends AppCompatActivity {

    private EditText etGesNombreUser, etGesPassUser;
    private Button bBorrar, bModificar, bCerrarSesion;
    private AdminSQLiteOpenHelper admin;
    private SQLiteDatabase db;
    private ArrayList<String> ArrayUsuarios;
    private ArrayAdapter adaptador;
    private ListView lvUsuarios;
    private String userSelected, passSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_admin);

        lvUsuarios = findViewById(R.id.lvUsuarios);
        etGesNombreUser = findViewById(R.id.etGesNombreUser);
        etGesPassUser = findViewById(R.id.etGesPassUser);
        bBorrar = findViewById(R.id.bBorrar);
        bModificar = findViewById(R.id.bModificar);
        bCerrarSesion = findViewById(R.id.bCerrarSesion);

        etGesNombreUser.setVisibility(View.INVISIBLE);
        etGesPassUser.setVisibility(View.INVISIBLE);
        bBorrar.setVisibility(View.INVISIBLE);
        bModificar.setVisibility(View.INVISIBLE);
        bCerrarSesion.setVisibility(View.INVISIBLE);


        //admin = new AdminSQLiteOpenHelper(this,"administracion",null,1);

        justDoIt();


        //En este metodo asignamos a los items del ListView
        lvUsuarios.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
                // TODO Auto-generated method stub
                String itemSelected = lvUsuarios.getItemAtPosition(position).toString().split(" ")[1];

                etGesNombreUser.setVisibility(View.VISIBLE);
                etGesPassUser.setVisibility(View.VISIBLE);
                bBorrar.setVisibility(View.VISIBLE);
                bModificar.setVisibility(View.VISIBLE);
                bCerrarSesion.setVisibility(View.VISIBLE);

                etGesNombreUser.setText(selectBD(itemSelected).get(0));
                etGesPassUser.setText(selectBD(itemSelected).get(1));

                userSelected = selectBD(itemSelected).get(0);
                passSelected = selectBD(itemSelected).get(1);

                Toast.makeText(getApplicationContext(), "Usuario "+itemSelected+" seleccionado", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void borrar(View v){
        String nombreUser = etGesNombreUser.getText().toString();
        if(!TextUtils.isEmpty(nombreUser)){
            AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion",null,1);
            SQLiteDatabase db = admin.getWritableDatabase();

            if(db.delete("usuarios","nombre ="+"'"+nombreUser+"'", null)==1){
                Toast.makeText(this, "Usuario eliminado", Toast.LENGTH_SHORT).show();
                etGesNombreUser.setText("");
                etGesPassUser.setText("");
            }else{
                Toast.makeText(this, "El usuario no existe", Toast.LENGTH_SHORT).show();

            }

            db.close();

        }else{
            etGesNombreUser.setError("Introduce un usuario");

        }


        //justDoIt();

    }

    public void modificar(View v){
        String nombreUser = etGesNombreUser.getText().toString();
        String passUser = etGesPassUser.getText().toString();
        if(!TextUtils.isEmpty(nombreUser)){

            db.execSQL("UPDATE usuarios SET nombre='"+nombreUser+"', clave='"+passUser+"' WHERE nombre= '"+userSelected+"'");

        }else{

            if(TextUtils.isEmpty(nombreUser)) {
                etGesNombreUser.setError("Introduce un usuario");
            }
            if(TextUtils.isEmpty(passUser)){
                etGesPassUser.setError("Introduce una contraseña");
            }


        }



    }

    public void cancelar(View v){
        etGesNombreUser.setText("");
        etGesPassUser.setText("");

        etGesNombreUser.setVisibility(View.INVISIBLE);
        etGesPassUser.setVisibility(View.INVISIBLE);
        bBorrar.setVisibility(View.INVISIBLE);
        bModificar.setVisibility(View.INVISIBLE);
        bCerrarSesion.setVisibility(View.INVISIBLE);
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

        ArrayUsuarios = new ArrayList<>();

        //obtenemos la consulta de todos los usuarios y los añadimos a text view
        admin = new AdminSQLiteOpenHelper(this,"administracion",null,1);
        db = admin.getWritableDatabase();
        Cursor fila = db.rawQuery("select * from usuarios", null);


        if (fila.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                String nombreUser = "► " + fila.getString(0);
                ArrayUsuarios.add(nombreUser);


            } while (fila.moveToNext());
        }
        adaptador = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, ArrayUsuarios);
        lvUsuarios.setAdapter(adaptador);


        fila.close();
    }

    public ArrayList<String> selectBD(String nombreUser){
        ArrayList<String> datosUser = new ArrayList<>();
        admin = new AdminSQLiteOpenHelper(this,"administracion",null,1);
        db = admin.getWritableDatabase();
        Cursor fila = db.rawQuery("select * from usuarios where nombre = '"+nombreUser+"'", null);

        fila.moveToFirst();
        datosUser.add(fila.getString(0));
        datosUser.add(fila.getString(1));
        fila.close();


        return datosUser;
    }


}
