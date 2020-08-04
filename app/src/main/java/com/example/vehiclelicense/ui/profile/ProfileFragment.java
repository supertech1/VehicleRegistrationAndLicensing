package com.example.vehiclelicense.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.vehiclelicense.R;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;
    String owner_id ="", fullname = "", emailaddress = "", phonenumber = "", drivers_license = "";
    private TextView owner_id_tv, fullname_tv, emailaddress_tv, phonenumber_tv, drivers_license_tv;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel =
                ViewModelProviders.of(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        //from activity
        Bundle bundleActivity = getActivity().getIntent().getExtras();
        owner_id = bundleActivity.getString("owner_id");
        fullname = bundleActivity.getString("fullname");
        emailaddress = bundleActivity.getString("emailaddress");
        phonenumber = bundleActivity.getString("phonenumber");
        drivers_license = bundleActivity.getString("drivers_license");

        owner_id_tv = (TextView) root.findViewById(R.id.owner_id);
        owner_id_tv.setText(owner_id);
        fullname_tv = (TextView)root.findViewById(R.id.fullname);
        fullname_tv.setText(fullname);
        emailaddress_tv = (TextView)root.findViewById(R.id.emailaddress);
        emailaddress_tv.setText(emailaddress);
        phonenumber_tv = (TextView)root.findViewById(R.id.phonenumber);
        phonenumber_tv.setText(phonenumber);
        drivers_license_tv = (TextView) root.findViewById(R.id.driver_license_number);
        drivers_license_tv.setText(drivers_license);

//        profileViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }
}