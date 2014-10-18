package com.dsoft.mycalendar.Calendar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dsoft.mycalendar.R;

/**
 * Created by enrique on 17/10/14.
 */
public class GridCellAdapterDaysCalendar extends BaseAdapter {

    private final String[] weekdays = new String[] { "Sun", "Mon", "Tue",
            "Wed", "Thu", "Fri", "Sat" };
    private Context contextParent;
    private TextView gridcell;

    public GridCellAdapterDaysCalendar(Context c)
    {
        this.contextParent = c;
    }


    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {

        View row = view;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) contextParent
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.screen_gridcell_days, parent, false);
        }
        gridcell = (TextView) row.findViewById(R.id.num_day);

        String day = weekdays[i];

        gridcell.setText(day);
        gridcell.setTextColor(contextParent.getResources().getColor(R.color.orrange));


        return row;
    }
}
