package com.example.administrator.e_pic;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.security.Timestamp;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

/**
 * Created by Administrator on 12/02/2015.
 */
public class SneezeMapAdapter extends BaseAdapter {


    private Context context;
    private int resource;
    private TreeMap<Integer, Sneeze> sneezeMap;
    private ArrayList<Sneeze> allSneezes;

    private String[] mKeys;

    public SneezeMapAdapter(Context context, int resource, TreeMap<Integer, Sneeze> sneezeMap){
            this.context = context;
            this.resource = resource;
            this.sneezeMap  = sneezeMap;

            allSneezes = new ArrayList<>();
            for (Integer integer : sneezeMap.keySet()) {
                allSneezes.add(sneezeMap.get(integer));
            }
        }

        @Override
        public int getCount() {
            return allSneezes.size();
        }

        @Override
        public Object getItem(int position) {
            return allSneezes.get(position);
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

            TextView dateTextView = (TextView) v.findViewById(R.id.sneeze_date_list_item);
            TextView userTextView = (TextView) v.findViewById(R.id.sneeze_user_list_item);


            dateTextView.setText(allSneezes.get(pos).getTime());
            //java.sql.Timestamp timestamp = java.sql.Timestamp.valueOf(allSneezes.get(pos).getTime());

            Sneeze thisSneeze = allSneezes.get(pos);
            String name = pos + ". " + thisSneeze.getUser().getUsername();
            userTextView.setText(name);


            return v;
        }


    @Override
    public void notifyDataSetChanged() {
        allSneezes.clear();
        for (Integer integer : sneezeMap.keySet()) {
            allSneezes.add(sneezeMap.get(integer));
        }
        super.notifyDataSetChanged();
    }
}

