package com.example.adrian.proyectofinal;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //
    private Button bConfirmarRegistro, bRegistrar, bEntrar;
    private TextView tvRegistro;
    private EditText etNombre, etPass;
    private String nombre, pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //--------------------

        etNombre=findViewById(R.id.etNombre);
        etPass=findViewById(R.id.etPass);
        bRegistrar=findViewById(R.id.bRegistrar);
        bEntrar=findViewById(R.id.bEntrar);
        bConfirmarRegistro=findViewById(R.id.bConfrirmarRegistro);
        bConfirmarRegistro.setVisibility(View.INVISIBLE);

        if(!comprobarBD("root","root")){
            registrar("root", "root");
        }

    }
    public void bEntrar (View view){
        Intent i = new Intent(this, NavigationActivity.class);
        Intent a = new Intent(this, PantallaAdmin.class);
        nombre = etNombre.getText().toString();
        pass = etPass.getText().toString();
        if(!TextUtils.isEmpty(nombre) && !TextUtils.isEmpty(pass)) {
            //------------------------------------------------------
               // if(comprobarAdmin(nombre, pass))
                    //------------------------------------------------------
                if (comprobarBD(nombre, pass)) {
                    if(nombre.equals("root")  && pass.equals("root")){
                        startActivity(a);
                    }
                    else {
                        startActivity(i);
                    }

                } else {
                    Toast.makeText(this, "Usuario o contraseña erroneos", Toast.LENGTH_SHORT).show();
                }

        }else{
            if(TextUtils.isEmpty(nombre)) {
                etNombre.setError("Introduce un nombre");
            }
            if(TextUtils.isEmpty(pass)){
                etPass.setError("Introduce una contraseña");
            }

            Toast.makeText(this, "Por favor, rellena los campos", Toast.LENGTH_SHORT).show();
        }
    }

    public void bRegistrar (View view){
        etNombre.setText("");
        etPass.setText("");
        bEntrar.setVisibility(View.INVISIBLE);
        bRegistrar.setVisibility(View.INVISIBLE);
        bConfirmarRegistro.setVisibility(View.VISIBLE);

    }

    public void bConfirmar (View view){
        bEntrar.setVisibility(View.VISIBLE);
        bRegistrar.setVisibility(View.VISIBLE);
        bConfirmarRegistro.setVisibility(View.INVISIBLE);
        nombre = etNombre.getText().toString();
        pass = etPass.getText().toString();
        if(TextUtils.isEmpty(nombre) && TextUtils.isEmpty(pass)) {
            etNombre.setError("Introduce un nombre");
            etPass.setError("Introduce una contraseña");
            Toast.makeText(this,"rellena los campos", Toast.LENGTH_SHORT).show();

        }else {
            if(!comprobarBD(nombre, pass)) {
                registrar(nombre, pass);
            }else{
                Toast.makeText(this,"Introduce un usuario diferente", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public  void  registrar(String nombre, String pass) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion",null,1);
        SQLiteDatabase db = admin.getWritableDatabase();

            ContentValues registro = new ContentValues();
            registro.put("clave",pass);
            registro.put("nombre",nombre);

            db.insert("usuarios",null, registro);
            Toast.makeText(this,"Usuario: "+nombre+" registrado correctamente", Toast.LENGTH_SHORT).show();
            db.close();
            etNombre.setText("");
            etPass.setText("");
    }

    public boolean comprobarBD(String nombre, String pass){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion",null,1);
        SQLiteDatabase db = admin.getWritableDatabase();

        Cursor fila = db.rawQuery("select nombre, clave from usuarios where nombre= '"+nombre+"' and clave= '"+pass+"'", null);

        if(fila.moveToFirst()){
            fila.close();
            db.close();
            return true;
        }
        fila.close();
        db.close();
        return false;
    }


}
