package com.example.awmsales;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import static com.example.awmsales.static_class.ADDRESS;
import static com.example.awmsales.static_class.ADD_DETAILS;
import static com.example.awmsales.static_class.ADD_PERSON;
import static com.example.awmsales.static_class.AMOUNT;
import static com.example.awmsales.static_class.COMMISSION;
import static com.example.awmsales.static_class.EMAIL;
import static com.example.awmsales.static_class.IMAGE;
import static com.example.awmsales.static_class.LOGIN_PERSON;
import static com.example.awmsales.static_class.MOBILE;
import static com.example.awmsales.static_class.NAME;
import static com.example.awmsales.static_class.PASSWORD;
import static com.example.awmsales.static_class.PERID;
import static com.example.awmsales.static_class.REGID;
import static com.example.awmsales.static_class.REGISTER_DATE;
import static com.example.awmsales.static_class.REQUEST_TYPE;
import static com.example.awmsales.static_class.TYPE;

public class addSalesDetails extends AppCompatActivity {

    Intent intent ;
    Person loginPerson ;

    ImageView person_image;
    TextView person_name;

    Button Add,Calculate;

    EditText ed_name,ed_id,ed_date,ed_amount,ed_commission,ed_region;

    Spinner sp_region;

    Sales sales;
    Region region;

    Handler handler = new Handler();

    ProgressBar progressBar;

    String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sales_details);

        intent = getIntent();
        loginPerson = (Person) intent.getSerializableExtra(LOGIN_PERSON);
        sales=new Sales();
        region = new Region();

        person_image = findViewById(R.id.image_person);
        person_name = findViewById(R.id.welcome_person_name);
        Add = findViewById(R.id.BTN_Add);
        Calculate = findViewById(R.id.BTN_Calculate);
        ed_id = findViewById(R.id.ed_id);
        ed_date=findViewById(R.id.ed_registration_date);
        ed_name=findViewById(R.id.ed_name);
        ed_amount=findViewById(R.id.ed_amount);
        ed_commission=findViewById(R.id.ed_commission);
        ed_region=findViewById(R.id.ed_user_region);
        sp_region=findViewById(R.id.sp_region);
        progressBar=findViewById(R.id.progressBar);

        ed_name.setText(loginPerson.getName());
        ed_id.setText(String.valueOf(loginPerson.getPer_id()));
        ed_region.setText(loginPerson.getRegion_name().getName());
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.forLanguageTag("En")).format(new Date());
        ed_date.setText(date);

        new AsyncTaskLoadImage(person_image).execute(loginPerson.getImage());

        person_name.append("\n"+loginPerson.getName());

        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                if(sales.getCommission() == null || sales.getAmount() == null || sales.getRegion() == null){
                    return;
                }else{

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
                                    try {
                                         result = new PostInBackground().execute(
                                                REQUEST_TYPE, ADD_DETAILS,
                                                REGISTER_DATE, ed_date.getText().toString(),
                                                AMOUNT,String.valueOf(sales.getAmount()),
                                                COMMISSION,String.valueOf(sales.getCommission()),
                                                PERID,String.valueOf(sales.getPerson().getPer_id()),
                                                REGID,String.valueOf(sales.getRegion().getReg_id())).get();
                                    } catch (ExecutionException e) {
                                        e.printStackTrace();
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(addSalesDetails.this, result , Toast.LENGTH_LONG).show();
                                    progressBar.setVisibility(View.GONE);
                                    ed_amount.setText("");
                                    ed_commission.setText("");
                                }
                            });
                        }
                    }).start();
                }
            }
        }

        );

        Calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ed_amount.getText().toString().isEmpty())
                    return;

                Integer sales_region = (int) sp_region.getSelectedItemId() + 1;
                Integer person_region = loginPerson.getRegion_name().getReg_id();

                Double commission = CalculateCommission(ed_amount.getText().toString(), person_region, sales_region);


                region.setName(sp_region.getSelectedItem().toString());
                region.setReg_id((int) sp_region.getSelectedItemId()+1);

                sales.setPerson(loginPerson);
                sales.setAmount(Double.parseDouble(ed_amount.getText().toString()));
                sales.setCommission(commission);

                sales.setRegistration_date(new Date());

                sales.setRegion(region);

                ed_commission.setText(String.valueOf(commission));
            }
        });


    }

    public Double CalculateCommission(String strAmount,Integer p_region,Integer s_region) {
        if(strAmount.equals(""))return 0.0;

        Double amount = Double.parseDouble(ed_amount.getText().toString());
        Double commission = 0.0;

        //If sales person sales in his region
        if(p_region == s_region){
            if(amount < 1000000)
            {
                commission = amount * 0.05;
            }else{
                commission = (amount - 1000000) * 0.07;
                commission += 1000000 * 0.05;
            }
        }else{
            if(amount < 1000000)
            {
                commission = amount * 0.03;
            }else{
                commission = (amount - 1000000) * 0.04;
                commission += 1000000 * 0.03;
            }
        }


        return commission;
    }
}
