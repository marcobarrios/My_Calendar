package com.dsoft.mycalendar.Notes;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dsoft.mycalendar.R;
import com.faizmalkani.floatingactionbutton.FloatingActionButton;
/**
 * Created by Marco Barrios on 08/10/2014.
 */
public class FragmentAnotaciones extends Fragment {
    View v;
    FloatingActionButton btn;
    protected static final int REQUEST_CODE = 10;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_view_anotaciones, container, false);
        inicializarButon();
        return v;
    }

    public void inicializarButon() {
        btn = (FloatingActionButton)v.findViewById(R.id.fab_anotacion);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =  new Intent(getActivity().getApplicationContext(), ActivityAnotacion.class);
                //i.putExtra("fecha", String.valueOf(calendario.getDate()));
                startActivity(i);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == REQUEST_CODE) {
                String result = data.getStringExtra("result");
                Toast.makeText(getActivity().getApplicationContext(), result, Toast.LENGTH_SHORT).show();
            }
        }catch(Exception exp) {

        }
    }
}
