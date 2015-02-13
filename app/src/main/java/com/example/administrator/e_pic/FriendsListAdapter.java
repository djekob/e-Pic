package com.example.administrator.e_pic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Administrator on 13/02/2015.
 */
public class FriendsListAdapter extends ArrayAdapter {


        private ArrayList<String> users;
        private int resource;
        private Context context;

        public FriendsListAdapter(Context context, int resource, ArrayList<String> arrayList) {
            super(context, resource, arrayList);
            this.context = context;
            this.resource = resource;
            users = arrayList;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View v = convertView;

            if (v != null) {


            } else {
                v = LayoutInflater.from(context).inflate(resource, null);

            }

            TextView userTextView = (TextView) v.findViewById(R.id.search_friend_option_list_item);


            String friend = users.get(position);
            userTextView.setText(friend);


            return v;
        }


}
