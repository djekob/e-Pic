package com.example.administrator.e_pic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Created by Administrator on 13/02/2015.
 */
public class FriendsMapAdapter extends BaseAdapter {
    private Context context;
    private int resource;
    private TreeMap<Integer, User> friendsMap = new TreeMap<>();
    private ArrayList<User> allFriends;

    private String[] mKeys;


    public FriendsMapAdapter(Context context, int resource, TreeMap<Integer, User> friendsMap) {
        this.context = context;
        this.resource = resource;
        this.friendsMap = friendsMap;

        allFriends = new ArrayList<>();
        for (Integer integer : friendsMap.keySet()) {
            allFriends.add(friendsMap.get(integer));
        }
    }

    @Override
    public int getCount() {
        return allFriends.size();
    }

    @Override
    public Object getItem(int position) {
        return allFriends.get(position);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {


        View v = convertView;

        if (v != null) {


        } else {
            v = LayoutInflater.from(context).inflate(resource, null);

        }

        TextView userTextView = (TextView) v.findViewById(R.id.search_friend_option_list_item);


        User friend = allFriends.get(pos);
        userTextView.setText(friend.getUsername() + "(" + friend.getFirstname() + " " +friend.getName() + ")");


        return v;

    }
}
