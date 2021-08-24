package com.example.awmsales;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import static com.example.awmsales.static_class.ADDRESS;
import static com.example.awmsales.static_class.ADD_DETAILS;
import static com.example.awmsales.static_class.ADD_PERSON;
import static com.example.awmsales.static_class.AFTERDISCOUNT;
import static com.example.awmsales.static_class.AMOUNT;
import static com.example.awmsales.static_class.CATEGORY;
import static com.example.awmsales.static_class.COMMISSION;
import static com.example.awmsales.static_class.DISCOUNT;
import static com.example.awmsales.static_class.EMAIL;
import static com.example.awmsales.static_class.GET_ALL;
import static com.example.awmsales.static_class.IMAGE;
import static com.example.awmsales.static_class.ITEM_TYPE;
import static com.example.awmsales.static_class.LOGIN_PERSON;
import static com.example.awmsales.static_class.MOBILE;
import static com.example.awmsales.static_class.NAME;
import static com.example.awmsales.static_class.PASSWORD;
import static com.example.awmsales.static_class.PERID;
import static com.example.awmsales.static_class.PROID;
import static com.example.awmsales.static_class.REGID;
import static com.example.awmsales.static_class.REGISTER_DATE;
import static com.example.awmsales.static_class.REQUEST_TYPE;
import static com.example.awmsales.static_class.TOTAL;
import static com.example.awmsales.static_class.TYPE;
import static com.example.awmsales.static_class.USEID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class addSalesDetails extends AppCompatActivity {

    Intent intent ;
    Person loginPerson ;

    ImageView person_image;
    TextView person_name,Price,Available;

    Button Add,Calculate;

    EditText ed_total,ed_date,ed_amount,ed_discount,ed_afterdiscount,ed_email;

    Spinner[] sp = new Spinner[3];

    Sales sales;

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

        sales.setPerson(loginPerson);

        person_image = findViewById(R.id.image_person);
        person_name = findViewById(R.id.welcome_person_name);
        Price = findViewById(R.id.txt_price);
        Available = findViewById(R.id.txt_available);
        Add = findViewById(R.id.BTN_Add);
        Calculate = findViewById(R.id.BTN_Calculate);
        ed_date=findViewById(R.id.ed_registration_date);
        ed_email=findViewById(R.id.ed_email);
        ed_total=findViewById(R.id.ed_total);
        ed_amount=findViewById(R.id.ed_amount);
        ed_amount.setEnabled(false);
        ed_discount=findViewById(R.id.ed_Discount);
        ed_afterdiscount=findViewById(R.id.ed_afterdiscount);
        sp[0] = findViewById(R.id.sp_To);
        sp[1] = findViewById(R.id.sp_category);
        sp[2] = findViewById(R.id.sp_product);
        progressBar=findViewById(R.id.progressBar);

        ArrayList<users> arrayMap = users.getarryusers();
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,arrayMap);
        sp[0].setAdapter(arrayAdapter);
        sp[0].setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position==0){
                    sp[1].setEnabled(false);
                    sp[2].setEnabled(false);
                }else {
                    sales.setUser((users) sp[0].getSelectedItem());
                    sp[1].setEnabled(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayList<Category> cat = Category.getarrycategory();
        ArrayAdapter category = new ArrayAdapter(this, android.R.layout.simple_spinner_item,cat);
        sp[1].setAdapter(category);
        sp[1].setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position==0){
                    sp[2].setEnabled(false);
                }else {
                    Category category1 =(Category) sp[1].getSelectedItem();
                    ArrayList<Product> pro = Product.getarryproduct(category1.getCat_id());
                    ArrayAdapter product = new ArrayAdapter(view.getContext(), android.R.layout.simple_spinner_item,pro);
                    sp[2].setAdapter(product);
                    sp[2].setEnabled(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sp[2].setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position!=0) {
                    Product product = (Product) sp[2].getSelectedItem();
                    Price.setText(product.getPrice());
                    String qua = "";
                    try {
                        result = new GetInBackground().execute(
                                REQUEST_TYPE, GET_ALL,
                                ITEM_TYPE, "sum_av",
                                "id", product.getProduct_id()
                        ).get();

                        JSONArray jsonArray = new JSONArray(result);
                        JSONObject jsonObject = new JSONObject(String.valueOf(jsonArray.getJSONObject(0)));
                        qua = jsonObject.get("sum_quantity").toString();

                    } catch (ExecutionException | InterruptedException | JSONException e) {
                        String msg = e.getMessage();
                        Log.e("ERROR", msg);
                    }
                    if (qua.equals("null")){
                        Available.setText(String.valueOf(product.getAvailable_qty()));
                    }else {
                        Available.setText(String.valueOf(Integer.parseInt(qua) - Integer.parseInt(product.getAvailable_qty())));
                    }
                    if (product.getCount() > 0) {
                        sales.setProduct((Product) sp[2].getSelectedItem());
                        ed_amount.setEnabled(true);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        ed_amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Product product = (Product)sp[2].getSelectedItem();
                try {
                    if (Integer.parseInt(s.toString()) <= Integer.parseInt(product.getAvailable_qty())) {
                        int av = Integer.parseInt(product.getAvailable_qty()) - Integer.parseInt(s.toString());
                        Available.setText(String.valueOf(av));
                        long tot = Long.parseLong(s.toString()) * Long.parseLong(Price.getText().toString());
                        ed_total.setText(String.valueOf(tot));
                    } else {
                        alertDialog.setTitle("Unavailabe");
                        alertDialog.setMessage("Amount not available");
                        alertDialog.show();
                        ed_amount.setText("");
                        Available.setText(product.getAvailable_qty());
                    }
                }catch (NumberFormatException e){
                    if (s.toString().isEmpty()|| s.toString().equals("")){
                        Available.setText(product.getAvailable_qty());
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                sales.setAmount(Double.parseDouble(s.toString()));
            }
        });

        ed_email.setText(loginPerson.getEmail());
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.forLanguageTag("En")).format(new Date());
        ed_date.setText(date);

//        new AsyncTaskLoadImage(person_image).execute(loginPerson.getImage());

        person_name.append("\n"+loginPerson.getName());

        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                if(sales.getDiscount() == null || sales.getAmount() == null){
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
                                                 PERID, String.valueOf(sales.getPerson().getPer_id()),
                                                 USEID, sales.getUser().getPerson_id(),
                                                 PROID, sales.getProduct().product_id,
                                                 TOTAL,String.valueOf(sales.getTotal()),
                                                AMOUNT,String.valueOf(sales.getAmount()),
                                                DISCOUNT,String.valueOf(sales.getDiscount()),
                                                AFTERDISCOUNT,String.valueOf(sales.getAfterdiscount()),
                                                REGISTER_DATE,ed_date.getText().toString()
                                         ).get();
                                    } catch (ExecutionException | InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(addSalesDetails.this, result , Toast.LENGTH_LONG).show();
                                    progressBar.setVisibility(View.GONE);
                                    ed_amount.setEnabled(false);
                                    ed_discount.setEnabled(false);
                                    try {
                                        ed_amount.setText(null);
                                    }catch (NumberFormatException e){
                                        e.printStackTrace();
                                    }
                                    try {
                                        ed_discount.setText(null);
                                    }catch (NumberFormatException e){
                                        e.printStackTrace();
                                    }
                                    sp[0].setSelection(0);
                                    sp[1].setSelection(0);
                                    sp[2].setSelection(0);
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

                if (ed_discount.getText().toString()!=null||!ed_discount.getText().equals("")) {
                    Double afterdiscount = CalculateDiscount(ed_total.getText().toString(), ed_discount.getText().toString());
                    ed_afterdiscount.setText(String.valueOf(afterdiscount));
                    sales.setDiscount(Double.parseDouble(ed_discount.getText().toString()));
                    sales.setTotal(Double.parseDouble(ed_total.getText().toString()));
                    sales.setAfterdiscount(afterdiscount);
                }else {

                }
            }
        });


    }

    public Double CalculateDiscount(String Total,String Discount) {
        if(Total.equals(""))return 0.0;

        if(Discount.equals("")||Discount.isEmpty()){
            Discount = "0";
        }
        Double total = Double.parseDouble(Total);
        Double disc = Double.parseDouble(Discount.toString());
        Double afterdiscount = 0.0;
        afterdiscount =total - (total * (disc / 100));
        return afterdiscount;
    }
}
