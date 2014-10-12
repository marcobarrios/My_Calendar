package com.dsoft.mycalendar;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

/**
 * Created by Marco Barrios on 08/10/2014.
 */
public class FragmentCalendario extends Fragment {
    View v;
    CalendarView calendario;

    private void inicializarCalendario() {
        calendario = (CalendarView)v.findViewById(R.id.calendario);
        calendario.setSelectedDateVerticalBar(R.color.MyCalendar_color_darker);
        calendario.setWeekSeparatorLineColor(getResources().getColor(R.color.transparente));
        calendario.setSelectedWeekBackgroundColor(getResources().getColor(R.color.MyCalendar_accent_color_light));
        calendario.setShowWeekNumber(false);
        calendario.setFirstDayOfWeek(2);

        calendario.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int anio, int mes, int dia) {
                //mes = mes + 1;
                //Toast.makeText(getActivity().getApplicationContext(), "Crear evento para: " + dia + "/" + mes + "/" + anio, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_view_calendario, container, false);
        inicializarCalendario();
        return v;

    }
}
