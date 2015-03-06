package com.example.administrator.e_pic;



import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;


public class test extends CustomActionBarActivity /*implements Runnable*/ {
/*    public ArrayList<Sneeze> sneezeList = new ArrayList<>();
    public ArrayList<User> friendsList = new ArrayList<>();

    private static final int NUM_PAGES = 3;

    private ViewPager mPager;

    public PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setOffscreenPageLimit(2);
        //mPager.setCurrentItem(0);
        mPager.setCurrentItem(1);
        //mPager.setCurrentItem(2);
        mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // When changing pages, reset the action bar actions since they are dependent
                // on which page is currently active. An alternative approach is to have each
                // fragment expose actions itself (rather than the activity exposing actions),
                // but for simplicity, the activity provides the actions in this sample.
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
        } else if(id == R.id.edit_profile_data_menu_item) {
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
        SneezeOverviewFragment f = ((ScreenSlidePagerAdapter)mPagerAdapter).frag0;
        f.sneezeList.clear();
        f.sneezeList.addAll(sneezeList);
        System.out.println(sneezeList);
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(f);
        MyFriendsFragment m = ((ScreenSlidePagerAdapter)mPagerAdapter).frag2;
        System.out.println(m);
        System.out.println(friendsList);
        m.friends.clear();
        m.friends.addAll(friendsList);
        System.out.println(m.friends);
        handler.post(m);
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
            switch (position){
                case 0: frag0 =  new SneezeOverviewFragment();
                    return frag0;
                case 1: frag1 = new iSneezeFragment();
                    return frag1;
                case 2: frag2 = new MyFriendsFragment();
                    return frag2;
                default: return new iSneezeFragment();
            }
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
*/
}
