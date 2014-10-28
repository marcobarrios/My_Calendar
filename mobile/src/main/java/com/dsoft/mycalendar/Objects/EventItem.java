package com.dsoft.mycalendar.Objects;

/**
 * Created by enrique on 19/10/14.
 */
public class EventItem {
    private Long idEvent;
    private String title;
    private String description;
    private String location;
    private String calendar;
    private Long dtStart;
    private Long dtEnd;

    private String colorEvent;
    private Boolean isAllDay;

    public EventItem(){};
    public EventItem(String title, String description, String location, String calendar, Long dtStart, Long dtEnd)
    {
        this.title = title;
        this.description = description;
        this.calendar = calendar;
        this.dtStart = dtStart;
        this.dtEnd = dtEnd;
        this.location = location;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getDtStart() {
        return dtStart;
    }

    public void setDtStart(Long dtStart) {
        this.dtStart = dtStart;
    }

    public Long getDtEnd() {
        return dtEnd;
    }

    public void setDtEnd(Long dtEnd) {
        this.dtEnd = dtEnd;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getColorEvent() {
        return colorEvent;
    }

    public void setColorEvent(String colorEvent) {
        this.colorEvent = colorEvent;
    }

    public Boolean getIsAllDay() {
        return isAllDay;
    }

    public void setIsAllDay(Boolean isAllDay) {
        this.isAllDay = isAllDay;
    }

    public Long getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(Long idEvent) {
        this.idEvent = idEvent;
    }

    public String getCalendar() {
        return calendar;
    }

    public void setCalendar(String calendar) {
        this.calendar = calendar;
    }
}
