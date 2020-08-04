package com.example.vehiclelicense.ui.logout;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vehiclelicense.R;

public class v_logout extends Fragment {

    private VLogoutViewModel mViewModel;

    public static v_logout newInstance() {
        return new v_logout();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        getActivity().finish();
        return inflater.inflate(R.layout.v_logout_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(VLogoutViewModel.class);
        // TODO: Use the ViewModel
    }

}
