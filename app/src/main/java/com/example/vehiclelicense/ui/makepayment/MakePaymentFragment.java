package com.example.vehiclelicense.ui.makepayment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.vehiclelicense.R;
import com.example.vehiclelicense.ui.payment_gateway.PaymentProcessor;

public class MakePaymentFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private MakePaymentViewModel makePaymentViewModel;
    private Spinner operation_spinner, vehicle_id_spinner;
    String vehicles_id[] = {};
    private Button proceed_payment_btn;
    String selected_vehicle_id = "", selected_vehicle_operation = "";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        makePaymentViewModel =
                ViewModelProviders.of(this).get(MakePaymentViewModel.class);
        View root = inflater.inflate(R.layout.fragment_makepayment, container, false);
//        makePaymentViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        operation_spinner = (Spinner) root.findViewById(R.id.operation_spinner);
        vehicle_id_spinner = (Spinner) root.findViewById(R.id.vehicle_id_spinner);

        //from activity
        Bundle bundleActivity = getActivity().getIntent().getExtras();
        vehicles_id = bundleActivity.getStringArray("vehicles_id");




        String operations[] = {"Vehicle Registration Payment", "Vehicle Renewal Payment"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, operations);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        operation_spinner.setAdapter(arrayAdapter);
        operation_spinner.setOnItemSelectedListener(this);

        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, vehicles_id);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        vehicle_id_spinner.setAdapter(arrayAdapter2);
        vehicle_id_spinner.setOnItemSelectedListener(this);

        proceed_payment_btn = (Button)root.findViewById(R.id.proceed_payment_btn);
        proceed_payment_btn.setOnClickListener(this);

        if(vehicles_id.length == 0){
            proceed_payment_btn.setEnabled(false);
        } else {
            selected_vehicle_id = vehicles_id[0];
            selected_vehicle_operation = "registration";
        }


        return root;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if(parent.getId() == R.id.vehicle_id_spinner){
            selected_vehicle_id = vehicles_id[position];
        } else if(parent.getId() == R.id.operation_spinner){
            if(position == 0)
                selected_vehicle_operation = "registration";
            if(position == 1)
                selected_vehicle_operation = "renewal";
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.proceed_payment_btn){
            PaymentProcessor paymentProcessor = new PaymentProcessor();
            Bundle bundle = new Bundle();
            bundle.putString("selected_vehicle_id", selected_vehicle_id);
            bundle.putString("selected_vehicle_operation", selected_vehicle_operation);
            paymentProcessor.setArguments(bundle);
            getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, paymentProcessor,paymentProcessor.getTag() ).addToBackStack(null).commit();
        }
    }
}