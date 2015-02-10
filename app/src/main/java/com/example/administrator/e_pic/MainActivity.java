package com.example.administrator.e_pic;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends ActionBarActivity {

    private Button mLoginButton, mRegisterButton, testButty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mLoginButton = (Button) findViewById(R.id.login_button_main);
        mRegisterButton = (Button) findViewById(R.id.register_button_main);
        testButty = (Button) findViewById(R.id.test_butty);

        mRegisterButton.setOnClickListener(new registerOnClickListener());
        mLoginButton.setOnClickListener(new loginOnClickListener());

        testButty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Connections(getApplicationContext(),"admin",Connections.GET_ALL_SNEEZES_CODE);
            }
        });




    }

    private class loginOnClickListener implements View.OnClickListener {



        @Override
        public void onClick(View v) {
            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(i);
        }
    }

    private class registerOnClickListener implements View.OnClickListener {



        @Override
        public void onClick(View v) {
            Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
            startActivity(i);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
