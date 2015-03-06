package com.example.administrator.e_pic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;


public class iSneezeFragment extends android.support.v4.app.Fragment {

    public static final String ADD_FRIEND_CODE = "add_friend";

    private TextView myNameTextView;
    private Connections c;
    private ImageButton isneeze_image_button;
    private String username;
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.activity_i_sneeze, container, false);
        context = getActivity();

        username = ((CustomActionBarActivity)getActivity()).user.getUsername();
        isneeze_image_button = (ImageButton) rootView.findViewById(R.id.isneeze_image_button);
        myNameTextView = (TextView) rootView.findViewById(R.id.my_name_textview);


        myNameTextView.setText(username);

        isneeze_image_button.setOnClickListener(new SneezeClickListener());


        return rootView;
        // Inflate the layout for this fragment
    }

    public class SneezeClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            new Connections(getActivity(), Connections.CREATE_SNEEZE_CODE);

        }
    }

}
