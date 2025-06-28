package com.vu.parentportalapp;

public class LeaveStatusItem {


    private int leaveId;
    private String studentName;
    private String studentClass;

    public LeaveStatusItem(int leaveId, String studentName, String studentClass) {
        this.leaveId = leaveId;
        this.studentName = studentName;
        this.studentClass = studentClass;
    }
    // Getters
    public int getLeaveId() {
        return leaveId;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getStudentClass() {
        return studentClass;
    }

    // Setters (optional, if needed)
    public void setLeaveId(int leaveId) {
        this.leaveId = leaveId;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public void setStudentClass(String studentClass) {
        this.studentClass = studentClass;
    }
}