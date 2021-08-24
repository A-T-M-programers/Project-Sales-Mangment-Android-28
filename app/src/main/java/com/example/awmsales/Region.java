package com.example.awmsales;

import static com.example.awmsales.static_class.GET_ALL;
import static com.example.awmsales.static_class.ITEM_TYPE;
import static com.example.awmsales.static_class.PERSONS;
import static com.example.awmsales.static_class.REGIONS;
import static com.example.awmsales.static_class.REQUEST_TYPE;

import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Region implements Serializable {
    private int reg_id;
    private String name;
    private int select;

    public Region(int reg_id, String name,int select) {
        this.reg_id = reg_id;
        this.name = name;
        this.select = select;
    }

    public Region(){}

    public int getSelect() {
        return select;
    }

    public void setSelect(int select) {
        this.select = select;
    }

    public int getReg_id() {
        return reg_id;
    }

    public void setReg_id(int reg_id) {
        this.reg_id = reg_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static ArrayList<Region> getarryregion(){
        ArrayList<Region> Region = new ArrayList<>();

        String result = "";
        try {
            result = new GetInBackground().execute(
                    REQUEST_TYPE,GET_ALL,
                    ITEM_TYPE,REGIONS
            ).get();

            JSONArray jsonArray = new JSONArray(result);

            for(int x=0; x<jsonArray.length(); x++){
                JSONObject jsonObject = new JSONObject(String.valueOf(jsonArray.getJSONObject(x)));
                int s = Integer.parseInt(jsonObject.get("region_id").toString());
                Region.add(new Region(s,jsonObject.get("name").toString(),x));
            }


        } catch (ExecutionException | InterruptedException | JSONException e) {
            String msg = e.getMessage();
            Log.e("ERROR",msg);
        }

        return Region;
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
