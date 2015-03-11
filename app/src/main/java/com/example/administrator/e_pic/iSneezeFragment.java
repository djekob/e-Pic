package com.example.administrator.e_pic;

import android.app.Dialog;
import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
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
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;



public class iSneezeFragment extends android.support.v4.app.Fragment implements Runnable {
    public static final String ADD_FRIEND_CODE = "add_friend";

    private TextView myNameTextView;
    private Connections c;
    private Button isneeze_image_button;
    private String username;
    private int postcode;
    private Context context;
    private SwipeRefreshLayout swipeRefreshLayout;
    private MapView mapView;
    private GoogleMap map;
    private Location usersLocation;
    protected GoogleApiClient mGoogleApiClient;
    private CameraUpdate cameraUpdate;
    private LocationManager service;
    private MyLocationListener locationListener;
    private Marker marker;
    private ArrayList<LatLng> sneezeLocationsInNeighbourhood;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sneezeLocationsInNeighbourhood = new ArrayList<>();

    }

    private ArrayList<LatLng> getSneezesLocationsInNeighbourhood() {
        return null;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_i_sneeze, container, false);

        username = ((CustomActionBarActivity)getActivity()).user.getUsername();
        locationListener = new MyLocationListener();

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
                    Toast.makeText(getActivity(), "No internet available", Toast.LENGTH_LONG).show();
                    Log.i("Internet", "Not available");
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });


        myNameTextView.setText(username);

        isneeze_image_button.setOnClickListener(new SneezeClickListener());
        service = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        service.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 10, locationListener);
        service.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 10, locationListener);
        usersLocation = getMyLocation();
        System.out.println(usersLocation);
        if(usersLocation!=null) {
            System.out.println("locatie word hier in savehs gezet");
            SaveSharedPreference.setLatitude(getActivity(), usersLocation.getLatitude());
            SaveSharedPreference.setLongitude(getActivity(), usersLocation.getLongitude());
            System.out.println(usersLocation.getLatitude() + "///////////" +usersLocation.getLongitude());
            updateCameraToNewLocation(usersLocation);
        }
        if(usersLocation==null) {
            System.out.println(SaveSharedPreference.getLatitude(getActivity()) +" ++++" + SaveSharedPreference.getLongitude(getActivity()));
            if(SaveSharedPreference.getLatitude(getActivity())!=0 && SaveSharedPreference.getLongitude(getActivity())!=0) {
                System.out.println("WE ZITTEN ER IN");
                LatLng latLng = new LatLng(SaveSharedPreference.getLatitude(getActivity()), SaveSharedPreference.getLongitude(getActivity()));
                System.out.println(latLng);
                updateCameraToNewLocation(latLng);
            } else {

                updateCameraToNewLocation(new LatLng(51.053912, 3.721863));
            }
        }

        return rootView;
    }

    private void  moveCameraToNewLocation(Location location) {
        cameraUpdate = CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude()));
        map.moveCamera(cameraUpdate);
    }
    private void updateCameraToNewLocation(Location location) {
        cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 17);
        map.animateCamera(cameraUpdate);
    }

    private void updateCameraToNewLocation(LatLng location) {
        cameraUpdate = CameraUpdateFactory.newLatLngZoom(location, 17);
        map.animateCamera(cameraUpdate);
    }

    private Location getMyLocation() {
        Criteria criteria = new Criteria();
        String provider = service.getBestProvider(criteria, false);

        return service.getLastKnownLocation(provider);

    }


    private void updateMarkersToMap() {
        map.clear();
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE);
        marker = map.addMarker(new MarkerOptions().position(new LatLng(usersLocation.getLatitude(), usersLocation.getLongitude())).icon(bitmapDescriptor));
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
                postcode = getPostalCode(usersLocation);
                System.out.println("dit zou de postcode moeten zijn" + postcode);
                new Connections(getActivity(), username, postcode, Connections.CREATE_SNEEZE_CODE);
            } else {
                Toast.makeText(getActivity(), "No internet available", Toast.LENGTH_LONG).show();
            }

        }
    }

    private int getPostalCode(Location location) {
        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
        } catch(IOException e) {
            e.printStackTrace();
        }
        Address address=null;
        int postcode=0;

        if (addresses != null && addresses.size() > 0) {

            for (int i = 0; i < addresses.size(); i++) {
                address = addresses.get(i);
                if (address.getPostalCode() != null) {
                    postcode = Integer.parseInt(address.getPostalCode());
                    //stad = address.getLocality();
                    //land = address.getAdminArea(); --> werkt niet
                    break;
                }

            }
        }
        return postcode;
    }

    private class MyLocationListener implements LocationListener {
        @Override
        public void onLocationChanged(Location location) {
            usersLocation = location;
            updateCameraToNewLocation(usersLocation);
            updateMarkersToMap();
        }
        @Override
        public void onProviderDisabled(String provider) {
            // TODO Auto-generated method stub
        }
        @Override
        public void onProviderEnabled(String provider) {
            // TODO Auto-generated method stub
        }
        @Override
        public void onStatusChanged(String provider, int status,
                                    Bundle extras) {
            // TODO Auto-generated method stub
        }
}

    @Override
    public void onPause() {
        super.onPause();
        service.removeUpdates(locationListener);
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
        service.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 10, locationListener);
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

    /*Handler handler = new Handler(Looper.getMainLooper());
    Runnable r = new Runnable() {
        @Override
        public void run() {

        }
    }*/




}
