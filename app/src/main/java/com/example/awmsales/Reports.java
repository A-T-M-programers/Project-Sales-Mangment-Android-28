package com.example.awmsales;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
import java.util.Iterator;
import java.util.concurrent.ExecutionException;

import static com.example.awmsales.static_class.ADD_PERSON_ALERT;
import static com.example.awmsales.static_class.COMMISSION;
import static com.example.awmsales.static_class.GET_ALL;
import static com.example.awmsales.static_class.ITEM_TYPE;
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

        context = this;
        gridView = findViewById(R.id.report_gridView);

        ED_Group_1 = findViewById(R.id.ED_Group_1);
        ED_Group_4 = findViewById(R.id.ED_Group_4);
        ED_Date_Group_5 = findViewById(R.id.ED_Date_Group_5);
        ED_Date_Group_5_per = findViewById(R.id.ED_Date_Group_5_per);

        SP_Group_2 = findViewById(R.id.SP_Group_2);
        SP_Group_3 = findViewById(R.id.SP_Group_3);

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
                final String reg_date = ED_Date_Group_5.getText().toString();
                final String per_id = ED_Date_Group_5_per.getText().toString();
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
                final String region_id = String.valueOf(SP_Group_2.getSelectedItemId() + 1);
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
                                        ITEM_TYPE, COMMISSION,
                                        REGID, region_id);
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
                                        ITEM_TYPE, COMMISSION,
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

            GridReportsAdapter gridReportsAdapter = new GridReportsAdapter(context, jsonArray);
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
                                    ITEM_TYPE,COMMISSION
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
                                    ITEM_TYPE,COMMISSION,
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