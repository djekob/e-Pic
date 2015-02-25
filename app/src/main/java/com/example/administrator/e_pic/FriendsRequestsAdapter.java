package com.example.administrator.e_pic;

import android.content.Context;
import android.graphics.Color;
import android.os.Looper;
import android.os.SystemClock;
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
import java.util.logging.Handler;

/**
 * Created by Administrator on 15/02/2015.
 */
public class FriendsRequestsAdapter extends ArrayAdapter {

    private Context context;
    private TreeMap<String, Integer> originalPendingFriends;
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
        originalPendingFriends = new TreeMap<String, Integer>();
        for(String s : friendsList){
            originalPendingFriends.put(s,0);
        }
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

        if(originalPendingFriends.get(friend)>0)  {
            acceptFriendButton.setBackgroundResource(R.drawable.my_button_pressed);
        }
        else {
            acceptFriendButton.setBackgroundResource(R.drawable.my_button);
        }
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
        originalPendingFriends.remove(friendname);
        originalPendingFriends.put(friendname, 1);
        notifyDataSetChanged();
        handler.postAtTime(new deletePendingFriend(friendname), SystemClock.uptimeMillis() + 2000);
    }
    public class deletePendingFriend implements Runnable{
        String friend;
        public deletePendingFriend(String friend){
            this.friend = friend;
        }
        @Override
        public void run() {
            originalPendingFriends.remove(friend);
            friendsList.remove(friend);
            notifyDataSetChanged();
            System.out.println("deleted");
        }
    }
    private final android.os.Handler handler = new android.os.Handler(Looper.getMainLooper());
}
