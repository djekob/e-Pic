package com.example.administrator.e_pic;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class LoginActivity extends Activity {

    private Button mOkButton;
    private EditText mUsernameEditText, mPasswordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mOkButton = (Button) findViewById(R.id.ok_button_login);
        mUsernameEditText = (EditText) findViewById(R.id.username_edit_text_login);
        mPasswordEditText = (EditText) findViewById(R.id.password_edit_text_login);

        mOkButton.setOnClickListener(new onOkClickListener());


    }
    private class onOkClickListener implements View.OnClickListener {


        @Override
        public void onClick(View v) {
            new Connections(getApplicationContext(), mUsernameEditText.getText().toString(), mPasswordEditText.getText().toString());

        }
    }
}
