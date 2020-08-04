package com.example.vehiclelicense.ui.v_registration;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.vehiclelicense.R;
import com.example.vehiclelicense.ui.home.HomeFragment;

public class reg_page3 extends Fragment {

    private RegPage3ViewModel mViewModel;

    String vehicle_type, vehicle_make, vehicle_capacity,chassis_number,engine_number,plate_number, vehicle_id;
    private TextView vehicle_id_tv, vehicle_make_tv, vehicle_type_tv, vehicle_capacity_tv,engine_number_tv,chassis_number_tv,plate_number_tv;
    public static reg_page3 newInstance() {
        return new reg_page3();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.reg_page3_fragment, container, false);

        vehicle_id_tv = (TextView) v.findViewById(R.id.vehicle_id);
        vehicle_make_tv = (TextView) v.findViewById(R.id.vehicle_make);
        vehicle_type_tv = (TextView) v.findViewById(R.id.vehicle_type);
        vehicle_capacity_tv = (TextView) v.findViewById(R.id.vehicle_capacity);
        engine_number_tv = (TextView) v.findViewById(R.id.engine_number);
        chassis_number_tv = (TextView) v.findViewById(R.id.chassis_number);
        plate_number_tv = (TextView) v.findViewById(R.id.plate_number);


        Bundle bundle = getArguments();
        if(bundle != null){
            vehicle_id = bundle.getString("vehicle_id");
            vehicle_make = bundle.getString("vehicle_make");
            vehicle_type = bundle.getString("vehicle_type");
            vehicle_capacity = bundle.getString("vehicle_capacity");
            engine_number = bundle.getString("engine_number");
            chassis_number = bundle.getString("chassis_number");
            plate_number = bundle.getString("plate_number");

            vehicle_id_tv.setText(vehicle_id);
            vehicle_make_tv.setText(vehicle_make);
            vehicle_type_tv.setText(vehicle_type);
            vehicle_capacity_tv.setText(vehicle_capacity);
            engine_number_tv.setText(engine_number);
            chassis_number_tv.setText(chassis_number);
            plate_number_tv.setText(plate_number);
        }

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(RegPage3ViewModel.class);
        // TODO: Use the ViewModel
    }


}
