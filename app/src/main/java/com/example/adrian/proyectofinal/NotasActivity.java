package com.example.adrian.proyectofinal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
    private File FILENAME = new File("/storage/emulated/0/Android/data/com.example.adrian.proyectofinal/files/notas.txt");
    private TextView tituloNota;
    private String contenidoNota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notas);
        lvNotas = findViewById(R.id.lvNotas);

        listados();

        lvNotas.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
                // TODO Auto-generated method stub

                Intent e = new Intent(getApplicationContext(), EditorNotasActivity.class);
                String notaSeleccionada = lvNotas.getItemAtPosition(position).toString();
                contenidoNota = lista.get(notaSeleccionada);
                e.putExtra("tituloNota",notaSeleccionada);
                e.putExtra("contenidoNota",contenidoNota);
                startActivity(e);
            }
        });


    }
    public void listados(){

        arrayNombreNotas = new ArrayList<>();

        Iterator it = lista.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry e = (Map.Entry)it.next();
            arrayNombreNotas.add(e.getKey().toString());
        }

        adaptador = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, arrayNombreNotas);
        lvNotas.setAdapter(adaptador);
    }
    public void escribir(){

        String contenido = etNotas.getText().toString();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILENAME))) {
            String datosAloj = "";
            for (Map.Entry<String, String> entry : lista.entrySet()) {
                bw.write(entry.getKey()+ ":" +entry.getValue()+"\n");
            }
        } catch (IOException e) {
            System.out.println("Error E/S: " + e);
        }
        Toast.makeText(this,"texto escrito",Toast.LENGTH_SHORT).show();

    }

    /*public boolean leer(){
        String titulo,contenido;
        try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))){
            String lineaLeida = br.readLine();
            while(lineaLeida != null){
                String[] partesLinea = lineaLeida.split(":");
                titulo = partesLinea[0];
                contenido = partesLinea[1];
                nota
                lineaLeida = br.readLine();
            }
            br.close();
            return true;

        } catch (FileNotFoundException e) {
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return  false;
        }
    }*/

    public void crearNota(View v){
        Intent i = new Intent(this,EditorNotasActivity.class);
        startActivity(i);
    }

}
