package com.example.administrator.e_pic;

import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;


public class RegisterActivity extends ActionBarActivity {

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
        datePickerButton.setOnClickListener(new datePickerOnClickListener());

    }

    private class datePickerOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
               showDatePickerDialog(v);
        }

        public void showDatePickerDialog(View v) {
            DialogFragment newFragment = new DatePickerFragment();
            newFragment.show(getSupportFragmentManager(), "datePicker");
        }
    }
    private class okOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            setValues();
            Context context = getApplicationContext();
            new Connections(voornaam, achternaam, username, password, age, context);
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

        birthDateTextView = (TextView) findViewById(R.id.date_of_birth_text_view);
        birthDateTextView.setText("");
        datePickerButton = (Button) findViewById(R.id.date_of_birth_button);
        mVoornaamEditText = (EditText) findViewById(R.id.first_name_edit_text);
        mAchternaamEditText = (EditText) findViewById(R.id.achternaam_edit_text);
        mPasswordEditText = (EditText) findViewById(R.id.password_edit_text);
        mUsernameEditText = (EditText) findViewById(R.id.username_edit_text);
        //mAgePicker = (NumberPicker) findViewById(R.id.age_number_picker);
        mOkButton = (Button) findViewById(R.id.register_button);

        //mAgePicker.setMinValue(0);
        //mAgePicker.setMaxValue(100);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
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
