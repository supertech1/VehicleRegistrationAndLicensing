package com.example.vehiclelicense.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.vehiclelicense.R;
import com.example.vehiclelicense.ui.makepayment.MakePaymentFragment;
import com.example.vehiclelicense.ui.v_registration.VRegistrationFragment;
import com.example.vehiclelicense.ui.vehiclerenewal.VehicleRenewalFragment;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private HomeViewModel homeViewModel;

    private TextView fullname_textview, owners_id_textview, vehicle_license_tv, vehicle_owned, drivers_license_tv;
    private Button vehicle_reg_btn, make_payment_btn, vehicle_renewal_btn;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        fullname_textview = (TextView) root.findViewById(R.id.fullname_textview);
        owners_id_textview = (TextView) root.findViewById(R.id.owners_id_textview);
        vehicle_license_tv = (TextView) root.findViewById(R.id.license_status_tv);
        drivers_license_tv = (TextView) root.findViewById(R.id.drivers_license_textview);
        vehicle_owned = (TextView) root.findViewById(R.id.vehicle_owned);
        Bundle bundle = getActivity().getIntent().getExtras();
        fullname_textview.setText(bundle.getString("fullname"));
        owners_id_textview.setText(bundle.getString("owner_id"));
        vehicle_license_tv.setText(bundle.getString("license_status"));
        drivers_license_tv.setText(bundle.getString("drivers_license"));
        vehicle_owned.setText(bundle.getString("count"));

        vehicle_reg_btn = (Button)root.findViewById(R.id.vehicle_reg_btn);
        vehicle_reg_btn.setOnClickListener(this);
        make_payment_btn = (Button) root.findViewById(R.id.make_payment_btn);
        make_payment_btn.setOnClickListener(this);
        vehicle_renewal_btn = (Button) root.findViewById(R.id.vehicle_renewal_btn);
        vehicle_renewal_btn.setOnClickListener(this);
//        final TextView textView = root.findViewById(R.id.text_home);
//        homeViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.vehicle_reg_btn){
            VRegistrationFragment registrationFragment = new VRegistrationFragment();
            getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, registrationFragment,registrationFragment.getTag() ).addToBackStack(null).commit();
        }

        if(v.getId() == R.id.make_payment_btn){
            MakePaymentFragment makePaymentFragment = new MakePaymentFragment();
            getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, makePaymentFragment,makePaymentFragment.getTag() ).addToBackStack(null).commit();
        }

        if(v.getId() == R.id.vehicle_renewal_btn){
            VehicleRenewalFragment vehicleRenewalFragment = new VehicleRenewalFragment();
            getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, vehicleRenewalFragment,vehicleRenewalFragment.getTag() ).addToBackStack(null).commit();
        }

    }
}