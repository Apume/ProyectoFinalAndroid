package com.example.adrian.proyectofinal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static android.provider.Telephony.Mms.Part.FILENAME;

public class NotasActivity extends AppCompatActivity {

    private ListView lvNotas;
    private EditText etNotas;
    private ArrayAdapter adaptador;
    private ArrayList<String> arrayNombreNotas;
    private HashMap <String,String> lista = new HashMap();
    private File FILENAME = new File("./notas.csv");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notas);
        lvNotas = findViewById(R.id.lvNotas);
        etNotas = findViewById(R.id.editTextNotas);
        listados();
        escribir();

    }
    public void listados(){

        arrayNombreNotas = new ArrayList<>();
        lista.put("Pepe","hoa");
        lista.put("Paco","adios");
        lista.put("Pepa","hoa");
        lista.put("Pepo","hoa");
        lista.put("Pepr","hoa");
        lista.put("Pepw","hoa");
        Iterator it = lista.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry e = (Map.Entry)it.next();
            arrayNombreNotas.add(e.getKey().toString());
        }

        adaptador = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, arrayNombreNotas);
        lvNotas.setAdapter(adaptador);
    }
    public void escribir(){


        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILENAME))) {
            String datosAloj = "";
            for (Map.Entry<String, String> entry : lista.entrySet()) {
                /*Alojamiento alojamientoEnMemoria = entry.getValue(); // coje los valores del objeto recorriedo en el for
                datosAloj = alojamientoEnMemoria.getInfoAlojamientoParaArchivo(); // formateo los datos del objeto que utilizo para llamar al meto getInfoAlojamientoParaArchivo
                //System.out.println(datosAloj);*/
                bw.write(entry.getKey()+ ":" +entry.getValue()+"\n"); // escribe los datos completos de cada alojamiento junto con los saltos de linea correspondientes
            }
        } catch (IOException e) {
            System.out.println("Error E/S: " + e);
        }
        Toast.makeText(this,"texto escrito",Toast.LENGTH_SHORT).show();

    }
    public void leer(){

    }
}
