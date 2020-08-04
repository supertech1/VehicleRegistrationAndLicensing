package com.example.vehiclelicense.ui.vehicle_detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.vehiclelicense.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class VehicleDetailFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private VehicleDetailViewModel vehicleDetailViewModel;
    private Spinner spinner;
    private TextView vehicle_id_tv, plate_number_tv, vehicle_make_tv, vehicle_type_tv, vehicle_capacity_tv, engine_number_tv, chassis_number_tv, vehicle_color_tv, vehicle_year_tv, status_tv, license_status;
    String vehicles_id[] = {};
    private ScrollView vehicle_detail_scrollview;
    JSONArray vehicles = null;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        vehicleDetailViewModel =
                ViewModelProviders.of(this).get(VehicleDetailViewModel.class);
        View root = inflater.inflate(R.layout.fragment_vehicle_detail, container, false);

        vehicle_id_tv = (TextView) root.findViewById(R.id.vehicle_id);
        plate_number_tv =(TextView) root.findViewById(R.id.plate_number);
        vehicle_make_tv = (TextView) root.findViewById(R.id.vehicle_make);
        vehicle_type_tv = (TextView) root.findViewById(R.id.vehicle_type);
        vehicle_capacity_tv = (TextView) root.findViewById(R.id.vehicle_capacity);
        engine_number_tv = (TextView) root.findViewById(R.id.engine_number);
        chassis_number_tv = (TextView) root.findViewById(R.id.chassis_number);
        vehicle_color_tv = (TextView) root.findViewById(R.id.vehicle_color);
        vehicle_year_tv = (TextView) root.findViewById(R.id.vehicle_year);
        vehicle_detail_scrollview = (ScrollView) root.findViewById(R.id.vehicle_detail_scrollview);
        status_tv = (TextView) root.findViewById(R.id.status_tv);
        license_status = (TextView) root.findViewById(R.id.license_status) ;
        //from activity
        Bundle bundleActivity = getActivity().getIntent().getExtras();
        vehicles_id = bundleActivity.getStringArray("vehicles_id");
        if(vehicles_id.length == 0){
            vehicle_detail_scrollview.setVisibility(View.INVISIBLE);
            status_tv.setText("NO VEHICLE REGISTERED YET");
        }
        try {
            vehicles = new JSONArray(bundleActivity.getString("vehicles"));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        spinner = (Spinner) root.findViewById(R.id.vehicle_id_spinner);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, vehicles_id);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(this);

//        final TextView textView = root.findViewById(R.id.text_share);
//        vehicleDetailViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        TextView v = (TextView) view;
        String text = v.getText().toString();
        if(vehicles_id != null && vehicles_id.length >0){
            try {
                JSONObject vehicle = vehicles.getJSONObject(position);
                vehicle_id_tv.setText(vehicle.getString("vehicle_id"));
                plate_number_tv.setText(vehicle.getString("plate_number"));
                vehicle_make_tv.setText(vehicle.getString("vehicle_make"));
                vehicle_type_tv.setText(vehicle.getString("vehicle_type"));
                vehicle_capacity_tv.setText(vehicle.getString("vehicle_capacity"));
                engine_number_tv.setText(vehicle.getString("engine_number"));
                chassis_number_tv.setText(vehicle.getString("chassis_number"));
                vehicle_color_tv.setText(vehicle.getString("vehicle_color"));
                vehicle_year_tv.setText(vehicle.getString("vehicle_year"));
                String expire_status = vehicle.getString("expire_status");
                if (expire_status.equals("")){
                    license_status.setText("INACTIVE");
                } else {
                    license_status.setText(expire_status.toUpperCase());
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Toast.makeText(getContext(),text,Toast.LENGTH_LONG).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}