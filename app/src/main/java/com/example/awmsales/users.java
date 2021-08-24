package com.example.awmsales;

import static com.example.awmsales.static_class.GET_ALL;
import static com.example.awmsales.static_class.ITEM_TYPE;
import static com.example.awmsales.static_class.REGIONS;
import static com.example.awmsales.static_class.REQUEST_TYPE;
import static com.example.awmsales.static_class.USERS;

import android.support.annotation.NonNull;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class users {
    String user_id,email,password,person_id,user_status;
    int count = 0;

    public users(){
        this.email = "";
        this.password = "";
        this.user_id = "";
        this.person_id = "";
        this.user_status = "";
    }
    public users(String user_id,String email,String password,String person_id,String user_status,Integer count){
        this.user_id = user_id;
        this.email = email;
        this.password = password;
        this.person_id = person_id;
        this.user_status = user_status;
        this.count = count;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setUser_status(String user_status) {
        this.user_status = user_status;
    }

    public String getUser_status() {
        return user_status;
    }

    public String getPerson_id() {
        return person_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPerson_id(String person_id) {
        this.person_id = person_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public static ArrayList<users> getarryusers(){
        ArrayList<users> user = new ArrayList<>();

        String result = "";
        try {
            result = new GetInBackground().execute(
                    REQUEST_TYPE,GET_ALL,
                    ITEM_TYPE,USERS
            ).get();

            JSONArray jsonArray = new JSONArray(result);

            user.add(new users("None","None","","","",0));

            for(int x=1; x<=jsonArray.length(); x++){
                JSONObject jsonObject = new JSONObject(String.valueOf(jsonArray.getJSONObject(x-1)));
                user.add(new users(jsonObject.get("user_id").toString(),jsonObject.get("email").toString(),jsonObject.get("password").toString(),jsonObject.get("per_id").toString(),jsonObject.get("user_status").toString(),x));
            }


        } catch (ExecutionException | InterruptedException | JSONException e) {
            String msg = e.getMessage();
            Log.e("ERROR",msg);
        }

        return user;
    }

    @NonNull
    @Override
    public String toString() {
        return email;
    }

}
