package com.example.vehiclelicense;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

import co.paystack.android.Transaction;

public class LoginActivity extends AppCompatActivity {

    private Button forgot_password_btn, login_btn;
    private EditText username, password;
    private TextView status_textview;

    ProgressDialog progressDialog;

    ConnectivityManager connectivityManager;
    NetworkInfo networkInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        LoginActivity_Button_Handler loginActivity_button_handler = new LoginActivity_Button_Handler();

        forgot_password_btn = (Button) findViewById(R.id.forgot_password_btn);
        forgot_password_btn.setOnClickListener(loginActivity_button_handler);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);

        login_btn = (Button) findViewById(R.id.login_btn);
        login_btn.setOnClickListener(loginActivity_button_handler);
        status_textview = (TextView) findViewById(R.id.status_textview);


        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();

        //check is user is connected
        if(networkInfo != null && networkInfo.isConnected()){
            login_btn.setEnabled(true);
        }
        else {
            status_textview.setText("No internet connectivity");

        }

        registerReceiver(new ConnectivityChangeReceiver(),
                new IntentFilter(connectivityManager.CONNECTIVITY_ACTION));


        SharedPreferences pref = getApplicationContext().getSharedPreferences("pref_file", MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        edit.putString("tr_status", "");
        edit.putString("tr_ref", "");
        edit.commit();

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Authenticating");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        finish();
        return true;
    }

    private class LoginActivity_Button_Handler implements View.OnClickListener{
        Intent intent = null;
        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.forgot_password_btn){
                intent = new Intent(LoginActivity.this, ForgotPassword.class);
                startActivity(intent);
            }

            if(v.getId() == R.id.login_btn){
                String user = username.getText().toString();
                String pw = password.getText().toString();


                //check if username and pw is not empty
                if(user.equals("") || pw.equals("")){
                    if (user.equals("")) status_textview.setText("Please enter your username");
                    else if(pw.equals("")) status_textview.setText("Please enter your password");


                } else {

                    BackGroundTask backGroundTask = new BackGroundTask();
                    backGroundTask.execute(user, pw);
                }


            }
        }
    }

    class BackGroundTask extends AsyncTask<String, Void, String> {

        String login_url = "";
        Intent intent = null;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            login_url="https://fasa.oauife.edu.ng/login_script.php";
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
                    password.setText("");
                    JSONObject user = jsonObject.getJSONObject("record");
                    String owner_id = user.getString("owner_id");
                    String fullname = user.getString("fullname");
                    String emailaddress = user.getString("emailaddress");
                    String phonenumber = user.getString("phonenumber");
                    String drivers_license = user.getString("drivers_license");
                    String count = jsonObject.getString("count");
                    JSONArray vehicles = null;
                    String v_id[] = {};
                    if(jsonObject.get("vehicles_id") != null){
                        JSONArray vehicles_id = (JSONArray) jsonObject.get("vehicles_id");
                        vehicles = (JSONArray) jsonObject.get("vehicles");
                        Log.d("DEBUGT", "onPostExecute: "+vehicles_id);
                        v_id = new String[vehicles_id.length()];
                        if(vehicles_id != null){
                            int length = vehicles_id.length();
                            for(int i = 0; i < length; i++){
                                v_id[i] = vehicles_id.getString(i);
                            }
                        }
                    }

                    int active_count = Integer.parseInt(jsonObject.getString("active_count"));
                    int expired_count = Integer.parseInt(jsonObject.getString("expired_count"));

                    int vehicle_count = Integer.parseInt(count);
                    String license_status = "";
                    if(active_count == 0 && expired_count == 0){
                        license_status = "No vehicle Licensed yet";
                    } else if(vehicle_count == 1) {
                        if(active_count == 1){
                            license_status = "1 Vehicle Active";
                        } else {
                            license_status = "1 Vehicle Expired";
                        }
                    } else {
                        license_status = active_count +" Vehicle Active, "+expired_count+ " Vehicle Expired";
                    }

                    intent = new Intent(getApplicationContext(), Dashboard.class);
                    intent.putExtra("owner_id", owner_id);
                    intent.putExtra("fullname", fullname);
                    intent.putExtra("emailaddress", emailaddress);
                    intent.putExtra("phonenumber", phonenumber);
                    intent.putExtra("drivers_license", drivers_license);
                    intent.putExtra("count", count);
                    intent.putExtra("vehicles_id", v_id);
                    intent.putExtra("license_status", license_status);
                    intent.putExtra("vehicles", vehicles.toString());
                    startActivity(intent);
                } else {
                    login_btn.setEnabled(true);
                    status_textview.setText(valid);
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
            String user = params[0];
            String pw = params[1];

            try {
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String post_data = URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(user,"UTF-8")+"&"+
                        URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(pw,"UTF-8");

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
