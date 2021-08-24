package com.example.awmsales;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;

import static com.example.awmsales.static_class.ADD_PERSON_ALERT;
import static com.example.awmsales.static_class.COMMISSION;
import static com.example.awmsales.static_class.DISCOUNT;
import static com.example.awmsales.static_class.GET_ALL;
import static com.example.awmsales.static_class.ITEM_TYPE;
import static com.example.awmsales.static_class.LOGIN_PERSON;
import static com.example.awmsales.static_class.PERID;
import static com.example.awmsales.static_class.REGID;
import static com.example.awmsales.static_class.REGISTER_DATE;
import static com.example.awmsales.static_class.REPORT;
import static com.example.awmsales.static_class.REQUEST_TYPE;
import static com.example.awmsales.static_class.TOP;

public class Reports extends AppCompatActivity {

    private Context context;
    private GridView gridView;
    private JSONArray SampleProducts;
    Person loginPerson;

    Handler handler = new Handler();

    LinearLayout Group_1, Group_2, Group_4, Group_3,Group_5;

    Button BTN_Group_1, BTN_Group_2, BTN_Group_3 , BTN_Group_4 , BTN_Group_5;

    ProgressBar progressBar;

    EditText ED_Group_1, ED_Group_4 , ED_Date_Group_5 , ED_Date_Group_5_per;
    Spinner SP_Group_2 , SP_Group_3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);
        Intent login_intent = getIntent();
        loginPerson = (Person) login_intent.getSerializableExtra(LOGIN_PERSON);
        TextWatcher tw = new TextWatcher() {
            private String current = "";
            private String ddmmyyyy = "DDMMYYYY";
            private Calendar cal = Calendar.getInstance();
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(current)) {
                    String clean = s.toString().replaceAll("[^\\d.]|\\.", "");
                    String cleanC = current.replaceAll("[^\\d.]|\\.", "");

                    int cl = clean.length();
                    int sel = cl;
                    for (int i = 2; i <= cl && i < 6; i += 2) {
                        sel++;
                    }
                    //Fix for pressing delete next to a forward slash
                    if (clean.equals(cleanC)) sel--;

                    if (clean.length() < 8){
                        clean = clean + ddmmyyyy.substring(clean.length());
                    }else{
                        //This part makes sure that when we finish entering numbers
                        //the date is correct, fixing it otherwise
                        int day  = Integer.parseInt(clean.substring(0,2));
                        int mon  = Integer.parseInt(clean.substring(2,4));
                        int year = Integer.parseInt(clean.substring(4,8));

                        mon = mon < 1 ? 1 : mon > 12 ? 12 : mon;
                        cal.set(Calendar.MONTH, mon-1);
                        year = (year<1900)?1900:(year>2100)?2100:year;
                        cal.set(Calendar.YEAR, year);
                        // ^ first set year for the line below to work correctly
                        //with leap years - otherwise, date e.g. 29/02/2012
                        //would be automatically corrected to 28/02/2012

                        day = (day > cal.getActualMaximum(Calendar.DATE))? cal.getActualMaximum(Calendar.DATE):day;
                        clean = String.format("%02d%02d%02d",day, mon, year);
                    }

                    clean = String.format("%s/%s/%s", clean.substring(0, 2),
                            clean.substring(2, 4),
                            clean.substring(4, 8));

                    sel = sel < 0 ? 0 : sel;
                    current = clean;
                    ED_Date_Group_5.setText(current);
                    ED_Date_Group_5.setSelection(sel < current.length() ? sel : current.length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        context = this;
        gridView = findViewById(R.id.report_gridView);

        ED_Group_1 = findViewById(R.id.ED_Group_1);
        ED_Group_4 = findViewById(R.id.ED_Group_4);
        ED_Date_Group_5 = findViewById(R.id.ED_Date_Group_5);
        ED_Date_Group_5.addTextChangedListener(tw);
        ED_Date_Group_5_per = findViewById(R.id.ED_Date_Group_5_per);
        SP_Group_2 = findViewById(R.id.SP_Group_2);
        SP_Group_3 = findViewById(R.id.SP_Group_3);

        ArrayList<Region> arrayMap = Region.getarryregion();
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,arrayMap);
        SP_Group_2.setAdapter(arrayAdapter);
        SP_Group_3.setAdapter(arrayAdapter);

        progressBar = findViewById(R.id.progressBar);

        Group_1 = findViewById(R.id.Group_1);
        Group_2 = findViewById(R.id.Group_2);
        Group_3 = findViewById(R.id.Group_3);
        Group_4 = findViewById(R.id.Group_4);
        Group_5 = findViewById(R.id.Group_5);

        BTN_Group_1 = findViewById(R.id.BTN_Group_1);
        BTN_Group_2 = findViewById(R.id.BTN_Group_2);
        BTN_Group_4 = findViewById(R.id.BTN_Group_4);
        BTN_Group_3 = findViewById(R.id.BTN_Group_3);
        BTN_Group_5 = findViewById(R.id.BTN_Group_5);


        //LISTENERS
        BTN_Group_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String reg_date = ED_Date_Group_5.getText().toString().replaceAll("/",",");
                final String per_id = ED_Date_Group_5_per.getText().toString();
                progressBar.setVisibility(View.VISIBLE);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setIndeterminate(true);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                report_generation(
                                        REQUEST_TYPE, REPORT,
                                        PERID, per_id,
                                        REGISTER_DATE,reg_date);
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
        });

        BTN_Group_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Region region = (Region) SP_Group_2.getSelectedItem();
                progressBar.setVisibility(View.VISIBLE);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setIndeterminate(true);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                //EXAMPLE => http://mws-amw-c2.atwebpages.com/database.php?submit=getall&itemType=commission&reg_id=3
                                report_generation(
                                        REQUEST_TYPE, GET_ALL,
                                        ITEM_TYPE, DISCOUNT,
                                        REGID, String.valueOf(region.getReg_id()));
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
        });
        BTN_Group_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String person_id = ED_Group_1.getText().toString();
                progressBar.setVisibility(View.VISIBLE);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setIndeterminate(true);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                // EXAMPLE => http://mws-amw-c2.atwebpages.com/database.php?submit=getall&itemType=commission&per_id=1
                                report_generation(
                                        REQUEST_TYPE, GET_ALL,
                                        ITEM_TYPE, DISCOUNT,
                                        PERID, person_id);
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
        });
        BTN_Group_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String person_top_id = ED_Group_4.getText().toString();
                progressBar.setVisibility(View.VISIBLE);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setIndeterminate(true);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                report_generation(
                                        REQUEST_TYPE, REPORT,
                                        PERID, person_top_id);
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
        });
        BTN_Group_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String region_top_id = String.valueOf(SP_Group_3.getSelectedItemId() + 1);
                progressBar.setVisibility(View.VISIBLE);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setIndeterminate(true);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                //EXAMPLE => http://mws-amw-c2.atwebpages.com/database.php?submit=getall&itemType=commission&reg_id=3
                                report_generation(
                                        REQUEST_TYPE, REPORT,
                                        REGID, region_top_id);
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
        });

    }


    //METHODS
    private void report_generation(String... params) {
        try {
            String result = new GetInBackground().execute(params).get();

            JSONArray jsonArray = new JSONArray(result);

            GridReportsAdapter gridReportsAdapter = new GridReportsAdapter(context, jsonArray,loginPerson);
            gridView.setAdapter(gridReportsAdapter);

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.reports_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.report_all) {
            Group_2.setVisibility(View.GONE);
            Group_1.setVisibility(View.GONE);
            Group_4.setVisibility(View.GONE);
            Group_3.setVisibility(View.GONE);
            Group_5.setVisibility(View.GONE);

            //EXAMPLE => http://mws-amw-c2.atwebpages.com/database.php?submit=getall&itemType=commission
            progressBar.setVisibility(View.VISIBLE);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    progressBar.setIndeterminate(true);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            report_generation(
                                    REQUEST_TYPE, GET_ALL,
                                    ITEM_TYPE,DISCOUNT
                            );
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
        } else if (id == R.id.report_person) {
            Group_2.setVisibility(View.GONE);
            Group_1.setVisibility(View.VISIBLE);
            Group_4.setVisibility(View.GONE);
            Group_3.setVisibility(View.GONE);
            Group_5.setVisibility(View.GONE);

        } else if (id == R.id.report_region) {
            Group_2.setVisibility(View.VISIBLE);
            Group_1.setVisibility(View.GONE);
            Group_4.setVisibility(View.GONE);
            Group_3.setVisibility(View.GONE);
            Group_5.setVisibility(View.GONE);

        } else if (id == R.id.report_top_all) {
            Group_2.setVisibility(View.GONE);
            Group_1.setVisibility(View.GONE);
            Group_4.setVisibility(View.GONE);
            Group_3.setVisibility(View.GONE);
            Group_5.setVisibility(View.GONE);

            //EXAMPLE => http://mws-amw-c2.atwebpages.com/database.php?submit=getall&itemType=commission&top=1
            progressBar.setVisibility(View.VISIBLE);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    progressBar.setIndeterminate(true);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            report_generation(
                                    REQUEST_TYPE, GET_ALL,
                                    ITEM_TYPE,DISCOUNT,
                                    TOP,TOP
                            );
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

        } else if (id == R.id.report_sum_grouping_persons) {
            Group_2.setVisibility(View.GONE);
            Group_1.setVisibility(View.GONE);
            Group_4.setVisibility(View.GONE);
            Group_3.setVisibility(View.GONE);
            Group_5.setVisibility(View.GONE);
            //EXAMPLE => http://mws-amw-c2.atwebpages.com/database.php?submit=report
            progressBar.setVisibility(View.VISIBLE);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    progressBar.setIndeterminate(true);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            report_generation(
                                    REQUEST_TYPE, REPORT
                            );
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
        } else if (id == R.id.report_sum_grouping_regions_date) {
            Group_2.setVisibility(View.GONE);
            Group_1.setVisibility(View.GONE);
            Group_4.setVisibility(View.GONE);
            Group_3.setVisibility(View.GONE);
            Group_5.setVisibility(View.VISIBLE);
        }

        return super.onOptionsItemSelected(item);
    }

}