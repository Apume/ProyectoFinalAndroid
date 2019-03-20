package com.example.adrian.proyectofinal;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    }
    public void entrar (View v)
    {
        Intent i = new Intent(this, NavigationActivity.class);
        startActivity(i);
    }

    public void bRegistrar (View view){
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
        if(pass!=null&& nombre!=null){

            if(comprobarBD(nombre, pass)==false){
                registrar(nombre, pass);
                Toast.makeText(this,"Usuario registrado correctamente", Toast.LENGTH_SHORT).show();
            }


        }else {
            Toast.makeText(this,"rellena los campos", Toast.LENGTH_SHORT).show();
        }

    }

    public  void  registrar(String nombre, String pass) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion",null,1);
        SQLiteDatabase db = admin.getWritableDatabase();

            ContentValues registro = new ContentValues();
            registro.put("clave",pass);
            registro.put("nombre",nombre);

            db.insert("usuarios",null, registro);
            db.close();
            etNombre.setText("");
            etPass.setText("");

    }

    public boolean comprobarBD(String nombre, String pass){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion",null,1);
        SQLiteDatabase db = admin.getWritableDatabase();

            Cursor elCursor = db.rawQuery("select nombre from usuarios where nombre = "+nombre, null);
            if(elCursor.moveToFirst()){
                Toast.makeText(this,elCursor.getCount(), Toast.LENGTH_SHORT).show();
                if(elCursor.getCount()!=0) {
                    int numUsuariosEncontrados = elCursor.getCount();
                    Toast.makeText(this,"El usuario ya existe. Usuarios:"+numUsuariosEncontrados, Toast.LENGTH_SHORT).show();
                    return true;
                }
                db.close();
            }
            return false;

    }
}
