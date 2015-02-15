package com.example.administrator.e_pic;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;


public class SearchFriendActivity extends ActionBarActivity {

    private String username;
    private Button searchButton;
    private EditText friendsNameEditText;
    private ListView listView;
    private ArrayList<String> users;
    private FriendsListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_friend);

        initialisation();

        username = getIntent().getStringExtra(iSneezeActivity.ADD_FRIEND_CODE);
        users = new ArrayList<>();
        users = getIntent().getExtras().getStringArrayList(Connections.NAAM_VAR_USERS_NOT_FRIEND);
        adapter = new FriendsListAdapter(this, R.layout.search_friends_list_item, users, username);

        listView.setAdapter(adapter);
    }

    private void initialisation() {

        friendsNameEditText = (EditText) findViewById(R.id.friends_search_edit_text);
        listView = (ListView) findViewById(R.id.all_friends_suggestions_list);

        friendsNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s.toString());


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_friend, menu);
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