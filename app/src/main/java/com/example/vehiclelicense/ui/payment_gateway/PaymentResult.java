package com.example.vehiclelicense.ui.payment_gateway;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vehiclelicense.R;

public class PaymentResult extends Fragment {

    private PaymentResultViewModel mViewModel;
    private TextView payment_id_tv, vehicle_id_tv, owner_name_tv, chassis_number_tv, engine_number_tv, engine_capacity_tv, vehicle_make_tv, vehicle_type_tv, vehicle_color_tv, mfg_year_tv, vehicle_license_tv, plate_number_tv;
    private String selected_vehicle_operation;

    public static PaymentResult newInstance() {
        return new PaymentResult();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.payment_result_fragment, container, false);


        payment_id_tv = (TextView) root.findViewById(R.id.payment_id_tv);
        vehicle_id_tv = (TextView) root.findViewById(R.id.vehicle_id_tv);
        owner_name_tv = (TextView) root.findViewById(R.id.owner_name_tv);
        chassis_number_tv = (TextView) root.findViewById(R.id.chassis_number_tv);
        engine_number_tv = (TextView) root.findViewById(R.id.engine_number_tv);
        engine_capacity_tv = (TextView) root.findViewById(R.id.engine_capacity_tv);
        vehicle_make_tv = (TextView) root.findViewById(R.id.vehicle_make_tv);
        vehicle_type_tv = (TextView) root.findViewById(R.id.vehicle_type_tv);
        vehicle_color_tv = (TextView) root.findViewById(R.id.vehicle_color_tv);
        mfg_year_tv = (TextView) root.findViewById(R.id.mfg_year_tv);
        vehicle_license_tv = (TextView) root.findViewById(R.id.vehicle_license_tv);
        plate_number_tv = (TextView) root.findViewById(R.id.plate_number_tv);





        Bundle bundle = getArguments();
        if(bundle != null){
            vehicle_id_tv.setText(bundle.getString("vehicle_id"));
            owner_name_tv.setText(bundle.getString("fullname"));
            plate_number_tv.setText(bundle.getString("plate_number"));
            chassis_number_tv.setText(bundle.getString("chassis_number"));
            engine_number_tv.setText(bundle.getString("engine_number"));
            engine_capacity_tv.setText(bundle.getString("vehicle_capacity"));
            vehicle_make_tv.setText(bundle.getString("vehicle_make"));
            vehicle_type_tv.setText(bundle.getString("vehicle_type"));
            vehicle_color_tv.setText(bundle.getString("vehicle_color"));
            mfg_year_tv.setText(bundle.getString("vehicle_year"));
            vehicle_license_tv.setText("Expiry Date : "+bundle.getString("vehicle_expiry_date"));
            payment_id_tv.setText(bundle.getString("latest_payment_id"));

//            selected_vehicle_operation = bundle.getString("selected_vehicle_operation");
//            if(selected_vehicle_operation.equals("registration")){
//                vehicle_operation_tv.setText("VEHICLE REGISTRATION");
//            } else if (selected_vehicle_operation.equals("renewal")){
//                vehicle_operation_tv.setText("VEHICLE RENEWAL");
//            }

        }


        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(PaymentResultViewModel.class);
        // TODO: Use the ViewModel
    }

}
