package com.example.adrian.proyectofinal;

import android.app.Activity;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Toast;

public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final int PERMISSION_REQUEST_CODE = 1;
    private final int TOMAR_FOTO = 55;
    private String nombreUser, passUser, user;
    private ImageView avatarUser;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*
        avatarUser = findViewById(R.id.avatarMenu);
        nombreUser = getIntent().getStringExtra("nombreUser");
        passUser = getIntent().getStringExtra("passUser");


        if(nombreUser.equals("zizu") && passUser.equals("loli")){
            user = "zizu";
        }else if(nombreUser.equals("vitilla") && passUser.equals("tortilla")){
            user = "vitilla";
        }else if(nombreUser.equals("adri") && passUser.equals("dri")){
            user = "adri";
        }else if(nombreUser.equals("jairo") && passUser.equals("4k")){
            user = "jairo";
        }



        switch (user)
        {
            case "zizu":
                id = getResources().getIdentifier("avatarzizu", "drawable", getPackageName());
                avatarUser.setImageResource(id);
                break;

            case "vitilla":
                id = getResources().getIdentifier("avatarvitilla", "drawable", getPackageName());
                avatarUser.setImageResource(id);
                break;

            case "adri":
                //avatarUser.setImageResource();
                break;

            case "jairo":
                id = getResources().getIdentifier("avatarjairo", "drawable", getPackageName());
                avatarUser.setImageResource(id);
                break;
        }

        */

    // Boton de enviar correo

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    private Intent elintent;
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startActivity(elintent);
                }
                break;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.optionCreditos){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id){
            case R.id.nav_imagenes:
                Intent i = new Intent(); // nuevo Intent
                i.setAction(android.content.Intent.ACTION_VIEW); // clase de acción
                i.setType("image/*"); // a que tipo de datos queremos aplicar la acción
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // queremos un task nuevo
                startActivity(i);
                break;

            case R.id.nav_notas:

                break;

            case R.id.nav_sonidos:

                break;

            case R.id.nav_camara:
                Intent lafoto = new Intent(this, capturaActivity.class);
                startActivity(lafoto);
                break;

            case R.id.nav_cerrarSesion:
                Intent intent = new Intent(this, MainActivity.class);
                finish();
                startActivity(intent);
                break;
        }

        /*
        if (id == R.id.nav_imagenes) {
            /*int SELECT_FILE = 1;
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(intent.ACTION_GET_CONTENT);
            startActivityForResult(
                    intent.createChooser(intent, "Selecciona una imagen"), SELECT_FILE);*/
            /*-----------------------------------------------------------------------------
            Intent i = new Intent(); // nuevo Intent
            i.setAction(android.content.Intent.ACTION_VIEW); // clase de acción
            i.setType("image/*"); // a que tipo de datos queremos aplicar la acción
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // queremos un task nuevo
            startActivity(i);

        }  else if (id == R.id.nav_notas) {

        } else if (id == R.id.nav_sonidos) {

        }
        */

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
