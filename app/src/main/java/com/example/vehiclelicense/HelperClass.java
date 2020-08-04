package com.example.vehiclelicense;

import android.widget.EditText;
import android.widget.TextView;

public class HelperClass {
    public static boolean isEmptyField(EditText[] e, TextView status){
        String value = "";
        Boolean result = false;
        for(int i = 0; i < e.length; i++){
            value = e[i].getText().toString();
            if(value.equals("")){
                e[i].setBackgroundResource(R.drawable.border_error);
                result = true;
            } else{
                e[i].setBackgroundResource(R.drawable.border);
                result = false;
            }
        }
        if(result == true){
            status.setText("Some fields are missing");
        } else{
            status.setText("");
        }
        return result;
    }
}
