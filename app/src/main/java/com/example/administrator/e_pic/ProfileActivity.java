package com.example.administrator.e_pic;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.LoginFilter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class ProfileActivity extends CustomActionBarActivity {

    private TextView myNameTextView, mySneezesTextView;
    private ListView myFriendsListView;
    private ImageView myProfilePictureImageView;
    private String username;
    private ArrayList<User> friendsList;
    private int nrOfSneezesFriend;
    private int position;
    private Button addFriendButton;
    private ArrayList<String> myFriends;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        initialization();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);


        username = getIntent().getStringExtra(Connections.TAG_FRIENDNAME);
        friendsList = (ArrayList) getIntent().getSerializableExtra(Connections.TAG_FRIENDS);
        nrOfSneezesFriend = getIntent().getIntExtra(Connections.TAG_NR_OF_SNEEZES_FRIEND, -500);
        myNameTextView.setText(username);
        mySneezesTextView.setText(nrOfSneezesFriend+"");
        MyFriendsListAdapter adapter = new MyFriendsListAdapter(this, R.layout.friend_list_item, friendsList);
        myFriendsListView.setAdapter(adapter);

        if(myFriends.contains(username)) {
            addFriendButton.setEnabled(false);
            addFriendButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.my_button_accepted_friend));
            addFriendButton.setText("Friends");
        }


    }

    private void initialization() {
        myNameTextView = (TextView) findViewById(R.id.my_name_text_view_profile_activity);
        myFriendsListView= (ListView) findViewById(R.id.friends_list_profile_activity);
        mySneezesTextView = (TextView) findViewById(R.id.number_of_sneezes_text_view_profile_activity);
        myProfilePictureImageView = (ImageView) findViewById(R.id.my_profile_picture_profile_activity);
        addFriendButton =(Button) findViewById(R.id.add_friend_button_profile_activity);
        addFriendButton.setOnClickListener(new AddFriendClickListener());

        myFriends = new ArrayList<>();
        BigClass bigClass = BigClass.ReadData(getContext());
        if(bigClass!=null) {
            for(User u: bigClass.getFriendsArrayList()) {
                myFriends.add(u.getUsername());
            }
        }


    }
    private class AddFriendClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {


            new Connections(getContext(), SaveSharedPreference.getUserName(getContext()), username, Connections.ADD_FRIEND_CODE2);
            addFriendButton.setEnabled(false);
            addFriendButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.my_button_pending_friends));
            addFriendButton.setText("Added!");
            }
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

    private Context getContext() {
        return this;
    }
}
