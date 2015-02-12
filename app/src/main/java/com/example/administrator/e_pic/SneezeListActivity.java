package com.example.administrator.e_pic;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;


public class SneezeListActivity extends ActionBarActivity {

    private ListAdapter SneezeListAdapter;
    private ListView mListView;
    private ArrayList<Sneeze> sneezeList;
    HashMap<Integer, ArrayList<Sneeze>> sneezeMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sneeze_list);

        mListView = (ListView) findViewById(R.id.all_sneezes_list);
        ArrayListVuller();
        SneezeListAdapter = new SneezeMapAdapter(getApplicationContext(), R.layout.sneeze_list_item, sneezeMap);

        mListView.setAdapter(SneezeListAdapter);
    }

    private void ArrayListVuller() {

        sneezeList = new ArrayList<>();

        sneezeMap= new HashMap<>();
        sneezeMap = (HashMap<Integer, ArrayList<Sneeze>>) getIntent().getSerializableExtra(Connections.TAG_SNEEZES);

        for(Integer i : sneezeMap.keySet()) {
            for(Sneeze sneeze : sneezeMap.get(i)) {
                sneezeList.add(sneeze);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sneeze_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
