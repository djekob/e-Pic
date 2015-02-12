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

/**
 * Created by Administrator on 12/02/2015.
 */
public class SneezeMapAdapter extends BaseAdapter {


    private Context context;
    private int resource;
    private HashMap<Integer, ArrayList<Sneeze>> sneezeMap = new HashMap<>();
    private ArrayList<Sneeze> allSneezes;

    private String[] mKeys;


        public SneezeMapAdapter(Context context, int resource, HashMap<Integer, ArrayList<Sneeze>> sneezeMap){
            this.context = context;
            this.resource = resource;
            this.sneezeMap  = sneezeMap;

            allSneezes = new ArrayList<>();
            for (ArrayList<Sneeze> sneezeArrayList : sneezeMap.values()) {


                for(Sneeze s : sneezeArrayList) {
                    allSneezes.add(s);


                }
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

            TextView dateView = (TextView) v.findViewById(R.id.sneeze_date_list_item);
            TextView userView = (TextView) v.findViewById(R.id.sneeze_user_list_item);


            dateView.setText(allSneezes.get(pos).getTime());
            java.sql.Timestamp timestamp = java.sql.Timestamp.valueOf(allSneezes.get(pos).getTime());

            Sneeze thisSneeze = allSneezes.get(pos);
            String name = thisSneeze.getUser().getUsername() + " (" + thisSneeze.getUser().getFirstname()+ " " + thisSneeze.getUser().getName() + ")";
            userView.setText(name);


            return v;
        }

}

