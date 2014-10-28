package com.dsoft.mycalendar.Calendar;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.text.format.Time;

import com.dsoft.mycalendar.Objects.EventItem;

import java.util.ArrayList;

/**
 * Created by enrique on 27/10/14.
 */
public class QuerysCalendar {

    /**The main/basic URI for the android events table*/
    private static final Uri EVENT_URI = CalendarContract.Events.CONTENT_URI;

    /**Builds the Uri for events (as a Sync Adapter)*/
    public static Uri buildEventUri(String account_name) {
        return EVENT_URI
                .buildUpon()
                .appendQueryParameter(CalendarContract.CALLER_IS_SYNCADAPTER, "true")
                .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_NAME, account_name)
                .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_TYPE,
                        CalendarContract.ACCOUNT_TYPE_LOCAL)
                .build();
    }

    /**Finds an event based on the ID
     * @param ctx The context (e.g. activity)
     * @param id The id of the event to be found
     */
    public static EventItem getEventByID(Context ctx,String calendar_mail,long id) {
        ContentResolver cr = ctx.getContentResolver();
        //Projection array for query (the values you want)
        final String[] PROJECTION;
        PROJECTION = new String[] {
                CalendarContract.Events._ID,
                CalendarContract.Events.TITLE,
                CalendarContract.Events.DESCRIPTION,
                CalendarContract.Events.EVENT_LOCATION,
                CalendarContract.Events.DTSTART,
                CalendarContract.Events.DTEND,
        };
        final int ID_INDEX = 0, TITLE_INDEX = 1, DESC_INDEX = 2, LOCATION_INDEX = 3,
                START_INDEX = 4, END_INDEX = 5, ACCOUNT_NAME = 6;

        final String selection = "("+ CalendarContract.Events.OWNER_ACCOUNT+" = ? AND "+ CalendarContract.Events._ID+" = ?)";
        final String[] selectionArgs = new String[] {calendar_mail, id+""};
        Cursor cursor = cr.query(Uri.parse("content://com.android.calendar/events"), PROJECTION, selection, selectionArgs, null);
        //at most one event will be returned because event ids are unique in the table
        EventItem event = null;
        if (cursor.moveToFirst()) {
            event = new EventItem();
            event.setIdEvent(cursor.getLong(ID_INDEX));
            event.setTitle(cursor.getString(TITLE_INDEX));
            event.setDescription(cursor.getString(DESC_INDEX));
            event.setLocation(cursor.getString(LOCATION_INDEX));
            event.setDtStart(cursor.getLong(START_INDEX));
            event.setDtEnd(cursor.getLong(END_INDEX));
            event.setCalendar(calendar_mail);
        //do something with the values...
        }
        cursor.close();
        return event;
    }

    /**Create event - Add an event to our calendar
     * @param dtstart Event start time (in millis)
     * @param dtend Event end time (in millis)
     */
    public static Long addEvent(Context ctx, String calendar_mail, String title, String description,
                                String location,long dtstart, long dtend)
    {
        ContentResolver cr = ctx.getContentResolver();
        ContentValues cv = new ContentValues();
        cv.put(CalendarContract.Events.CALENDAR_ID, 1);
        cv.put(CalendarContract.Events.TITLE, title);
        cv.put(CalendarContract.Events.DTSTART, dtstart);
        cv.put(CalendarContract.Events.DTEND, dtend);
        cv.put(CalendarContract.Events.EVENT_LOCATION, location);
        cv.put(CalendarContract.Events.DESCRIPTION, description);
        cv.put(CalendarContract.Events.EVENT_TIMEZONE, "America/Guatemala");
        Uri uri = cr.insert(buildEventUri(calendar_mail), cv);

        return Long.parseLong(uri.getLastPathSegment());
    }

    /**Create event - Update an event to our calendar
     */
    public static void updateEvent(Context ctx, String calendar_mail, Long id,String title, String description,
                                String location,long dtstart, long dtend)
    {
        ContentResolver cr = ctx.getContentResolver();
        ContentValues cv = new ContentValues();
        cv.put(CalendarContract.Events.CALENDAR_ID, 1);
        cv.put(CalendarContract.Events.TITLE, title);
        cv.put(CalendarContract.Events.DTSTART, dtstart);
        cv.put(CalendarContract.Events.DTEND, dtend);
        cv.put(CalendarContract.Events.EVENT_LOCATION, location);
        cv.put(CalendarContract.Events.DESCRIPTION, description);
        cv.put(CalendarContract.Events.EVENT_TIMEZONE, "America/Guatemala");
        Uri updateUri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, id);
        int rows = ctx.getContentResolver().update(updateUri, cv, null, null);

    }

    public static ArrayList<EventItem> getEventsOfDay(Context context,int day,int month ,int year ) {

        ContentResolver cr = context.getContentResolver();
        String selection = "((" + CalendarContract.Events.DTSTART
                + " >= ?) AND (" + CalendarContract.Events.DTEND + " <= ?) " +
                "OR (" + CalendarContract.Events.RRULE + " is not null ))";

        Time t = new Time();
        t.set(0,0,0,day, month, year);
        String dtStart = Long.toString(t.toMillis(false));
        t.set(59, 59, 23, day, month, year);
        String dtEnd = Long.toString(t.toMillis(false));
        String[] selectionArgs = new String[] { dtStart, dtEnd };

        String[] l_projection = new String[]{"title", "dtstart", "dtend" , CalendarContract.Events._ID, CalendarContract.Events.EVENT_COLOR, CalendarContract.Events.ACCOUNT_NAME};

        Cursor l_managedCursor = cr.query(CalendarContract.Events.CONTENT_URI,
                null, selection, selectionArgs, null);

        ArrayList <EventItem> res = null;

        if (l_managedCursor.moveToFirst()) {

            int l_cnt = 0;

            String l_title;

            String l_begin;

            String l_end;

            res = new ArrayList<EventItem>();

            StringBuilder l_displayText = new StringBuilder();

            int l_colTitle = l_managedCursor.getColumnIndex(l_projection[0]);

            int l_colBegin = l_managedCursor.getColumnIndex(l_projection[1]);

            int l_colEnd = l_managedCursor.getColumnIndex(l_projection[2]);

            int l_colID = l_managedCursor.getColumnIndex(l_projection[3]);

            int l_colColor = l_managedCursor.getColumnIndex(l_projection[4]);

            int l_colAccount_name = l_managedCursor.getColumnIndex(l_projection[5]);



            do {

                l_title = l_managedCursor.getString(l_colTitle);

                // l_begin = getDate(l_managedCursor.getLong(l_colBegin));

                //l_end = getDate(l_managedCursor.getLong(l_colEnd));

                EventItem n = new EventItem();
                n.setTitle(l_title);
                n.setDtStart(l_managedCursor.getLong(l_colBegin));
                n.setDtEnd(l_managedCursor.getLong(l_colEnd));
                n.setColorEvent(l_managedCursor.getString(l_colColor));
                n.setIdEvent(l_managedCursor.getLong(l_colID));
                n.setCalendar(l_managedCursor.getString(l_colAccount_name));

                //n.setColorEvent(l_managedCursor.getString(l_color));
                res.add(n);


            } while (l_managedCursor.moveToNext());
        }
        l_managedCursor.close();
        return res;
    }
}
