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

    public HashMap<Integer, User> friends;
    public HashMap<Integer,Sneeze> sneezes;

    public BigClass() {
        friends = new HashMap<>();
        sneezes = new HashMap<>();
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




}
