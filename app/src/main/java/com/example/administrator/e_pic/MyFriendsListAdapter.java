package com.example.administrator.e_pic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MyFriendsListAdapter extends ArrayAdapter {


    private ArrayList<User> myFriends;
    private int resource;

    @Override
    public void notifyDataSetChanged() {
        //myFriends.clear();
        //myFriends.addAll(myFriends);
        super.notifyDataSetChanged();
        System.out.println(myFriends);
    }

    private Context context;
    private String username;

    public MyFriendsListAdapter(Context context, int resource, ArrayList<User> myFriends) {
        super(context, resource, myFriends);
        this.context = context;
        this.resource = resource;
        this.myFriends = myFriends;
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

        TextView friendsNameTextView = (TextView) v.findViewById(R.id.friend_name_text_view_friends_list);
        TextView friendsSneezesTextView = (TextView) v.findViewById(R.id.number_of_sneezes_list_item);


        String friend = myFriends.get(position).getUsername();
        friendsNameTextView.setText(friend);
        int nrsneezes= myFriends.get(position).getNumberOfSneezes();
        friendsSneezesTextView.setText(""+nrsneezes);

        v.setOnClickListener(new OnFriendClickListener(friend, position));

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
            if(RandomShit.haveNetworkConnection(context)){
                new Connections(context, friendname, position, myFriends ,Connections.GO_TO_FRIENDS_PROFILE_CODE, myFriends.get(position).getNumberOfSneezes());
            }
            else{
                Toast.makeText(context, "No internet available", Toast.LENGTH_LONG).show();
            }

        }

    }

}

