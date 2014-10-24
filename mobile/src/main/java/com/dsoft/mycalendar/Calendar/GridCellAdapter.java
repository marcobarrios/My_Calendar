package com.dsoft.mycalendar.Calendar;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dsoft.mycalendar.Interfaces.OnDateSelected;
import com.dsoft.mycalendar.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

/**
 * Created by enrique on 17/10/14.
 */
public class GridCellAdapter extends BaseAdapter implements View.OnClickListener {
    private static final String tag = "GridCellAdapter";
    private final Context context;
    private OnDateSelected listener ;
    private final List<String> list;
    private static final int DAY_OFFSET = 1;
    private final String[] weekdays = new String[] { "Sun", "Mon", "Tue",
            "Wed", "Thu", "Fri", "Sat" };
    private final String[] months = { "January", "February", "March",
            "April", "May", "June", "July", "August", "September",
            "October", "November", "December" };
    private final int[] daysOfMonth = { 31, 28, 31, 30, 31, 30, 31, 31, 30,
            31, 30, 31 };
    private int daysInMonth;
    private int currentDayOfMonth;
    private int currentWeekDay;
    private Button gridcell;
    private Button gridcellCurrent;
    private TextView num_events_per_day;
    private final HashMap<String, Integer> eventsPerMonthMap;
    private final SimpleDateFormat dateFormatter = new SimpleDateFormat(
            "dd-MMM-yyyy");

    public static final String[] EVENT_PROJECTION = new String[] {
            CalendarContract.Calendars._ID, // 0
            CalendarContract.Calendars.ACCOUNT_NAME, // 1
            CalendarContract.Calendars.CALENDAR_DISPLAY_NAME // 2
    };

    private static final int PROJECTION_DISPLAY_NAME_INDEX = 2;

    // Days in Current Month
    public GridCellAdapter(Context context, int textViewResourceId,
                           int month, int year,FragmentMonthCalender parent) {
        super();
        this.context = context;
        this.list = new ArrayList<String>();
        Log.d(tag, "==> Passed in Date FOR Month: " + month + " "
                + "Year: " + year);
        Calendar calendar = Calendar.getInstance();
        setCurrentDayOfMonth(calendar.get(Calendar.DAY_OF_MONTH));
        setCurrentWeekDay(calendar.get(Calendar.DAY_OF_WEEK));
        Log.d(tag, "New Calendar:= " + calendar.getTime().toString());
        Log.d(tag, "CurrentDayOfWeek :" + getCurrentWeekDay());
        Log.d(tag, "CurrentDayOfMonth :" + getCurrentDayOfMonth());

        // Print Month
        printMonth(month, year);

        // Find Number of Events
        eventsPerMonthMap = findNumberOfEventsPerMonth(year, month);
        listener = (OnDateSelected) parent;
    }

    private String getMonthAsString(int i) {
        return months[i];
    }

    private String getWeekDayAsString(int i) {
        return weekdays[i];
    }

    private int getNumberOfDaysOfMonth(int i) {
        return daysOfMonth[i];
    }

    public String getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    /**
     * Prints Month
     *
     * @param mm
     * @param yy
     */
    private void printMonth(int mm, int yy) {
        Log.d(tag, "==> printMonth: mm: " + mm + " " + "yy: " + yy);
        int trailingSpaces = 0;
        int daysInPrevMonth = 0;
        int prevMonth = 0;
        int prevYear = 0;
        int nextMonth = 0;
        int nextYear = 0;

        int currentMonth = mm - 1;
        String currentMonthName = getMonthAsString(currentMonth);
        daysInMonth = getNumberOfDaysOfMonth(currentMonth);

        Log.d(tag, "Current Month: " + " " + currentMonthName + " having "
                + daysInMonth + " days.");

        GregorianCalendar cal = new GregorianCalendar(yy, currentMonth, 1);
        Log.d(tag, "Gregorian Calendar:= " + cal.getTime().toString());

        if (currentMonth == 11) {
            prevMonth = currentMonth - 1;
            daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
            nextMonth = 0;
            prevYear = yy;
            nextYear = yy + 1;
            Log.d(tag, "*->PrevYear: " + prevYear + " PrevMonth:"
                    + prevMonth + " NextMonth: " + nextMonth
                    + " NextYear: " + nextYear);
        } else if (currentMonth == 0) {
            prevMonth = 11;
            prevYear = yy - 1;
            nextYear = yy;
            daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
            nextMonth = 1;
            Log.d(tag, "**--> PrevYear: " + prevYear + " PrevMonth:"
                    + prevMonth + " NextMonth: " + nextMonth
                    + " NextYear: " + nextYear);
        } else {
            prevMonth = currentMonth - 1;
            nextMonth = currentMonth + 1;
            nextYear = yy;
            prevYear = yy;
            daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
            Log.d(tag, "***---> PrevYear: " + prevYear + " PrevMonth:"
                    + prevMonth + " NextMonth: " + nextMonth
                    + " NextYear: " + nextYear);
        }

        int currentWeekDay = cal.get(Calendar.DAY_OF_WEEK) - 1;
        trailingSpaces = currentWeekDay;

        Log.d(tag, "Week Day:" + currentWeekDay + " is "
                + getWeekDayAsString(currentWeekDay));
        Log.d(tag, "No. Trailing space to Add: " + trailingSpaces);
        Log.d(tag, "No. of Days in Previous Month: " + daysInPrevMonth);

        if (cal.isLeapYear(cal.get(Calendar.YEAR)))
            if (mm == 2)
                ++daysInMonth;
            else if (mm == 3)
                ++daysInPrevMonth;

        // Trailing Month days
        for (int i = 0; i < trailingSpaces; i++) {
            Log.d(tag,
                    "PREV MONTH:= "
                            + prevMonth
                            + " => "
                            + getMonthAsString(prevMonth)
                            + " "
                            + String.valueOf((daysInPrevMonth
                            - trailingSpaces + DAY_OFFSET)
                            + i));
            list.add(String
                    .valueOf((daysInPrevMonth - trailingSpaces + DAY_OFFSET)
                            + i)
                    + "-GREY"
                    + "-"
                    + getMonthAsString(prevMonth)
                    + "-"
                    + prevYear);
        }

        // Current Month Days
        for (int i = 1; i <= daysInMonth; i++) {
            Log.d(currentMonthName, String.valueOf(i) + " "
                    + getMonthAsString(currentMonth) + " " + yy);
            if (i == getCurrentDayOfMonth()) {
                list.add(String.valueOf(i) + "-BLUE" + "-"
                        + getMonthAsString(currentMonth) + "-" + yy);
            } else {
                list.add(String.valueOf(i) + "-WHITE" + "-"
                        + getMonthAsString(currentMonth) + "-" + yy);
            }
        }

        // Leading Month days
        for (int i = 0; i < list.size() % 7; i++) {
            Log.d(tag, "NEXT MONTH:= " + getMonthAsString(nextMonth));
            list.add(String.valueOf(i + 1) + "-GREY" + "-"
                    + getMonthAsString(nextMonth) + "-" + nextYear);
        }
    }

    /**
     * NOTE: YOU NEED TO IMPLEMENT THIS PART Given the YEAR, MONTH, retrieve
     * ALL entries from a SQLite database for that month. Iterate over the
     * List of All entries, and get the dateCreated, which is converted into
     * day.
     *
     * @param year
     * @param month
     * @return
     */
    private HashMap<String, Integer> findNumberOfEventsPerMonth(int year,
                                                                int month) {
        

        HashMap<String, Integer> map = new HashMap<String, Integer>();



        return map;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.screen_gridcell, parent, false);
        }


        if(position > 6) {
            // Get a reference to the Day gridcell
            gridcell = (Button) row.findViewById(R.id.calendar_day_gridcell);
            gridcell.setOnClickListener(this);

            // ACCOUNT FOR SPACING

            Log.d(tag, "Current Day: " + getCurrentDayOfMonth());
            String[] day_color = list.get(position).split("-");
            String theday = day_color[0];
            String themonth = day_color[2];
            String theyear = day_color[3];
            if ((!eventsPerMonthMap.isEmpty()) && (eventsPerMonthMap != null)) {
                if (eventsPerMonthMap.containsKey(theday)) {
                    num_events_per_day = (TextView) row
                            .findViewById(R.id.num_events_per_day);
                    Integer numEvents = (Integer) eventsPerMonthMap.get(theday);
                    num_events_per_day.setText(numEvents.toString());
                }
            }

            // Set the Day GridCell
            gridcell.setText(theday);
            gridcell.setTag(theday + "-" + themonth + "-" + theyear);
            Log.d(tag, "Setting GridCell " + theday + "-" + themonth + "-"
                    + theyear);

            if (day_color[1].equals("GREY")) {
                gridcell.setTextColor(context.getResources()
                        .getColor(R.color.lightgray02));
            }
            if (day_color[1].equals("WHITE")) {
                gridcell.setTextColor(context.getResources().getColor(
                        R.color.white));
            }
            if (day_color[1].equals("BLUE")) {
                gridcell.setTextColor(context.getResources().getColor(R.color.white));
                gridcell.setActivated(true);
                gridcellCurrent = gridcell;
            }

        }
        else
        {
            gridcell = (Button) row.findViewById(R.id.calendar_day_gridcell);
            gridcell.setText(weekdays[position]);
        }

        return row;
    }

    @Override
    public void onClick(View view) {
        String date_month_year = (String) view.getTag();
        //queryCalendar(view);
        //getAllAccounts();
        gridcell.setActivated(false);
        gridcellCurrent.setActivated(false);
        view.setActivated(true);
        gridcell = (Button)view;
        listener.onDateSelected(date_month_year);
        //Toast.makeText(context, date_month_year, Toast.LENGTH_LONG).show();

        Log.e("Selected date", date_month_year);
        try {
            Date parsedDate = dateFormatter.parse(date_month_year);
            Log.d(tag, "Parsed Date: " + parsedDate.toString());

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public int getCurrentDayOfMonth() {
        return currentDayOfMonth;
    }

    private void setCurrentDayOfMonth(int currentDayOfMonth) {
        this.currentDayOfMonth = currentDayOfMonth;
    }

    public void setCurrentWeekDay(int currentWeekDay) {
        this.currentWeekDay = currentWeekDay;
    }

    public int getCurrentWeekDay() {
        return currentWeekDay;
    }

    public void queryCalendar(View view) {
        Cursor cur = null;
        ContentResolver cr = context.getContentResolver();
        Uri uri = CalendarContract.Calendars.CONTENT_URI;
        String selection = "(" + CalendarContract.Calendars.ACCOUNT_TYPE + " = ?)";

        String[] selectionArgs = new String[] { "com.google" };
        cur = cr.query(uri, EVENT_PROJECTION, selection, selectionArgs, null);

        while (cur.moveToNext()) {
            String displayName = null;
            displayName = cur.getString(PROJECTION_DISPLAY_NAME_INDEX);
            Toast.makeText(context, "Calendar " + displayName, Toast.LENGTH_SHORT)
                    .show();
        }
    }

    /**
     * Get All Account available in Phone and add into list
     */
    private void getAllAccounts() {
        int counter = 0;
        final AccountManager accManager = AccountManager
                .get(context);
        final Account accounts[] = accManager.getAccountsByType("com.google");

        for (int i = 0; i < accounts.length; i++) {
            Log.i(tag, "Name " + accounts[i].name + ", Type "
                    + accounts[i].type);
        }
    }




}