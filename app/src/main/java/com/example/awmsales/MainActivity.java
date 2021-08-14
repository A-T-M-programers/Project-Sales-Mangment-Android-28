package com.example.awmsales;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLEncoder;

import static com.example.awmsales.static_class.ACCOUNT_TYPE;
import static com.example.awmsales.static_class.AUTHEN;
import static com.example.awmsales.static_class.EMAIL;
import static com.example.awmsales.static_class.LOGIN_PERSON;
import static com.example.awmsales.static_class.NAME;
import static com.example.awmsales.static_class.PASSWORD;
import static com.example.awmsales.static_class.REGID;
import static com.example.awmsales.static_class.REQUEST_TYPE;

public class MainActivity extends AppCompatActivity {


    Button btn;
    EditText emailTXT, passTXT;
    TextView tv,link_contact;
    Handler handler = new Handler();
    ProgressBar progressBar ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (Button) findViewById(R.id.btn_login);
        emailTXT = (EditText) findViewById(R.id.input_email);
        passTXT = (EditText) findViewById(R.id.input_password);
        progressBar = findViewById(R.id.progressBar);
        tv = (TextView) findViewById(R.id.tv);
        link_contact = (TextView) findViewById(R.id.link_contact);



        link_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setIcon(R.drawable.ic_power_settings_new_black_24dp);
                builder.setTitle(R.string.about_us);
                builder.setMessage(R.string.about_us_messege);
                builder.setCancelable(true);

                AlertDialog al = builder.create();
                al.show();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                                login();
                            }
                        });
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.setVisibility(View.INVISIBLE);
                            }
                        });
                    }
                }).start();
            }
        });
    }

    private void login() {
        if (!checkConnect()) {
            tv.setText(R.string.noInternt);
            return;
        }
        try {
            String email = emailTXT.getText().toString();
            String password = passTXT.getText().toString();

            //***********
            //VALIDATION
            //***********
            if (email.isEmpty() || !email.contains("@") || !email.contains(".")) {
                tv.setText(R.string.checkYourEmail);
                return;
            }
            if (password.isEmpty() || password.length() < 6) {
                tv.setText(R.string.checkYourPassword);
                return;
            }

            String StringJson = new PostInBackground().execute(
                    REQUEST_TYPE, AUTHEN,
                    EMAIL, email,
                    PASSWORD, password).get();

            if (StringJson.equals("[]null")) {
                tv.setText(R.string.errorEmail);
                return;

            } else {

                //read data as json array
                JSONArray jsonArray = new JSONArray(StringJson);

                //extract all json objects but here we have one json object so we use index 0
                JSONObject jsonObject = new JSONObject(String.valueOf(jsonArray.getJSONObject(0)));

                //create object of Person class and put the information from json object
                //using getPersonFromJson method
                Person loginPerson = Person.getPersonFromJson(jsonObject);


                Intent intentAdmin = new Intent(MainActivity.this, admin_menu.class);

                intentAdmin.putExtra(LOGIN_PERSON, loginPerson);

                startActivity(intentAdmin);

                finish();
            }
        } catch (Exception ex) {
            tv.setText(ex.getMessage());
        }
    }

    public boolean checkConnect() {
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED || connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        } else
            connected = false;

        return connected;
    }


}
