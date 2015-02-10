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
    private ArrayList<Sneeze> sneezes;
    private ArrayList<String> sneezeList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sneeze_list);

        mListView = (ListView) findViewById(R.id.all_sneezes_list);
        sneezes = new ArrayList<>();
        ArrayListVuller();
        SneezeListAdapter = new SneezeListAdapter(getApplicationContext(), R.layout.sneeze_list_item, sneezeList);

        mListView.setAdapter(SneezeListAdapter);
    }

    private void ArrayListVuller() {

        sneezeList = new ArrayList<>();
        sneezeList = getIntent().getExtras().getStringArrayList(Connections.TAG_SNEEZES);


        User thomasty = new User("patat", "thomas", "deletter", 20);
        User gilels = new User("magic", "gilles", "mahy", 20);
        User iveu = new User("piveu", "ivo", "tanghe", 21);
        User leon = new User("legend", "leon", "vauterin", 19);






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
