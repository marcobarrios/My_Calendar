package com.dsoft.mycalendar.Notes;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.dsoft.mycalendar.R;

import java.util.List;

/**
 * Created by Marco Barrios on 29/10/2014.
 */
public class AnotacionListAdapter extends ArrayAdapter<Anotacion> {

    private Activity ctx;

    public AnotacionListAdapter(Activity context, List<Anotacion> anotacion) {
        super(context, R.layout.item_anotacion, anotacion);
        this.ctx = context;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if(view == null) {
            view = ctx.getLayoutInflater().inflate(R.layout.item_anotacion, parent, false);
        }
        Anotacion actual = this.getItem(position);
        inicializarCampos(view, actual);
        return view;
    }

    private void inicializarCampos(View view, Anotacion actual) {
        TextView textView = (TextView)view.findViewById(R.id.viewTituloAnotacion);
        textView.setText(actual.getTitulo());
        textView = (TextView)view.findViewById(R.id.viewDescripcionAnotacion);
        textView.setText(actual.getDescripcion());
        textView = (TextView)view.findViewById(R.id.viewFechaAnotacion);
        textView.setText(actual.getFecha());
    }
}
