package com.example.adrian.proyectofinal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class NotasActivity extends AppCompatActivity {

    private int contadorAgregarNota = 0;
    private String nombreNotaNueva, contenidoNotaNueva;
    private ListView lvNotas;
    private ArrayAdapter adaptador;
    private ArrayList<String> arrayNombreNotas;
    private HashMap <String,String> HashMapNotas = new HashMap();
    private File FILENAME = new File("/storage/emulated/0/Android/data/com.example.adrian.proyectofinal/files/notas.csv");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notas);

        //compruebo si vengo de crear una nota
        contadorAgregarNota = getIntent().getIntExtra("contadorAgregarNota", contadorAgregarNota);


        lvNotas = findViewById(R.id.lvNotas);

        if(contadorAgregarNota!=0){
            nombreNotaNueva = getIntent().getStringExtra("nombreNota");
            contenidoNotaNueva = getIntent().getStringExtra("contenidoNota");

            agregarNotaAlHashMap(nombreNotaNueva,contenidoNotaNueva);
        }

        leerNotasDelArchivo();

        listados();

        //En este metodo asignamos a los items del ListView su funcion al hacer click en ellos
        lvNotas.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
                // TODO Auto-generated method stub
                String notaSelected = lvNotas.getItemAtPosition(position).toString();

                Intent i = new Intent(getApplicationContext(), EditarNotasActivity.class);

                int contadorEditarNota = 1;
                i.putExtra("contadorEditarNota", contadorEditarNota);
                i.putExtra("nombreNota", notaSelected);
                i.putExtra("contenidoNota", HashMapNotas.get(notaSelected));
                HashMapNotas.remove(notaSelected);
                finish();
                startActivity(i);

            }
        });

    }

    @Override
    protected void onDestroy() //En este metodo se ejecutara el escribir() al cerrar el activity NotasActivity
    {
        super.onDestroy();
        escribir();
    }

    public void leerNotasDelArchivo()  //leera las notas del archivo y las agregara al HashMap
    {

        try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {

            String lineaNota = br.readLine();

            while (lineaNota != null) {
                String[] datosNota = lineaNota.split(":",2);
                HashMapNotas.put(datosNota[0],datosNota[1]);
                lineaNota = br.readLine();
            }

        } catch (Exception e) {
            System.out.println("Error E/S: " + e);
        }

    }

    public void listados() //agrega las notas del HashMap al ListView de notas
    {

        arrayNombreNotas = new ArrayList<>();

        Iterator it = HashMapNotas.entrySet().iterator();
        for (Map.Entry<String, String> entry : HashMapNotas.entrySet()) {
            arrayNombreNotas.add(entry.getKey()); // escribe el nombre de la nota separando por ":" el contenido de la nota
        }

        adaptador = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, arrayNombreNotas);
        lvNotas.setAdapter(adaptador);
    }

    public void escribir() //escribe en el archivo notas.csv las notas que contiene el hashmap
    {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILENAME))) {
            for (Map.Entry<String, String> entry : HashMapNotas.entrySet()) {

                bw.write(entry.getKey()+ ":" +entry.getValue()+"\n"); // escribe el nombre de la nota separando por ":" el contenido de la nota
            }
        } catch (IOException e) {
            System.out.println("Error E/S: " + e);
        }
        //Toast.makeText(this,"texto escrito",Toast.LENGTH_SHORT).show();

    }

    public void bCrearNotaNueva(View view) //metodo del boton Crear Nota nueva
    {
        Intent i = new Intent(this, EditarNotasActivity.class);
        int opcionActivity = 1;
        i.putExtra("opcionActivity", opcionActivity);
        finish();
        startActivity(i);
    }

    public void agregarNotaAlHashMap(String nombreNota, String contenidoNota) //agrega una nota al HashMap
    {
        HashMapNotas.put(nombreNota, contenidoNota);
        Toast toast = Toast.makeText(getApplicationContext(), "Nota "+nombreNota+" guardada", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER|Gravity.CENTER_HORIZONTAL,0,0);
        toast.show();
    }

}
