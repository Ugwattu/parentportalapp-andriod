package com.vu.parentportalapp;

public class DailyDiaryItem {
    String DiaryDescripton;
    String DiaryDate;

    public DailyDiaryItem(String diaryDescripton, String diaryDate) {
        this.DiaryDescripton = diaryDescripton;
        this.DiaryDate = diaryDate;
    }

    public String getDiaryDescripton() {
        return DiaryDescripton;
    }

    public void setDiaryDescripton(String diaryDescripton) {
        this.DiaryDescripton = diaryDescripton;
    }

    public String getDiaryDate() {
        return DiaryDate;
    }

    public void setDiaryDate(String diaryDate) {
        this.DiaryDate = diaryDate;
    }
}
