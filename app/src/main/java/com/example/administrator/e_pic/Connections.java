package com.example.administrator.e_pic;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.security.Timestamp;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

//TODO connectie maken
public class Connections extends Activity {

    private static final String TAG_SUCCESS = "success";
    private static final String URL_CREATE_USER = "http://unuzeleirstest.netau.net/create_user.php";
    private static final String URL_ADD_SNEEZE = "http://unuzeleirstest.netau.net/add_sneeze.php";
    private static final String URL_CHECK_LOGIN = "http://unuzeleirstest.netau.net/check_login.php";
    private static final String URL_ALL_SNEEZES = "http://unuzeleirstest.netau.net/get_all_sneezes.php";
    public static final String URL_GET_USERS_NOT_FRIEND = "http://unuzeleirstest.netau.net/get_users_not_friends.php";
    public static final String URL_GET_ONE_USER = "http://unuzeleirstest.netau.net/get_one_user.php";
    public static final String URL_ADD_FRIEND_REQUEST = "http://unuzeleirstest.netau.net/add_friend_request.php";
    public static final String URL_GET_PENDING_FRIENDS = "http://unuzeleirstest.netau.net/get_pending_friends.php";
    public static final String NAAM_VAR_USER = "Username";
    public static final String NAAM_VAR_USERS_NOT_FRIEND = "Users not friends";
    public static final String NAAM_VAR_PENDING_FRIENDS = "pending friends";
    public static final String TAG_SNEEZES = "sneezes";
    public static final String TAG_USER_ID = "User_id";
    public static final String TAG_TIME = "Time";
    public static final String TAG_LOGINNAME = "Loginnaam";
    public static final String TAG_USERS_NOT_FRIEND= "users";
    public static final String TAG_ID = "_id";
    public static final String TAG_VOORNAAM = "Voornaam";
    public static final String TAG_ACHTERNAAM = "Achternaam";
    public static final String TAG_LEEFTIJD = "Leeftijd";
    public static final String TAG_USER = "users";
    public static final String TAG_FRIENDNAME = "Friend";
    public static final String TAG_PENDING_FRIENDS = "Pending_Friends";
    public static final String TAG_MY_FRIENDS = "My_Friends";



    public static final int ADD_USER = 0;
    public static final int CREATE_SNEEZE_CODE = 1;
    public static final int GET_ALL_SNEEZES_CODE = 2;
    public static final int GET_ALL_USERS_NO_FRIENDS = 3;
    public static final int ADD_FRIEND_CODE = 4;
    public static final int GET_ONE_USER_CODE = 5;
    public static final int GET_PENDING_FRIENDS = 6;
    public static final int ACCEPT_FRIEND_CODE = 7;
    public static final int GET_ALL_SNEEZES_GRAPH_CODE = 8;
    public static final int GO_TO_FRIENDS_PROFILE_CODE = 9;


    private Context context;
    private View buttonView;
    private String prename = null, name = null, username = null, password = null;
    private int age;
    private String friendname;
    private int position;
    private TreeMap<String, Integer> originalUsers;
    private ArrayList<User> myFriends;


    public Connections(Context context, String username, int code){
        this.context = context;
        this.username = username;


        if(code==CREATE_SNEEZE_CODE) {
            new CreateSneeze().execute();
        } else if (code == GET_ALL_SNEEZES_CODE) {
            new AllSneezes().execute();
        } else if (code == GET_ALL_USERS_NO_FRIENDS) {
            new GetUsers().execute();
        } else if(code == GET_ONE_USER_CODE) {
            new GetOneUser().execute();
        } else if(code == Connections.GET_PENDING_FRIENDS) {
            new GetPendingFriends().execute();
        } else if(code == Connections.GET_ALL_SNEEZES_GRAPH_CODE) {
            new OpenSneezesGraph().execute();
        }

    }

    public Connections(Context context, View v, String username, int code){
        this.context = context;
        this.buttonView = buttonView;

    }

    public Connections(Context context, String username, String friendname, int position, TreeMap<String, Integer> originalUsers, int code) {
        this.context = context;
        this.username = username;
        this.friendname = friendname;
        this.position = position;
        this.originalUsers = originalUsers;
        if(code == ADD_FRIEND_CODE) {
            new AddFriend().execute();
        }
    }

    public Connections(Context context, String username, String friendame, int code) {
        this.context = context;
        this.username = username;
        this.friendname= friendame;
        if (code== ACCEPT_FRIEND_CODE) {
            new AcceptFriendRequest().execute();
        }

    }

    public Connections(Context context, String username, String password){
        this.context = context;
        this.username = username;
        this.password = password;
        new Login().execute();
    }

    public Connections(String prename, String name, String username, String password, int age, int code, Context context) {
        if (code == ADD_FRIEND_CODE) {
            this.prename = prename;
            this.name = name;
            this.username = username;
            this.password = password;
            this.age = age;
            this.context = context;

            new CreateNewUser().execute();
        } else if(code == 234435) {

        }
    }

    public Connections(Context context, String username, String friendname, int position, ArrayList<User> myFriends , int code) {
        myFriends = new ArrayList<User>();
        if (code== GO_TO_FRIENDS_PROFILE_CODE) {
            this.context = context;
            this.username = username;
            this.friendname = friendname;
            this.position = position;
            this.myFriends = myFriends;

            new GoToFriendsProfile().execute();
        }
    }



    private class GoToFriendsProfile extends AsyncTask<String, String, Boolean> {
    //TODO volledige klasse schrijven  + php
        @Override
        protected Boolean doInBackground(String... params) {

            Intent i = new Intent(context, ProfileActivity.class);
            i.putExtra(TAG_MY_FRIENDS, myFriends);
            return null;
        }
    }
    private class OpenSneezesGraph extends AsyncTask<String, String ,Boolean>{

        JSONArray sneezes = new JSONArray();
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected Boolean doInBackground(String... args) {

            List<NameValuePair> params = new ArrayList<>();


            TreeMap<Integer, Sneeze> sneezeHashMapDef;

            JSONParser jsonParser = new JSONParser();
            JSONObject json = jsonParser.makeHttpRequest(URL_ALL_SNEEZES,"GET", params);

            try {
                sneezeHashMapDef = new TreeMap<>();
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

                        //TODO sneezes ophalen

                        int totalSneezes = 0;

                        User user = new User(name, firstname, secondname, leeftijd, id, totalSneezes);
                        Sneeze sneeze = new Sneeze(time, user, sneeze_id);

                        sneezeHashMapDef.put(sneeze_id, sneeze);
                    }
                    // successfully created product

                    // closing this screen


                    Intent ik = new Intent(context, SneezeOverviewActivity.class);
                    ik.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    ik.putExtra(NAAM_VAR_USER, username);
                    ik.putExtra(TAG_SNEEZES, sneezeHashMapDef);

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


    private class Login extends AsyncTask<String, String, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected Boolean doInBackground(String... args) {
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("Loginnaam", username));
            params.add(new BasicNameValuePair("Password", password));

            JSONParser jsonParser = new JSONParser();


            JSONObject json = jsonParser.makeHttpRequest(URL_CHECK_LOGIN,
                    "POST", params);

            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    Intent i = new Intent(context, iSneezeActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra(NAAM_VAR_USER, username);
                    context.startActivity(i);

                } else {

                    return true;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return false;
        }

        @Override
        protected void onPostExecute(Boolean b) {
            super.onPostExecute(b);
            if (b) Toast.makeText(context, "Login mislukt", Toast.LENGTH_LONG).show();
        }


    }
    private class CreateNewUser extends AsyncTask<String, String, String> {


        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... args) {

            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("Voornaam", prename));
            params.add(new BasicNameValuePair("Achternaam", name));
            params.add(new BasicNameValuePair("Loginnaam", username));
            params.add(new BasicNameValuePair("Password", password));
            params.add(new BasicNameValuePair("Leeftijd", age+""));
            JSONParser jsonParser = new JSONParser();


            JSONObject json = jsonParser.makeHttpRequest(URL_CREATE_USER,
                    "POST", params);

            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {

                    Intent i = new Intent(context, iSneezeActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra(NAAM_VAR_USER, username);
                    context.startActivity(i);

                } else {

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(String file_url) {

        }

    }
    private class CreateSneeze extends AsyncTask<String, String, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

           }

        @Override
        protected String doInBackground(String... args) {


            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("Loginnaam", username));
            JSONParser jsonParser = new JSONParser();


            JSONObject json = jsonParser.makeHttpRequest(URL_ADD_SNEEZE,
                    "POST", params);

            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {

                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(context, "Sneeze added ;)", Toast.LENGTH_SHORT).show();
                        }
                    });



                } else {
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

        }
    }
    private class AllSneezes extends AsyncTask<String, String, Boolean> {


        JSONArray sneezes = new JSONArray();
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected Boolean doInBackground(String... args) {

            List<NameValuePair> params = new ArrayList<>();


            TreeMap<Integer, Sneeze> sneezeHashMapDef;

            JSONParser jsonParser = new JSONParser();
            JSONObject json = jsonParser.makeHttpRequest(URL_ALL_SNEEZES,"GET", params);

            try {
                sneezeHashMapDef = new TreeMap<>();
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {

                    sneezes = json.getJSONArray(TAG_SNEEZES);

                    ArrayList<Sneeze> temporarySneezes = new ArrayList<>();

                    for (int i = 0; i < sneezes.length(); i++) {
                        JSONObject c = sneezes.getJSONObject(i);


                        int id = c.getInt(TAG_USER_ID);
                        String time = c.getString(TAG_TIME);
                        String name = c.getString(TAG_LOGINNAME);
                        int sneeze_id = c.getInt(TAG_ID);
                        String firstname = c.getString(TAG_VOORNAAM);
                        String secondname = c.getString(TAG_ACHTERNAAM);
                        String leeftijd2 = c.getString(TAG_LEEFTIJD);
                        int leeftijd = Integer.parseInt(leeftijd2);


                        //TODO sneezes ophalen
                        int totalSneezes = 0;

                        User user = new User(name, firstname, secondname, leeftijd, id, totalSneezes);
                        Sneeze sneeze = new Sneeze(time, user, sneeze_id);

                        sneezeHashMapDef.put(sneeze_id, sneeze);
                    }

                    Intent ik = new Intent(context, SneezeListActivity.class);
                    ik.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    ik.putExtra(NAAM_VAR_USER, username);
                    ik.putExtra(TAG_SNEEZES, sneezeHashMapDef);

                    context.startActivity(ik);

                } else {


                    return true;

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
    private class GetUsers extends AsyncTask<String, String, Boolean> {


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

            JSONObject json = jsonParser.makeHttpRequest(URL_GET_USERS_NOT_FRIEND,"POST", params);

            try {
                users = new ArrayList<>();
                int success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    JSONArray userkes = json.getJSONArray(TAG_USERS_NOT_FRIEND);

                    for (int i = 0; i < userkes.length(); i++) {
                        String k = userkes.getString(i);
                        users.add(k);
                    }

                    return false;

                } else {
                    return true;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return true;

        }

        protected void onPostExecute(Boolean b) {
            super.onPostExecute(b);
            if (b) Toast.makeText(context, "Laden users mislukt.", Toast.LENGTH_LONG).show();
            else {
                Intent ik = new Intent(context, SearchFriendActivity.class);
                ik.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ik.putExtra(NAAM_VAR_USERS_NOT_FRIEND, users);
                ik.putExtra(iSneezeActivity.ADD_FRIEND_CODE, username);
                context.startActivity(ik);
            }

        }

    }
    private class GetOneUser extends AsyncTask<String, String, User> {


        private User gebruiker;

        //JSONArray sneezes = new JSONArray();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected User doInBackground(String... args) {


            List<NameValuePair> params = new ArrayList<>();

            params.add(new BasicNameValuePair(TAG_LOGINNAME, username));

            JSONParser jsonParser = new JSONParser();

            JSONObject json = jsonParser.makeHttpRequest(URL_GET_ONE_USER,"POST", params);

            try {

                int success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    JSONArray userkes = json.getJSONArray(TAG_USER);


                    for (int i = 0; i < userkes.length(); i++) {
                        JSONObject c = userkes.getJSONObject(i);


                        int id = c.getInt(TAG_ID);

                        String firstname = c.getString(TAG_VOORNAAM);
                        String secondname = c.getString(TAG_ACHTERNAAM);

                        gebruiker = new User(username, firstname, secondname, id);

                    }

                    return gebruiker;
                } else {


                    return gebruiker;

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return gebruiker;

        }

        protected void onPostExecute(User user) {
            super.onPostExecute(user);


        }

    }

    private class AddFriend extends AsyncTask<String, String, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected Boolean doInBackground(String... args) {
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair(TAG_LOGINNAME, username));
            params.add(new BasicNameValuePair(TAG_FRIENDNAME, friendname));


            JSONParser jsonParser = new JSONParser();


            JSONObject json = jsonParser.makeHttpRequest(URL_ADD_FRIEND_REQUEST,
                    "POST", params);

            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    return true;
                } else {
                    return false;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return false;

        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(aBoolean) {
                ((SearchFriendActivity) context).adapter.changeOriginalUser(friendname);
            }
        }
    }

    private class GetPendingFriends extends AsyncTask<String, String, Boolean> {

        ArrayList<String> pendingFriends;

        @Override
        protected Boolean doInBackground(String... args) {


            List<NameValuePair> params = new ArrayList<>();

            System.out.println("USERNAME: " + username);
            params.add(new BasicNameValuePair(TAG_LOGINNAME, username));

            JSONParser jsonParser = new JSONParser();

            JSONObject json = jsonParser.makeHttpRequest(URL_GET_PENDING_FRIENDS,"POST", params);

            try {
                pendingFriends = new ArrayList<>();
                int success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    JSONArray userkes = json.getJSONArray(TAG_PENDING_FRIENDS);

                    for (int i = 0; i < userkes.length(); i++) {
                        String k = userkes.getString(i);
                        pendingFriends.add(k);
                        Log.e("VRIEND", k);
                    }
                    Intent i = new Intent(context, FriendRequestsActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra(NAAM_VAR_PENDING_FRIENDS, pendingFriends);
                    i.putExtra(TAG_LOGINNAME, username);
                    context.startActivity(i);
                    return false;

                } else if (success ==2) {
                    Intent i = new Intent(context, FriendRequestsActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra(NAAM_VAR_PENDING_FRIENDS, pendingFriends);
                    i.putExtra(TAG_LOGINNAME, username);
                    context.startActivity(i);
                    return false;
                } else {
                    return true;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return true;


        }
    }

    private class AcceptFriendRequest extends AsyncTask<String, String, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            return null;
        }
    }
 }
