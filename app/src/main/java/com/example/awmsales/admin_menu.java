package com.example.awmsales;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.awmsales.static_class.ACCOUNT_TYPE;
import static com.example.awmsales.static_class.IS_ADMIN;
import static com.example.awmsales.static_class.LOGIN_PERSON;
import static com.example.awmsales.static_class.NAME;
import static com.example.awmsales.static_class.REGID;

public class admin_menu extends AppCompatActivity {

    TextView mainTXT;
    Button BTN_Log_out,BTN_Show_Reports,BTN_Add_Sales_Details,BTN_Show_Sales_Persons;

    private View.OnClickListener mainOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.BTN_Log_out){
                Intent intent = new Intent(admin_menu.this,MainActivity.class);
                startActivity(intent);
                finish();
            }else if(v.getId() == R.id.BTN_Show_Reports){
                Intent intent = new Intent(admin_menu.this,Reports.class);
                startActivity(intent);
            }else if(v.getId() == R.id.BTN_Add_Sales_Details){

                Intent intent = new Intent(admin_menu.this,addSalesDetails.class);
                Intent login_intent = getIntent();
                Person loginPerson = (Person) login_intent.getSerializableExtra(LOGIN_PERSON);
                intent.putExtra(LOGIN_PERSON, loginPerson);
                startActivity(intent);

            }else if(v.getId() == R.id.BTN_Show_Sales_Persons) {

                Intent intent = new Intent(admin_menu.this,SalesPersons.class);
                Intent login_intent = getIntent();
                Person loginPerson = (Person) login_intent.getSerializableExtra(LOGIN_PERSON);
                intent.putExtra(LOGIN_PERSON, loginPerson);
                startActivity(intent);
            }
    }};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu);

        BTN_Log_out = (Button) findViewById(R.id.BTN_Log_out);
        BTN_Show_Reports = (Button) findViewById(R.id.BTN_Show_Reports);
        BTN_Add_Sales_Details = (Button) findViewById(R.id.BTN_Add_Sales_Details);
        BTN_Show_Sales_Persons = (Button) findViewById(R.id.BTN_Show_Sales_Persons);

        BTN_Log_out.setOnClickListener(mainOnClickListener);
        BTN_Show_Reports.setOnClickListener(mainOnClickListener);
        BTN_Add_Sales_Details.setOnClickListener(mainOnClickListener);
        BTN_Show_Sales_Persons.setOnClickListener(mainOnClickListener);

        Intent login_intent = getIntent();
        Person loginPerson = (Person) login_intent.getSerializableExtra(LOGIN_PERSON);

        if(!loginPerson.isType()){
            BTN_Show_Reports.setVisibility(View.INVISIBLE);
        }

        mainTXT = (TextView) findViewById(R.id.mainText);

        mainTXT.setText( getString(R.string.Welcome) + loginPerson.getName());

    }
}
