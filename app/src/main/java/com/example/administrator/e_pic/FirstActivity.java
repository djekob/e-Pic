package com.example.administrator.e_pic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FirstActivity extends Activity {

    private Button mLoginButton, mRegisterButton;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        context = this;

        mLoginButton = (Button) findViewById(R.id.login_button_main);
        mRegisterButton = (Button) findViewById(R.id.register_button_main);

        mRegisterButton.setOnClickListener(new registerOnClickListener());
        mLoginButton.setOnClickListener(new loginOnClickListener());






    }


    private class loginOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent i = new Intent(getContext(), LoginActivity.class);
            startActivity(i);
            FirstActivity firstActivity = (FirstActivity) context;
        }
    }

    private class registerOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent i = new Intent(getContext(), RegisterActivity.class);
            startActivity(i);
            FirstActivity firstActivity = (FirstActivity) context;
        }
    }

    private Context getContext(){
        return this;
    }
}
