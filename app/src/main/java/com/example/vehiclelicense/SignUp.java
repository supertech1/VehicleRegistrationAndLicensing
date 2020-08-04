package com.example.vehiclelicense;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

public class SignUp extends AppCompatActivity {

    private EditText fullname, emailaddress, phonenumber, password, confirmpassword, drivers_license;
    private Button signup_btn;
    private TextView signup_status;
    ProgressDialog progressDialog;
    private HelperClass helperClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Toolbar toolbar = (Toolbar)findViewById(R.id.signup_toolbar);
        setSupportActionBar(toolbar);
        setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        fullname = (EditText)findViewById(R.id.fullname);
        emailaddress = (EditText)findViewById(R.id.emailaddress);
        phonenumber = (EditText)findViewById(R.id.phonenumber);
        password = (EditText)findViewById(R.id.signup_password);
        confirmpassword = (EditText)findViewById(R.id.confirmpassword);
        drivers_license = (EditText)findViewById(R.id.drivers_license);
        signup_status = (TextView) findViewById(R.id.signup_status);

        signup_btn = (Button) findViewById(R.id.signup_btn);
        SignupActivity_Button_Handler signupActivity_button_handler = new SignupActivity_Button_Handler();
        signup_btn.setOnClickListener(signupActivity_button_handler);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Processing");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        finish();
        return true;
    }

    private class SignupActivity_Button_Handler implements View.OnClickListener{
        Intent intent = null;
        @Override
        public void onClick(View v) {

            if(v.getId() == R.id.signup_btn){
                String name = fullname.getText().toString();
                String email = emailaddress.getText().toString();
                String number = phonenumber.getText().toString();
                String dLicense = drivers_license.getText().toString();
                String pw = password.getText().toString();
                String pw2 = confirmpassword.getText().toString();


                //check if username and pw is not empty
                EditText[] fields = {fullname, emailaddress, phonenumber, drivers_license, password, confirmpassword};
                if(!helperClass.isEmptyField(fields, signup_status)){
                    if(!pw.equals(pw2)) signup_status.setText("Password not the same");
                    else {
                        SignUp.BackGroundTask backGroundTask = new SignUp.BackGroundTask();
                        backGroundTask.execute(name, email, number, dLicense, pw);
                    }

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
            signup_btn.setEnabled(false);
            login_url="https://fasa.oauife.edu.ng/signup_script.php";
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
            Intent intent = new Intent(SignUp.this, LoginActivity.class);

            //check result to know if credential is valid
            if(s.equals("success")) {
                Toast.makeText(getApplicationContext(), "User's Registration Successful, Please Login", Toast.LENGTH_LONG).show();
                finish();
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
            }

        }

        @Override
        protected String doInBackground(String... params) {
            String fullname = params[0];
            String emailaddress = params[1];
            String phonenumber = params[2];
            String drivers_license = params[3];
            String password = params[4];

            try {
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String post_data = URLEncoder.encode("fullname","UTF-8")+"="+URLEncoder.encode(fullname,"UTF-8")+"&"+
                        URLEncoder.encode("emailaddress","UTF-8")+"="+URLEncoder.encode(emailaddress,"UTF-8")+"&"+
                        URLEncoder.encode("phonenumber","UTF-8")+"="+URLEncoder.encode(phonenumber,"UTF-8")+"&"+
                        URLEncoder.encode("drivers_license","UTF-8")+"="+URLEncoder.encode(drivers_license,"UTF-8")+"&"+
                        URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8");

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
