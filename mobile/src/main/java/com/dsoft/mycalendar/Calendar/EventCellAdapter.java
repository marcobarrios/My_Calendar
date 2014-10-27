package com.dsoft.mycalendar.Calendar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.dsoft.mycalendar.R;

import java.util.ArrayList;

/**
 * Created by enrique on 23/10/14.
 */
public abstract  class EventCellAdapter extends BaseAdapter {

    private ArrayList<?> entradas;
    private Context context;
    private int R_layout_IdView;

    public EventCellAdapter(Context context, int R_layout_IdView, ArrayList<?> entradas)
    {
        super();
        this.context = context;
        this.R_layout_IdView = R_layout_IdView;
        this.entradas = entradas;

    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = vi.inflate(R.layout.item_eventos, null);
        }
        onEntrada (entradas.get(position), view);
        return view;
    }

    @Override
    public int getCount() {
        if(entradas!=null)
        return entradas.size();
        else
            return 0;
    }

    @Override
    public Object getItem(int i) {
        return entradas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    /** Devuelve cada una de las entradas con cada una de las vistas a la que debe de ser asociada
     * @param entrada La entrada que será la asociada a la view. La entrada es del tipo del paquete/handler
     * @param view View particular que contendrá los datos del paquete/handler
     */
    public abstract void onEntrada (Object entrada, View view);


}
