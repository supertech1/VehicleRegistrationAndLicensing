package com.example.vehiclelicense;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class Discover extends AppCompatActivity implements View.OnClickListener {

    private TextView vehicle_id_tv, vehicle_make_tv, vehicle_color_tv, engine_number_tv, chassis_number_tv, vehicle_owner_tv,license_status_tv, status_tv;
    private Button view_detail_btn;
    private LinearLayout layout_content;
    private EditText value;

    ConnectivityManager connectivityManager;
    NetworkInfo networkInfo;

    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover);
        Toolbar toolbar = (Toolbar)findViewById(R.id.discover_toolbar);
        setSupportActionBar(toolbar);
        setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        vehicle_id_tv = (TextView) findViewById(R.id.vehicle_id_tv);
        vehicle_make_tv = (TextView) findViewById(R.id.vehicle_make_tv);
        vehicle_color_tv = (TextView) findViewById(R.id.vehicle_color_tv);
        engine_number_tv = (TextView) findViewById(R.id.engine_number_tv);
        chassis_number_tv = (TextView) findViewById(R.id.chassis_number_tv);
        vehicle_owner_tv = (TextView) findViewById(R.id.vehicle_owner_tv);
        license_status_tv =(TextView)findViewById(R.id.license_status_tv);
        status_tv = (TextView) findViewById(R.id.status_tv);
        value = (EditText) findViewById(R.id.lookup_value);

        view_detail_btn = (Button) findViewById(R.id.view_detail_btn);
        view_detail_btn.setOnClickListener(this);



        layout_content = (LinearLayout) findViewById(R.id.layout_content);
        layout_content.setVisibility(View.INVISIBLE);



        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();

        //check is user is connected
        if(networkInfo != null && networkInfo.isConnected()){
            view_detail_btn.setEnabled(true);
        }
        else {

            layout_content.setVisibility(View.INVISIBLE);
            status_tv.setText("No internet connectivity");
            view_detail_btn.setEnabled(false);

        }

//        registerReceiver(new ConnectivityChangeReceiver(),
//                new IntentFilter(connectivityManager.CONNECTIVITY_ACTION));




        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Fetching Vehicle Details");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.view_detail_btn){
            String lookup_value = value.getText().toString();


            //check if username and pw is not empty
            if(lookup_value.equals("")){
                status_tv.setText("Please enter a value");

            } else {

                BackGroundTask backGroundTask = new BackGroundTask();
                backGroundTask.execute(lookup_value);
            }


        }

    }

    class BackGroundTask extends AsyncTask<String, Void, String> {

        String login_url = "";
        Intent intent = null;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            login_url="https://fasa.oauife.edu.ng/discover_script.php";
            progressDialog.show();
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {


                    try {
                        Thread.sleep(500);
                    } catch (Exception e) {


                    }
                }
            });
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();

            //check result to know if credential is valid
            try {
                JSONObject jsonObject = new JSONObject(s);
                String valid = jsonObject.getString("valid");
                if(valid.equals("yes")){
                    JSONObject user = jsonObject.getJSONObject("vehicle_detail");
                    String vehicle_id = user.getString("vehicle_id");
                    String vehicle_make = user.getString("vehicle_make");
                    String vehicle_color = user.getString("vehicle_color");
                    String engine_number = user.getString("engine_number");
                    String chassis_number = user.getString("chassis_number");

                    JSONObject owner = jsonObject.getJSONObject("vehicle_owner");

                    String fullname = owner.getString("fullname");

                    status_tv.setText("");
                    vehicle_id_tv.setText(vehicle_id);
                    vehicle_make_tv.setText(vehicle_make);
                    vehicle_color_tv.setText(vehicle_color);
                    engine_number_tv.setText(engine_number);
                    chassis_number_tv.setText(chassis_number);
                    vehicle_owner_tv.setText(fullname);
                    license_status_tv.setText("Active");

                    String expire_status = user.getString("expire_status");
                    if (expire_status.equals("")){
                        license_status_tv.setText("INACTIVE");
                    } else {
                        license_status_tv.setText(expire_status.toUpperCase());
                    }


                    layout_content.setVisibility(View.VISIBLE);


                } else {
                    view_detail_btn.setEnabled(true);
                    status_tv.setText("No record found");
                    layout_content.setVisibility(View.INVISIBLE);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


//            if(s.equals("valid")) {
//                password.setText("");
//                intent = new Intent(getApplicationContext(), Dashboard.class);
//                startActivity(intent);
//            } else {
//                login_btn.setEnabled(true);
//                status_textview.setText(s);
//            }

        }

        @Override
        protected String doInBackground(String... params) {
            String value = params[0];


            try {
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String post_data = URLEncoder.encode("value","UTF-8")+"="+URLEncoder.encode(value,"UTF-8");

                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();


                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"ISO8859-1"));
                String result = "";
                String line = "";

                while((line = bufferedReader.readLine()) != null) {
                    result += line;

                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}
