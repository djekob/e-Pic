package com.example.administrator.e_pic;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.TreeMap;

public class MyFriendsListAdapter extends ArrayAdapter implements Filterable {


    private ArrayList<User> myFriends;
    private int resource;
    private Context context;
    private TextView friendsNameTextView, friendsSneezesTextView;
    private Button goToProfile;
    private String username;

    public MyFriendsListAdapter(Context context, int resource, ArrayList<User> myFriends, String username) {
        super(context, resource, myFriends);
        this.context = context;
        this.resource = resource;
        this.myFriends = myFriends;
        this.username = username;
    }

    public int getCount() {
        return myFriends.size();
    }

    public Object getItem(int position) {
        return myFriends.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v != null) {


        } else {
            v = LayoutInflater.from(context).inflate(resource, null);

        }

        friendsNameTextView = (TextView) v.findViewById(R.id.friend_name_text_view_friends_list);
        goToProfile = (Button) v.findViewById(R.id.go_to_profile_button_list_item);

        String friend = myFriends.get(position).getUsername();
        int sneezes = myFriends.get(position).getNumberOfSneezes();
        goToProfile.setOnClickListener(new OnFriendClickListener(friend, position));

        friendsNameTextView.setText(friend);
        friendsSneezesTextView.setText(sneezes);
        return v;
    }
    public class OnFriendClickListener implements View.OnClickListener {


        private String friendname;
        private int position;

        public OnFriendClickListener(String friendname, int position){
            this.friendname = friendname;
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            new Connections(context, username, friendname, position, myFriends ,Connections.GO_TO_FRIENDS_PROFILE_CODE);
            //v.setBackgroundColor(Color.RED);

            //handler.post(updateResults);
        }


    }

}

