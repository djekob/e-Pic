package com.example.administrator.e_pic;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.transition.Transition;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
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

import java.security.Provider;
import java.util.ArrayList;
import java.util.Calendar;


public class iSneezeActivity extends CustomActionBarActivity implements Runnable {
    public ArrayList<Sneeze> sneezeList = new ArrayList<>();
    public ArrayList<User> friendsList = new ArrayList<>();


    //private TextView myNameTextViewNavigationDrawer, nrOfSneezesNavigationDrawer, myFullNameTextViewNavigationDrawer;

    private static final int NUM_PAGES = 3;
    private ViewPager mPager;
    public PagerAdapter mPagerAdapter;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private ListView mDrawerList;
    private ArrayList<String> drawerList;
    private FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slide_screens);

        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        frameLayout = (FrameLayout) findViewById(R.id.content_frame);
        /*myNameTextViewNavigationDrawer  = (TextView) findViewById(R.id.my_name_text_view_navigation_drawer);
        myFullNameTextViewNavigationDrawer = (TextView) findViewById(R.id.my_full_name_navigation_drawer);
        nrOfSneezesNavigationDrawer = (TextView) findViewById(R.id.nr_of_sneezes_navigation_drawer);*/
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        drawerList = new ArrayList<>();
        vulActivityItems();

        /*myNameTextViewNavigationDrawer.setText(SaveSharedPreference.getUserName(getContext()));
        nrOfSneezesNavigationDrawer.setText(SaveSharedPreference.getNrOfSneezes(getContext()) + "");
        myFullNameTextViewNavigationDrawer.setText(SaveSharedPreference.getFirstname(getContext()) + " " +SaveSharedPreference.getName(getContext())) ;
        */
        mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, drawerList));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        mDrawerList.bringToFront();
        mDrawerLayout.requestLayout();
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
        actionBarDrawerToggle = new ActionBarDrawerToggle (
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                null,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getActionBar().setTitle("iSneeze");
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActionBar().setTitle("iSneeze");
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(actionBarDrawerToggle);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);


        new Connections(this, Connections.GET_ALL_SNEEZES_GRAPH_CODE_AND_FRIENDS);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent e) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            // your action...

            if (!mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
                mDrawerLayout.openDrawer(Gravity.LEFT);
            } else {
                mDrawerLayout.closeDrawer(Gravity.LEFT);
            }
            return true;
        }
        return super.onKeyDown(keyCode, e);
    }

    private void vulActivityItems(){
        drawerList.add(getResources().getString(R.string.all_sneezes));
        drawerList.add(getResources().getString(R.string.my_friends));
        drawerList.add(getResources().getString(R.string.add_friend));
        drawerList.add(getResources().getString(R.string.pending_friends));
        drawerList.add(getResources().getString(R.string.all_sneezes_graph));
        drawerList.add(getResources().getString(R.string.edit_profile_data));
        drawerList.add(getResources().getString(R.string.logout));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*int id = item.getItemId();

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
        }*/
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

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
        iSneezeFragment i = ((ScreenSlidePagerAdapter) mPagerAdapter).frag1;
        handler.post(i);
    }


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

    public class DrawerItemClickListener implements AdapterView.OnItemClickListener{


        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            System.out.println(position);
            switch (position){
                case 0: new Connections(getContext(), Connections.GET_ALL_FRIEND_SNEEZES_CODE);
                    break;
                case 1: Intent i = new Intent(getContext(), MyFriendsActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    getContext().startActivity(i);
                    break;
                case 2: new Connections(getContext(), Connections.GET_ALL_USERS_NO_FRIENDS);
                    break;
                case 3: Intent j = new Intent(getContext(), FriendRequestsActivity.class);
                    j.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    j.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    getContext().startActivity(j);
                    break;
                case 4: new Connections(getContext(), Connections.GET_ALL_SNEEZES_GRAPH_CODE);
                    break;
                case 5: new Connections(getContext(), Connections.GET_PROFILE_DATA_CODE);
                    break;
                case 6: new Connections(getContext(), Connections.DELETE_REGID_CODE);
                    break;
                default: break;
            }
        }
    }
    public Context getContext(){
        return this;
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
