package com.example.administrator.e_pic;

import android.content.DialogInterface;
import android.content.Intent;
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
    private String username;

    public static final String ADD_FRIEND_CODE = "add_friend";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_i_sneeze);

        myName = getIntent().getExtras().getString(Connections.NAAM_VAR_USER);

        username = myName;
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

        getMenuInflater().inflate(R.menu.menu_i_sneeze, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.go_to_all_sneezes_list_action_bar) {
            new Connections(this, username, Connections.GET_ALL_SNEEZES_CODE);
        } else if (id == R.id.add_friend_action_bar) {
            new Connections(this, username, Connections.GET_ALL_USERS_NO_FRIENDS);

        }

        return super.onOptionsItemSelected(item);
    }
}
