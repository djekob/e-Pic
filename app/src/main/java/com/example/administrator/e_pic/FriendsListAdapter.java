package com.example.administrator.e_pic;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by Administrator on 13/02/2015.
 */
public class FriendsListAdapter extends ArrayAdapter implements Filterable {


    private TreeMap<String, Integer> originalUsers;
    private ArrayList<String> filteredUsers;
    private int resource;
    private Context context;
    private ItemFilter mFilter = new ItemFilter();
    private TextView userTextView;
    private Button addFriendButton;
    private String username;

    public FriendsListAdapter(Context context, int resource, ArrayList<String> arrayList, String username) {
        super(context, resource, arrayList);
        this.context = context;
        this.resource = resource;
        originalUsers = new TreeMap<String, Integer>();
        for(String s : arrayList){
            originalUsers.put(s,0);
        }
        filteredUsers = new ArrayList<>();
        for(String s : originalUsers.keySet()){
            filteredUsers.add(s);
        }
        this.username = username;
    }

    public int getCount() {
        return filteredUsers.size();
    }

    public Object getItem(int position) {
        return filteredUsers.get(position);
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

        userTextView = (TextView) v.findViewById(R.id.search_friend_option_list_item);
        addFriendButton = (Button) v.findViewById(R.id.add_friend_button_list_item);

        String friend = filteredUsers.get(position);
        addFriendButton.setOnClickListener(new OnFriendClickListener(friend, position));
        if(originalUsers.get(friend)>0) addFriendButton.setBackgroundColor(Color.RED);
        else addFriendButton.setBackgroundColor(Color.TRANSPARENT);
        userTextView.setText(friend);
        return v;
    }

    public Filter getFilter() {
        return mFilter;
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String filterString = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();

            final TreeMap<String, Integer> list = originalUsers;

            final ArrayList<String> nlist = new ArrayList<>();

            String filterableString;
            for(String s : list.keySet()){
                filterableString = s;
                if (filterableString.toLowerCase().contains(filterString)) {
                    nlist.add(filterableString);
                }
            }

            results.values = nlist;
            results.count = nlist.size();

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredUsers = (ArrayList<String> )results.values;
            notifyDataSetChanged();
        }

    }

    public class OnFriendClickListener implements View.OnClickListener {
        /*private final Handler handler = new Handler(Looper.getMainLooper());
        private final Runnable updateResults = new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, "Vriend: " + username + " toegevoegd.", Toast.LENGTH_LONG).show();
            }
        };*/
        private String friendname;
        private int position;

        public OnFriendClickListener(String friendname, int position){
            this.friendname = friendname;
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            new Connections(context, username, friendname, position, originalUsers ,Connections.ADD_FRIEND_CODE);
            //v.setBackgroundColor(Color.RED);

            //handler.post(updateResults);
        }


    }

    public void changeOriginalUser(String friendname){
        originalUsers.remove(friendname);
        originalUsers.put(friendname, 1);
        notifyDataSetChanged();
    }

}




