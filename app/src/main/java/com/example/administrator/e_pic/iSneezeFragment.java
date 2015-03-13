package com.example.administrator.e_pic;


import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;



public class iSneezeFragment extends android.support.v4.app.Fragment implements Runnable {
    public static final String ADD_FRIEND_CODE = "add_friend";

    private Connections c;

    public Button isneeze_image_button;
    private String username;
    private int postcode;
    private Context context;
    private SwipeRefreshLayout swipeRefreshLayout;
    private MapView mapView;
    private GoogleMap map;
    private Location usersLocation;
    private CameraUpdate cameraUpdate;
    private LocationManager service;
    private MyLocationListener locationListener;
    private ArrayList<Sneeze> sneezeLocationsInBuurt;
    private float hoogte;
    private Marker marker;
    private HashMap<String, Float> usernameKleur;

    //private DataReceiver dataReceiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sneezeLocationsInBuurt = new ArrayList<>();
        usernameKleur = new HashMap<>();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_i_sneeze, container, false);

        username = ((CustomActionBarActivity)getActivity()).user.getUsername();
        locationListener = new MyLocationListener();

        isneeze_image_button = (Button) rootView.findViewById(R.id.i_sneeze_button_i_sneeze_fragment);

        hoogte =isneeze_image_button.getTranslationY();
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
                if (RandomShit.haveNetworkConnection(getActivity())) {
                    new Connections(getActivity(), Connections.GET_ALL_SNEEZES_GRAPH_CODE_AND_FRIENDS);
                } else {
                    Toast.makeText(getActivity(), "No internet available", Toast.LENGTH_LONG).show();
                    Log.i("Internet", "Not available");
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });



        isneeze_image_button.setOnClickListener(new SneezeClickListener());
        service = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        service.requestLocationUpdates(LocationManager.GPS_PROVIDER, 15*1000, 10, locationListener);
        service.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 15*1000, 10, locationListener);
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
            if((SaveSharedPreference.getSharedPreferences(getActivity()).getString(SaveSharedPreference.TAG_LONGITUDE, "-1")!="")
                    || (SaveSharedPreference.getSharedPreferences(getActivity()).getString(SaveSharedPreference.TAG_LATITUDE, "-1")!="")){
                if(SaveSharedPreference.getLatitude(getActivity())!=0 && SaveSharedPreference.getLongitude(getActivity())!=0) {
                    System.out.println("WE ZITTEN ER IN");
                    LatLng latLng = new LatLng(SaveSharedPreference.getLatitude(getActivity()), SaveSharedPreference.getLongitude(getActivity()));
                    System.out.println(latLng);
                    updateCameraToNewLocation(latLng);
                } else {

                    updateCameraToNewLocation(new LatLng(51.053912, 3.721863));
                }
            }
            else {

                updateCameraToNewLocation(new LatLng(51.053912, 3.721863));
            }
        }

        return rootView;
    }

    public void resetButtonLocation()  {
        isneeze_image_button.setTranslationY(hoogte);
    }

    public void setButtonLocation(float x) {
        System.out.println(hoogte);
        isneeze_image_button.setTranslationY(x + hoogte);
    }
    public Button getButton() {
        return isneeze_image_button;
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


    private void updateMarkersToMap(ArrayList<Sneeze> sneezes) {
        map.clear();
        ArrayList<Float> colors = RandomShit.generateShadeColors(usernameKleur.size(),0,359);
        int inti=0;
        for(String s : usernameKleur.keySet()) {
            usernameKleur.put(s,colors.get(inti));
            inti++;
        }
        for(Sneeze s : sneezeLocationsInBuurt) {

            BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.defaultMarker(usernameKleur.get(s.getUser().getUsername()));
            OurOnMarkerClickListener ourOnMarkerClickListener = new OurOnMarkerClickListener();
            marker = map.addMarker(new MarkerOptions()
                    .title(s.getUser().getUsername())
                    .position(new LatLng(s.getLatitude(), s.getLongitude()))
                    .icon(bitmapDescriptor)
                    .alpha(0.8f)
                    .snippet(s.getUser().getFirstname() + " " + s.getUser().getName()));
            map.setOnMarkerClickListener(ourOnMarkerClickListener);
        }
    }

    private class OurOnMarkerClickListener implements GoogleMap.OnMarkerClickListener{

        @Override
        public boolean onMarkerClick(Marker marker) {
            marker.showInfoWindow();
            return false;
        }
    }

    private void initializeMap() {
        if(map == null) {
            map = mapView.getMap();

            if(map == null) {
                Toast.makeText(getActivity(), "Unable to create maps", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void setSneezeLocationsInBuurt(ArrayList<Sneeze> list){
        sneezeLocationsInBuurt.clear();
        sneezeLocationsInBuurt.addAll(list);
        System.out.println(sneezeLocationsInBuurt);
    }

    @Override
    public void run() {
        if(swipeRefreshLayout.isRefreshing()) swipeRefreshLayout.setRefreshing(false);
        if(sneezeLocationsInBuurt.size()!=0) {
            for (Sneeze s: sneezeLocationsInBuurt) {
                usernameKleur.put(s.getUser().getUsername(), null);
            }
            updateMarkersToMap(sneezeLocationsInBuurt);

        }
    }

    public class SneezeClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            String time1 = RandomShit.getTimestamp();
            User user = new User(SaveSharedPreference.getUserName(getActivity()));
            user.setFirstname(SaveSharedPreference.getFirstname(getActivity()));
            user.setName(SaveSharedPreference.getName(getActivity()));
            Sneeze s1 = new Sneeze(time1, usersLocation.getLongitude(), usersLocation.getLatitude(), getPostalCode(usersLocation));
            s1.setUser(user);
            usernameKleur.put(SaveSharedPreference.getUserName(getActivity()), null);
            sneezeLocationsInBuurt.add(s1);
            if(RandomShit.haveNetworkConnection(getActivity())) {
                postcode = getPostalCode(usersLocation);
                String time = RandomShit.getTimestamp();
                System.out.println("dit zou de postcode moeten zijn" + postcode);
                Sneeze s = new Sneeze(time, usersLocation.getLongitude(), usersLocation.getLatitude(), postcode);

                new Connections(getActivity(), s, Connections.CREATE_SNEEZE_CODE);
                updateMarkersToMap(sneezeLocationsInBuurt);
            } else {
                Toast.makeText(getActivity(), "No internet available, Sneeze zal verstuurd worden wanneer internet available is", Toast.LENGTH_LONG).show();
                BigClass bigClass = BigClass.ReadData(getActivity());
                updateMarkersToMap(sneezeLocationsInBuurt);
                //postcode = getPostalCode(usersLocation);
                bigClass.addNotSendSneezes(new Sneeze(RandomShit.getTimestamp(), usersLocation.getLongitude(), usersLocation.getLatitude()));
                bigClass.writeData(getActivity());
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post((iSneezeActivity)getActivity());
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
            System.out.println("UMOEDERE");
            SaveSharedPreference.setPostcode(getActivity(), getPostalCode(usersLocation));
            updateCameraToNewLocation(usersLocation);
            Intent i = new Intent(getActivity(), SneezesInBuurtIntentService.class);
            i.putExtra(SneezesInBuurtIntentService.TAG_LATITUDE, location.getLatitude());
            i.putExtra(SneezesInBuurtIntentService.TAG_LONGITUDE, location.getLongitude());
            context.startService(i);
            if(sneezeLocationsInBuurt!=null) updateMarkersToMap(sneezeLocationsInBuurt);
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
        service.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 15*1000, 10, locationListener);
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
