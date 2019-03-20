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

    }
    public void entrar (View v)
    {
        Intent i = new Intent(this, NavigationActivity.class);
        nombre = etNombre.getText().toString();
        pass = etPass.getText().toString();

        if(comprobarBD(nombre, pass)) {
            startActivity(i);
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

        String[] args = new String[] {nombre};

            Cursor elCursor = db.rawQuery("select clave, nombre from usuarios where nombre =?", args);
            if(elCursor.moveToFirst()){

                do{
                    String usuarioEncontrado= elCursor.getString(1);
                    Toast.makeText(this,"Usuario: "+usuarioEncontrado+" ya existe", Toast.LENGTH_SHORT).show();
                }while(elCursor.moveToNext());

                db.close();
            }
            return false;

    }
}
