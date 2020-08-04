package com.example.vehiclelicense.ui.v_payment_history;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.vehiclelicense.R;
import com.example.vehiclelicense.ui.payment_gateway.PaymentProcessor;
import com.example.vehiclelicense.ui.payment_gateway.PaymentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class V_PAYMENT_HISTORY extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener  {

    private VPaymentHistoryViewModel mViewModel;
    private Spinner certificate_spinner_vid;
    String vehicles_id[] = {};
    String selected_vehicle_id = "";
    int selected_position =0;
    private Button view_certificate;
    JSONArray vehicles = null;

    public static V_PAYMENT_HISTORY newInstance() {
        return new V_PAYMENT_HISTORY();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.v_payment_history_fragment, container, false);

        certificate_spinner_vid = (Spinner) root.findViewById(R.id.certificate_renewal_spinner_vid);
        view_certificate = (Button) root.findViewById(R.id.viewcertificate_btn);


        Bundle bundleActivity = getActivity().getIntent().getExtras();
        vehicles_id = bundleActivity.getStringArray("vehicles_id");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, vehicles_id);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        certificate_spinner_vid.setAdapter(arrayAdapter);
        certificate_spinner_vid.setOnItemSelectedListener(this);

        view_certificate = (Button) root.findViewById(R.id.viewcertificate_btn);
        view_certificate.setOnClickListener(this);

        if(vehicles_id.length == 0){
            view_certificate.setEnabled(false);
        } else {
            selected_vehicle_id = vehicles_id[0];
        }

        try {
            vehicles = new JSONArray(bundleActivity.getString("vehicles"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(VPaymentHistoryViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if(parent.getId() == R.id.certificate_renewal_spinner_vid){
            selected_vehicle_id = vehicles_id[position];
            selected_position = position;
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.viewcertificate_btn){
            PaymentResult paymentResult = new PaymentResult();
            Bundle bundle = getActivity().getIntent().getExtras();
            try {
                JSONObject vehicle = vehicles.getJSONObject(selected_position);
                bundle.putString("vehicle_id", selected_vehicle_id);
                bundle.putString("fullname", bundle.getString("fullname"));
                bundle.putString("plate_number", vehicle.getString("plate_number"));
                bundle.putString("chassis_number", vehicle.getString("chassis_number"));
                bundle.putString("engine_number", vehicle.getString("engine_number"));
                bundle.putString("vehicle_capacity", vehicle.getString("vehicle_capacity"));
                bundle.putString("vehicle_make", vehicle.getString("vehicle_make"));
                bundle.putString("vehicle_type", vehicle.getString("vehicle_type"));
                bundle.putString("vehicle_color", vehicle.getString("vehicle_color"));
                bundle.putString("vehicle_year", vehicle.getString("vehicle_year"));
                bundle.putString("vehicle_expiry_date", vehicle.getString("vehicle_expiry_date"));
                bundle.putString("latest_payment_id", vehicle.getString("latest_payment_id"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            paymentResult.setArguments(bundle);
            getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, paymentResult,paymentResult.getTag() ).addToBackStack(null).commit();
        }
    }

}
