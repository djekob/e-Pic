package com.example.administrator.e_pic;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class LoginActivity extends Activity {

    private Button mOkButton;
    private EditText mUsernameEditText, mPasswordEditText;
    private String username, password;
    private TextView warningTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setProgressBarIndeterminateVisibility(true);
        setContentView(R.layout.activity_login);

        initialization();


    }

    private void initialization() {

        mOkButton = (Button) findViewById(R.id.ok_button_login);
        mUsernameEditText = (EditText) findViewById(R.id.username_edit_text_login);
        mPasswordEditText = (EditText) findViewById(R.id.password_edit_text_login);
        warningTextView = (TextView) findViewById(R.id.warning_text_view_login_activity);
        warningTextView.setVisibility(View.INVISIBLE);
        mOkButton.setOnClickListener(new onOkClickListener());

    }
    private class onOkClickListener implements View.OnClickListener {


        @Override
        public void onClick(View v) {
            username=mUsernameEditText.getText().toString().trim();
            password = mPasswordEditText.toString().trim();
            if(!username.isEmpty() && !password.isEmpty()) {
                if(RandomShit.haveNetworkConnection(getContext())){
                    new Connections(getContext(), mUsernameEditText.getText().toString(), mPasswordEditText.getText().toString());
                }
                else{
                    Toast.makeText(getContext(), "No internet available", Toast.LENGTH_LONG).show();
                }
            } else {
                warningTextView.setVisibility(View.VISIBLE);
            }

        }
    }

    private Context getContext(){
        return this;
    }
}
