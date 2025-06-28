package com.vu.parentportalapp;

public class TimeTableItem {
    String TimeTableSubject;
    String TimeTableTeacher;
    String TimeTableDuration;

    public TimeTableItem(String timeTableDuration, String timeTableTeacher, String timeTableSubject) {
        this.TimeTableDuration = timeTableDuration;
        this.TimeTableTeacher = timeTableTeacher;
        this.TimeTableSubject = timeTableSubject;
    }

    public String getTimeTableSubject() {
        return TimeTableSubject;
    }

    public void setTimeTableSubject(String timeTableSubject) {
        TimeTableSubject = timeTableSubject;
    }

    public String getTimeTableTeacher() {
        return TimeTableTeacher;
    }

    public void setTimeTableTeacher(String timeTableTeacher) {
        TimeTableTeacher = timeTableTeacher;
    }

    public String getTimeTableDuration() {
        return TimeTableDuration;
    }

    public void setTimeTableDuration(String timeTableDuration) {
        TimeTableDuration = timeTableDuration;
    }
}
