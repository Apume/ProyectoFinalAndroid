package com.example.adrian.proyectofinal;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class PantallaAdmin extends AppCompatActivity {

    private EditText etGesNombreUser, etGesPassUser;
    private TextView tvUserSelected, tvPassSelected, tvToast;
    private Button bBorrar, bModificar, bCancelar, bCerrarSesion;
    private AdminSQLiteOpenHelper admin;
    private SQLiteDatabase db;
    private ArrayList<String> ArrayUsuarios;
    private ArrayAdapter adaptador;
    private ListView lvUsuarios;
    private String userSelected, passSelected;
    private Toast toast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_admin);

        // Toast personalizado
        toast = new Toast(getApplicationContext());

        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.layout_toast,
                (ViewGroup) findViewById(R.id.lytToast));

        tvToast = layout.findViewById(R.id.tvToast);
        toast.setGravity(Gravity.CENTER|Gravity.CENTER_HORIZONTAL, 0,0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);




        // Declaracion de los componentes -------------------------------
        lvUsuarios = findViewById(R.id.lvUsuarios);
        tvUserSelected = findViewById(R.id.tvUserSelected);
        tvPassSelected = findViewById(R.id.tvPassSelected);
        etGesNombreUser = findViewById(R.id.etGesNombreUser);
        etGesPassUser = findViewById(R.id.etGesPassUser);
        bBorrar = findViewById(R.id.bBorrar);
        bModificar = findViewById(R.id.bModificar);
        bCancelar = findViewById(R.id.bCancelar);
        bCerrarSesion = findViewById(R.id.bCerrarSesion);

        tvUserSelected.setVisibility(View.INVISIBLE);
        tvPassSelected.setVisibility(View.INVISIBLE);
        etGesNombreUser.setVisibility(View.INVISIBLE);
        etGesPassUser.setVisibility(View.INVISIBLE);
        bBorrar.setVisibility(View.INVISIBLE);
        bModificar.setVisibility(View.INVISIBLE);
        bCancelar.setVisibility(View.INVISIBLE);
        //---------------------------------------------------------------

        justDoIt();

        lvUsuarios.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
                // TODO Auto-generated method stub
                String itemSelected = lvUsuarios.getItemAtPosition(position).toString().split(" ")[1];

                tvUserSelected.setVisibility(View.VISIBLE);
                tvPassSelected.setVisibility(View.VISIBLE);
                etGesNombreUser.setVisibility(View.VISIBLE);
                etGesPassUser.setVisibility(View.VISIBLE);
                bBorrar.setVisibility(View.VISIBLE);
                bModificar.setVisibility(View.VISIBLE);
                bCancelar.setVisibility(View.VISIBLE);

                userSelected = selectBD(itemSelected).get(0);
                passSelected = selectBD(itemSelected).get(1);

                tvUserSelected.setText("Modificando el usuario \n➤ "+userSelected);
                tvPassSelected.setText("Contraseña actual \n➤ "+passSelected);
                etGesNombreUser.setText(userSelected);
                etGesPassUser.setText(passSelected);

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
                limpiarVentana();
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

        if(!TextUtils.isEmpty(nombreUser) && !TextUtils.isEmpty(passUser)){
            ContentValues valores = new ContentValues();
            valores.put("nombre",nombreUser);
            valores.put("clave", passUser);

            db.update("usuarios", valores, "nombre='"+userSelected+"'", null);
            tvToast.setText(" Usuario "+userSelected+" actualizado");
            toast.show();
            limpiarVentana();
            //db.execSQL("UPDATE usuarios SET nombre='"+nombreUser+"', clave='"+passUser+"' WHERE nombre= '"+userSelected+"'");

        }else{

            if(TextUtils.isEmpty(nombreUser)) {
                etGesNombreUser.requestFocus();
                etGesNombreUser.setError("Introduce un usuario");
            }
            if(TextUtils.isEmpty(passUser)){
                etGesPassUser.setError("Introduce una contraseña");
            }
        }
    }

    public void cancelar(View v){
        limpiarVentana();

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

    public void limpiarVentana(){
        tvUserSelected.setText("");
        tvPassSelected.setText("");
        etGesNombreUser.setText("");
        etGesPassUser.setText("");

        tvUserSelected.setVisibility(View.INVISIBLE);
        tvPassSelected.setVisibility(View.INVISIBLE);
        etGesNombreUser.setVisibility(View.INVISIBLE);
        etGesPassUser.setVisibility(View.INVISIBLE);
        bBorrar.setVisibility(View.INVISIBLE);
        bModificar.setVisibility(View.INVISIBLE);
        bCancelar.setVisibility(View.INVISIBLE);
    }

}
