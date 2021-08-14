package com.example.awmsales;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.telecom.Call;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

import static com.example.awmsales.static_class.AUTHEN;
import static com.example.awmsales.static_class.EMAIL;
import static com.example.awmsales.static_class.SERVER_LINK;


class PostInBackground extends AsyncTask<String, String, String> {

    public String result = "";
    public String data = "";
    public HttpURLConnection http;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected String doInBackground(String... strings) {

        Integer i = 0;

        while (i < strings.length) {
            try {
                data += URLEncoder.encode(strings[i++], "utf-8");
                data += "=";
                data += URLEncoder.encode(strings[i], "utf-8");
                data += "&";
                i++;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        Log.e("Data ",data);
        data = data.substring(0,data.length()-1);
        result = makePost(SERVER_LINK, data);

        return result;
    }


    public String makePost(String strUrl, String data) {
        String returnData = "";
        try {
            URL url = new URL(strUrl);
            http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("POST");
            http.setDoInput(true);
            http.setDoOutput(true);
            http.setReadTimeout(100000);
            http.setReadTimeout(20000);
            http.connect();

            OutputStream output = http.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output));

            writer.write(data);
            writer.flush();
            writer.close();
            output.close();

            InputStream input = new BufferedInputStream(http.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(input, "ISO-8859-1"));
            String line = "";
            while (line != null) {
                line = reader.readLine();
                returnData += line;
            }

            reader.close();
            input.close();
            http.disconnect();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnData;
    }
}

