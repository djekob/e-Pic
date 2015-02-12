package com.example.administrator.e_pic;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//TODO connectie maken
public class Connections {

    private static final String TAG_SUCCESS = "success";
    private static final String URL_CREATE_USER = "http://unuzeleirstest.netau.net/create_user.php";
    private static final String URL_ADD_SNEEZE = "http://unuzeleirstest.netau.net/add_sneeze.php";
    private static final String URL_CHECK_LOGIN = "http://unuzeleirstest.netau.net/check_login.php";
    private static final String URL_ALL_SNEEZES = "http://unuzeleirstest.netau.net/get_all_sneezes.php";
    public static final String URL_GET_USERS_NOT_FRIEND = "http://unuzeleirstest.netau.net/get_users_not_friend.php";
    public static final String NAAM_VAR_USER = "Username";
    public static final String NAAM_VAR_USERS_NOT_FRIEND = "Users not friends";
    public static final String TAG_SNEEZES = "sneezes";
    public static final String TAG_USER_ID = "User_id";
    public static final String TAG_TIME = "Time";
    public static final String TAG_LOGINNAME = "Loginnaam";
    public static final String TAG_USERS_NOT_FRIEND= "Users";
    public static final String TAG_ID = "_id";
    public static final String TAG_VOORNAAM = "Voornaam";
    public static final String TAG_ACHTERNAAM = "Achternaam";
    public static final String TAG_LEEFTIJD = "Leeftijd";



    public static final int CREATE_SNEEZE_CODE = 1;
    public static final int GET_ALL_SNEEZES_CODE = 2;


    //public static final int ADD_USER = 0;
    private ProgressDialog pDialog;
    private Context context;
    private String prename = null, name = null, username = null, password = null;
    private int age;

    public Connections(Context context, String username, int code){
        this.context = context;
        this.username = username;

        if(code==CREATE_SNEEZE_CODE) {
            new CreateSneeze().execute();
        } else if (code == GET_ALL_SNEEZES_CODE) {
            new AllSneezes().execute();
        }

    }



    public Connections(Context context, String username, String password){
        this.context = context;
        this.username = username;
        this.password = password;
        new Login().execute();
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

     private class Login extends AsyncTask<String, String, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            /*pDialog = new ProgressDialog(context);
            pDialog.setMessage("Creating Login..");
            pDialog.setCancelable(true);
            pDialog.show();*/
        }

        protected Boolean doInBackground(String... args) {

            // Building Parameters
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("Loginnaam", username));
            params.add(new BasicNameValuePair("Password", password));

            // getting JSON Object
            // Note that create product url accepts POST method
            JSONParser jsonParser = new JSONParser();


            JSONObject json = jsonParser.makeHttpRequest(URL_CHECK_LOGIN,
                    "POST", params);

            // check log cat fro response

            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // successfully created product
                    Intent i = new Intent(context, iSneezeActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra(NAAM_VAR_USER, username);
                    context.startActivity(i);

                    // closing this screen


                } else {

                    return true;
                    // failed to create product
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return false;
        }

        @Override
        protected void onPostExecute(Boolean b) {
            super.onPostExecute(b);
            //pDialog.dismiss();
            if (b) Toast.makeText(context, "Login mislukt", Toast.LENGTH_LONG).show();


        }


    }
     class CreateNewUser extends AsyncTask<String, String, String> {


        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            /*pDialog = new ProgressDialog(context);
            pDialog.setMessage("Creating Login..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();*/
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
                    i.putExtra(NAAM_VAR_USER, username);
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
            //pDialog.dismiss();
        }

    }

    class CreateSneeze extends AsyncTask<String, String, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            /*pDialog = new ProgressDialog(context);
            pDialog.setMessage("Add sneeze..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();*/
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
                    Toast.makeText(context, "cva", Toast.LENGTH_LONG).show();
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
            //pDialog.dismiss();
        }
    }

    class AllSneezes extends AsyncTask<String, String, Boolean> {

        private ArrayList<String> sneezeList = new ArrayList<>();
        private ArrayList<Sneeze> arrayListSneezes = new ArrayList<>();

        JSONArray sneezes = new JSONArray();
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected Boolean doInBackground(String... args) {

            List<NameValuePair> params = new ArrayList<>();

            HashMap<Integer, ArrayList<Sneeze>> sneezeMap ;

            JSONParser jsonParser = new JSONParser();
            JSONObject json = jsonParser.makeHttpRequest(URL_ALL_SNEEZES,"GET", params);

            try {
                sneezeMap = new HashMap<>();
                int success = json.getInt(TAG_SUCCESS);

                    if (success == 1) {
                        // products found
                        // Getting Array of Products
                        sneezes = json.getJSONArray(TAG_SNEEZES);

                        ArrayList<Sneeze> temporarySneezes = new ArrayList<>();
                        // looping through All Products
                        for (int i = 0; i < sneezes.length(); i++) {
                            JSONObject c = sneezes.getJSONObject(i);

                            // Storing each json item in variable
                            int id = c.getInt(TAG_USER_ID);
                            String time = c.getString(TAG_TIME);
                            String name = c.getString(TAG_LOGINNAME);
                            int sneeze_id = c.getInt(TAG_ID);
                            String firstname = c.getString(TAG_VOORNAAM);
                            String secondname = c.getString(TAG_ACHTERNAAM);
                            String leeftijd2 = c.getString(TAG_LEEFTIJD);
                            int leeftijd = Integer.parseInt(leeftijd2);

                            User user = new User(name, firstname, secondname, leeftijd);
                            Sneeze sneeze = new Sneeze(time, user, sneeze_id);

                            temporarySneezes.add(sneeze);

                            if(sneezeMap.containsKey(id)) {
                                ArrayList<Sneeze> sneezekes = new ArrayList<>();
                                sneezekes = sneezeMap.get(id);
                                sneezekes.add(sneeze);
                                sneezeMap.remove(id);
                                sneezeMap.put(id, sneezekes);

                            } else {
                                ArrayList<Sneeze> sneezekes = new ArrayList<>();
                                sneezekes.add(sneeze);
                                sneezeMap.put(id, sneezekes);
                            }
                        }
                        // successfully created product

                        // closing this screen


                        Intent ik = new Intent(context, SneezeListActivity.class);
                        ik.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        ik.putExtra(NAAM_VAR_USER, username);
                        ik.putExtra(TAG_SNEEZES, sneezeMap);
                        System.out.println("jolaaaa " + sneezeMap);
                        context.startActivity(ik);

                    } else {


                        return true;
                        // failed to create product
                    }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return false;
        }

        @Override
        protected void onPostExecute(Boolean b) {
            super.onPostExecute(b);
            //pDialog.dismiss();
            if (b) Toast.makeText(context, "Laden sneezes mislukt.", Toast.LENGTH_LONG).show();


        }
    }


    class GetUsers extends AsyncTask<String, String, Boolean> {


        private ArrayList<String> users;

        //JSONArray sneezes = new JSONArray();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected Boolean doInBackground(String... args) {

            List<NameValuePair> params = new ArrayList<>();

            params.add(new BasicNameValuePair(TAG_LOGINNAME, username));

            JSONParser jsonParser = new JSONParser();

            JSONObject json = jsonParser.makeHttpRequest(URL_GET_USERS_NOT_FRIEND,"GET", params);

            try {
                users = new ArrayList<String>();
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // products found
                    // Getting Array of Products
                    JSONArray sneezes = json.getJSONArray(TAG_USERS_NOT_FRIEND);


                    // looping through All Products
                    for (int i = 0; i < sneezes.length(); i++) {
                        JSONObject c = sneezes.getJSONObject(i);

                        // Storing each json item in variable

                        String name = c.getString(TAG_LOGINNAME);
                        users.add(name);
                    }
                    // successfully created product

                    // closing this screen

                    System.out.println("DIT ZIJN ZE:" + users);
                    Intent ik = new Intent(context, SearchFriendActivity.class);
                    ik.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    ik.putExtra(NAAM_VAR_USERS_NOT_FRIEND, users);
                    context.startActivity(ik);

                } else {


                    return true;
                    // failed to create product
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return false;

        }
    }
}
