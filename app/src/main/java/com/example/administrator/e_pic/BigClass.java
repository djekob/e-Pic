package com.example.administrator.e_pic;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 8/03/2015.
 */
public class BigClass implements Serializable {

    public HashMap<String, User> friends;
    public ArrayList<Sneeze> ownSneezes;

    public BigClass() {
        friends = new HashMap<>();
        ownSneezes = new ArrayList<>();
    }

    public boolean WriteData(Context context) {
        File f = new File(context.getFilesDir() +"/" + SaveSharedPreference.getUserName(context));
        try{
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(f));
            out.writeObject(this);
            out.close();

        }
        catch(FileNotFoundException fd){
            fd.printStackTrace();
            return false;
        } catch(IOException i) {
            i.printStackTrace();
            return false;
        }
    return true;
    }

    public static BigClass ReadData(Context context ) {
        File f = new File(context.getFilesDir() + "/" + SaveSharedPreference.getUserName(context));
        if(f.exists()) {
            try {
                ObjectInputStream in = new ObjectInputStream(new FileInputStream(f));
                BigClass b = (BigClass) in.readObject();
                in.close();
                return b;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public ArrayList<User> getFriendsArrayList(){
        ArrayList<User> vrienden = new ArrayList<>();
        for(User u : friends.values()){
            vrienden.add(u);
        }
        return vrienden;
    }

    public void compareFriends(ArrayList<User> vrienden){
        for(User u : vrienden){
            if(friends.containsKey(u.getUsername())){
                if(friends.get(u.getUsername()).getNumberOfSneezes() != u.getNumberOfSneezes()){
                    friends.remove(u.getUsername());
                    friends.put(u.getUsername(), new User(u.getUsername(), u.getNumberOfSneezes()));
                }
            }
            else{
                friends.put(u.getUsername(), new User(u.getUsername(), u.getNumberOfSneezes()));
            }
        }
    }

    public void setOwnSneezes(Context ctx, ArrayList<String> sneezes){
        ownSneezes.clear();
        for(String s : sneezes){
            ownSneezes.add(new Sneeze(s));
        }

    }
}
