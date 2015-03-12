package com.example.administrator.e_pic;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


public class PictureActivity extends CustomActionBarActivity {
    private String bestand;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);
        ImageView imageView = (ImageView) findViewById(R.id.image);
        Button buttonOpslaan = (Button) findViewById(R.id.buttonOpslaan);
        Button buttonVerwijder = (Button) findViewById(R.id.buttonVerwijder);

        bestand = getIntent().getExtras().getString("naam bestand");
        //File f = new File(s);


            String photoPath = bestand;
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap bitmap = BitmapFactory.decodeFile(photoPath, options);
            imageView.setImageBitmap(bitmap);

        buttonOpslaan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("naam bestand", bestand);
                setResult(RESULT_OK,returnIntent);
                finish();
            }
        });

        buttonVerwijder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("naam bestand", bestand);
                setResult(RESULT_CANCELED,returnIntent);
                finish();
            }
        });


    }



}
