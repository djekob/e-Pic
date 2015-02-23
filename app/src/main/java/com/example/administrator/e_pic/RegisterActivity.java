package com.example.administrator.e_pic;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;

import static android.content.pm.PackageManager.NameNotFoundException;

//TODO opkuisen, regid pas toevoegen in connections
public class RegisterActivity extends Activity {

    private EditText mVoornaamEditText, mAchternaamEditText, mPasswordEditText, mUsernameEditText;

    private TextView birthDateTextView;
    private Button mOkButton, datePickerButton;
    private String voornaam, achternaam, password, username;
    private int age;
    private int month, year, day;

    //TODO age afleiden uit textView die wordt aangepast door DatePickerFragment

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initialization();

        mOkButton.setOnClickListener(new okOnClickListener());

    }

    private class okOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            setValues();
            new Connections(voornaam, achternaam, username, password, age ,Connections.REGISTER_CODE, getContext());
        }
    }

    private void setValues() {
        voornaam = mVoornaamEditText.getText().toString();
        achternaam = mAchternaamEditText.getText().toString();
        username = mUsernameEditText.getText().toString();
        password = mPasswordEditText.getText().toString();
        //age = mAgePicker.getValue();
    }

    private void initialization() {

        mVoornaamEditText = (EditText) findViewById(R.id.first_name_edit_text);
        mAchternaamEditText = (EditText) findViewById(R.id.achternaam_edit_text);
        mPasswordEditText = (EditText) findViewById(R.id.password_edit_text);
        mUsernameEditText = (EditText) findViewById(R.id.username_edit_text);
        mOkButton = (Button) findViewById(R.id.register_button);

        //mAgePicker.setMinValue(0);
        //mAgePicker.setMaxValue(100);
    }

    private Context getContext(){
        return this;
    }
}
