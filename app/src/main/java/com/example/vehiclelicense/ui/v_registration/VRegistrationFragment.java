package com.example.vehiclelicense.ui.v_registration;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.vehiclelicense.HelperClass;
import com.example.vehiclelicense.R;

public class VRegistrationFragment extends Fragment implements View.OnClickListener {

    private VRegistrationViewModel galleryViewModel;
    private Button next;
    private EditText vehicle_type, vehicle_make, vehicle_capacity, chassis_number, engine_number, plate_number;
    private  TextView status_textview;
    private HelperClass helperClass;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(VRegistrationViewModel.class);
        View root = inflater.inflate(R.layout.fragment_v_registration, container, false);
        next = (Button) root.findViewById(R.id.next);
        next.setOnClickListener(this);

        vehicle_type = (EditText) root.findViewById(R.id.vehicle_type);
        vehicle_make = (EditText) root.findViewById(R.id.vehicle_make);
        vehicle_capacity = (EditText) root.findViewById(R.id.vehicle_capacity);
        chassis_number = (EditText) root.findViewById(R.id.chassis_number);
        engine_number = (EditText) root.findViewById(R.id.engine_number);
        plate_number = (EditText) root.findViewById(R.id.plate_number);
        status_textview = (TextView) root.findViewById(R.id.status_textview);
//        Toast.makeText(getActivity(), "Register", Toast.LENGTH_SHORT).show();
//        final TextView textView = root.findViewById(R.id.text_gallery);
//        galleryViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }

    @Override
    public void onClick(View v) {

        EditText fields[] = {vehicle_make,vehicle_capacity,vehicle_type,engine_number,chassis_number,plate_number};

            if(v.getId() == R.id.next){
                if(!helperClass.isEmptyField(fields, status_textview)){
                    reg_page2 pg2 = new reg_page2();
                    Bundle bundle = new Bundle();
                    bundle.putString("vehicle_type", vehicle_type.getText().toString());
                    bundle.putString("vehicle_make", vehicle_make.getText().toString());
                    bundle.putString("vehicle_capacity", vehicle_capacity.getText().toString());
                    bundle.putString("chassis_number", chassis_number.getText().toString());
                    bundle.putString("engine_number", engine_number.getText().toString());
                    bundle.putString("plate_number", plate_number.getText().toString());
                    pg2.setArguments(bundle);
                    getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, pg2,pg2.getTag() ).addToBackStack(null).commit();
                }

            }


    }
}