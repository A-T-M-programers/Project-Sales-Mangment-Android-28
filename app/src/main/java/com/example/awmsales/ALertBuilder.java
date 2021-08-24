package com.example.awmsales;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static com.example.awmsales.static_class.ADDRESS;
import static com.example.awmsales.static_class.ADD_PERSON;
import static com.example.awmsales.static_class.ADD_PERSON_ALERT;
import static com.example.awmsales.static_class.ADMIN_ALERT;
import static com.example.awmsales.static_class.DELETE;
import static com.example.awmsales.static_class.EMAIL;
import static com.example.awmsales.static_class.IMAGE;
import static com.example.awmsales.static_class.MOBILE;
import static com.example.awmsales.static_class.NAME;
import static com.example.awmsales.static_class.PASSWORD;
import static com.example.awmsales.static_class.PERID;
import static com.example.awmsales.static_class.PERSON_TYPE;
import static com.example.awmsales.static_class.REGID;
import static com.example.awmsales.static_class.REQUEST_TYPE;
import static com.example.awmsales.static_class.TYPE;
import static com.example.awmsales.static_class.UPDATE_PERSON;
import static com.example.awmsales.static_class.USER_ALERT;
import static com.example.awmsales.static_class.User_Status;

public class ALertBuilder {

    String result = "";

    String type;

    Person person;

    Context context;

    boolean isAdmin;

    public String getResult() {
        return result;
    }

    public ALertBuilder(String type, Person person, Context context,boolean isAdmin) {
        this.type = type;
        this.person = person;
        this.context = context;
        this.isAdmin = isAdmin;
    }

    public ALertBuilder(String type, Person person, Context context) {
        this.type = type;
        this.person = person;
        this.context = context;
    }

    public ALertBuilder(String type, Context context) {
        this.type = type;
        this.context = context;
    }

    public AlertDialog.Builder MyBuilder() {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View mView = inflater.inflate(R.layout.custom_builder, null);
        TextView alert_title = mView.findViewById(R.id.alert_title);

        if (type.equals(ADMIN_ALERT)||type.equals(USER_ALERT)) {

            alert_title.setText(person.getName());

            final EditText ed_id = mView.findViewById(R.id.ed_id);
            final EditText ed_name = mView.findViewById(R.id.ed_name);
            final EditText ed_email = mView.findViewById(R.id.ed_email);
            final EditText ed_address = mView.findViewById(R.id.ed_address);
            final EditText ed_password = mView.findViewById(R.id.ed_password);
            final EditText ed_mobile = mView.findViewById(R.id.ed_mobile);
            final Switch sw_user_status = mView.findViewById(R.id.User_status);
            final Switch sw_person_type = mView.findViewById(R.id.Person_Type);
            final Spinner sp_region = mView.findViewById(R.id.sp_region);
            ArrayList<Region> arrayMap = Region.getarryregion();
            ArrayAdapter arrayAdapter = new ArrayAdapter(mView.getContext(), android.R.layout.simple_spinner_item, arrayMap);
            sp_region.setAdapter(arrayAdapter);

            ed_id.setText(String.valueOf(person.getPer_id()));
            ed_name.setText(person.getName());
            ed_email.setText(person.getEmail());
            ed_address.setText(person.getAddress());
            ed_password.setText(person.getPassword());
            ed_mobile.setText(person.getMobile());
            sw_user_status.setChecked(!Boolean.parseBoolean(person.getUser_status()));
            sw_person_type.setChecked(person.isType());

            if (!isAdmin) {
                ed_email.setEnabled(false);
                ed_password.setEnabled(false);
            }
            final Region region = person.getRegion_name();
            sp_region.setSelection(region.getSelect());


            builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    try {
                        result = new GetInBackground().execute(
                                REQUEST_TYPE, DELETE, PERID, String.valueOf(person.getPer_id())).get();
                        dialog.dismiss();
                    } catch (ExecutionException | InterruptedException e) {
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });

            builder.setNeutralButton(R.string.close, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            builder.setNegativeButton(R.string.update, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Person newPerson = new Person();
                    newPerson.setPer_id(Integer.parseInt(ed_id.getText().toString()));
                    newPerson.setName(ed_name.getText().toString());
                    newPerson.setEmail(ed_email.getText().toString());
                    newPerson.setAddress(ed_address.getText().toString());
                    if (sw_user_status.isChecked()) {
                        newPerson.setUser_status("1");
                    } else {
                        newPerson.setUser_status("0");
                    }
                    newPerson.setPassword(ed_password.getText().toString());
                    newPerson.setMobile(ed_mobile.getText().toString());

                    if (sw_person_type.isChecked()) {
                        newPerson.setPerson_type("2");
                    } else {
                        newPerson.setPerson_type("3");
                    }

                    Region newRegion = (Region) sp_region.getSelectedItem();

                    if (newPerson.getName().isEmpty() ||
                            newPerson.getEmail().isEmpty() ||
                            newPerson.getPassword().isEmpty() ||
                            String.valueOf(newRegion.getReg_id()).isEmpty()
                    ) {
                        Toast.makeText(context, R.string.fillAllRequired, Toast.LENGTH_SHORT).show();
                        return;
                    } else if (
                            !newPerson.getEmail().contains("@") || !newPerson.getEmail().contains(".")
                    ) {
                        Toast.makeText(context, R.string.checkemail, Toast.LENGTH_SHORT).show();
                        return;
                    } else if (newPerson.getPassword().length() < 6
                    ) {
                        Toast.makeText(context, R.string.checkPasswird, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    try {
                        result = new PostInBackground().execute(
                                REQUEST_TYPE, UPDATE_PERSON,
                                PERID, String.valueOf(newPerson.getPer_id()),
                                NAME, newPerson.getName(),
                                EMAIL, newPerson.getEmail(),
                                PASSWORD, newPerson.getPassword(),
                                MOBILE, newPerson.getMobile(),
                                ADDRESS, newPerson.getAddress(),
                                User_Status, newPerson.getUser_status(),
                                PERSON_TYPE, newPerson.getPerson_type(),
                                REGID, String.valueOf(newRegion.getReg_id())
                        ).get();
                        dialog.dismiss();
                    } catch (ExecutionException e) {
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG);
                    } catch (InterruptedException e) {
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG);
                    }
                }
            });
        } else if(type.equals(ADD_PERSON_ALERT)) {
            alert_title.setText(R.string.add_sales_person);

            final EditText ed_name = mView.findViewById(R.id.ed_name);
            final EditText ed_email = mView.findViewById(R.id.ed_email);
            final EditText ed_address = mView.findViewById(R.id.ed_address);
            final EditText ed_password = mView.findViewById(R.id.ed_password);
            final EditText ed_mobile = mView.findViewById(R.id.ed_mobile);
            final Switch sw_user_status = mView.findViewById(R.id.User_status);
            final Switch sw_person_type = mView.findViewById(R.id.Person_Type);
            final Spinner sp_region = mView.findViewById(R.id.sp_region);
            ArrayList<Region> arrayMap = Region.getarryregion();
            ArrayAdapter arrayAdapter = new ArrayAdapter(mView.getContext(), android.R.layout.simple_spinner_item,arrayMap);
            sp_region.setAdapter(arrayAdapter);
            if (person.isType()){
                sw_person_type.setEnabled(true);
            }else {
                sw_person_type.setEnabled(false);
            }

            builder.setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    Person newPerson = new Person();
                    newPerson.setName(ed_name.getText().toString());
                    newPerson.setEmail(ed_email.getText().toString());
                    newPerson.setAddress(ed_address.getText().toString());
                    if (sw_user_status.isChecked()) {
                        newPerson.setUser_status("1");
                    }else {
                        newPerson.setUser_status("0");
                    }
                    newPerson.setPassword(ed_password.getText().toString());
                    newPerson.setMobile(ed_mobile.getText().toString());

                    if (sw_person_type.isChecked()) {
                        newPerson.setPerson_type("2");
                    }else {
                        newPerson.setPerson_type("3");
                    }

                    Region newRegion = (Region) sp_region.getSelectedItem();

                    if(newPerson.getName().isEmpty() ||
                    newPerson.getEmail().isEmpty() ||
                            newPerson.getPassword().isEmpty() ||
                            String.valueOf(newRegion.getReg_id()).isEmpty()
                    )
                    {
                        Toast.makeText(context, R.string.fillAllRequired, Toast.LENGTH_SHORT).show();
                        return;
                    }else if(
                            !newPerson.getEmail().contains("@") || !newPerson.getEmail().contains(".")
                    ) {
                        Toast.makeText(context, R.string.checkemail, Toast.LENGTH_SHORT).show();
                        return;
                    }else if( newPerson.getPassword().length() < 6
                    ){
                        Toast.makeText(context, R.string.checkPasswird, Toast.LENGTH_SHORT).show();
                        return;
                    }

                    try {
                        result = new PostInBackground().execute(
                                REQUEST_TYPE, ADD_PERSON,
                                NAME, newPerson.getName(),
                                EMAIL, newPerson.getEmail(),
                                PASSWORD, newPerson.getPassword(),
                                MOBILE, newPerson.getMobile(),
                                TYPE,newPerson.getPerson_type(),
                                ADDRESS, newPerson.getAddress(),
                                User_Status, newPerson.getUser_status(),
                                REGID, String.valueOf(newRegion.getReg_id())
                        ).get();
                        dialog.dismiss();
                    } catch (ExecutionException e) {
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG);
                    } catch (InterruptedException e) {
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG);
                    }
                }
            });

            builder.setNeutralButton(R.string.close, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        }
        builder.setView(mView);
        return builder;
    }
}
