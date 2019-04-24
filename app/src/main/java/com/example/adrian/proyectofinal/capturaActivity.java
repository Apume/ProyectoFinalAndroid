package com.example.adrian.proyectofinal;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class capturaActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 1;
    private final int TOMAR_FOTO = 55;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_captura);
        this.setTitle(R.string.titulo_camara);

    }
    public void capturarFoto(View v) {
        Intent intent = new Intent(
                android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, TOMAR_FOTO);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == TOMAR_FOTO) {
            ImageView iv_foto = (ImageView) findViewById(R.id.imagenfondo);
            /*
             * Si se reciben datos en el intent tenemos una vista previa
             * (thumbnail)
             */
            if (data != null) {
                Toast.makeText(this,"Foto hecha", Toast.LENGTH_SHORT).show();
                /**
                 * En el caso de una vista previa, obtenemos el extra "data" del
                 * intent y lo mostramos en el ImageView
                 */
                if (data.hasExtra("data")) {
                    iv_foto.setImageBitmap((Bitmap) data.getParcelableExtra("data"));
                }
            }
        }
    }
}
