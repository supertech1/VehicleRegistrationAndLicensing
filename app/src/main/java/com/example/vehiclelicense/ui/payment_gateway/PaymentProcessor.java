package com.example.vehiclelicense.ui.payment_gateway;

import androidx.lifecycle.ViewModelProviders;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vehiclelicense.HelperClass;
import com.example.vehiclelicense.MakePayment;
import com.example.vehiclelicense.R;
import com.example.vehiclelicense.ui.v_registration.reg_page2;
import com.example.vehiclelicense.ui.v_registration.reg_page3;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import co.paystack.android.Paystack;
import co.paystack.android.PaystackSdk;
import co.paystack.android.Transaction;
import co.paystack.android.model.Card;
import co.paystack.android.model.Charge;

import static android.content.Context.MODE_PRIVATE;

public class PaymentProcessor extends Fragment implements View.OnClickListener {

    private PaymentProcessorViewModel mViewModel;
    private EditText card_number, month, year, cvc, pin;
    private TextView status_textview;
    private Button pay_btn;
    ProgressDialog progressDialog;

    private HelperClass helperClass;

    private String selected_vehicle_id, selected_vehicle_operation;


    public static PaymentProcessor newInstance() {
        return new PaymentProcessor();
    }
    Card card = null;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.payment_processor_fragment, container, false);

        card_number = (EditText) root.findViewById(R.id.card_number);
        month = (EditText) root.findViewById(R.id.month);
        year = (EditText) root.findViewById(R.id.year);
        cvc = (EditText) root.findViewById(R.id.cvc);
        pin = (EditText) root.findViewById(R.id.pin);

        status_textview = (TextView) root.findViewById(R.id.status_view) ;

        pay_btn = (Button) root.findViewById(R.id.pay_btn);
        pay_btn.setOnClickListener(this);

        PaystackSdk.initialize(getActivity().getApplicationContext());

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Processing Payment");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        Bundle bundle = getArguments();
        if(bundle != null){
            selected_vehicle_id = bundle.getString("selected_vehicle_id");
            selected_vehicle_operation = bundle.getString("selected_vehicle_operation");
        }


        Toast.makeText(getContext(),selected_vehicle_id,Toast.LENGTH_LONG).show();

        return root;
    }

    @Override
    public void onClick(View v) {


        if(v.getId() == R.id.pay_btn) {


            EditText fields[] = {card_number, month, year, cvc, pin};
            if(!helperClass.isEmptyField(fields,status_textview)){
                String cardNumber = card_number.getText().toString();
                int expiryMonth = Integer.parseInt(month.getText().toString());
                int expiryYear = Integer.parseInt(year.getText().toString());
                String cvv = cvc.getText().toString();

                card = new Card(cardNumber, expiryMonth, expiryYear, cvv);
                //check if card is valid
                if(card.isValid()){
                    Toast.makeText(getContext(), "Card detail is valid", Toast.LENGTH_SHORT).show();
//                SharedPreferences pref = getContext().getSharedPreferences("pref_file", MODE_PRIVATE);
//                SharedPreferences.Editor edit = pref.edit();
//
//                edit.putString("trans_status", "");
//                edit.putString("trans_ref", "");
//                edit.commit();

                    BackGroundTask backGroundTask = new BackGroundTask();
                    backGroundTask.execute();
                } else {
                    Toast.makeText(getContext(), "Card detail is not valid", Toast.LENGTH_SHORT).show();
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
            login_url="https://fasa.oauife.edu.ng/process_payment.php";
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

        public void performCharge(){
            Charge charge = new Charge();
            charge.setCard(card);
            charge.setAmount(30000);
            charge.setCurrency("NGN");
            charge.setEmail("tosiano4real10@gmail.com");


            TransactionPaystack transactionPaystack = new TransactionPaystack();



            PaystackSdk.chargeCard(getActivity(),charge, transactionPaystack);


        }

        class TransactionPaystack implements Paystack.TransactionCallback {



            @Override
            public void onSuccess(Transaction transaction) {

                Toast.makeText(getContext(), "Amount deducted succesfully", Toast.LENGTH_LONG).show();
                try {
                    FileOutputStream fileOutputStream = getActivity().openFileOutput("ref_file.txt", MODE_PRIVATE);
                    fileOutputStream.write(transaction.getReference().getBytes());
                    fileOutputStream.close();


                } catch (FileNotFoundException e){
                    e.printStackTrace();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void beforeValidate(Transaction transaction) {

            }

            @Override
            public void onError(Throwable error, Transaction transaction) {
                Toast.makeText(getContext(), "Failure", Toast.LENGTH_LONG).show();
                try {
                    FileOutputStream fileOutputStream = getActivity().openFileOutput("ref_file.txt", MODE_PRIVATE);
                    fileOutputStream.write(transaction.getReference().getBytes());
                    fileOutputStream.close();

                } catch (FileNotFoundException e){
                    e.printStackTrace();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        }



        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();

            //check result to know if credential is valid
            try {
                if(s != null) {
                    JSONObject jsonObject = new JSONObject(s);
                    String valid = jsonObject.getString("valid");
                    if (valid.equals("yes")) {
                        Toast.makeText(getContext(), "Payment sucessfully done", Toast.LENGTH_LONG).show();
                        JSONObject obj = jsonObject.getJSONObject("vehicle_detail");
                        JSONObject owner = jsonObject.getJSONObject("owner_detail");
                        PaymentResult paymentResult = new PaymentResult();
                        Bundle bundle = new Bundle();
                        bundle.putString("vehicle_id", obj.getString("vehicle_id"));
                        bundle.putString("fullname", owner.getString("fullname"));
                        bundle.putString("plate_number", obj.getString("plate_number"));
                        bundle.putString("chassis_number", obj.getString("chassis_number"));
                        bundle.putString("engine_number", obj.getString("engine_number"));
                        bundle.putString("vehicle_capacity", obj.getString("vehicle_capacity"));
                        bundle.putString("vehicle_make", obj.getString("vehicle_make"));
                        bundle.putString("vehicle_type", obj.getString("vehicle_type"));
                        bundle.putString("vehicle_color", obj.getString("vehicle_color"));
                        bundle.putString("vehicle_year", obj.getString("vehicle_year"));
                        bundle.putString("vehicle_expiry_date", obj.getString("vehicle_expiry_date"));
                        bundle.putString("latest_payment_id", obj.getString("latest_payment_id"));
                        paymentResult.setArguments(bundle);
                        getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, paymentResult,paymentResult.getTag() ).addToBackStack(null).commit();
                    } else {
                        Toast.makeText(getContext(), valid, Toast.LENGTH_LONG).show();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }



        }

        @Override
        protected String doInBackground(String... params) {


            performCharge();
//            SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("pref_file", MODE_PRIVATE);
//            String trans_stat = pref.getString("tr_status", "def");
//            String trans_ref = pref.getString("tr_ref","def");

            try {
                FileInputStream fileInputStream = getActivity().openFileInput("ref_file.txt");
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                BufferedReader b = new BufferedReader(inputStreamReader);
                String trans_ref ="";
                String trans_msg = "Success";
                String lines = "";
                while( (lines = b.readLine()) != null ){
                    trans_ref +=lines;
                }



                URL url = new URL("https://fasa.oauife.edu.ng/process_payment.php");
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String post_data = URLEncoder.encode("vehicle_id","UTF-8")+"="+URLEncoder.encode(selected_vehicle_id,"UTF-8")+"&"+
                        URLEncoder.encode("payment_ref","UTF-8")+"="+URLEncoder.encode(trans_ref,"UTF-8")+"&"+
                        URLEncoder.encode("transaction_status","UTF-8")+"="+URLEncoder.encode(trans_msg,"UTF-8");

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
//                PaymentResult paymentResult = new PaymentResult();
//                Bundle bundle = new Bundle();
//                bundle.putString("payment_id", trans_ref);
//                bundle.putString("selected_vehicle_id", selected_vehicle_id);
//                bundle.putString("selected_vehicle_operation",selected_vehicle_operation);
//                paymentResult.setArguments(bundle);
//                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, paymentResult,paymentResult.getTag() ).addToBackStack(null).commit();
                return  result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e){
                e.printStackTrace();
            }

            catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(PaymentProcessorViewModel.class);
        // TODO: Use the ViewModel
    }

}
