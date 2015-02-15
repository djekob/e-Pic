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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 13/02/2015.
 */
public class FriendsListAdapter extends ArrayAdapter implements Filterable {


    private ArrayList<String> originalUsers, filteredUsers;
    private int resource;
    private Context context;
    private ItemFilter mFilter = new ItemFilter();
    private TextView userTextView;
    private Button addFriendButton;
    private int plaats;
    private String username;

    public FriendsListAdapter(Context context, int resource, ArrayList<String> arrayList, String username) {
            super(context, resource, arrayList);
            this.context = context;
            this.resource = resource;
            originalUsers = arrayList;
            filteredUsers = arrayList;
            this.username = username;
    }

    public int getCount() {
        return filteredUsers.size();
    }

    public Object getItem(int position) {
        plaats = position;
        return filteredUsers.get(position);
    }

    public long getItemId(int position) {
        plaats = position;
        return position;
    }

    @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View v = convertView;

            plaats = position;
            if (v != null) {


            } else {
                v = LayoutInflater.from(context).inflate(resource, null);

            }

            userTextView = (TextView) v.findViewById(R.id.search_friend_option_list_item);
            addFriendButton = (Button) v.findViewById(R.id.add_friend_button_list_item);
            addFriendButton.setOnClickListener(new OnFriendClickListener(filteredUsers.get(position)));

            String friend = filteredUsers.get(position);
            userTextView.setText(friend);


            return v;
        }

    private class addFriendButtonOnClickListener implements View.OnClickListener {


        @Override
        public void onClick(View v) {
            String friendname = filteredUsers.get(plaats);
            Connections connections= new Connections(getContext(), username, Connections.ADD_FRIEND_CODE, friendname);
        }
    }

    public Filter getFilter() {
        return mFilter;
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String filterString = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();

            final List<String> list = originalUsers;

            int count = list.size();
            final ArrayList<String> nlist = new ArrayList<>(count);

            String filterableString ;

            for (int i = 0; i < count; i++) {
                filterableString = list.get(i);
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
            filteredUsers = (ArrayList<String>) results.values;
            notifyDataSetChanged();
        }

    }

    private class OnFriendClickListener implements View.OnClickListener {
        private final Handler handler = new Handler(Looper.getMainLooper());
        private final Runnable updateResults = new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, "Vriend: " + username + " toegevoegd.", Toast.LENGTH_LONG).show();
            }
        };
        private String username = "";

        public OnFriendClickListener(String username){
            this.username = username;
        }

        @Override
        public void onClick(View v) {
            v.setBackgroundColor(Color.RED);
            handler.post(updateResults);
        }
    }


}




