package com.example.vehiclelicense.ui.vehiclerenewal;

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

public class VehicleRenewalFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private VehicleRenewalViewModel vehicleRenewalViewModel;
    private Spinner renewalspinner_vid;
    String vehicles_id[] = {};
    String selected_vehicle_id = "";
    private Button proceed_renewal_btn;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        vehicleRenewalViewModel =
                ViewModelProviders.of(this).get(VehicleRenewalViewModel.class);
        View root = inflater.inflate(R.layout.fragment_renewal, container, false);

        renewalspinner_vid = (Spinner) root.findViewById(R.id.renewal_spinner_vid);


        Bundle bundleActivity = getActivity().getIntent().getExtras();
        vehicles_id = bundleActivity.getStringArray("vehicles_id");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, vehicles_id);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        renewalspinner_vid.setAdapter(arrayAdapter);
        renewalspinner_vid.setOnItemSelectedListener(this);

        proceed_renewal_btn = (Button) root.findViewById(R.id.proceedrenewal_btn);
        proceed_renewal_btn.setOnClickListener(this);

        if(vehicles_id.length == 0){
            proceed_renewal_btn.setEnabled(false);
        } else {
            selected_vehicle_id = vehicles_id[0];
        }

//        final TextView textView = root.findViewById(R.id.text_slideshow);
//        vehicleRenewalViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if(parent.getId() == R.id.renewal_spinner_vid){
            selected_vehicle_id = vehicles_id[position];
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.proceedrenewal_btn){
            PaymentProcessor paymentProcessor = new PaymentProcessor();
            Bundle bundle = new Bundle();
            bundle.putString("selected_vehicle_id", selected_vehicle_id);
            bundle.putString("selected_vehicle_operation", "renewal");
            paymentProcessor.setArguments(bundle);
            getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, paymentProcessor,paymentProcessor.getTag() ).addToBackStack(null).commit();
        }
    }
}