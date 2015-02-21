package com.example.administrator.e_pic;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class MyFriendsActivity extends ActionBarActivity {

    private String username;
    private ArrayList<User> friends;
    private ListView listView;
    private MyFriendsListAdapter myFriendsListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_friends_list);

        username = getIntent().getStringExtra(Connections.NAAM_VAR_USER);
        friends = new ArrayList<>();
        friends = (ArrayList) getIntent().getSerializableExtra(Connections.TAG_FRIENDS);
        listView = (ListView) findViewById(R.id.my_friends_list_view_my_friends_activity);
        myFriendsListAdapter = new MyFriendsListAdapter(this, R.layout.friend_list_item, friends, username);
        listView.setAdapter(myFriendsListAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_get_friends_list, menu);
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
