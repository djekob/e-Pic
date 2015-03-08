package com.example.administrator.e_pic;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;

import java.security.Provider;
import java.util.ArrayList;
import java.util.Calendar;


public class iSneezeActivity extends CustomActionBarActivity implements Runnable {
    public ArrayList<Sneeze> sneezeList = new ArrayList<>();
    public ArrayList<User> friendsList = new ArrayList<>();

    private TextView myNameTextView, locationTextView;

    private static final int NUM_PAGES = 3;
    private ViewPager mPager;
    public PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slide_screens);

        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setOffscreenPageLimit(2);
        mPager.setCurrentItem(1);
        mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                invalidateOptionsMenu();
            }
        });
        new Connections(this, Connections.GET_ALL_SNEEZES_GRAPH_CODE_AND_FRIENDS);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_i_sneeze, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.go_to_all_sneezes_list_action_bar) {
            new Connections(this, Connections.GET_ALL_FRIEND_SNEEZES_CODE);
        } else if (id == R.id.add_friend_action_bar) {
            new Connections(this, Connections.GET_ALL_USERS_NO_FRIENDS);
        } else if (id == R.id.pending_friends_action_bar) {
            Intent i = new Intent(this, FriendRequestsActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            this.startActivity(i);
        } else if (id == R.id.go_to_all_sneezes_graph_action_bar) {
            new Connections(this, Connections.GET_ALL_SNEEZES_GRAPH_CODE);
        } else if (id == R.id.logout_user) {
            new Connections(this, Connections.DELETE_REGID_CODE);
        } else if (id == R.id.my_friends_action_bar) {
            new Connections(this, Connections.GET_ALL_FRIENDS_CODE);
        } else if (id == R.id.edit_profile_data_menu_item) {
            new Connections(this, Connections.GET_PROFILE_DATA_CODE);
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    @Override
    public void run() {
        SneezeOverviewFragment f = ((ScreenSlidePagerAdapter) mPagerAdapter).frag0;
        f.sneezeList.clear();
        f.sneezeList.addAll(sneezeList);
        System.out.println(sneezeList);
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(f);
        MyFriendsFragment m = ((ScreenSlidePagerAdapter) mPagerAdapter).frag2;
        System.out.println(m);
        System.out.println(friendsList);
        m.friends.clear();
        m.friends.addAll(friendsList);
        System.out.println(m.friends);
        handler.post(m);
    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public iSneezeFragment frag1;
        public SneezeOverviewFragment frag0;
        public MyFriendsFragment frag2;

        public ScreenSlidePagerAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            switch (position) {
                case 0:
                    frag0 = new SneezeOverviewFragment();
                    return frag0;
                case 1:
                    frag1 = new iSneezeFragment();
                    return frag1;
                case 2:
                    frag2 = new MyFriendsFragment();
                    return frag2;
                default:
                    return new iSneezeFragment();
            }
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}


/*
    private TextView myNameTextView;
    private Connections c;
    private ImageButton isneeze_image_button;
    private String username;
    private Context context;
    private LocationManager locationManager;
    private String provider;
    private Location location;


    public static final String ADD_FRIEND_CODE = "add_friend";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_i_sneeze);

        context = this;

        username = user.getUsername();
        isneeze_image_button = (ImageButton) findViewById(R.id.isneeze_image_button);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationTextView = (TextView) findViewById(R.id.location_text_view);

        myNameTextView = (TextView) findViewById(R.id.my_name_textview);


        try {
            MapsInitializer.initialize(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        myNameTextView.setText(username);

        isneeze_image_button.setOnClickListener(new SneezeClickListenertest());


    }

    public class SneezeClickListenertest implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent i = new Intent(context, test.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            context.startActivity(i);

        }
    }

    public class SneezeClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            new Connections(getContext(), Connections.CREATE_SNEEZE_CODE);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_i_sneeze, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.go_to_all_sneezes_list_action_bar) {
            new Connections(this, Connections.GET_ALL_FRIEND_SNEEZES_CODE);
        } else if (id == R.id.add_friend_action_bar) {
            new Connections(this, Connections.GET_ALL_USERS_NO_FRIENDS);
        } else if (id == R.id.pending_friends_action_bar) {
            Intent i = new Intent(context, FriendRequestsActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            context.startActivity(i);
        } else if (id == R.id.go_to_all_sneezes_graph_action_bar) {
            new Connections(this, Connections.GET_ALL_SNEEZES_GRAPH_CODE);
        } else if (id == R.id.logout_user) {
            new Connections(this, Connections.DELETE_REGID_CODE);
        } else if (id == R.id.my_friends_action_bar) {
            new Connections(this, Connections.GET_ALL_FRIENDS_CODE);
        } else if(id == R.id.edit_profile_data_menu_item) {
            new Connections(this, Connections.GET_PROFILE_DATA_CODE);
        }

        return super.onOptionsItemSelected(item);
    }

    private Context getContext(){
        return this;
    }
}*/
