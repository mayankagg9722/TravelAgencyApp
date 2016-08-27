package com.example.mayank.travelagentproject;

import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.TimePicker;

/**
 * Created by mayank on 27-08-2016.
 */
public class TimeChange implements TimePickerDialog.OnTimeSetListener {

    Context context;

    public TimeChange(Context context) {
        this.context = context;
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1){
        String time=i+":"+i1;
        Modedislpay.timefield.setText(time);
    }

}
