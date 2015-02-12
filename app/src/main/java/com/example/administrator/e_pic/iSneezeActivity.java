package com.example.administrator.e_pic;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;


public class iSneezeActivity extends ActionBarActivity {

    private TextView myNameTextView;
    private String myName;
    private Connections c;
    private ImageButton isneeze_image_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_i_sneeze);

        myName = getIntent().getExtras().getString(Connections.NAAM_VAR_USER);

        myNameTextView = (TextView) findViewById(R.id.my_name_textview);
        isneeze_image_button = (ImageButton) findViewById(R.id.isneeze_image_button);

        //myname definieren
        myNameTextView.setText(myName);

        isneeze_image_button.setOnClickListener(new SneezeClickListener());

    }

    public class SneezeClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            //TODO zorgen dat kleur neus verandert als op geduwd wordt isneeze_image_button.setColorFilter(Color.RED);
            new Connections(getApplicationContext(), myName, Connections.CREATE_SNEEZE_CODE);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_i_sneeze, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
