package com.example.mayank.travelagentproject;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.*;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Created by mayank on 27-08-2016.
 */
public class Datesetting implements DatePickerDialog.OnDateSetListener{

    Context context;

    public Datesetting(Context context) {
        this.context = context;
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        String date=i2+"/"+i1+"/"+i;
        Modedislpay.datefield.setText(date);

    }
}
