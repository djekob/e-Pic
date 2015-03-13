package com.example.administrator.e_pic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

/**
 * Created by Administrator on 13/03/2015.
 */
public class NavigationDrawerListAdapter extends ArrayAdapter {

    private HashMap<String, Integer> navigationDrawerItemsAndNewStuff;
    private ArrayList<String> titles;
    private int resource;
    private Context context;
    private TextView title, newStuff;

    public NavigationDrawerListAdapter(Context context, int resource, ArrayList<String> arrayList) {
        super(context, resource, arrayList);
        this.context = context;
        this.resource = resource;
        titles= new ArrayList<>();
        titles = arrayList;
        navigationDrawerItemsAndNewStuff = new HashMap<>();
        for(String s : arrayList){
            navigationDrawerItemsAndNewStuff.put(s, 0);
        }
    }

    public int getCount() {
        return titles.size();
    }

    public Object getItem(int position) {
        return titles.get(position);
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

        title = (TextView) v.findViewById(R.id.title_text_view_navigation_drawer);
        newStuff = (TextView) v.findViewById(R.id.text_new_stuff_navigation_drawer);
        newStuff.setVisibility(View.INVISIBLE);

        title.setText(titles.get(position));
        if(titles.get(position).equals("Pending friends")) {
            newStuff.setText(SaveSharedPreference.getFriendRequests(context) + "");
            if(SaveSharedPreference.getFriendRequests(context) > 0) {
                newStuff.setVisibility(View.VISIBLE);
            }
        }
        return v;
    }


}
