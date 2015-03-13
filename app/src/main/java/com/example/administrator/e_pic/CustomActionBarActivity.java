package com.example.administrator.e_pic;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;


public abstract class CustomActionBarActivity extends FragmentActivity {
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = SaveSharedPreference.getUser(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(SaveSharedPreference.getUserName(this) == "") {

            Intent i = new Intent(getApplicationContext(), RealMainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        }
        //Toast.makeText(this, "onresume", Toast.LENGTH_SHORT).show();
    }
}
