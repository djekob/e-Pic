package com.example.administrator.e_pic;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.TreeMap;

/**
 * Created by Administrator on 15/02/2015.
 */
public class FriendsRequestsAdapter extends ArrayAdapter {

    private Context context;
    private String username;
    private ArrayList<String> friendsList;
    private int resource;

    public FriendsRequestsAdapter(Context context, int resource) {
        super(context, resource);
    }

    public FriendsRequestsAdapter(Context context, int resource, ArrayList<String> friendsRequestList, String username) {
        super(context, resource, friendsRequestList);
        this.context = context;
        this.resource = resource;
        friendsList = friendsRequestList;
        Collections.sort(friendsList);
        this.username = username;
    }

    public int getCount() {
        return friendsList.size();
    }

    public Object getItem(int position) {
        return friendsList.get(position);
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

        TextView friendsNameTextView = (TextView) v.findViewById(R.id.friends_name_text_view_list_item);
        Button acceptFriendButton = (Button) v.findViewById(R.id.accept_friend_button_list_item);

        String friend = friendsList.get(position);
        friendsNameTextView.setText(friend);

        acceptFriendButton.setOnClickListener(new acceptFriendOnClickListener(friend));
        return v;
    }

    public class acceptFriendOnClickListener implements View.OnClickListener{
        String friend;
        public acceptFriendOnClickListener(String friend){
            this.friend = friend;
        }

        @Override
        public void onClick(View v) {
            new Connections(context, username, friend , Connections.ACCEPT_FRIEND_CODE);
        }
    }

    public void changePendingFriends(String friendname){
        friendsList.remove(friendname);
        notifyDataSetChanged();
    }
}
