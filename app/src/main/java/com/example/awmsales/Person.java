package com.example.awmsales;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.text.Editable;
import android.widget.ImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Externalizable;
import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;

public class Person implements Serializable {

    private int per_id;
    private String name;
    private String email;
    private String password;
    private String mobile;
    private String address;
    private String user_status;
    private boolean type;
    private String person_type;
    private Region region;

    public Person(int per_id, String name, String email, String password, String mobile, String address, String user_status, boolean type, Region region) {
        this.per_id = per_id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.mobile = mobile;
        this.address = address;
        this.user_status = user_status;
        this.type = type;
        this.region = region;
    }

    public Person(){}

    public String getPerson_type() {
        return person_type;
    }

    public void setPerson_type(String person_type) {
        this.person_type = person_type;
    }

    public int getPer_id() {
        return per_id;
    }

    public void setPer_id(int per_id) {
        this.per_id = per_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUser_status() {
        return user_status;
    }

    public void setUser_status(String user_status) {
        this.user_status = user_status;
    }

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }


    public Region getRegion_name() {
        return region;
    }

    public void setRegion_name(Region region) {
        this.region = region;
    }


    public static Person getPersonFromJson(JSONObject jsonObject) throws JSONException {

        Person person = new Person();

        Region region = new Region();

        region.setReg_id(Integer.parseInt(jsonObject.get("reg_id").toString()));

        region.setName(jsonObject.get("region_name").toString());

        person.setPer_id(Integer.parseInt(jsonObject.get("per_id").toString()));

        person.setName(jsonObject.get("name").toString());

        person.setEmail(jsonObject.get("email").toString());

        person.setPassword(jsonObject.get("password").toString());

        person.setMobile(jsonObject.get("mobile").toString());

        person.setAddress(jsonObject.get("address").toString());

        person.setUser_status(jsonObject.get("user_status").toString());

        person.setType(Integer.parseInt(jsonObject.get("type").toString()) == 1);

        person.setPerson_type(jsonObject.get("type").toString());

        person.setRegion_name(region);

        return  person;
    }

}
