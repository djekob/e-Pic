package com.example.administrator.e_pic;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Created by Administrator on 25/02/2015.
 */
public class FriendsListProfileAdapter extends ArrayAdapter {
    private String username;
    private Context context;
    private int resource;
    private ArrayList<User> friendsList;

    public FriendsListProfileAdapter(Context context, int resource, ArrayList<User> arrayList, String username) {
        super(context, resource, arrayList);
        this.context = context;
        this.resource = resource;
        this.friendsList = arrayList;
        this.username = username;
    }

    public int getCount() {
        return friendsList.size();
    }

    public User getItem(int position) {
        return friendsList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView friendsNameTextView, friendsSneezesTextView;


        View v = convertView;

        if (v != null) {


        } else {
            v = LayoutInflater.from(context).inflate(resource, null);

        }

        friendsNameTextView = (TextView) v.findViewById(R.id.friend_name_text_view_friends_list);
        friendsSneezesTextView = (TextView) v.findViewById(R.id.number_of_sneezes_list_item);

        User friend = friendsList.get(position);

        friendsNameTextView.setText(friend.getUsername());
        friendsSneezesTextView.setText("#" +friend.getNumberOfSneezes());
        return v;
    }
}
