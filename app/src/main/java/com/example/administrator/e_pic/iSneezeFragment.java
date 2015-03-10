package com.example.administrator.e_pic;

import android.app.Dialog;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;


public class iSneezeFragment extends android.support.v4.app.Fragment implements Runnable {
    public static final String ADD_FRIEND_CODE = "add_friend";

    private TextView myNameTextView;
    private Connections c;
    private Button isneeze_image_button;
    private String username;
    private Context context;
    private SwipeRefreshLayout swipeRefreshLayout;
    private MapView mapView;
    private GoogleMap map;
    private Location location;
    protected GoogleApiClient mGoogleApiClient;
    private CameraUpdate cameraUpdate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //buildGoogleApiClient();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_i_sneeze, container, false);

        username = ((CustomActionBarActivity)getActivity()).user.getUsername();


        isneeze_image_button = (Button) rootView.findViewById(R.id.i_sneeze_button_i_sneeze_fragment);
        myNameTextView = (TextView) rootView.findViewById(R.id.my_name_textview);
        mapView = (MapView) rootView.findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);

        try {
            initializeMap();
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.getUiSettings().setMyLocationButtonEnabled(false);
        map.setMyLocationEnabled(true);
        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);


        MapsInitializer.initialize(getActivity());
        map.getUiSettings().setAllGesturesEnabled(false);
        probeerselVoorLocation();



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

    private void probeerselVoorLocation() {
        LocationManager service = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = service.getBestProvider(criteria, false);
        Location location = service.getLastKnownLocation(provider);
        System.out.println(location);
        LatLng userLocation = new LatLng(location.getLatitude(),location.getLongitude());
        System.out.println(userLocation);
        cameraUpdate = CameraUpdateFactory.newLatLngZoom(userLocation, 17);//location.getLatitude(), location.getLongitude()), 18);
        map.animateCamera(cameraUpdate);
    }


    private void initializeMap() {
        if(map == null) {
            map = mapView.getMap();

            if(map == null) {
                Toast.makeText(getActivity(), "Unable to create maps", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void run() {
        if(swipeRefreshLayout.isRefreshing()) swipeRefreshLayout.setRefreshing(false);
    }


    public class SneezeClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if(RandomShit.haveNetworkConnection(getActivity())) {
                new Connections(getActivity(), Connections.CREATE_SNEEZE_CODE);
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
