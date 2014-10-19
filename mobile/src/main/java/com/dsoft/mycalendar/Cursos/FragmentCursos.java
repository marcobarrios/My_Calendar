package com.dsoft.mycalendar.Cursos;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dsoft.mycalendar.R;
import com.faizmalkani.floatingactionbutton.FloatingActionButton;

/**
 * Created by Marco Barrios on 08/10/2014.
 */
public class FragmentCursos extends Fragment {
    View v;
    FloatingActionButton btn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_view_cursos, container, false);
        inicializarButon();
        return v;
    }

    public void inicializarButon() {
        btn = (FloatingActionButton)v.findViewById(R.id.fab_curso);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
