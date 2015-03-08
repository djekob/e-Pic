package com.example.administrator.e_pic;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;


public class iSneezeFragment extends android.support.v4.app.Fragment implements Runnable {

    public static final String ADD_FRIEND_CODE = "add_friend";

    private TextView myNameTextView;
    private Connections c;
    private ImageButton isneeze_image_button;
    private String username;
    private Context context;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_i_sneeze, container, false);


        username = ((CustomActionBarActivity)getActivity()).user.getUsername();
        isneeze_image_button = (ImageButton) rootView.findViewById(R.id.isneeze_image_button);
        myNameTextView = (TextView) rootView.findViewById(R.id.my_name_textview);
        context = getActivity();
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.i_sneeze_swiper);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                new Connections(getActivity(), Connections.GET_ALL_SNEEZES_GRAPH_CODE_AND_FRIENDS);
            }
        });


        myNameTextView.setText(username);

        isneeze_image_button.setOnClickListener(new SneezeClickListener());



        return rootView;
    }

    @Override
    public void run() {
        if(swipeRefreshLayout.isRefreshing()) swipeRefreshLayout.setRefreshing(false);
    }


    public class SneezeClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            new Connections(getActivity(), Connections.CREATE_SNEEZE_CODE);

        }
    }

}
