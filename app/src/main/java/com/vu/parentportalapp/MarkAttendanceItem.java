package com.vu.parentportalapp;

public class MarkAttendanceItem {
    private String rollNo;
    private String studentName;
    private String attendanceStatus;

    // Constructor
    public MarkAttendanceItem(String rollNo, String studentName, String attendanceStatus) {
        this.rollNo = rollNo;
        this.studentName = studentName;
        this.attendanceStatus = attendanceStatus;
    }

    // Getters
    public String getRollNo() {
        return rollNo;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getAttendanceStatus() {
        return attendanceStatus;
    }
}
