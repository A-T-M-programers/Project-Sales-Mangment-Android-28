package com.example.awmsales;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
import static com.example.awmsales.static_class.REGID;
import static com.example.awmsales.static_class.REQUEST_TYPE;
import static com.example.awmsales.static_class.TYPE;
import static com.example.awmsales.static_class.UPDATE_PERSON;
import static com.example.awmsales.static_class.USER_ALERT;

public class ALertBuilder {

    String result = "";

    String type;

    Person person;

    Context context;

    public String getResult() {
        return result;
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

        if (type.equals(ADMIN_ALERT)) {

            alert_title.setText(person.getName());

            final EditText ed_id = mView.findViewById(R.id.ed_id);
            final EditText ed_name = mView.findViewById(R.id.ed_name);
            final EditText ed_email = mView.findViewById(R.id.ed_email);
            final EditText ed_address = mView.findViewById(R.id.ed_address);
            final EditText ed_password = mView.findViewById(R.id.ed_password);
            final EditText ed_mobile = mView.findViewById(R.id.ed_mobile);
            final EditText ed_image_link = mView.findViewById(R.id.ed_image_link);
            final Spinner sp_region = mView.findViewById(R.id.sp_region);

            ed_id.setText(String.valueOf(person.getPer_id()));
            ed_name.setText(person.getName());
            ed_email.setText(person.getEmail());
            ed_address.setText(person.getAddress());
            ed_password.setText(person.getPassword());
            ed_mobile.setText(person.getMobile());
            ed_image_link.setText(person.getImage());

            final Region region = person.getRegion_name();
            sp_region.setSelection(region.getReg_id() - 1);


            builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    try {
                        result = new GetInBackground().execute(
                                REQUEST_TYPE, DELETE, PERID, String.valueOf(person.getPer_id())).get();
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

            builder.setNegativeButton(R.string.update, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Person newPerson = new Person();
                    newPerson.setPer_id(Integer.parseInt(ed_id.getText().toString()));
                    newPerson.setName(ed_name.getText().toString());
                    newPerson.setEmail(ed_email.getText().toString());
                    newPerson.setAddress(ed_address.getText().toString());
                    String image = ed_image_link.getText().toString().isEmpty() ? "http://mws-amw-c2.atwebpages.com/images/def" : ed_image_link.getText().toString();
                    newPerson.setImage(image);
                    newPerson.setPassword(ed_password.getText().toString());
                    newPerson.setMobile(ed_mobile.getText().toString());
                    Region newRegion = new Region();
                    newRegion.setName(sp_region.getSelectedItem().toString());
                    newRegion.setReg_id((int) sp_region.getSelectedItemId());

                    try {
                        result = new PostInBackground().execute(
                                REQUEST_TYPE, UPDATE_PERSON,
                                PERID, String.valueOf(newPerson.getPer_id()),
                                NAME, newPerson.getName(),
                                EMAIL, newPerson.getEmail(),
                                PASSWORD, newPerson.getPassword(),
                                MOBILE, newPerson.getMobile(),
                                ADDRESS, newPerson.getAddress(),
                                IMAGE, newPerson.getImage(),
                                REGID, String.valueOf(newRegion.getReg_id() + 1)
                        ).get();
                        dialog.dismiss();
                    } catch (ExecutionException e) {
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG);
                    } catch (InterruptedException e) {
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG);
                    }
                }
            });

        }else if(type.equals(USER_ALERT)){
            alert_title.setText(person.getName());

            final EditText ed_id = mView.findViewById(R.id.ed_id);
            final EditText ed_name = mView.findViewById(R.id.ed_name);
            final EditText ed_email = mView.findViewById(R.id.ed_email);
            final EditText ed_address = mView.findViewById(R.id.ed_address);
            final EditText ed_mobile = mView.findViewById(R.id.ed_mobile);
            final EditText ed_image_link = mView.findViewById(R.id.ed_image_link);
            final Spinner sp_region = mView.findViewById(R.id.sp_region);

            ed_id.setText(String.valueOf(person.getPer_id()));
            ed_name.setText(person.getName());
            ed_email.setText(person.getEmail());
            ed_address.setText(person.getAddress());
            ed_mobile.setText(person.getMobile());
            ed_image_link.setText(person.getImage());

            ed_name.setEnabled(false);
            ed_address.setEnabled(false);
            ed_email.setEnabled(false);
            ed_image_link.setEnabled(false);
            ed_mobile.setEnabled(false);

            final Region region = person.getRegion_name();
            sp_region.setSelection(region.getReg_id() - 1);

            builder.setNeutralButton(R.string.close, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        }else if(type.equals(ADD_PERSON_ALERT)) {
            alert_title.setText(R.string.add_sales_person);

            final EditText ed_name = mView.findViewById(R.id.ed_name);
            final EditText ed_email = mView.findViewById(R.id.ed_email);
            final EditText ed_address = mView.findViewById(R.id.ed_address);
            final EditText ed_password = mView.findViewById(R.id.ed_password);
            final EditText ed_mobile = mView.findViewById(R.id.ed_mobile);
            final EditText ed_image_link = mView.findViewById(R.id.ed_image_link);
            final Spinner sp_region = mView.findViewById(R.id.sp_region);


            builder.setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    Person newPerson = new Person();
                    newPerson.setName(ed_name.getText().toString());
                    newPerson.setEmail(ed_email.getText().toString());
                    newPerson.setAddress(ed_address.getText().toString());
                    String image = ed_image_link.getText().toString().isEmpty() ? "http://mws-amw-c2.atwebpages.com/images/def" : ed_image_link.getText().toString();
                    newPerson.setImage(image);
                    newPerson.setPassword(ed_password.getText().toString());
                    newPerson.setMobile(ed_mobile.getText().toString());


                    Region newRegion = new Region();
                    newRegion.setName(sp_region.getSelectedItem().toString());
                    newRegion.setReg_id((int) sp_region.getSelectedItemId()+1);

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
                                TYPE,String.valueOf(0),
                                ADDRESS, newPerson.getAddress(),
                                IMAGE, newPerson.getImage(),
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
