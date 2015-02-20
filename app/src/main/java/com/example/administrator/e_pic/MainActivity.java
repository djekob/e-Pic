package com.example.administrator.e_pic;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.security.Timestamp;


public class MainActivity extends Activity {

    private Button mLoginButton, mRegisterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mLoginButton = (Button) findViewById(R.id.login_button_main);
        mRegisterButton = (Button) findViewById(R.id.register_button_main);

        mRegisterButton.setOnClickListener(new registerOnClickListener());
        mLoginButton.setOnClickListener(new loginOnClickListener());






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
}
