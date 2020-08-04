package com.example.vehiclelicense.ui.v_registration;

import androidx.lifecycle.ViewModelProviders;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vehiclelicense.HelperClass;
import com.example.vehiclelicense.R;

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

public class reg_page2 extends Fragment implements View.OnClickListener {

    private RegPage2ViewModel mViewModel;
    Button previous, submit_form;
    String vehicle_type, vehicle_make, vehicle_capacity,chassis_number,engine_number,plate_number;
    private HelperClass helperClass;
    private EditText vehicle_color, vehicle_year;
    private TextView status_textview;
    ProgressDialog progressDialog;
    String owner_id = "";

    public static reg_page2 newInstance() {
        return new reg_page2();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.reg_page2_fragment, container, false);
        Bundle bundle = getArguments();
        if(bundle != null){
            vehicle_make = bundle.getString("vehicle_make");
            vehicle_type = bundle.getString("vehicle_type");
            vehicle_capacity = bundle.getString("vehicle_capacity");
            engine_number = bundle.getString("engine_number");
            chassis_number = bundle.getString("chassis_number");
            plate_number = bundle.getString("plate_number");
        }

        //from activity
        Bundle bundleActivity = getActivity().getIntent().getExtras();
        owner_id = bundleActivity.getString("owner_id");

        vehicle_color = (EditText) v.findViewById(R.id.vehicle_color);
        vehicle_year = (EditText) v.findViewById(R.id.vehicle_year);
        status_textview = (TextView)v.findViewById(R.id.status_textview);

        previous = (Button) v.findViewById(R.id.previous);
        previous.setOnClickListener(this);

        submit_form = (Button) v.findViewById(R.id.submit_form);
        submit_form.setOnClickListener(this);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Registration in Progress");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(RegPage2ViewModel.class);

        // TODO: Use the ViewModel
    }

    @Override
    public void onClick(View v) {

        EditText fields[] = {vehicle_color, vehicle_year};

        if(v.getId() == R.id.previous){
            getFragmentManager().popBackStackImmediate();
        }
        if(v.getId() == R.id.submit_form){
            if(!helperClass.isEmptyField(fields,status_textview)){
                BackGroundTask backGroundTask = new BackGroundTask();
                backGroundTask.execute(vehicle_make,vehicle_type, vehicle_capacity, engine_number, chassis_number, plate_number, vehicle_color.getText().toString(),vehicle_year.getText().toString(), owner_id);
            }

        }
    }

    class BackGroundTask extends AsyncTask<String, Void, String> {

        String login_url = "";
        Intent intent = null;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            login_url="https://fasa.oauife.edu.ng/vehicle_register.php";
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
                    Toast.makeText(getContext(), "Vehicle Succesfully Registered", Toast.LENGTH_LONG).show();
                    String vehicle_id = jsonObject.getString("vehicle_id");
                    reg_page3 pg3 = new reg_page3();
                    Bundle bundle = new Bundle();
                    bundle.putString("vehicle_id", vehicle_id);
                    bundle.putString("vehicle_type", vehicle_type);
                    bundle.putString("vehicle_make", vehicle_make);
                    bundle.putString("vehicle_capacity", vehicle_capacity);
                    bundle.putString("chassis_number", chassis_number);
                    bundle.putString("engine_number", engine_number);
                    bundle.putString("plate_number", plate_number);
                    pg3.setArguments(bundle);
                    getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, pg3,pg3.getTag() ).addToBackStack(null).commit();
                } else {
                    Toast.makeText(getContext(), valid, Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


//            if(s.equals("success")) {
//
//                Toast.makeText(getContext(), "Vehicle Succesfully Registered", Toast.LENGTH_LONG).show();
//                reg_page3 pg3 = new reg_page3();
//                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, pg3,pg3.getTag() ).addToBackStack(null).commit();
//
//            } else {
//                Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();
//            }

        }

        @Override
        protected String doInBackground(String... params) {
            String vehicle_make = params[0];
            String vehicle_type = params[1];
            String vehicle_capacity = params[2];
            String engine_number = params[3];
            String chassis_number = params[4];
            String plate_number = params[5];
            String vehicle_color = params[6];
            String vehicle_year = params[7];
            String owner_id = params[8];

            try {
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String post_data = URLEncoder.encode("vehicle_make","UTF-8")+"="+URLEncoder.encode(vehicle_make,"UTF-8")+"&"+
                        URLEncoder.encode("vehicle_type","UTF-8")+"="+URLEncoder.encode(vehicle_type,"UTF-8")+"&"+
                        URLEncoder.encode("vehicle_capacity","UTF-8")+"="+URLEncoder.encode(vehicle_capacity,"UTF-8")+"&"+
                        URLEncoder.encode("engine_number","UTF-8")+"="+URLEncoder.encode(engine_number,"UTF-8")+"&"+
                        URLEncoder.encode("chassis_number","UTF-8")+"="+URLEncoder.encode(chassis_number,"UTF-8")+"&"+
                        URLEncoder.encode("plate_number","UTF-8")+"="+URLEncoder.encode(plate_number,"UTF-8")+"&"+
                        URLEncoder.encode("vehicle_color","UTF-8")+"="+URLEncoder.encode(vehicle_color,"UTF-8")+"&"+
                        URLEncoder.encode("vehicle_year","UTF-8")+"="+URLEncoder.encode(vehicle_year,"UTF-8")+"&"+
                        URLEncoder.encode("owner_id","UTF-8")+"="+URLEncoder.encode(owner_id,"UTF-8");

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
