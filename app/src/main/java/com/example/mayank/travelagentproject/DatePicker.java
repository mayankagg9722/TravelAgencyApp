package com.example.mayank.travelagentproject;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import java.util.Calendar;

/**
 * Created by mayank on 27-08-2016.
 */
public class DatePicker extends DialogFragment {

    static int flag=0;
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar calendar=Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH);
        int day=calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog;
        Datesetting datechange=new Datesetting(getActivity());
        datePickerDialog=new DatePickerDialog(getActivity(),datechange,year,month,day);
        datePickerDialog.show();
        return datePickerDialog;
    }
}
