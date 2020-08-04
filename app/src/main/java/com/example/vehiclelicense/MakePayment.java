package com.example.vehiclelicense;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import co.paystack.android.Paystack;
import co.paystack.android.PaystackSdk;
import co.paystack.android.Transaction;
import co.paystack.android.model.Card;
import co.paystack.android.model.Charge;

public class MakePayment extends AppCompatActivity {
    Card card = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_payment);

        PaystackSdk.initialize(getApplicationContext());

        String cardNumber = "4084084084084081";
        int expiryMonth = 11;
        int expiryYear = 21;
        String cvv = "408";

        card = new Card(cardNumber, expiryMonth, expiryYear, cvv);

        //check if card is valid
        if(card.isValid()){
            Toast.makeText(MakePayment.this, "Card detail is valid", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MakePayment.this, "Card detail is not valid", Toast.LENGTH_SHORT).show();
        }

        performCharge();

    }

    public void performCharge(){
        Charge charge = new Charge();
        charge.setCard(card);
        charge.setAmount(30000);
        charge.setCurrency("NGN");
        charge.setEmail("tosiano4real10@gmail.com");



        PaystackSdk.chargeCard(MakePayment.this,charge, new Paystack.TransactionCallback(){
            @Override
            public void onSuccess(Transaction transaction) {
                Toast.makeText(MakePayment.this, "Amount deducted successfully", Toast.LENGTH_LONG).show();

            }

            @Override
            public void beforeValidate(Transaction transaction) {

            }

            @Override
            public void onError(Throwable error, Transaction transaction) {
                Toast.makeText(MakePayment.this, "Error occured making payment", Toast.LENGTH_LONG).show();
                Log.d("TAG", "onError: error occured : "+error);
            }
        });

    }
}
