package com.example.administrator.e_pic;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.TreeMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.net.ssl.HandshakeCompletedEvent;

//TODO connectie maken
public class Connections {
    public static final String GOOGLE_API_KEY = "AIzaSyAm2MCwTfk5Yqbmsv2XYl-w4vUN1TJMHr0";

    private static final String TAG_SUCCESS = "success";

    private static final String IP = "http://130.211.57.199/";
    private static final String URL_CREATE_USER = IP+"create_user.php";
    private static final String URL_ADD_SNEEZE = IP+"add_sneeze.php";
    private static final String URL_TEST = IP+"add_sneeze_test.php";
    private static final String URL_CHECK_LOGIN = IP+"check_login.php";
    private static final String URL_ALL_SNEEZES = IP+"get_all_sneezes.php";
    private static final String URL_ALL_FRIEND_SNEEZES = IP+ "get_all_friend_sneezes.php";
    public static final String URL_GET_USERS_NOT_FRIEND = IP+ "get_users_not_friends.php";
    public static final String URL_GET_ONE_USER = IP+"get_one_user.php";
    public static final String URL_ADD_FRIEND_REQUEST = IP+"add_friend_request.php";
    public static final String URL_GET_PENDING_FRIENDS = IP+"get_pending_friends.php";
    public static final String URL_ACCEPT_FRIEND_REQUEST = IP+"accept_friend_request.php";
    public static final String URL_GET_FRIENDS = IP+"get_friends.php";
    public static final String URL_DELETE_REGID= IP+"logout.php";
    public static final String URL_GET_ALL_OWN_SNEEZES = IP + "get_all_own_sneezes.php";
    private static final String URL_GET_FRIENDS_NO_EXTRA_DATA = IP + "get_friends_no_extra_data.php";

    public static final String NAAM_VAR_USER = "Username";
    public static final String NAAM_VAR_USERS_NOT_FRIEND = "Users not friends";
    public static final String NAAM_VAR_PENDING_FRIENDS = "pending friends";
    public static final String TAG_SNEEZES = "sneezes";
    public static final String TAG_AANTAL_SNEEZES = "Sneezes";
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
    public static final String TAG_FRIENDS = "Friends";
    public static final String TAG_NOTIFICATIONS ="notifications";
    public static final String TAG_AANTAL = "Aantal";
    public static final String TAG_NR_OF_SNEEZES_FRIEND = "Nr of sneezes friend";
    public static final String TAG_ADD_FRIEND = "add_friend";
    public static final String TAG_ARRAY_FRIENDS = "friends[]";
    public static final String TAG_ARRAY_SNEEZES = "aantal[]";
    public static final String TAG_NR_OF_SNEEZES = "Aantal";




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
    public static final int GET_ALL_FRIENDS_CODE = 10;
    public static final int GET_ALL_FRIEND_SNEEZES_CODE = 11;
    public static final int REGISTER_CODE = 12;
    public static final int DELETE_REGID_CODE = 13;
    public static final int GET_PROFILE_DATA_CODE = 14;
    public static final int GET_ALL_SNEEZES_GRAPH_CODE_AND_FRIENDS = 15;
    public static final int RELOAD_ALL_SNEEZES_CODE = 16;


    private Context context;
    private View buttonView;
    private String prename = null, name = null, username = null, password = null;
    private int age;
    private String friendname;
    private int position;
    private TreeMap<String, Integer> originalUsers;
    private ArrayList<User> myFriends;
    private int nrOfSneezesFriend;


    public Connections(Context context, int code){
        this.context = context;
        this.username = SaveSharedPreference.getUserName(context);


        if(code==CREATE_SNEEZE_CODE) {
            new CreateSneeze().execute();
        } else if (code == GET_ALL_FRIEND_SNEEZES_CODE) {
            new GetAllFriendSneezes().execute();
        } else if (code == GET_ALL_USERS_NO_FRIENDS) {
            new GetAllUsers().execute();
        } else if(code == GET_ONE_USER_CODE) {
            new GetOneUser().execute();
        } else if(code == Connections.GET_PENDING_FRIENDS) {
            new GetPendingFriends().execute();
        } else if(code == Connections.GET_ALL_SNEEZES_GRAPH_CODE) {
            new OpenSneezesGraph().execute();
        } else if(code == Connections.GET_ALL_FRIENDS_CODE) {
            new getFriends().execute();
        } else if(code == Connections.DELETE_REGID_CODE) {
            new DeleteRegId().execute();
        } else if(code == Connections.GET_PROFILE_DATA_CODE) {
            new GetOneUser().execute();
        } else if(code == Connections.GET_ALL_SNEEZES_GRAPH_CODE_AND_FRIENDS){
            new OpenSneezesGraphAndGetFriends().execute();
        }

    }

    public Connections(Context context, String friendname, int position, TreeMap<String, Integer> originalUsers, int code) {
        this.context = context;
        this.username = SaveSharedPreference.getUserName(context);
        this.friendname = friendname;
        this.position = position;
        this.originalUsers = originalUsers;
        if(code == ADD_FRIEND_CODE) {
            new AddFriend().execute();
        }
    }

    public Connections(Context context, String myName, String friendname, int code) {
        this.context= context;
        this.username = myName;
        this.friendname = friendname;
        if(code == Connections.ADD_FRIEND_CODE) {
            new AddFriend().execute();
        }
    }

    public Connections(Context context, String friendname, int code) {
        this.context = context;
        this.username = SaveSharedPreference.getUserName(context);
        this.friendname = friendname;
        if (code== ACCEPT_FRIEND_CODE) {
            new AcceptFriendRequest().execute();
        } else if(code == RELOAD_ALL_SNEEZES_CODE) {
            new ReloadFriendsSneezesList().execute();
        }

    }

    public Connections(Context context, String username, String password){
        this.context = context;
        this.username = username;
        this.password = password;
        new Login().execute();
    }

    public Connections(String prename, String name, String username, String password, int age ,int code, Context context) {
        if (code == REGISTER_CODE) {
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

    public Connections(Context context, String friendname, int position, ArrayList<User> myFriends , int code, int nrOfSneezesFriend) {
        myFriends = new ArrayList<User>();
        if (code== GO_TO_FRIENDS_PROFILE_CODE) {
            this.context = context;
            this.username = SaveSharedPreference.getUserName(context);
            this.friendname = friendname;
            this.position = position;
            this.myFriends = myFriends;
            this.nrOfSneezesFriend = nrOfSneezesFriend;

            new GoToFriendsProfile().execute();
        }
    }

    public Connections(Context context, String username) {
        this.context= context;
        this.username= username;
    }

    private class DeleteRegId extends AsyncTask<String, String, Boolean> {

        private String username;

        @Override
        protected Boolean doInBackground(String... params) {

            username = SaveSharedPreference.getUserName(context);
            List<NameValuePair> pars = new ArrayList<>();
            pars.add(new BasicNameValuePair("Loginnaam", username));


            JSONParser jsonParser = new JSONParser();


            JSONObject json = jsonParser.makeHttpRequest(URL_DELETE_REGID,
                    "POST", pars);

            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    SaveSharedPreference.setUserName(context, "");
                    SaveSharedPreference.setRegid(context, "");

                    Intent i = new Intent(context, FirstActivity.class);
                    context.startActivity(i);
                    iSneezeActivity iSneezeActivity= (iSneezeActivity) context;
                    iSneezeActivity.finish();
                } else {

                    return true;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return false;

        }
    }

    private class GoToFriendsProfile extends AsyncTask<String, String, Boolean> {

        private ArrayList<User> friendsOfFriend;
        private JSONArray friendsData;
        @Override
        protected Boolean doInBackground(String... params) {
            friendsOfFriend = new ArrayList<>();

            List<NameValuePair> pars = new ArrayList<>();

            pars.add(new BasicNameValuePair(TAG_LOGINNAME, friendname));


            JSONParser jsonParser = new JSONParser();
            JSONObject json = jsonParser.makeHttpRequest(URL_GET_FRIENDS, "POST", pars);

            try {
                friendsOfFriend = new ArrayList<>();

                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {

                    friendsData = json.getJSONArray(TAG_FRIENDS);


                    for (int i = 0; i < friendsData.length(); i++) {

                        String name = friendsData.getJSONObject(i).getString(TAG_LOGINNAME);
                        int aantalSneezes = friendsData.getJSONObject(i).getInt(TAG_AANTAL);

                        User friend = new User(name, aantalSneezes);
                        friendsOfFriend.add(friend);
                    }

                    Intent i = new Intent(context, ProfileActivity.class);
                    i.putExtra(TAG_FRIENDNAME, friendname);
                    i.putExtra(TAG_NR_OF_SNEEZES_FRIEND, nrOfSneezesFriend);
                    i.putExtra(TAG_FRIENDS, friendsOfFriend);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
                    //Activity activity = (Activity) context;
                    //activity.finish();
                    return null;

                } else {
                    return true;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

    }
    private class ReloadFriendsSneezesList extends AsyncTask<String, String, Boolean> {

        private ProgressDialog progress;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            /*progress = RandomShit.getProgressDialog(context);
            progress.sho6w();*/

        }

        protected Boolean doInBackground(String... args) {

            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair(TAG_LOGINNAME, username));

            TreeMap<Integer, Sneeze> sneezeHashMapDef;

            JSONParser jsonParser = new JSONParser();
            JSONObject json = jsonParser.makeHttpRequest(URL_ALL_FRIEND_SNEEZES,"POST", params);

            try {
                sneezeHashMapDef = new TreeMap<>();
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {

                    JSONArray sneezes = json.getJSONArray(TAG_SNEEZES);

                    for (int i = 0; i < sneezes.length(); i++) {
                        JSONObject c = sneezes.getJSONObject(i);


                        String time = c.getString(TAG_TIME);
                        String name = c.getString(TAG_LOGINNAME);

                        int totalSneezes = 0;

                        User user = new User(name, /*firstname, secondname, leeftijd, id, */totalSneezes);
                        Sneeze sneeze = new Sneeze(time, user/*, sneeze_id*/);

                        sneezeHashMapDef.put(i, sneeze);
                    }

                    SneezeListActivity sneezeListActivity = (SneezeListActivity) context;
                    sneezeListActivity.setSneezeMap(sneezeHashMapDef);
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(sneezeListActivity);

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
            //progress.dismiss();
            //pDialog.dismiss();
            if (b) Toast.makeText(context, "Laden sneezes mislukt.", Toast.LENGTH_LONG).show();


        }


    }

    private class OpenSneezesGraphAndGetFriends extends AsyncTask<String, String ,Boolean>{

        ProgressDialog progress;
        private ArrayList<User> friends;
        private JSONArray friendsData;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress= RandomShit.getProgressDialog(context);
            progress.show();
        }

        protected Boolean doInBackground(String... args) {

            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair(TAG_LOGINNAME, SaveSharedPreference.getUserName(context)));

            JSONParser jsonParser = new JSONParser();
            JSONObject json = jsonParser.makeHttpRequest(URL_GET_ALL_OWN_SNEEZES,"POST", params);

            try {
                ArrayList<Sneeze> sneezes = new ArrayList<>();
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // products found
                    // Getting Array of Products
                    JSONArray tijden = json.getJSONArray(TAG_SNEEZES);

                    // looping through All Products
                    for (int i = 0; i < tijden.length(); i++) {
                        // Storing each json item in variable
                        String time = tijden.getString(i);

                        Sneeze sneeze = new Sneeze(time, SaveSharedPreference.getUser(context));

                        sneezes.add(sneeze);
                    }

                    List<NameValuePair> pars = new ArrayList<>();

                    pars.add(new BasicNameValuePair(TAG_LOGINNAME, username));


                    JSONParser jsonParser2 = new JSONParser();
                    JSONObject json2 = jsonParser.makeHttpRequest(URL_GET_FRIENDS, "POST", pars);

                    try {
                        friends = new ArrayList<>();

                        int success2 = json2.getInt(TAG_SUCCESS);

                        if (success2 == 1) {

                            friendsData = json2.getJSONArray(TAG_FRIENDS);


                            for (int i = 0; i < friendsData.length(); i++) {

                                String name = friendsData.getJSONObject(i).getString(TAG_LOGINNAME);
                                int aantalSneezes = friendsData.getJSONObject(i).getInt(TAG_AANTAL);

                                User friend = new User(name, aantalSneezes);
                                friends.add(friend);
                            }

                            iSneezeActivity activity = (iSneezeActivity)context;
                            activity.sneezeList = sneezes;
                            System.out.println(sneezes);
                            activity.friendsList = friends;
                            System.out.println(activity.friendsList);
                            Handler handler = new Handler(Looper.getMainLooper());
                            handler.post(activity);


                        } else {


                            return true;

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    // successfully created product

                    // closing this screen


                    /*Intent ik = new Intent(context, SneezeOverviewActivity.class);
                    ik.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    ik.putExtra(TAG_SNEEZES, sneezes);

                    context.startActivity(ik);*/

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
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            progress.dismiss();
        }
    }

    private class OpenSneezesGraph extends AsyncTask<String, String ,Boolean>{

        ProgressDialog progress;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress= RandomShit.getProgressDialog(context);
            progress.show();
        }

        protected Boolean doInBackground(String... args) {

            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair(TAG_LOGINNAME, SaveSharedPreference.getUserName(context)));

            JSONParser jsonParser = new JSONParser();
            JSONObject json = jsonParser.makeHttpRequest(URL_GET_ALL_OWN_SNEEZES,"POST", params);

            try {
                ArrayList<Sneeze> sneezes = new ArrayList<>();
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // products found
                    // Getting Array of Products
                    JSONArray tijden = json.getJSONArray(TAG_SNEEZES);

                    // looping through All Products
                    for (int i = 0; i < tijden.length(); i++) {
                        // Storing each json item in variable
                        String time = tijden.getString(i);

                        Sneeze sneeze = new Sneeze(time, SaveSharedPreference.getUser(context));

                        sneezes.add(sneeze);
                    }

                    /*test activity = (test)context;
                    activity.sneezeList = sneezes;
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(activity);*/
                    // successfully created product

                    // closing this screen


                    Intent ik = new Intent(context, SneezeOverviewActivity.class);
                    ik.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    ik.putExtra(TAG_SNEEZES, sneezes);

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
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            progress.dismiss();
        }
    }


    private class Login extends AsyncTask<String, String, Boolean> {
        private ProgressDialog progress;
        private GCMRegister reg;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = RandomShit.getProgressDialog(context);
            progress.show();
        }

        protected Boolean doInBackground(String... args) {

            reg = new GCMRegister();
            boolean b = reg.execute();
            if(!b) return false;
            String regid = reg.regid;

            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("Loginnaam", username));
            params.add(new BasicNameValuePair("Password", password));
            params.add(new BasicNameValuePair("Regid", regid));

            JSONParser jsonParser = new JSONParser();


            JSONObject json = jsonParser.makeHttpRequest(URL_CHECK_LOGIN,
                    "POST", params);

            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    JSONObject jsonObject = json.getJSONObject(TAG_USER);
                    SaveSharedPreference.setUserName(context, username);
                    SaveSharedPreference.setFirstName(context, jsonObject.getString(TAG_VOORNAAM));
                    SaveSharedPreference.setName(context, jsonObject.getString(TAG_ACHTERNAAM));
                    SaveSharedPreference.setNrOfSneezes(context, jsonObject.getInt(TAG_NR_OF_SNEEZES));
                    //System.out.println(SaveSharedPreference.getNrOfSneezes(context));
                    Intent i = new Intent(context, iSneezeActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.putExtra(NAAM_VAR_USER, username);
                    context.startActivity(i);
                    LoginActivity loginActivity = (LoginActivity) context;
                    loginActivity.finish();

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
            progress.dismiss();
            if (b) Toast.makeText(context, "Login mislukt", Toast.LENGTH_LONG).show();
            //handler.post(progressDialogClose);
        }


    }
    private class CreateNewUser extends AsyncTask<String, String, String> {
        private boolean b;
        private ProgressDialog progress;
        private GCMRegister reg;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.e("plooooooooooooooooons", "plons");
            progress = RandomShit.getProgressDialog(context);
                progress.show();

        }

        protected String doInBackground(String... args) {

            reg = new GCMRegister();
            b = reg.execute();
            if(!b) return null;
            String regid = reg.regid;

            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("Voornaam", prename));
            params.add(new BasicNameValuePair("Achternaam", name));
            params.add(new BasicNameValuePair("Loginnaam", username));
            params.add(new BasicNameValuePair("Password", password));
            params.add(new BasicNameValuePair("Leeftijd", age+""));
            params.add(new BasicNameValuePair("Regid", regid));
            System.out.println(regid);
            JSONParser jsonParser = new JSONParser();


            JSONObject json = jsonParser.makeHttpRequest(URL_CREATE_USER,
                    "POST", params);

            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    SaveSharedPreference.setNrOfSneezes(context, 0);
                    SaveSharedPreference.setUserName(context, username);
                    SaveSharedPreference.setName(context, name);
                    SaveSharedPreference.setFirstName(context, prename);
                    Intent i = new Intent(context, iSneezeActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.putExtra(NAAM_VAR_USER, username);
                    context.startActivity(i);

                    RegisterActivity registerActivity = (RegisterActivity) context;
                    registerActivity.finish();

                } else {

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(String file_url) {
            progress.dismiss();
        }

    }

    private class CreateSneeze extends AsyncTask<String, String, String>{

        ProgressDialog progress;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //progress = RandomShit.getProgressDialog(context);
            //progress.show();

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
                    SaveSharedPreference.addSneeze(context);
                    iSneezeActivity main = (iSneezeActivity) context;
                    main.runOnUiThread(new Runnable() {
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
            //progress.dismiss();
        }
    }

    private class GetAllSneezes extends AsyncTask<String, String, Boolean> {

        ProgressDialog progress;

        JSONArray sneezes = new JSONArray();
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = RandomShit.getProgressDialog(context);
            progress.show();
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
            progress.dismiss();

        }
    }

    private class GetAllUsers extends AsyncTask<String, String, Boolean> {


        private ArrayList<String> users;
        ProgressDialog progress;

        //JSONArray sneezes = new JSONArray();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = RandomShit.getProgressDialog(context);
            progress.show();
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
                context.startActivity(ik);
                progress.dismiss();
            }

        }

    }

    private class GetOneUser extends AsyncTask<String, String, Boolean> {


        ProgressDialog progress;
        private User gebruiker;

        //JSONArray sneezes = new JSONArray();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = RandomShit.getProgressDialog(context);
            progress.show();
        }

        protected Boolean doInBackground(String... args) {


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
                    Intent i = new Intent(context, EditProfileActivity.class);
                    i.putExtra(TAG_USER, gebruiker);
                    i.putExtra(CameraActivity.TAG_URI, "");
                    context.startActivity(i);
                    return true;
                } else {


                    return true;

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return false;

        }

        protected void onPostExecute(Boolean b) {
            super.onPostExecute(b);
            progress.dismiss();

        }

    }

    private class AddFriend extends AsyncTask<String, String, Boolean> {

        ProgressDialog progress;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = RandomShit.getProgressDialog(context);
            progress.show();

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
                progress.dismiss();
            }
        }
    }

    private class GetPendingFriends extends AsyncTask<String, String, Boolean> {

        ArrayList<String> pendingFriends;
        ProgressDialog progress;
        private final Handler handler = new Handler(Looper.getMainLooper());


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress= RandomShit.getProgressDialog(context);
            progress.show();
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            progress.dismiss();
        }

        @Override
        protected Boolean doInBackground(String... args) {


            List<NameValuePair> params = new ArrayList<>();

            params.add(new BasicNameValuePair(TAG_LOGINNAME, username));

            JSONParser jsonParser = new JSONParser();

            JSONObject json = jsonParser.makeHttpRequest(URL_GET_PENDING_FRIENDS,"POST", params);

            try {
                pendingFriends = new ArrayList<String>();
                int success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    JSONArray userkes = json.getJSONArray(TAG_PENDING_FRIENDS);

                    for (int i = 0; i < userkes.length(); i++) {
                        String k = userkes.getString(i);
                        pendingFriends.add(k);
                        Log.e("VRIEND", k);
                    }
                    ((FriendRequestsActivity)context).pendingFriends = pendingFriends;
                    handler.post((FriendRequestsActivity) context);

                    return false;

                } else {
                    ((FriendRequestsActivity)context).pendingFriends = pendingFriends;
                    handler.post((FriendRequestsActivity)context);
                    return true;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return true;


        }
    }

    private class AcceptFriendRequest extends AsyncTask<String, String, Boolean> {

        ProgressDialog progress;

        @Override
        protected Boolean doInBackground(String... args) {


            List<NameValuePair> params = new ArrayList<>();

            params.add(new BasicNameValuePair(TAG_LOGINNAME, username));
            params.add(new BasicNameValuePair(TAG_FRIENDNAME, friendname));

            JSONParser jsonParser = new JSONParser();

            JSONObject json = jsonParser.makeHttpRequest(URL_ACCEPT_FRIEND_REQUEST, "POST", params);

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
        protected void onPreExecute() {
            super.onPreExecute();
            progress = RandomShit.getProgressDialog(context);
            progress.show();
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(aBoolean) {
                ((FriendRequestsActivity) context).adapter.changePendingFriends(friendname);
                progress.dismiss();
            }
        }
    }

    private class getFriends extends AsyncTask<String, String, Boolean> {

        private ProgressDialog progress;
        private ArrayList<User> friends;
        private JSONArray friendsData;


        @Override
        protected Boolean doInBackground(String... params) {
            List<NameValuePair> pars = new ArrayList<>();
            BigClass bigClass = BigClass.ReadData(context);
            if(bigClass==null){
                bigClass = new BigClass();
            }

            pars.add(new BasicNameValuePair(TAG_LOGINNAME, username));

            for(User u : bigClass.friends.values()){
                pars.add(new BasicNameValuePair(TAG_ARRAY_FRIENDS, u.getUsername()));
            }
            for(User u : bigClass.friends.values()){
                pars.add(new BasicNameValuePair(TAG_ARRAY_SNEEZES, ""+u.getNumberOfSneezes()));
            }

            JSONParser jsonParser = new JSONParser();
            JSONObject json = jsonParser.makeHttpRequest(URL_GET_FRIENDS_NO_EXTRA_DATA, "POST", pars);

            try {
                friends = new ArrayList<>();

                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {

                    friendsData = json.getJSONArray(TAG_FRIENDS);


                    for (int i = 0; i < friendsData.length(); i++) {

                        String name = friendsData.getJSONObject(i).getString(TAG_LOGINNAME);
                        int aantalSneezes = friendsData.getJSONObject(i).getInt(TAG_AANTAL);

                        User friend = new User(name, aantalSneezes);
                        friends.add(friend);
                    }

                    bigClass.compareFriends(friends);
                    bigClass.WriteData(context);

                    /*Intent ik = new Intent(context, MyFriendsActivity.class);
                    ik.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    ik.putExtra(NAAM_VAR_USER, username);
                    ik.putExtra(TAG_FRIENDS, friends);*

                    context.startActivity(ik);*/
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post((MyFriendsActivity)context);

                } else {


                    return true;

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = RandomShit.getProgressDialog(context);
//            progress.show();
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            progress.dismiss();
        }
    }

    public TreeMap<Integer, Sneeze> loadAllFriendSneezes() {
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair(TAG_LOGINNAME, username));

        TreeMap<Integer, Sneeze> sneezeHashMapDef = new TreeMap<>();

        JSONParser jsonParser = new JSONParser();
        JSONObject json = jsonParser.makeHttpRequest(URL_ALL_FRIEND_SNEEZES,"POST", params);

        try {
            sneezeHashMapDef = new TreeMap<>();
            int success = json.getInt(TAG_SUCCESS);

            if (success == 1) {

                JSONArray sneezes = json.getJSONArray(TAG_SNEEZES);

                for (int i = 0; i < sneezes.length(); i++) {
                    JSONObject c = sneezes.getJSONObject(i);


                    String time = c.getString(TAG_TIME);
                    String name = c.getString(TAG_LOGINNAME);

                    int totalSneezes = 0;

                    User user = new User(name, /*firstname, secondname, leeftijd, id, */totalSneezes);
                    Sneeze sneeze = new Sneeze(time, user/*, sneeze_id*/);

                    sneezeHashMapDef.put(i, sneeze);
                }


            } else {


                return sneezeHashMapDef;

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return sneezeHashMapDef;
    }

    private class GetAllFriendSneezes extends AsyncTask<String, String, Boolean> {
        private ProgressDialog progress;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = RandomShit.getProgressDialog(context);
  //          progress.show();

        }

        protected Boolean doInBackground(String... args) {

            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair(TAG_LOGINNAME, username));

            TreeMap<Integer, Sneeze> sneezeHashMapDef;

            JSONParser jsonParser = new JSONParser();
            JSONObject json = jsonParser.makeHttpRequest(URL_ALL_FRIEND_SNEEZES,"POST", params);

            try {
                sneezeHashMapDef = new TreeMap<>();
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {

                    JSONArray sneezes = json.getJSONArray(TAG_SNEEZES);

                    for (int i = 0; i < sneezes.length(); i++) {
                        JSONObject c = sneezes.getJSONObject(i);


                        String time = c.getString(TAG_TIME);
                        String name = c.getString(TAG_LOGINNAME);

                        int totalSneezes = 0;

                        User user = new User(name, /*firstname, secondname, leeftijd, id, */totalSneezes);
                        Sneeze sneeze = new Sneeze(time, user/*, sneeze_id*/);

                        sneezeHashMapDef.put(i, sneeze);
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
            progress.dismiss();
            //pDialog.dismiss();
            if (b) Toast.makeText(context, "Laden sneezes mislukt.", Toast.LENGTH_LONG).show();


        }
    }

    public class GCMRegister implements Runnable {
        private GoogleCloudMessaging gcm;
        public String regid;
        private String SENDER_ID = "952325312721";
        public static final String PROPERTY_REG_ID = "registration_id";
        private static final String PROPERTY_APP_VERSION = "appVersion";
        private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

        public GCMRegister(){
            gcm = GoogleCloudMessaging.getInstance(context);
        }

        public boolean execute(){
            if (checkPlayServices()) {

                regid = getRegistrationId(context.getApplicationContext());
                System.out.println(regid);
                if (regid.isEmpty()) {
                    Thread thread = new Thread(this);
                    thread.start();
                    System.out.println("dmsqlkfj 0000000000000000000000000 ");
                    try {
                        thread.join(60000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if (!regid.isEmpty()) return true;
                    /*Long tijd = SystemClock.uptimeMillis()+60000;
                    while (rib.getStatus()==AsyncTask.Status.RUNNING){
                        if(tijd == SystemClock.uptimeMillis()) return false;
                    }*/

                    Log.i("registerinback", "begonnen");
                }
                else return true;
            } else {
                Log.i("GPS APK", "No valid Google Play Services APK found.");
            }
            return false;
        }


        @Override
        public void run() {
                try {
                    Log.i("reg", "echt begonnen");
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(context.getApplicationContext());
                    }
                    regid = gcm.register(SENDER_ID);
                    System.out.println(regid);

                    // You should send the registration ID to your server over HTTP, so it
                    // can use GCM/HTTP or CCS to send messages to your app.
                    //sendRegistrationIdToBackend();

                    // For this demo: we don't need to send it because the device will send
                    // upstream messages to a server that echo back the message using the
                    // 'from' address in the message.

                    // Persist the regID - no need to register again.
                    storeRegistrationId(context.getApplicationContext(), regid);
                } catch (IOException ex) {
                    System.out.println("Error :" + ex.getMessage());
                    // If there is an error, don't just keep trying to register.
                    // Require the user to click a button again, or perform
                    // exponential back-off.
                }
        }


        /**
         * Stores the registration ID and the app versionCode in the application's
         * {@code SharedPreferences}.
         *
         * @param context application's context.
         * @param regId registration ID
         */
        private void storeRegistrationId(Context context, String regId) {
            /*final SharedPreferences prefs = getGcmPreferences(context);
            int appVersion = getAppVersion(context);
            Log.i("storeRegistrationId", "Saving regId on app version " + appVersion);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(PROPERTY_REG_ID, regId);
            editor.putInt(PROPERTY_APP_VERSION, appVersion);
            editor.commit();*/
            SaveSharedPreference.setRegid(context, regId);
        }

        /**
         * @return Application's version code from the {@code PackageManager}.
         */
        private  int getAppVersion(Context context) {
            try {
                PackageInfo packageInfo = context.getPackageManager()
                        .getPackageInfo(context.getPackageName(), 0);
                return packageInfo.versionCode;
            } catch (PackageManager.NameNotFoundException e) {
                // should never happen
                throw new RuntimeException("Could not get package name: " + e);
            }
        }

        /**
         * @return Application's {@code SharedPreferences}.
         */
        private SharedPreferences getGcmPreferences(Context context) {
            // This sample app persists the registration ID in shared preferences, but
            // how you store the regID in your app is up to you.
            return context.getSharedPreferences(RegisterActivity.class.getSimpleName(),
                    Context.MODE_PRIVATE);
        }

        /**
         * Gets the current registration ID for application on GCM service, if there is one.
         * <p>
         * If result is empty, the app needs to register.
         *
         * @return registration ID, or empty string if there is no existing
         *         registration ID.
         */


        private String getRegistrationId(Context context) {
            /*final SharedPreferences prefs = getGcmPreferences(context);
            String registrationId = prefs.getString(PROPERTY_REG_ID, "");
            if (registrationId.isEmpty()) {
                Log.i("getRegistrationId", "Registration not found.");
                return "";
            }*/
            // Check if app was updated; if so, it must clear the registration ID
            // since the existing regID is not guaranteed to work with the new
            // app version.
        /*int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            Log.i("getRegistrationId", "App version changed.");
            return "";
        }*/

            return SaveSharedPreference.getRegid(context);
        }


        /**
         * Registers the application with GCM servers asynchronously.
         * <p>
         * Stores the registration ID and the app versionCode in the application's
         * shared preferences.
         */

        /**
         * Substitute you own sender ID here. This is the project number you got
         * from the API Console, as described in "Getting Started."
         */





        private boolean checkPlayServices() {
            int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
            if (resultCode != ConnectionResult.SUCCESS) {
                if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                    GooglePlayServicesUtil.getErrorDialog(resultCode, (RegisterActivity)context,
                            PLAY_SERVICES_RESOLUTION_REQUEST).show();
                } else {
                    Log.i("checkPlayServices", "This device is not supported.");
                    ((RegisterActivity)context).finish();
                }
                return false;
            }
            return true;
        }

    }

}
