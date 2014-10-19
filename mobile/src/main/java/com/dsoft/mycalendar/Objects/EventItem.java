package com.dsoft.mycalendar.Objects;

/**
 * Created by enrique on 19/10/14.
 */
public class EventItem {
    private String title;
    private String description;
    private String location;
    private Long dtStart;
    private Long dtEnd;

    public EventItem (String title, String description,String location,Long dtStart , Long dtEnd)
    {
        this.title = title;
        this.description = description;
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
}
