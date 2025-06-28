package com.vu.parentportalapp;

public class ReportCardItem {
    private String term;
    private String subject;
    private int maxMarks;
    private int marksObtained;
    private String grade;

    public ReportCardItem(String term, String subject, int maxMarks, int marksObtained, String grade) {
        this.term = term;
        this.subject = subject;
        this.maxMarks = maxMarks;
        this.marksObtained = marksObtained;
        this.grade = grade;
    }

    // Getters
    public String getTerm() {
        return term;
    }

    public String getSubject() {
        return subject;
    }

    public int getMaxMarks() {
        return maxMarks;
    }

    public int getMarksObtained() {
        return marksObtained;
    }

    public String getGrade() {
        return grade;
    }

    // Setters (if needed)
    public void setTerm(String term) {
        this.term = term;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setMaxMarks(int maxMarks) {
        this.maxMarks = maxMarks;
    }

    public void setMarksObtained(int marksObtained) {
        this.marksObtained = marksObtained;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
