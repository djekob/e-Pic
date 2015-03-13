package com.example.administrator.e_pic;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//TODO opkuisen, regid pas toevoegen in connections
public class RegisterActivity extends Activity {

    private EditText mVoornaamEditText, mAchternaamEditText, mPasswordEditText, mUsernameEditText;

    private TextView warningTextView;
    private Button mOkButton, datePickerButton;
    private String voornaam, achternaam, password, username;


    //TODO age afleiden uit textView die wordt aangepast door DatePickerFragment

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initialization();


    }

    private class okOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            setValues();
            if(!voornaam.trim().isEmpty() && !achternaam.trim().isEmpty() && !password.trim().isEmpty() && !username.trim().isEmpty()) {
                Log.e("foutje", "dikke fout");
                if(RandomShit.haveNetworkConnection(getContext())){
                    new Connections(voornaam, achternaam, username, password, 0 ,Connections.REGISTER_CODE, getContext());
                }
                else{
                    Toast.makeText(getContext(), "No internet available", Toast.LENGTH_LONG).show();
                }
            } else {
                Log.e("ajaj", "ajaja");
                warningTextView.setVisibility(View.VISIBLE);
            }
        }
    }

    private void setValues() {
        voornaam = mVoornaamEditText.getText().toString();
        achternaam = mAchternaamEditText.getText().toString();
        username = mUsernameEditText.getText().toString();
        password = mPasswordEditText.getText().toString();


    }

    private void initialization() {

        warningTextView = (TextView) findViewById(R.id.warning_text_view_register_activity);
        mVoornaamEditText = (EditText) findViewById(R.id.first_name_edit_text);
        mAchternaamEditText = (EditText) findViewById(R.id.achternaam_edit_text);
        mPasswordEditText = (EditText) findViewById(R.id.password_edit_text);
        mUsernameEditText = (EditText) findViewById(R.id.username_edit_text);
        mOkButton = (Button) findViewById(R.id.register_button);

        warningTextView.setVisibility(View.INVISIBLE);

        mOkButton.setOnClickListener(new okOnClickListener());

    }

    private Context getContext(){
        return this;
    }
}
