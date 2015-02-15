package com.example.administrator.e_pic;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;


public class SneezeListActivity extends ActionBarActivity {

    private ListView mListView;
    private ArrayList<Sneeze> sneezeList;
    TreeMap<Integer, Sneeze> sneezeMapDef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sneeze_list);

        mListView = (ListView) findViewById(R.id.all_sneezes_list);
        ArrayListVuller();
        SneezeMapAdapter adapter= new SneezeMapAdapter(getApplicationContext(), R.layout.sneeze_list_item, sneezeMapDef);

        mListView.setAdapter(adapter);
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
}
