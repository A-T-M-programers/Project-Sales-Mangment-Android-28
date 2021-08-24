package com.example.awmsales;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONObject;

public class GridReportsAdapter extends BaseAdapter {
    private final Context Context;
    private final JSONArray Data;
    private final Person person;

    public GridReportsAdapter(android.content.Context context, JSONArray Data,Person person) {
        Context = context;
        this.Data = Data;
        this.person = person;
    }


    @Override
    public int getCount() {
        return this.Data.length();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public JSONObject getItem(int position)  {
        JSONObject curJson = new JSONObject();
        try {
            curJson = this.Data.getJSONObject( position );
        } catch( Exception e ){
            e.printStackTrace();
        }
        return curJson;
    }


    @SuppressLint({"InflateParams", "SetTextI18n", "ViewHolder"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) Context.getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE);

        View gridView = inflater.inflate(R.layout.grid_reports, null);

        JSONObject SingleRowOfData = getItem(position);

        TextView ord_id = (TextView) gridView.findViewById(R.id.ord_id);
        TextView customer_id = (TextView) gridView.findViewById(R.id.customer_id);
        TextView created_id = (TextView) gridView.findViewById(R.id.created_id);
        TextView per_name = (TextView) gridView.findViewById(R.id.per_name);
        TextView price = (TextView) gridView.findViewById(R.id.price);
        TextView amount = (TextView) gridView.findViewById(R.id.amount);
        TextView total = (TextView) gridView.findViewById(R.id.total);
        TextView discount = (TextView) gridView.findViewById(R.id.discount);
        TextView afterdiscount = (TextView) gridView.findViewById(R.id.after_discount);
        TextView sum_of_amount = (TextView) gridView.findViewById(R.id.sum_of_amount);
        TextView max_discount = (TextView) gridView.findViewById(R.id.max_discount);
        TextView sum_of_total = (TextView) gridView.findViewById(R.id.sum_of_total);
        TextView sum_after_discount = (TextView) gridView.findViewById(R.id.sum_after_discount);
        TextView region_name = (TextView) gridView.findViewById(R.id.region_name);
        TextView registration_date = (TextView) gridView.findViewById(R.id.registration_date);
        try {
                for (int x = 0; x < SingleRowOfData.length(); x++) {
                    if (!person.isType()){
                        if (SingleRowOfData.getString("customer_id").equals(String.valueOf(person.getPer_id()))||
                                SingleRowOfData.getString("created_id").equals(String.valueOf(person.getPer_id()))){
                            if (SingleRowOfData.has("order_id")) {
                                ord_id.setText("OrdID\n"+SingleRowOfData.getString("order_id"));
                                ord_id.setVisibility(View.VISIBLE);
                            }
                            if (SingleRowOfData.has("customer_name")) {
                                customer_id.setText("CustomerName\n"+SingleRowOfData.getString("customer_name"));
                                customer_id.setVisibility(View.VISIBLE);
                            }
                            if (SingleRowOfData.has("created_name")) {
                                created_id.setText("SuperVisorsName\n"+SingleRowOfData.getString("created_name"));
                                created_id.setVisibility(View.VISIBLE);
                            }
                            if (SingleRowOfData.has("product_name")) {
                                per_name.setText("ProductName\n"+SingleRowOfData.getString("product_name"));
                                per_name.setVisibility(View.VISIBLE);
                            }
                            if (SingleRowOfData.has("price")) {
                                price.setText("Price\n"+SingleRowOfData.getString("price"));
                                price.setVisibility(View.VISIBLE);
                            }
                            if (SingleRowOfData.has("amount")) {
                                amount.setText("Amount\n"+SingleRowOfData.getString("amount"));
                                amount.setVisibility(View.VISIBLE);
                            }
                            if (SingleRowOfData.has("total")) {
                                total.setText("Total\n"+SingleRowOfData.getString("total"));
                                total.setVisibility(View.VISIBLE);
                            }
                            if (SingleRowOfData.has("discount")) {
                                discount.setText("Discount\n"+SingleRowOfData.getString("discount"));
                                discount.setVisibility(View.VISIBLE);
                            }
                            if (SingleRowOfData.has("total_after_dis")) {
                                afterdiscount.setText("TotalAfterDis\n"+SingleRowOfData.getString("total_after_dis"));
                                afterdiscount.setVisibility(View.VISIBLE);
                            }
                            if (SingleRowOfData.has("sum_of_amount")) {
                                sum_of_amount.setText("SumAmount\n"+SingleRowOfData.getString("sum_of_amount"));
                                sum_of_amount.setVisibility(View.VISIBLE);
                            }
                            if (SingleRowOfData.has("max_discount")) {
                                max_discount.setText("MaxDiscount\n"+SingleRowOfData.getString("max_discount"));
                                max_discount.setVisibility(View.VISIBLE);
                            }
                            if (SingleRowOfData.has("sum_total")) {
                                sum_of_total.setText("SumTotal\n"+SingleRowOfData.getString("sum_total"));
                                sum_of_total.setVisibility(View.VISIBLE);
                            }
                            if (SingleRowOfData.has("sum_after_dis")) {
                                sum_after_discount.setText("SumAfterDis\n"+SingleRowOfData.getString("sum_after_dis"));
                                sum_after_discount.setVisibility(View.VISIBLE);
                            }
                            if (SingleRowOfData.has("region_name")) {
                                region_name.setText("Region\n"+SingleRowOfData.getString("region_name"));
                                region_name.setVisibility(View.VISIBLE);
                            }
                            if (SingleRowOfData.has("order_created_date")) {
                                registration_date.setText("Date\n"+SingleRowOfData.getString("order_created_date"));
                                registration_date.setVisibility(View.VISIBLE);
                            }
                        }
                    }else {
                        if (SingleRowOfData.has("order_id")) {
                            ord_id.setText("OrdID\n" + SingleRowOfData.getString("order_id"));
                            ord_id.setVisibility(View.VISIBLE);
                        }
                        if (SingleRowOfData.has("customer_name")) {
                            customer_id.setText("CustomerName\n" + SingleRowOfData.getString("customer_name"));
                            customer_id.setVisibility(View.VISIBLE);
                        }
                        if (SingleRowOfData.has("created_name")) {
                            created_id.setText("SuperVisorsName\n" + SingleRowOfData.getString("created_name"));
                            created_id.setVisibility(View.VISIBLE);
                        }
                        if (SingleRowOfData.has("product_name")) {
                            per_name.setText("ProductName\n" + SingleRowOfData.getString("product_name"));
                            per_name.setVisibility(View.VISIBLE);
                        }
                        if (SingleRowOfData.has("price")) {
                            price.setText("Price\n" + SingleRowOfData.getString("price"));
                            price.setVisibility(View.VISIBLE);
                        }
                        if (SingleRowOfData.has("amount")) {
                            amount.setText("Amount\n" + SingleRowOfData.getString("amount"));
                            amount.setVisibility(View.VISIBLE);
                        }
                        if (SingleRowOfData.has("total")) {
                            total.setText("Total\n" + SingleRowOfData.getString("total"));
                            total.setVisibility(View.VISIBLE);
                        }
                        if (SingleRowOfData.has("discount")) {
                            discount.setText("Discount\n" + SingleRowOfData.getString("discount"));
                            discount.setVisibility(View.VISIBLE);
                        }
                        if (SingleRowOfData.has("total_after_dis")) {
                            afterdiscount.setText("TotalAfterDis\n" + SingleRowOfData.getString("total_after_dis"));
                            afterdiscount.setVisibility(View.VISIBLE);
                        }
                        if (SingleRowOfData.has("sum_of_amount")) {
                            sum_of_amount.setText("SumAmount\n" + SingleRowOfData.getString("sum_of_amount"));
                            sum_of_amount.setVisibility(View.VISIBLE);
                        }
                        if (SingleRowOfData.has("max_discount")) {
                            max_discount.setText("MaxDiscount\n" + SingleRowOfData.getString("max_discount"));
                            max_discount.setVisibility(View.VISIBLE);
                        }
                        if (SingleRowOfData.has("sum_total")) {
                            sum_of_total.setText("SumTotal\n" + SingleRowOfData.getString("sum_total"));
                            sum_of_total.setVisibility(View.VISIBLE);
                        }
                        if (SingleRowOfData.has("sum_after_dis")) {
                            sum_after_discount.setText("SumAfterDis\n" + SingleRowOfData.getString("sum_after_dis"));
                            sum_after_discount.setVisibility(View.VISIBLE);
                        }
                        if (SingleRowOfData.has("region_name")) {
                            region_name.setText("Region\n" + SingleRowOfData.getString("region_name"));
                            region_name.setVisibility(View.VISIBLE);
                        }
                        if (SingleRowOfData.has("order_created_date")) {
                            registration_date.setText("Date\n" + SingleRowOfData.getString("order_created_date"));
                            registration_date.setVisibility(View.VISIBLE);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        return gridView;
    }
}
