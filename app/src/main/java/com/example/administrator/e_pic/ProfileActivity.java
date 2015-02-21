package com.example.administrator.e_pic;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.LoginFilter;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class ProfileActivity extends ActionBarActivity {

    private TextView myNameTextView, mySneezesTextView;
    private ListView myFriendsListView;
    private ImageView myProfilePictureImageView;
    private String username;
    private ArrayList<User> friendsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        initialization();

        username = getIntent().getStringExtra(Connections.TAG_LOGINNAME);
        friendsList = (ArrayList) getIntent().getSerializableExtra(Connections.TAG_FRIENDS);


        myNameTextView.setText(username);
    }

    private void initialization() {
        myNameTextView = (TextView) findViewById(R.id.my_name_text_view_profile_activity);
        myFriendsListView= (ListView) findViewById(R.id.friends_list_profile_activity);
        mySneezesTextView = (TextView) findViewById(R.id.number_of_sneezes_text_view_profile_activity);
        myProfilePictureImageView = (ImageView) findViewById(R.id.my_profile_picture_profile_activity);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
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
