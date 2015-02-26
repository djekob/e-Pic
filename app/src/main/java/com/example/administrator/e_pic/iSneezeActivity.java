package com.example.administrator.e_pic;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;


public class iSneezeActivity extends CustomActionBarActivity {

    private TextView myNameTextView;
    private Connections c;
    private ImageButton isneeze_image_button;
    private String username;
    private Context context;


    public static final String ADD_FRIEND_CODE = "add_friend";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_i_sneeze);

        context = this;

        username = user.getUsername();
        myNameTextView = (TextView) findViewById(R.id.my_name_textview);
        isneeze_image_button = (ImageButton) findViewById(R.id.isneeze_image_button);

        //myname definieren
        myNameTextView.setText(username);

        isneeze_image_button.setOnClickListener(new SneezeClickListener());


    }

    public class SneezeClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            //TODO zorgen dat kleur neus verandert als op geduwd wordt isneeze_image_button.setColorFilter(Color.RED);
            new Connections(getContext(), Connections.CREATE_SNEEZE_CODE);

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
            new Connections(this, Connections.GET_ALL_FRIEND_SNEEZES_CODE);
        } else if (id == R.id.add_friend_action_bar) {
            new Connections(this, Connections.GET_ALL_USERS_NO_FRIENDS);
        } else if (id == R.id.pending_friends_action_bar) {
            //new Connections(this, username, Connections.GET_PENDING_FRIENDS);

        } else if (id == R.id.go_to_all_sneezes_graph_action_bar) {
            new Connections(this, Connections.GET_ALL_SNEEZES_GRAPH_CODE);
        } else if (id == R.id.logout_user) {
            new Connections(this, Connections.DELETE_REGID_CODE);
        } else if (id == R.id.my_friends_action_bar) {
            new Connections(this, Connections.GET_ALL_FRIENDS_CODE);
        } else if(id == R.id.edit_profile_data_menu_item) {
            new Connections(this, Connections.GET_PROFILE_DATA_CODE);
        }

        return super.onOptionsItemSelected(item);
    }

    private Context getContext(){
        return this;
    }
}
