package com.dsoft.mycalendar.Calendar;

import android.annotation.SuppressLint;
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
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dsoft.mycalendar.Interfaces.OnDateSelected;
import com.dsoft.mycalendar.R;
import com.faizmalkani.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;
import java.util.Locale;

import static android.provider.CalendarContract.Calendars;

/**
 * Created by enrique on 15/10/14.
 */
public class FragmentMonthCalender extends Fragment implements View.OnClickListener , OnDateSelected {

    private View v;
    private FloatingActionButton btnNewEvent;
    protected static final int REQUEST_CODE = 10;
    private TextView currentMonth;
    private ImageView prevMonth;
    private ImageView nextMonth;
    private GridView calendarView;
    private GridView daysView;
    private GridCellAdapter adapter;
    private Calendar _calendar;
    @SuppressLint("NewApi")
    private int month, year;
    @SuppressWarnings("unused")
    @SuppressLint({ "NewApi", "NewApi", "NewApi", "NewApi" })
    private final DateFormat dateFormatter = new DateFormat();
    private static final String dateTemplate = "MMMM yyyy";

    String[] EVENT_PROJECTION = new String[]{CalendarContract.Events.TITLE,
            CalendarContract.Events.EVENT_LOCATION, CalendarContract.Instances.BEGIN,
            CalendarContract.Instances.END, CalendarContract.Events.ALL_DAY};
    private TextView events;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_month_calendar, container, false);
        inicializarButon();
        //Se crea una Instancia de Calendar para obtener el mes y dia actual.
        _calendar = Calendar.getInstance(Locale.getDefault());
        month = _calendar.get(Calendar.MONTH) + 1;
        year = _calendar.get(Calendar.YEAR);

        prevMonth = (ImageView) v.findViewById(R.id.prevMonth);
        prevMonth.setOnClickListener(this);

        currentMonth = (TextView) v.findViewById(R.id.currentMonth);
        currentMonth.setText(DateFormat.format(dateTemplate,
                _calendar.getTime()));

        events = (TextView) v.findViewById(R.id.events);

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
        getLastThreeEvents(_calendar.get(Calendar.DAY_OF_MONTH),month-1,year);
        return v;
    }

    public void inicializarButon() {
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

    /**
     *
     * @param month
     * @param year
     */
    private void setGridCellAdapterToDate(int month, int year) {
        adapter = new GridCellAdapter(getActivity().getApplicationContext(),
                R.id.calendar_day_gridcell, month, year,this);
        int aux = month - 1;
        _calendar.set(year,aux, _calendar.get(Calendar.DAY_OF_MONTH));
        currentMonth.setText(DateFormat.format(dateTemplate,
                _calendar.getTime()));
        adapter.notifyDataSetChanged();
        calendarView.setAdapter(adapter);

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
        }

    }

    @Override
    public void onDestroy() {
        Log.d(getTag(), "Destroying View ...");
        super.onDestroy();
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

   private void getLastThreeEvents(int day,int month ,int year ) {


       String selection = "((" + CalendarContract.Events.DTSTART
               + " >= ?) AND (" + CalendarContract.Events.DTEND + " <= ?) " +
               "OR (" + CalendarContract.Events.RRULE + " is not null ))";


       Time t = new Time();
       t.set(0,0,0,day, month, year);
       String dtStart = Long.toString(t.toMillis(false));
       t.set(59, 59, 23, day, month, year);
       String dtEnd = Long.toString(t.toMillis(false));
       String[] selectionArgs = new String[] { dtStart, dtEnd };


   /*    Uri l_eventUri;

        if (Build.VERSION.SDK_INT >= 8 ) {

            l_eventUri = Uri.parse("content://com.android.calendar/events");

        } else {

            l_eventUri = Uri.parse("content://calendar/events");

        }*/

        String[] l_projection = new String[]{"title", "dtstart", "dtend"};

        Cursor l_managedCursor = getActivity().managedQuery(CalendarContract.Events.CONTENT_URI,
                null, selection, selectionArgs, null);

        //Cursor l_managedCursor = this.managedQuery(l_eventUri, l_projection, null, null, null);

        if (l_managedCursor.moveToFirst()) {

            int l_cnt = 0;

            String l_title;

            String l_begin;

            String l_end;

            StringBuilder l_displayText = new StringBuilder();

            int l_colTitle = l_managedCursor.getColumnIndex(l_projection[0]);

            int l_colBegin = l_managedCursor.getColumnIndex(l_projection[1]);

            int l_colEnd = l_managedCursor.getColumnIndex(l_projection[1]);

            do {

                l_title = l_managedCursor.getString(l_colTitle);

                l_begin = l_managedCursor.getString(l_colBegin);

                l_end = l_managedCursor.getString(l_colEnd);

                l_displayText.append(l_title + "\n" + l_begin + "\n" + l_end + "\n----------------\n");

                ++l_cnt;

            } while (l_managedCursor.moveToNext() && l_cnt < 3);

            events.setText(l_displayText.toString());

        }

    }

    @Override
    public void onDateSelected(String date) {

        String[] date_ = date.split("-");
        String theday = date_[0];
        String themonth = date_[1];
        String theyear = date_[2];
        //Toast.makeText(getActivity(),date,Toast.LENGTH_SHORT).show();
        getLastThreeEvents(Integer.parseInt(theday),conv(themonth),Integer.parseInt(theyear));
    }

    private final String[] months = { "January", "February", "March",
            "April", "May", "June", "July", "August", "September",
            "October", "November", "December" };

    public int conv (String month)
    {
        if(month.equals(months[0]))
              return 0;
        else if(month.equals(months[1]))
            return 1;
        else if(month.equals(months[2]))
            return 2;
        else if(month.equals(months[3]))
            return 3;
        else if(month.equals(months[4]))
            return 4;
        else if(month.equals(months[5]))
            return 5;
        else if(month.equals(months[6]))
            return 6;
        else if(month.equals(months[7]))
            return 7;
        else if(month.equals(months[8]))
            return 8;
        else if(month.equals(months[9]))
            return 9;
        else if(month.equals(months[10]))
            return 10;
        else if (month.equals(months[11]))
            return 11;
        return 0;
    }
}








