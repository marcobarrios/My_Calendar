package com.dsoft.mycalendar;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

/**
 * Created by Marco Barrios on 08/10/2014.
 */
public class FragmentCalendario extends Fragment {
    CalendarView calendario;

    private void inicializarCalendario() {
        calendario = (CalendarView) getView().findViewById(R.id.calendario);
        calendario.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int anio, int mes, int dia) {
                Toast.makeText(getActivity().getApplicationContext(), "Crear evento para: " + dia + "/" + mes + "/" + anio, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_view_calendario, container, false);
        //inicializarCalendario();
        return v;

    }
}
