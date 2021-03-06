package com.example.administrator.e_pic;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;


public class SneezeListActivity extends CustomActionBarActivity implements Runnable{

    private ListView mListView;
    private ArrayList<Sneeze> sneezeList;
    private TreeMap<Integer, Sneeze> sneezeMapDef;
    private SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sneeze_list);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);

        swipeRefreshLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mListView = (ListView) findViewById(R.id.all_sneezes_list);
        ArrayListVuller();
        SneezeMapAdapter adapter= new SneezeMapAdapter(getContext(), R.layout.sneeze_list_item, sneezeMapDef);


        mListView.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                if(RandomShit.haveNetworkConnection(getContext())) {

                    new Connections(getContext(), SaveSharedPreference.getUserName(getContext()), Connections.RELOAD_ALL_SNEEZES_CODE);
                } else {
                    Toast.makeText(getContext(), "No internet available", Toast.LENGTH_LONG);
                    swipeRefreshLayout.setRefreshing(false);
                }


            }
        });
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(firstVisibleItem==0) {
                    swipeRefreshLayout.setEnabled(true);
                } else {
                    swipeRefreshLayout.setEnabled(false);
                }
            }
        });
    }


    private void ArrayListVuller() {

        sneezeList = new ArrayList<>();

        sneezeMapDef= new TreeMap<>((Map<Integer, Sneeze>)  getIntent().getSerializableExtra(Connections.TAG_SNEEZES));

        for(Integer i : sneezeMapDef.keySet()) {
                sneezeList.add(sneezeMapDef.get(i));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_sneeze_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private Context getContext(){
        return this;
    }

    @Override
    public void run() {
        ((SneezeMapAdapter) mListView.getAdapter()).notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);

    }

    public void setSneezeMap(TreeMap<Integer, Sneeze> sneezeMap) {
        this.sneezeMapDef.clear();
        this.sneezeMapDef.putAll(sneezeMap);
    }
}
