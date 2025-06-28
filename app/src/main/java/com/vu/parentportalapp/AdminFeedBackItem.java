package com.vu.parentportalapp;

public class AdminFeedBackItem {


    private int parentId;
    private String fatherName;
    private String studentName;
    private String studentClass;

    public AdminFeedBackItem(int parentId, String fatherName, String studentName, String studentClass) {
        this.parentId = parentId;
        this.fatherName = fatherName;
        this.studentName = studentName;
        this.studentClass = studentClass;
    }
    // Getters
    public int getParentId() {
        return parentId;
    }

    public String getFatherName() {
        return fatherName;
    }


    public String getStudentName() {
        return studentName;
    }

    public String getStudentClass() {
        return studentClass;
    }

    // Setters (optional, if needed)
    public void setParentId(int leaveId) {
        this.parentId = leaveId;
    }


    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public void setStudentClass(String studentClass) {
        this.studentClass = studentClass;
    }
}
