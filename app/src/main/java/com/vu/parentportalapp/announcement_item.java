package com.vu.parentportalapp;

public class announcement_item {

    String announcementName;
    String announcementDescription;
    String announcementDate;
    public announcement_item(String announcementName, String announcementDescription, String announcementDate) {
        this.announcementName = announcementName;
        this.announcementDescription = announcementDescription;
        this.announcementDate = announcementDate;
    }

    public String getAnnouncementName() {
        return announcementName;
    }

    public void setAnnouncementName(String announcementName) {
        this.announcementName = announcementName;
    }

    public String getAnnouncementDescription() {
        return announcementDescription;
    }

    public void setAnnouncementDescription(String announcementDescription) {
        this.announcementDescription = announcementDescription;
    }

    public String getAnnouncementDate() {
        return announcementDate;
    }

    public void setAnnouncementDate(String announcementDate) {
        this.announcementDate = announcementDate;
    }
}
