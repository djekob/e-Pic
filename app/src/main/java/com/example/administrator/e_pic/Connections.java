package com.example.administrator.e_pic;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

//TODO connectie maken
public class Connections {

    private static final String TAG_SUCCESS = "success";
    private static final String URL_CREATE_USER = "http://unuzeleirstest.netau.net/create_user.php";
    private static final String URL_ADD_SNEEZE = "http://unuzeleirstest.netau.net/add_sneeze.php";
    public static final String NAAM_VAR_USER = "Username";
    //public static final int ADD_USER = 0;
    private ProgressDialog pDialog;
    private Context context;
    private String prename = null, name = null, username = null, password = null, timestamp = null;
    private int age;

    public Connections(Context context, String timestamp){
        this.context = context;
        this.timestamp = timestamp;
        new CreateSneeze().execute();
    }

    public Connections(String prename, String name, String username, String password, int age, Context context) {
        this.prename = prename;
        this.name = name;
        this.username = username;
        this.password = password;
        this.age = age;
        this.context = context;

        new CreateNewUser().execute();
    }

    /**
     * Background Async Task to Create new user
     * */
    class CreateNewUser extends AsyncTask<String, String, String> {


        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(context);
            pDialog.setMessage("Creating Login..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * Creating product
         * */
        protected String doInBackground(String... args) {

            // Building Parameters
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("Voornaam", prename));
            params.add(new BasicNameValuePair("Achternaam", name));
            params.add(new BasicNameValuePair("Loginnaam", username));
            params.add(new BasicNameValuePair("Password", password));
            params.add(new BasicNameValuePair("Leeftijd", age+""));

            // getting JSON Object
            // Note that create product url accepts POST method
            JSONParser jsonParser = new JSONParser();


            JSONObject json = jsonParser.makeHttpRequest(URL_CREATE_USER,
                    "POST", params);

            // check log cat fro response

            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // successfully created product
                    Intent i = new Intent(context, iSneezeActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra(NAAM_VAR_USER, prename + " " + name);
                    context.startActivity(i);

                    // closing this screen


                } else {
                    // failed to create product
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            pDialog.dismiss();
        }

    }

    class CreateSneeze extends AsyncTask<String, String, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(context);
            pDialog.setMessage("Add sneeze..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {

            // Building Parameters
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("Loginnaam", username));
            //params.add(new BasicNameValuePair("Timestamp", timestamp));

            // getting JSON Object
            // Note that create product url accepts POST method
            JSONParser jsonParser = new JSONParser();


            JSONObject json = jsonParser.makeHttpRequest(URL_ADD_SNEEZE,
                    "POST", params);

            // check log cat fro response

            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // successfully created product
                    /*Intent i = new Intent(context, iSneezeActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);*/

                    // closing this screen


                } else {
                    // failed to create product
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pDialog.dismiss();
        }
    }
}
