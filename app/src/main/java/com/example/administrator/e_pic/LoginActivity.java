package com.example.administrator.e_pic;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class LoginActivity extends ActionBarActivity {

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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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

    private class onOkClickListener implements View.OnClickListener {


        @Override
        public void onClick(View v) {
            new Connections(getApplicationContext(), mUsernameEditText.getText().toString(), mPasswordEditText.getText().toString());

        }
    }
}
