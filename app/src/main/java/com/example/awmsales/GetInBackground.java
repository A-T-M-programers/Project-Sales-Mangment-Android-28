package com.example.awmsales;


import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.example.awmsales.static_class.SERVER_LINK;

public class GetInBackground extends AsyncTask<String, String, String> {

    HttpURLConnection urlConnection;

    @Override
    protected String doInBackground(String... args) {

        StringBuilder result = new StringBuilder();

        Integer i=0;
        String string_url = SERVER_LINK + "?";
        while (i<args.length){
            string_url += args[i++];
            string_url += "=";
            string_url += args[i];
            string_url += "&";
            i++;
        }
        string_url = string_url.substring(0,string_url.length()-1);

        try {
            URL url = new URL(string_url);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

        }catch( Exception e) {
            e.printStackTrace();
        }
        finally {
            urlConnection.disconnect();
        }


        return result.toString();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}