package com.example.administrator.e_pic;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public abstract class CustomActionBarActivity extends ActionBarActivity {

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
