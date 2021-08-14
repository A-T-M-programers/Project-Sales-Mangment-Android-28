package com.example.awmsales;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static com.example.awmsales.static_class.ACCOUNT_TYPE;
import static com.example.awmsales.static_class.ADD_PERSON_ALERT;
import static com.example.awmsales.static_class.GET_ALL;
import static com.example.awmsales.static_class.IS_ADMIN;
import static com.example.awmsales.static_class.ITEM_TYPE;
import static com.example.awmsales.static_class.LOGIN_PERSON;
import static com.example.awmsales.static_class.PERSONS;
import static com.example.awmsales.static_class.REQUEST_TYPE;

public class SalesPersons extends AppCompatActivity {

    
    PersonAdapterList adapter;
    Person loginPerson ;
    ProgressBar progressBar;
    Handler handler= new Handler();


    protected void RefreshList (){
    ListView list_persons = findViewById(R.id.list_persons);
    ArrayList<Person> list_person = new ArrayList<>();

    String result = "";
    try {
        result = new GetInBackground().execute(
                REQUEST_TYPE,GET_ALL,
                ITEM_TYPE,PERSONS
        ).get();

        JSONArray jsonArray = new JSONArray(result);

        for(int x=0; x<jsonArray.length(); x++){
            JSONObject jsonObject = new JSONObject(String.valueOf(jsonArray.getJSONObject(x)));
            Person person = Person.getPersonFromJson(jsonObject);
            list_person.add(person);
        }

        adapter = new PersonAdapterList(this,R.layout.list_persons,list_person,loginPerson.isType());

        list_persons.setAdapter(adapter);


    } catch (ExecutionException e) {
        String msg = e.getMessage();
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    } catch (InterruptedException e) {
        String msg = e.getMessage();
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    } catch (JSONException e) {
        String msg = e.getMessage();
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }
}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_persons);

        Intent login_intent = getIntent();
        loginPerson = (Person) login_intent.getSerializableExtra(LOGIN_PERSON);
        progressBar = findViewById(R.id.progressBar);

        progressBar.setVisibility(View.VISIBLE);
        refreshListTherad();

    }

    private void refreshListTherad() {
        progressBar.setVisibility(View.VISIBLE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setIndeterminate(true);
                    }
                });
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        RefreshList();
                    }
                });
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                    }
                });
            }
        }).start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (loginPerson.isType())
        getMenuInflater().inflate(R.menu.mymenu, menu);
        else{        return super.onCreateOptionsMenu(menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();
            if (id == R.id.bu_add_person) {
                final ALertBuilder alertBuilder = new ALertBuilder(ADD_PERSON_ALERT, SalesPersons.this);
                final android.app.AlertDialog.Builder builder = alertBuilder.MyBuilder();

                AlertDialog alert1 = builder.create();
                alert1.show();
                alert1.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        if (alertBuilder.getResult().isEmpty()) return;
                        Toast.makeText(SalesPersons.this, alertBuilder.getResult(), Toast.LENGTH_LONG).show();
                        RefreshList();
                    }
                });
            }
        if (id == R.id.bu_refresh) {
            progressBar.setVisibility(View.VISIBLE);
            refreshListTherad();
        }
        return super.onOptionsItemSelected(item);
    }
}
