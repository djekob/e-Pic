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
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;


public class iSneezeFragment extends android.support.v4.app.Fragment implements Runnable {

    public static final String ADD_FRIEND_CODE = "add_friend";

    private TextView myNameTextView;
    private Connections c;
    private ImageButton isneeze_image_button;
    private String username;
    private Context context;
    private SwipeRefreshLayout swipeRefreshLayout;
    private MapView mapView;
    private GoogleMap map;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_i_sneeze, container, false);

        username = ((CustomActionBarActivity)getActivity()).user.getUsername();

        isneeze_image_button = (ImageButton) rootView.findViewById(R.id.isneeze_image_button);
        myNameTextView = (TextView) rootView.findViewById(R.id.my_name_textview);
        mapView = (MapView) rootView.findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);
        map = mapView.getMap();
        map.getUiSettings().setMyLocationButtonEnabled(false);
        map.setMyLocationEnabled(true);

        // Needs to call MapsInitializer before doing any CameraUpdateFactory calls
        //try {
        MapsInitializer.initialize(getActivity());
        map.getUiSettings().setAllGesturesEnabled(false);
        /*} catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }*/
        // Updates the location and zoom of the MapView
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(43.1, -87.9), 10);
        map.animateCamera(cameraUpdate);

        context = getActivity();
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.i_sneeze_swiper);
        swipeRefreshLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                if(RandomShit.haveNetworkConnection(getActivity())) {
                    new Connections(getActivity(), Connections.GET_ALL_SNEEZES_GRAPH_CODE_AND_FRIENDS);
                } else {
                    Toast.makeText(getActivity(), "No internet available", Toast.LENGTH_LONG);
                    swipeRefreshLayout.setRefreshing(false);
                }
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
            if(RandomShit.haveNetworkConnection(getActivity())) {
                isneeze_image_button.setColorFilter(R.color.orange);
                new Connections(getActivity(), Connections.CREATE_SNEEZE_CODE);
                //isneeze_image_button.setColorFilter(R.color.orange);
            } else {
                Toast.makeText(getActivity(), "No internet available", Toast.LENGTH_LONG);
            }

        }
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}
