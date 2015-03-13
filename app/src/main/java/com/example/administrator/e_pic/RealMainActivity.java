package com.example.administrator.e_pic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.ProgressBar;


public class RealMainActivity extends Activity {
    private Context context;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setProgressBarIndeterminateVisibility(true);
        setContentView(R.layout.activity_real_main);
        progressBar = (ProgressBar) findViewById(R.id.progress_dialog);
        context = this;
        if(SaveSharedPreference.getUserName(RealMainActivity.this).length() == 0)
        {
            Intent i = new Intent(context, FirstActivity.class);
            startActivity(i);
            this.finish();
        }
        else
        {
            Intent i = new Intent(context, iSneezeActivity.class);
            String username = SaveSharedPreference.getUserName(context);
            i.putExtra(Connections.NAAM_VAR_USER, username);
            startActivity(i);
            this.finish();

        }

    }





}
