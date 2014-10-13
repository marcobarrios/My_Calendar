package com.dsoft.mycalendar;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.andexert.calendarlistview.library.DayPickerView;
import com.faizmalkani.floatingactionbutton.FloatingActionButton;

/**
 * Created by Marco Barrios on 08/10/2014.
 */
public class FragmentNotas extends Fragment implements com.andexert.calendarlistview.library.DatePickerController {
    View v;
    FloatingActionButton btn;
    private DayPickerView dayPickerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_view_notas, container, false);
        inicializarCalendario();
        inicializarButon();
        return v;
    }

    public void inicializarCalendario() {
        dayPickerView = (DayPickerView) v.findViewById(R.id.pickerView);
        dayPickerView.setmController(this);
    }

    public void inicializarButon() {
        btn = (FloatingActionButton)v.findViewById(R.id.fab_nota);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getMaxYear()
    {
        return 2015;
    }

    @Override
    public void onDayOfMonthSelected(int year, int month, int day)
    {
        Log.e("Day Selected", day + " / " + month + " / " + year);
    }
}
