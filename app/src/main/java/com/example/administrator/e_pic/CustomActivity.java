package com.example.administrator.e_pic;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;


public abstract class CustomActivity extends Activity {
    @Override
    protected void onResume() {
        super.onResume();
        if(SaveSharedPreference.getUserName(this) == "") {
            Intent i = new Intent(getApplicationContext(), RealMainActivity.class);
            //i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        }
        //Toast.makeText(this, "onresume", Toast.LENGTH_SHORT).show();
    }
}