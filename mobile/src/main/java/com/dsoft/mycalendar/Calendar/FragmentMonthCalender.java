package com.dsoft.mycalendar.Calendar;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dsoft.mycalendar.Interfaces.OnDateSelected;
import com.dsoft.mycalendar.Objects.EventItem;
import com.dsoft.mycalendar.R;
import com.faizmalkani.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.provider.CalendarContract.Calendars;

/**
 * Created by enrique on 15/10/14.
 */
public class FragmentMonthCalender extends Fragment implements View.OnClickListener , OnDateSelected {

    private View v;
    private ActionBar actionbar;
    private FloatingActionButton btnNewEvent;
    protected static final int REQUEST_CODE = 10;
    private TextView currentMonth;
    private ImageView prevMonth;
    private ImageView nextMonth;
    private GridView calendarView;
    private GridView daysView;
    private GridCellAdapter adapter;
    private Calendar _calendar;
    private ListView list_events;
    @SuppressLint("NewApi")
    private int month, year;
    @SuppressWarnings("unused")
    @SuppressLint({ "NewApi", "NewApi", "NewApi", "NewApi" })
    private final DateFormat dateFormatter = new DateFormat();
    private static final String dateTemplate = "MMMM yyyy";
    private final String[] months = { "January", "February", "March",
            "April", "May", "June", "July", "August", "September",
            "October", "November", "December" };

    private TextView daySelected;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_month_calendar, container, false);
        initButtonFAB();
        //Se crea una Instancia de Calendar para obtener el mes y dia actual.
        _calendar = Calendar.getInstance(Locale.getDefault());
        month = _calendar.get(Calendar.MONTH) + 1;
        year = _calendar.get(Calendar.YEAR);

        prevMonth = (ImageView) v.findViewById(R.id.prevMonth);
        prevMonth.setOnClickListener(this);

        currentMonth = (TextView) v.findViewById(R.id.currentMonth);
        currentMonth.setText(DateFormat.format(dateTemplate,
                _calendar.getTime()));

        daySelected = (TextView) v.findViewById(R.id.dia_seleccionado);
        daySelected.setText(DateFormat.format("EEEE, MMMM yyyy",_calendar));

        nextMonth = (ImageView) v.findViewById(R.id.nextMonth);
        nextMonth.setOnClickListener(this);

        calendarView = (GridView) v.findViewById(R.id.calendar);

        daysView = (GridView) v.findViewById(R.id.days);
        daysView.setAdapter( new GridCellAdapterDaysCalendar(getActivity().getApplicationContext()));

        // Initialised cells of calendarView
        adapter = new GridCellAdapter(getActivity().getApplicationContext(),
                R.id.calendar_day_gridcell, month, year,this);
        adapter.notifyDataSetChanged();
        calendarView.setAdapter(adapter);

        list_events = (ListView) v.findViewById(R.id.list_events);
        setAdapterEventList(QuerysCalendar.getEventsOfDay(getActivity().getApplicationContext(),_calendar.get(Calendar.DAY_OF_MONTH), month - 1, year));
        list_events.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listEvents, View view, int position, long id) {
                EventItem eventSelected = (EventItem) listEvents.getItemAtPosition(position);
                Intent i = new Intent(getActivity().getApplicationContext(), ActivityShowEvent.class);
                i.putExtra("idEvent",eventSelected.getIdEvent());
                i.putExtra("calendario", eventSelected.getCalendar());
                startActivityForResult(i, REQUEST_CODE);
            }
        });
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == REQUEST_CODE) {
                String result = data.getStringExtra("result");
                Toast.makeText(getActivity().getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                Time time = new Time();
                time.setToNow();
                setAdapterEventList(QuerysCalendar.getEventsOfDay(getActivity().getApplicationContext(),time.monthDay, month - 1, year));
            }
        }catch(Exception exp) {

        }
    }

    @Override
    public void onClick(View v) {
        if (v == prevMonth) {
            if (month <= 1) {
                month = 12;
                year--;
            } else {
                month--;
            }
            Log.d(getTag(), "Setting Prev Month in GridCellAdapter: " + "Month: "
                    + month + " Year:listener = (OnDateSelected) activity " + year);
            setGridCellAdapterToDate(month, year);
            Time time = new Time();
            time.setToNow();
            setAdapterEventList(QuerysCalendar.getEventsOfDay(getActivity().getApplicationContext(),time.monthDay, month - 1, year));
            //getLastThreeEvents(time.monthDay, month - 1, year);

            //CalendarService.readCalendar(getActivity().getApplicationContext());
        }
        if (v == nextMonth) {
            if (month > 11) {
                month = 1;
                year++;
            } else {
                month++;
            }
            Log.d(getTag(), "Setting Next Month in GridCellAdapter: " + "Month: "
                    + month + " Year: " + year);
            setGridCellAdapterToDate(month, year);
            Time time = new Time();
            time.setToNow();
            setAdapterEventList(QuerysCalendar.getEventsOfDay(getActivity().getApplicationContext(),time.monthDay, month - 1, year));
            //getLastThreeEvents(time.monthDay,month - 1,year);
        }
    }

    /**
     * get Date selected into Calendar
     * @param date date format (YYY-MM-DD)
     */
    @Override
    public void onDateSelected(String date) {

        String[] date_ = date.split("-");
        String theday = date_[0];
        String themonth = date_[1];
        String theyear = date_[2];
        //Toast.makeText(getActivity(),date,Toast.LENGTH_SHORT).show();
        //list_events.setAdapter(getAdapaterEvent(getEventsOfDay(Integer.parseInt(theday),getMonthNumber(themonth),Integer.parseInt(theyear))));
        setAdapterEventList(QuerysCalendar.getEventsOfDay(getActivity().getApplicationContext(),Integer.parseInt(theday), getMonthNumber(themonth), Integer.parseInt(theyear)));
        //getLastThreeEvents(Integer.parseInt(theday),getMonthNumber(themonth),Integer.parseInt(theyear));
        _calendar.set(Integer.parseInt(theyear),getMonthNumber(themonth),Integer.parseInt(theday));
        daySelected.setText(DateFormat.format("EEEE, MMMM yyyy",_calendar));

        //


    }

    /**
     * set Adapter to Calendars
     * @param month On Date Selected (1-12)
     * @param year On Date Selected (1-12)
     */
    private void setGridCellAdapterToDate(int month, int year) {

        adapter = new GridCellAdapter(getActivity().getApplicationContext(),
                R.id.calendar_day_gridcell, month, year,this);
        int aux = month - 1;
        _calendar.set(year,aux, _calendar.get(Calendar.DAY_OF_MONTH));
        currentMonth.setText(DateFormat.format(dateTemplate,_calendar.getTime()));
        adapter.notifyDataSetChanged();
        calendarView.setAdapter(adapter);
        ///
        daySelected.setText(DateFormat.format("EEEE, MMMM yyyy",_calendar));
    }

    /**
     * set Adapter Event List
     * @param list event list of Day
     */

    private void setAdapterEventList(ArrayList<EventItem> list)
    {
        EventCellAdapter event = new EventCellAdapter(getActivity().getApplicationContext(),R.layout.item_eventos,list) {
            @Override
            public void onEntrada(Object entrada, View view) {

                String l_begin;
                String l_end;

                l_begin = getDate(((EventItem) entrada).getDtStart());
                l_end = getDate(((EventItem) entrada).getDtEnd());
                StringBuilder l_displayText = new StringBuilder();
                l_displayText.append(l_begin).append(" - ").append(l_end);
                TextView texto_superior_entrada = (TextView) view.findViewById(R.id.fecha_evento);
                texto_superior_entrada.setText(l_displayText);

                TextView texto_inferior_entrada = (TextView) view.findViewById(R.id.titulo_evento_);
                texto_inferior_entrada.setText(((EventItem) entrada).getTitle());
            }
        };

        list_events.removeAllViewsInLayout();
        list_events.setAdapter(event);
    }

    public int getMonthNumber(Object month)
    {
        if(month.equals(months[0]))
              return Calendar.JANUARY;
        else if(month.equals(months[1]))
            return Calendar.FEBRUARY;
        else if(month.equals(months[2]))
            return Calendar.MARCH;
        else if(month.equals(months[3]))
            return Calendar.APRIL;
        else if(month.equals(months[4]))
            return Calendar.MAY;
        else if(month.equals(months[5]))
            return Calendar.JUNE;
        else if(month.equals(months[6]))
            return Calendar.JULY;
        else if(month.equals(months[7]))
            return Calendar.AUGUST;
        else if(month.equals(months[8]))
            return Calendar.SEPTEMBER;
        else if(month.equals(months[9]))
            return Calendar.OCTOBER;
        else if(month.equals(months[10]))
            return Calendar.NOVEMBER;
        else if (month.equals(months[11]))
            return Calendar.DECEMBER;
        return 0;
    }

    public String getDate(Long date)
    {
        return new SimpleDateFormat("h:mm a").format(new Date(date));
    }

    public void initButtonFAB() {
        btnNewEvent = (FloatingActionButton)v.findViewById(R.id.fab_calendario);
        btnNewEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity().getApplicationContext(), ActivityEvento.class);
                //i.putExtra("fecha", String.valueOf(calendario.getDate()));
                startActivityForResult(i, REQUEST_CODE);
            }
        });
    }

    private void conectToCalender()
    {
        String[] projection =
                new String[]{
                        Calendars._ID,
                        Calendars.NAME,
                        Calendars.ACCOUNT_NAME,
                        Calendars.ACCOUNT_TYPE};
        Cursor calCursor =
                getActivity().getContentResolver().
                        query(Calendars.CONTENT_URI,
                                projection,
                                Calendars.VISIBLE + " = 1",
                                null,
                                Calendars._ID + " ASC");
        if (calCursor.moveToFirst()) {
            do {
                long id = calCursor.getLong(0);
                String displayName = calCursor.getString(1);
                // ...
            } while (calCursor.moveToNext());
        }

        ContentValues values = new ContentValues();
        values.put(
                Calendars.ACCOUNT_NAME,
                "ematul.92@gmail.com");
        values.put(
                Calendars.ACCOUNT_TYPE,
                CalendarContract.ACCOUNT_TYPE_LOCAL);
        values.put(
                Calendars.NAME,
                "GrokkingAndroid Calendar");
        values.put(
                Calendars.CALENDAR_DISPLAY_NAME,
                "GrokkingAndroid Calendar");
        values.put(
                Calendars.CALENDAR_COLOR,
                0xffff0000);
        values.put(
                Calendars.CALENDAR_ACCESS_LEVEL,
                Calendars.CAL_ACCESS_OWNER);
        values.put(
                Calendars.OWNER_ACCOUNT,
                "some.account@googlemail.com");
        values.put(
                Calendars.CALENDAR_TIME_ZONE,
                "America/Guatemala");
        values.put(
                Calendars.SYNC_EVENTS,
                1);
        Uri.Builder builder =
                CalendarContract.Calendars.CONTENT_URI.buildUpon();
        builder.appendQueryParameter(
                Calendars.ACCOUNT_NAME,
                "com.dsoft.mycalendar");
        builder.appendQueryParameter(
                Calendars.ACCOUNT_TYPE,
                CalendarContract.ACCOUNT_TYPE_LOCAL);
        builder.appendQueryParameter(
                CalendarContract.CALLER_IS_SYNCADAPTER,
                "true");
        Uri uri =
                getActivity().getContentResolver().insert(builder.build(), values);

    }


   /* private void getCalendars() {

        String[] l_projection = new String[]{"_id", "displayName"};

        Uri l_calendars;

        if (Build.VERSION.SDK_INT >= 8 ) {

            l_calendars = Uri.parse("content://com.android.calendar/calendars");

        } else {

            l_calendars = Uri.parse("content://calendar/calendars");

        }

        Cursor l_managedCursor = getActivity().managedQuery(l_calendars, l_projection, null, null, null);    //all calendars

        //Cursor l_managedCursor = this.managedQuery(l_calendars, l_projection, "selected=1", null, null);   //active calendars

        if (l_managedCursor.moveToFirst()) {

            m_calendars = new MyCalendar[l_managedCursor.getCount()];

            String l_calName;

            String l_calId;

            int l_cnt = 0;

            int l_nameCol = l_managedCursor.getColumnIndex(l_projection[1]);

            int l_idCol = l_managedCursor.getColumnIndex(l_projection[0]);

            do {

                l_calName = l_managedCursor.getString(l_nameCol);

                l_calId = l_managedCursor.getString(l_idCol);

                m_calendars[l_cnt] = new MyCalendar(l_calName, l_calId);

                ++l_cnt;

            } while (l_managedCursor.moveToNext());

        }



    }*/



}








