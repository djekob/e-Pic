package com.example.administrator.e_pic;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;


public class iSneezeActivity extends CustomActionBarActivity implements Runnable {
    public ArrayList<Sneeze> sneezeList = new ArrayList<>();


    //private TextView myNameTextViewNavigationDrawer, nrOfSneezesNavigationDrawer, myFullNameTextViewNavigationDrawer;

    private iSneezeFragment isneezefrag;
    private SneezeOverviewFragment sneezeoverviewfrag;
    private MyFriendsFragment myfriendsoverviewfrag;


    private static final int NUM_PAGES = 3;
    private SwipeViewPager mPager;
    public PagerAdapter mPagerAdapter;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private ListView mDrawerList;
    private ArrayList<String> drawerList;
    private FrameLayout frameLayout;
    public TerugStuurKlasse terugstuurklasse;
    private DataReceiver dataReceiver;
    private ArrayList<Sneeze> sneezeLocationsInBuurt;
    private TextView myNameTextView, myNumberOfSneezesTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slide_screens);

        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        frameLayout = (FrameLayout) findViewById(R.id.content_frame);
        myNameTextView = (TextView) findViewById(R.id.name_text_view_navigation_drawer);
        myNumberOfSneezesTextView = (TextView) findViewById(R.id.number_of_sneezes_navigation_drawer);

        myNumberOfSneezesTextView.setText("(" + SaveSharedPreference.getNrOfSneezes(getContext()) +")");
        myNameTextView.setText(SaveSharedPreference.getUserName(getContext()));

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
        NavigationDrawerListAdapter navigationDrawerListAdapter = new NavigationDrawerListAdapter(getContext(), R.layout.drawer_list_item, drawerList);
        mDrawerList.setAdapter(navigationDrawerListAdapter);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        mDrawerList.bringToFront();
        mDrawerLayout.requestLayout();
        mPager = new SwipeViewPager(this);
        mPager = (SwipeViewPager) findViewById(R.id.pager);
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        mPagerAdapter = new ScreenSlidePagerAdapter(fragmentManager);

        mPager.setPageTransformer(true, new MyPageTransformer());



        mPager.setAdapter(mPagerAdapter);
        mPager.setOffscreenPageLimit(2);
        mPager.setCurrentItem(1);
        mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                invalidateOptionsMenu();
            }
        });

        actionBarDrawerToggle = new ActionBarDrawerToggle(
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
                drawerView.bringToFront();
                getActionBar().setTitle("iSneeze");
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(actionBarDrawerToggle);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);


        if (RandomShit.haveNetworkConnection(this)) {
            new Connections(this, Connections.GET_ALL_SNEEZES_GRAPH_CODE_AND_FRIENDS);
        } else {
            //Toast.makeText(this, "No internet available", Toast.LENGTH_LONG).show();
            Log.i("internet", "not available");
        }

        sneezeLocationsInBuurt = new ArrayList<>();
        dataReceiver = new DataReceiver();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Connections.ACTION_SNEEZE_IN_BUURT);
        registerReceiver(dataReceiver, intentFilter);


    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(dataReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        myNumberOfSneezesTextView.setText(""+SaveSharedPreference.getNrOfSneezes(getContext()));
        NavigationDrawerListAdapter navigationDrawerListAdapter = new NavigationDrawerListAdapter(getContext(), R.layout.drawer_list_item, drawerList);
        mDrawerList.setAdapter(navigationDrawerListAdapter);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Connections.ACTION_SNEEZE_IN_BUURT);
        registerReceiver(dataReceiver, intentFilter);
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

    private void vulActivityItems() {
        //drawerList.add(getResources().getString(R.string.all_sneezes));
        //drawerList.add(getResources().getString(R.string.my_friends));
        drawerList.add(getResources().getString(R.string.add_friend));
        drawerList.add(getResources().getString(R.string.pending_friends));
        //drawerList.add(getResources().getString(R.string.all_sneezes_graph));
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
        //f.sneezeList.clear();
        //f.sneezeList.addAll(sneezeList);
        //System.out.println(sneezeList);
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(f);
        MyFriendsFragment m = ((ScreenSlidePagerAdapter) mPagerAdapter).frag2;
        handler.post(m);
        iSneezeFragment i = ((ScreenSlidePagerAdapter) mPagerAdapter).frag1;
        if(sneezeLocationsInBuurt.size()!=0) {
            i.setSneezeLocationsInBuurt(sneezeLocationsInBuurt);
        }
        handler.post(i);
    }


    public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public iSneezeFragment frag1;
        public SneezeOverviewFragment frag0;
        public MyFriendsFragment frag2;

        public ScreenSlidePagerAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
            frag0 = new SneezeOverviewFragment();
            frag1 = new iSneezeFragment();
            frag2= new MyFriendsFragment();
            sneezeoverviewfrag = frag0;
            isneezefrag = frag1;
            myfriendsoverviewfrag=frag2;

            mPager.setFragment(frag1);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return frag0;
                case 1:
                    return frag1;
                case 2:
                    return frag2;
                default:
                    return frag1;
            }
        }

        public iSneezeFragment getFrag1() {
            return frag1;
        }

        public SneezeOverviewFragment getFrag0() {
            return frag0;
        }

        public MyFriendsFragment getFrag2() {
            return frag2;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

    public class DrawerItemClickListener implements AdapterView.OnItemClickListener {


        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            System.out.println(position);
            switch (position) {
                /*case 0: new Connections(getContext(), Connections.GET_ALL_FRIEND_SNEEZES_CODE);
                    break;*/
                /*case 1: Intent i = new Intent(getContext(), MyFriendsActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    getContext().startActivity(i);
                    break;*/
                case 0:
                    if (RandomShit.haveNetworkConnection(getContext())) {
                        new Connections(getContext(), Connections.GET_ALL_USERS_NO_FRIENDS);
                    } else {
                        Toast.makeText(getContext(), "No internet available", Toast.LENGTH_LONG).show();
                    }
                    break;
                case 1:
                    if (RandomShit.haveNetworkConnection(getContext())) {
                        Intent j = new Intent(getContext(), FriendRequestsActivity.class);
                        j.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        j.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        getContext().startActivity(j);
                    } else {
                        Toast.makeText(getContext(), "No internet available", Toast.LENGTH_LONG).show();
                    }

                    break;
                /*case 4: new Connections(getContext(), Connections.GET_ALL_SNEEZES_GRAPH_CODE);
                    break;*/
                case 2:
                    if (RandomShit.haveNetworkConnection(getContext())) {
                        new Connections(getContext(), Connections.GET_PROFILE_DATA_CODE);
                    } else {
                        Toast.makeText(getContext(), "No internet available", Toast.LENGTH_LONG).show();
                    }
                    break;
                case 3:
                    if (RandomShit.haveNetworkConnection(getContext())) {
                        new Connections(getContext(), Connections.DELETE_REGID_CODE);
                    } else {
                        Toast.makeText(getContext(), "You can't logout when there is no internet connection available.", Toast.LENGTH_LONG).show();
                    }
                    break;
                default:
                    break;
            }
        }
    }

    public Context getContext() {
        return this;
    }

    public TerugStuurKlasse terugstuurklasse(ArrayList<Sneeze> sneezes) {
        terugstuurklasse = new TerugStuurKlasse(sneezes);
        return terugstuurklasse;
    }

    public class TerugStuurKlasse implements Runnable {
        ArrayList<Sneeze> sneezes = null;

        public TerugStuurKlasse(ArrayList<Sneeze> sneezes) {
            if (this.sneezes != null) this.sneezes.clear();
            else {
                this.sneezes = new ArrayList<>();
            }
            this.sneezes.addAll(sneezes);
        }


        @Override
        public void run() {
            System.out.println(sneezes);

        }


    }



    public class DataReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            sneezeLocationsInBuurt = (ArrayList<Sneeze>) intent.getSerializableExtra(Connections.TAG_SNEEZES_IN_BUURT);
            Log.i("datareceiver", "werkt");
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post((iSneezeActivity)getContext());
        }
    }


    private class MyPageTransformer implements ViewPager.PageTransformer {



        public void transformPage(View view, float position) { //position = 1 als rechts, position = -1 als links
            int pageWidth = view.getWidth();
            System.out.println(position);
            position++;
                if (position < -1) { // [-Infinity,-1)
                    // This page is way off-screen to the left.
                    view.setAlpha(1);

                } else if (position<= 1) { // [-1,1]

                    isneezefrag.mapView.setTranslationX(-position * (pageWidth / 2)); //Half the normal speed

                } else { // (1,+Infinity]
                    // This page is way off-screen to the right.
                    view.setAlpha(1);
                }


            }
                /*sneezeoverviewfrag.graph.setTranslationX((float) (-(1 - position) * 0.5 * pageWidth));
                //mBlurLabel.setTranslationX((float) (-(1 - position) * 0.5 * pageWidth));

                //mDim.setTranslationX((float) (-(1 - position) * pageWidth));
                isneezefrag.mapView.setTranslationX((float) (-(1 - position) * pageWidth));

                isneezefrag.isneeze_image_button.setTranslationX((float) (-(1 - position) * 1.5 * pageWidth));
                //mDoneButton.setTranslationX((float) (-(1 - position) * 1.7 * pageWidth));
                // The 0.5, 1.5, 1.7 values you see here are what makes the view move in a different speed.
                // The bigger the number, the faster the view will translate.
                // The result float is preceded by a minus because the views travel in the opposite direction of the movement.

                myfriendsoverviewfrag.myFriendsTextView.setTranslationX((position) * (pageWidth / 4));

                myfriendsoverviewfrag.listView.setTranslationX((position) * (pageWidth));

               // mTint.setTranslationX((position) * (pageWidth / 2));

               // mDesaturate.setTranslationX((position) * (pageWidth / 1));
                // This is another way to do it


            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0);
            }*/
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
