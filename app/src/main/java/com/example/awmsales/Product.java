package com.example.awmsales;

import static com.example.awmsales.static_class.CATEGORY;
import static com.example.awmsales.static_class.GET_ALL;
import static com.example.awmsales.static_class.ID;
import static com.example.awmsales.static_class.ITEM_TYPE;
import static com.example.awmsales.static_class.PRODUCT;
import static com.example.awmsales.static_class.REQUEST_TYPE;

import android.support.annotation.NonNull;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Product {
    String product_id,name,available_qty,price,cat_id;
    int count;
    public Product(){
        this.product_id = "";
        this.name = "";
        this.available_qty = "";
        this.price = "";
        this.cat_id = "";
        this.count = 0;
    }
    public Product(String product_id,String name,String available_qty,String price,String cat_id,Integer count){
        this.product_id = product_id;
        this.name = name;
        this.available_qty = available_qty;
        this.price = price;
        this.cat_id = cat_id;
        this.count = count;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getName() {
        return name;
    }

    public String getCat_id() {
        return cat_id;
    }

    public int getCount() {
        return count;
    }

    public String getAvailable_qty() {
        return available_qty;
    }

    public String getPrice() {
        return price;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setAvailable_qty(String available_qty) {
        this.available_qty = available_qty;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }
    public static ArrayList<Product> getarryproduct(String id){
        ArrayList<Product> Pro = new ArrayList<>();

        String result = "";
        try {
            result = new GetInBackground().execute(
                    REQUEST_TYPE,GET_ALL,
                    ITEM_TYPE,PRODUCT,
                    ID,id
            ).get();

            JSONArray jsonArray = new JSONArray(result);

            Pro.add(new Product("","None","","","",0));

            for(int x=1; x<=jsonArray.length(); x++){
                JSONObject jsonObject = new JSONObject(String.valueOf(jsonArray.getJSONObject(x-1)));
                Pro.add(new Product(jsonObject.get("product_id").toString(),jsonObject.get("name").toString(),jsonObject.get("available_qty").toString(),jsonObject.get("price").toString(),jsonObject.get("cat_id").toString(),x));
            }


        } catch (ExecutionException | InterruptedException | JSONException e) {
            String msg = e.getMessage();
            Log.e("ERROR",msg);
        }

        return Pro;
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
