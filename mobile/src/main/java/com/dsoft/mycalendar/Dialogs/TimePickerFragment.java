package com.dsoft.mycalendar.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.Time;
import android.widget.TimePicker;

import com.dsoft.mycalendar.Interfaces.OnTimeSelected;

/**
 * Created by enrique on 19/10/14.
 */
public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    private OnTimeSelected listener ;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Time time_now = new Time();
        time_now.setToNow();

        return new TimePickerDialog(getActivity(),this,time_now.hour,time_now.minute,false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (OnTimeSelected) activity;
        } catch (ClassCastException e) {}
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
        String time = hour+":"+minute;
        listener.onTimeSelected(time);
    }
}
