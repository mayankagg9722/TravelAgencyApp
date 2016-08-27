package com.example.mayank.travelagentproject;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import java.util.Calendar;

/**
 * Created by mayank on 27-08-2016.
 */
public class TimePicker extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar calendar=Calendar.getInstance();
        int hours=calendar.get(Calendar.HOUR_OF_DAY);
        int min=calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog;
        TimeChange timeChange=new TimeChange(getActivity());
        timePickerDialog=new TimePickerDialog(getActivity(),timeChange,hours,min,
                android.text.format.DateFormat.is24HourFormat(getActivity()));
        timePickerDialog.show();
        return timePickerDialog;
    }
}
