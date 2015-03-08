package com.example.administrator.e_pic;

import android.content.Context;
import android.os.Bundle;
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
                R.layout.fragment_i_sneeze, container, false);

        context = getActivity();


        username = ((CustomActionBarActivity)getActivity()).user.getUsername();
        isneeze_image_button = (ImageButton) rootView.findViewById(R.id.isneeze_image_button);
        myNameTextView = (TextView) rootView.findViewById(R.id.my_name_textview);


        myNameTextView.setText(username);

        isneeze_image_button.setOnClickListener(new SneezeClickListener());


        return rootView;
    }



    public class SneezeClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            new Connections(getActivity(), Connections.CREATE_SNEEZE_CODE);

        }
    }

}
