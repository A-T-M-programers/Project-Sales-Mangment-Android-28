package com.example.awmsales;

import static com.example.awmsales.static_class.CATEGORY;
import static com.example.awmsales.static_class.GET_ALL;
import static com.example.awmsales.static_class.ID;
import static com.example.awmsales.static_class.ITEM_TYPE;
import static com.example.awmsales.static_class.REQUEST_TYPE;
import static com.example.awmsales.static_class.USERS;

import android.support.annotation.NonNull;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Category {
    String cat_id,name;
    int count;

    public Category(){

    }
    public Category(String cat_id,String name,Integer count ){
        this.cat_id = cat_id;
        this.name = name;
        this.count = count;
    }

    public String getCat_id() {
        return cat_id;
    }

    public int getCount() {
        return count;
    }

    public String getName() {
        return name;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static ArrayList<Category> getarrycategory(){
        ArrayList<Category> Cat = new ArrayList<>();

        String result = "";
        try {
            result = new GetInBackground().execute(
                    REQUEST_TYPE,GET_ALL,
                    ITEM_TYPE,CATEGORY
            ).get();

            JSONArray jsonArray = new JSONArray(result);

            Cat.add(new Category("","None",0));

            for(int x=1; x<=jsonArray.length(); x++){
                JSONObject jsonObject = new JSONObject(String.valueOf(jsonArray.getJSONObject(x-1)));
                Cat.add(new Category(jsonObject.get("cat_id").toString(),jsonObject.get("name").toString(),x));
            }


        } catch (ExecutionException | InterruptedException | JSONException e) {
            String msg = e.getMessage();
            Log.e("ERROR",msg);
        }

        return Cat;
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
