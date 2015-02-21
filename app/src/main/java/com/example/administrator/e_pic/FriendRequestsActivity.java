package com.example.administrator.e_pic;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class FriendRequestsActivity extends ActionBarActivity {

    private ListView pendingFriendsListView;
    private ArrayList<String> pendingFriends;
    private String username;
    private TextView noFriendsTextView;
    public FriendsRequestsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_requests);

        pendingFriendsListView = (ListView) findViewById(R.id.pending_friends_friends_request_activity);
        noFriendsTextView = (TextView) findViewById(R.id.zero_friends_text_view);
        pendingFriends = new ArrayList<>();
        pendingFriends = getIntent().getStringArrayListExtra(Connections.NAAM_VAR_PENDING_FRIENDS);
        if(pendingFriends.size()==0) {
            noFriendsTextView.setText(this.getResources().getString(R.string.no_friend_requests));
        }
        username = getIntent().getStringExtra(Connections.TAG_LOGINNAME);

        adapter = new FriendsRequestsAdapter(this, R.layout.friend_request_list_item, pendingFriends, username);

        pendingFriendsListView.setAdapter(adapter);



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_friend_requests, menu);
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
