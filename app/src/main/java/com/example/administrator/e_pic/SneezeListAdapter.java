package com.example.administrator.e_pic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Administrator on 9/02/2015.
 */
public class SneezeListAdapter extends ArrayAdapter<Sneeze> {

    private ArrayList<Sneeze> sneezeStrings;
    private int resource;
    private Context context;
    private String time;
    private String name;

    public SneezeListAdapter(Context context, int resource, ArrayList<Sneeze> arrayList) {
        super(context, resource, arrayList);
        this.context = context;
        this.resource = resource;
        sneezeStrings = arrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v != null) {


        } else {
            v = LayoutInflater.from(context).inflate(resource, null);

        }

        TextView textView = (TextView) v.findViewById(R.id.sneeze_date_list_item);
        TextView dateView = (TextView) v.findViewById(R.id.sneeze_user_list_item);
        Sneeze s = sneezeStrings.get(position);
        name = s.getUser().getUsername();
        time = s.getTime().substring(11);

        textView.setText(name);
        dateView.setText(time);


        return v;
    }
}
