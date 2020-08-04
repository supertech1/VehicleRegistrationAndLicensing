package com.example.vehiclelicense;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Set;

public class ConnectivityChangeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        debugIntent(context, intent);
    }

    private void debugIntent(Context c, Intent intent) {
        Bundle extras = intent.getExtras();
        Activity activity = (Activity) c;

        Button btn = (Button) activity.findViewById(R.id.login_btn);
        TextView status_textview = (TextView) activity.findViewById(R.id.status_textview);
        if (extras != null) {
            boolean isDeviceConnected = extras.getBoolean("noConnectivity");
            if (isDeviceConnected) {
                Log.d("Checking Connection", "debugIntent: Enable your internet connection to continue ");
                Toast.makeText(c, "Enable your internet connection to continue", Toast.LENGTH_LONG).show();
                status_textview.setText("No internet connectivity");
                btn.setEnabled(false);

            } else {
                Log.d("Checking Connection", "debugIntent: Internet connection enabled ");
                status_textview.setText("");
                btn.setEnabled(true);

            }

        } else {
            Log.d("Checking Connection", "debugIntent: no extras ");
        }
    }

}
