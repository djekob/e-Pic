package com.example.administrator.e_pic;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;


public class EditProfileActivity extends CustomActionBarActivity {

    private EditText usernameEditText, firstnameEditText, nameEditText, oldPasswordEditText, newPasswordEditText;
    private Button mOkButton, mCancelButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        initialization();

        usernameEditText.setText(user.getUsername());
        firstnameEditText.setText(user.getFirstname());
        nameEditText.setText(user.getName());


    }

    private void initialization() {
        usernameEditText = (EditText) findViewById(R.id.my_username_edit_text_edit_profile_activity);
        firstnameEditText = (EditText) findViewById(R.id.my_first_name_edit_text_edit_profile_activity);
        nameEditText= (EditText) findViewById(R.id.my_name_edit_text_edit_profile_activity);
        oldPasswordEditText = (EditText) findViewById(R.id.my_old_password_edit_text_edit_profile_activity);
        newPasswordEditText = (EditText) findViewById(R.id.my_new_password_edit_text_edit_profile_activity);
        mOkButton = (Button) findViewById(R.id.ok_edit_profile_data_button);
        mCancelButton = (Button) findViewById(R.id.cancel_edit_profile_data_button);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_profile, menu);
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
