package com.dsoft.mycalendar.Calendar;

/**
 * Created by enrique on 17/10/14.
 */
class MyCalendar {

    public String name;

    public String id;

    public MyCalendar(String _name, String _id) {

        name = _name;

        id = _id;

    }

    @Override

    public String toString() {

        return name;

    }

}