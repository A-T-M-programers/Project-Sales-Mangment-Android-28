package com.example.awmsales;

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

    public GridReportsAdapter(android.content.Context context, JSONArray Data) {
        Context = context;
        this.Data = Data;
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


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) Context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView = inflater.inflate(R.layout.grid_reports, null);

        JSONObject SingleRowOfData = getItem(position);

        TextView det_id = (TextView) gridView.findViewById(R.id.det_id);
        TextView per_id = (TextView) gridView.findViewById(R.id.per_id);
        TextView per_name = (TextView) gridView.findViewById(R.id.per_name);
        TextView amount = (TextView) gridView.findViewById(R.id.amount);
        TextView commission = (TextView) gridView.findViewById(R.id.commission);
        TextView sum_of_amount = (TextView) gridView.findViewById(R.id.sum_of_amount);
        TextView max_commission = (TextView) gridView.findViewById(R.id.max_commission);
        TextView sum_of_commission = (TextView) gridView.findViewById(R.id.sum_of_commission);
        TextView region_name = (TextView) gridView.findViewById(R.id.region_name);
        TextView registration_date = (TextView) gridView.findViewById(R.id.registration_date);
        try {
                for (Integer x = 0; x < SingleRowOfData.length(); x++) {
                    if (SingleRowOfData.has("det_id")) {
                        det_id.setText("DetID\n"+SingleRowOfData.getString("det_id"));
                        det_id.setVisibility(View.VISIBLE);
                    }
                    if (SingleRowOfData.has("per_id")) {
                        per_id.setText("PerID\n"+SingleRowOfData.getString("per_id"));
                        per_id.setVisibility(View.VISIBLE);
                    }
                    if (SingleRowOfData.has("per_name")) {
                        per_name.setText("Name\n"+SingleRowOfData.getString("per_name"));
                        per_name.setVisibility(View.VISIBLE);
                    }
                    if (SingleRowOfData.has("amount")) {
                        amount.setText("Amount\n"+SingleRowOfData.getString("amount"));
                        amount.setVisibility(View.VISIBLE);
                    }
                    if (SingleRowOfData.has("commission")) {
                        commission.setText("Commission\n"+SingleRowOfData.getString("commission"));
                        commission.setVisibility(View.VISIBLE);
                    }
                    if (SingleRowOfData.has("sum_of_amount")) {
                        sum_of_amount.setText("SumAmount\n"+SingleRowOfData.getString("sum_of_amount"));
                        sum_of_amount.setVisibility(View.VISIBLE);
                    }
                    if (SingleRowOfData.has("max_commission")) {
                        max_commission.setText("MaxCommission\n"+SingleRowOfData.getString("max_commission"));
                        max_commission.setVisibility(View.VISIBLE);
                    }
                    if (SingleRowOfData.has("sum_of_commission")) {
                        sum_of_commission.setText("SumCommission\n"+SingleRowOfData.getString("sum_of_commission"));
                        sum_of_commission.setVisibility(View.VISIBLE);
                    }
                    if (SingleRowOfData.has("region_name")) {
                        region_name.setText("Region\n"+SingleRowOfData.getString("region_name"));
                        region_name.setVisibility(View.VISIBLE);
                    }
                    if (SingleRowOfData.has("registration_date")) {
                        registration_date.setText("Date\n"+SingleRowOfData.getString("registration_date"));
                        registration_date.setVisibility(View.VISIBLE);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        return gridView;
    }
}
