package com.dsoft.mycalendar;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.faizmalkani.floatingactionbutton.FloatingActionButton;

/**
 * Created by Marco Barrios on 08/10/2014.
 */
public class FragmentNotas extends Fragment {

    FloatingActionButton b;
    View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        v = inflater.inflate(R.layout.fragment_view_notas, container, false);
        b= (FloatingActionButton) v.findViewById(R.id.fab);

        return v;
    }

    public void hideFab(View view) {
        b.hide(true,2);
        //getActionBar().hide();
    }

    public void showFab(View view) {
        b.hide(false,0);
        //getActionBar().show();
    }

    public void fabClicked(View view) {
        Toast.makeText(null,"Hola", Toast.LENGTH_LONG).show();


    }
}
