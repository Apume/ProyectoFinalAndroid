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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final int PERMISSION_REQUEST_CODE = 1;
    private final int TOMAR_FOTO = 55;
    private String nombreUser, passUser;
    private ImageView avatarUser;
    private TextView tvNombreUserNav;
    private int id, user;
    private ImageButton botonFondo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        botonFondo = findViewById(R.id.botonFondo);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        //con la siguiente linea obtienes el acceso a los componentes del navigationHeader
        View viewHeader = navigationView.getHeaderView(0);

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        avatarUser = viewHeader.findViewById(R.id.avatarMenu);
        tvNombreUserNav = viewHeader.findViewById(R.id.tvBienvenido);
        nombreUser = getIntent().getStringExtra("nombreUser");
        passUser = getIntent().getStringExtra("passUser");

        asignarAvatares(nombreUser, passUser);
    }

    public void asignarAvatares(String nombreUser, String passUser){

        if(nombreUser.equals("zizu") && passUser.equals("loli")){
            user = 1;
        }else if(nombreUser.equals("vitilla") && passUser.equals("tortilla")){
            user = 2;
        }else if(nombreUser.equals("adri") && passUser.equals("dri")){
            user = 3;
        }else
            if(nombreUser.equals("jairo") && passUser.equals("4k")){
            user = 4;
        }

        switch (user){
            case 1:
                id = getResources().getIdentifier("avatarzizu", "drawable", getPackageName());
                avatarUser.setImageResource(id);
                tvNombreUserNav.setText("Bienvenido "+nombreUser);
                break;

            case 2:
                id = getResources().getIdentifier("avatarvitilla", "drawable", getPackageName());
                avatarUser.setImageResource(id);
                tvNombreUserNav.setText("Bienvenido "+nombreUser);
                break;

            case 3:
                id = getResources().getIdentifier("avataradri", "drawable", getPackageName());
                avatarUser.setImageResource(id);
                tvNombreUserNav.setText("Bienvenido "+nombreUser);
                break;

            case 4:
                id = getResources().getIdentifier("avatarjairo", "drawable", getPackageName());
                avatarUser.setImageResource(id);
                tvNombreUserNav.setText("Bienvenido "+nombreUser);
                break;

            default:
                id = getResources().getIdentifier("iconodemo", "drawable", getPackageName());
                avatarUser.setImageResource(id);
                tvNombreUserNav.setText("Bienvenido "+nombreUser);

        }


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
            Toast.makeText(this, "Creado por: Adrián, Cristian, Jairo, y Victor", Toast.LENGTH_LONG).show();
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
                Intent notas = new Intent(this, NotasActivity.class );
                startActivity(notas);
                break;

            case R.id.nav_sonidos:
                Intent sonidos = new Intent(this,SonidosActivity.class);
                sonidos.putExtra("nombreUser", nombreUser);
                sonidos.putExtra("passUser", passUser);
                startActivity(sonidos);
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // método intener aleatorio entre maximo y minimo parametrizados.
    public static int getRandomInteger(int maximum, int minimum){
        return ((int) (Math.random()*(maximum - minimum))) + minimum;
    }

    public void cambiarImagen (View v) {
        int num = getRandomInteger(6,1);

        switch (num){
            case 1:
                id = getResources().getIdentifier("fondouno", "drawable", getPackageName());
                botonFondo.setImageResource(id);
                break;
            case 2:
                id = getResources().getIdentifier("fondodos", "drawable", getPackageName());
                botonFondo.setImageResource(id);
                break;
            case 3:
                id = getResources().getIdentifier("fondotres", "drawable", getPackageName());
                botonFondo.setImageResource(id);
                break;
            case 4:
                id = getResources().getIdentifier("fondocuatro", "drawable", getPackageName());
                botonFondo.setImageResource(id);
                break;
            case 5:
                id = getResources().getIdentifier("fondocinco", "drawable", getPackageName());
                botonFondo.setImageResource(id);
                break;
        }
    }

}
