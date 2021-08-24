package com.example.awmsales;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static android.content.Context.MODE_PRIVATE;
import static com.example.awmsales.static_class.ACCOUNT_TYPE;
import static com.example.awmsales.static_class.ADDRESS;
import static com.example.awmsales.static_class.ADD_PERSON_ALERT;
import static com.example.awmsales.static_class.ADMIN_ALERT;
import static com.example.awmsales.static_class.DELETE;
import static com.example.awmsales.static_class.EMAIL;
import static com.example.awmsales.static_class.IMAGE;
import static com.example.awmsales.static_class.IS_ADMIN;
import static com.example.awmsales.static_class.MOBILE;
import static com.example.awmsales.static_class.NAME;
import static com.example.awmsales.static_class.PASSWORD;
import static com.example.awmsales.static_class.PERID;
import static com.example.awmsales.static_class.REGID;
import static com.example.awmsales.static_class.REQUEST_TYPE;
import static com.example.awmsales.static_class.TYPE;
import static com.example.awmsales.static_class.UPDATE_PERSON;
import static com.example.awmsales.static_class.USER_ALERT;

public class PersonAdapterList extends ArrayAdapter<Person> {

    private Context context;
    private int resource;
    public static String result = "";
    public boolean isAdmin;



    public PersonAdapterList(@NonNull Context context, int resource, @NonNull ArrayList<Person> objects,boolean isAdmin) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.isAdmin = isAdmin;
    }


    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        final Person person = getItem(position);

        String name = person.getName();
        String email = person.getEmail();
//        String image = person.getImage();

        final LayoutInflater inflator = LayoutInflater.from(context);
        convertView = inflator.inflate(resource,parent,false);

        ImageView image_person = (ImageView)  convertView.findViewById(R.id.image_person);
        TextView txt_person_name = (TextView) convertView.findViewById(R.id.txt_person_name);
        TextView txt_person_email = (TextView) convertView.findViewById(R.id.txt_person_email);

        txt_person_name.setText(name);
        txt_person_email.setText(email);
//        new AsyncTaskLoadImage(image_person).execute(image);


        final View finalConvertView = convertView;
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAdmin) {
                    final ALertBuilder alertBuilder = new ALertBuilder(ADMIN_ALERT,person,context,isAdmin);
                    final android.app.AlertDialog.Builder builder = alertBuilder.MyBuilder();

                    final AlertDialog alert1 = builder.create();
                    alert1.show();
                    alert1.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            if(alertBuilder.getResult().isEmpty())return;
                            Toast.makeText(context, alertBuilder.getResult(),Toast.LENGTH_LONG).show();

                        }
                    });

                }else{
                    final ALertBuilder alertBuilder = new ALertBuilder(USER_ALERT,person,context,isAdmin);
                    final android.app.AlertDialog.Builder builder = alertBuilder.MyBuilder();

                    final AlertDialog alert1 = builder.create();
                    alert1.show();
                    alert1.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            if(alertBuilder.getResult().isEmpty())return;
                            Toast.makeText(context, alertBuilder.getResult(),Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });

        return convertView;

    }
}
