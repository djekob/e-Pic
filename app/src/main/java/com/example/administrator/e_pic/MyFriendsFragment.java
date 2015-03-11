package com.example.administrator.e_pic;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class MyFriendsFragment extends android.support.v4.app.Fragment implements Runnable{

    private String username;
    public ArrayList<User> friends;
    private ListView listView;
    private MyFriendsListAdapter myFriendsListAdapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        System.out.println("Myfriendsfragment werkt");
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_my_friends_list, container, false);
        username = ((CustomActionBarActivity)getActivity()).user.getUsername();
        friends = new ArrayList<>();
        BigClass bigClass = BigClass.ReadData(getActivity());
        if(bigClass!=null) friends.addAll(bigClass.getFriendsArrayList());

        //friends = (ArrayList) getIntent().getSerializableExtra(Connections.TAG_FRIENDS);
        listView = (ListView) rootView.findViewById(R.id.my_friends_list_view_my_friends_activity);
        myFriendsListAdapter = new MyFriendsListAdapter(getActivity(), R.layout.friend_list_item, friends);
        listView.setAdapter(myFriendsListAdapter);

        return rootView;
    }


    @Override
    public void run() {
        //listView.setAdapter(new MyFriendsListAdapter(getActivity(), R.layout.friend_list_item, friends));
        BigClass bigClass = BigClass.ReadData(getActivity());
        if(bigClass!=null) {
            friends.clear();
            friends.addAll(bigClass.getFriendsArrayList());
        }
        ((MyFriendsListAdapter)listView.getAdapter()).notifyDataSetChanged();
        Log.i("myfriendsfragment", friends.toString());
    }
}
