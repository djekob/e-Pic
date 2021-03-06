package com.example.administrator.e_pic;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;


public class MyFriendsActivity extends CustomActionBarActivity implements Runnable{

    private String username;
    private ArrayList<User> friends;
    private ListView listView;
    private MyFriendsListAdapter myFriendsListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_friends_list);

        username = user.getUsername();
        friends = new ArrayList<>();
        BigClass bigClass = BigClass.ReadData(this);
        if(bigClass!=null) friends.addAll(bigClass.getFriendsArrayList());
        Log.i("friends oncreate", friends.toString());
        listView = (ListView) findViewById(R.id.my_friends_list_view_my_friends_activity);
        myFriendsListAdapter = new MyFriendsListAdapter(this, R.layout.friend_list_item, friends);
        listView.setAdapter(myFriendsListAdapter);
        new Connections(getContext(), Connections.GET_ALL_FRIENDS_CODE);
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
    private Context getContext() {
        return this;
    }

    @Override
    public void run() {
        BigClass bigClass = BigClass.ReadData(this);
        friends.clear();
        friends.addAll(bigClass.getFriendsArrayList());
        System.out.println(friends);
        ((MyFriendsListAdapter)listView.getAdapter()).notifyDataSetChanged();
        System.out.println("run");
    }
}
