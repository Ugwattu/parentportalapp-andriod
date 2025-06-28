package com.vu.parentportalapp;

public class AddReportCardItem {

    private String rollNo;
    private String studentName;
    private String addSubjectNumber;

    public AddReportCardItem(String rollNo, String studentName, String addSubjectNumber) {
        this.rollNo = rollNo;
        this.studentName = studentName;
        this.addSubjectNumber = addSubjectNumber;
    }

    public String getRollNo() {
        return rollNo;
    }

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getAddSubjectNumber() {
        return addSubjectNumber;
    }

    public void setAddSubjectNumber(String addSubjectNumber) {
        this.addSubjectNumber = addSubjectNumber;
    }
}
