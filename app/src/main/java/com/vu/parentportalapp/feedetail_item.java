package com.vu.parentportalapp;

public class feedetail_item {
    String month;
    String feeType;
    String dueDate;
    String paidDate;
    String amount;
    String Status;

    public feedetail_item() {
        this.month = month;
        this.feeType = feeType;
        this.dueDate = dueDate;
        this.paidDate = paidDate;
        this.amount = amount;
        this.Status = Status;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getPaidDate() {
        return paidDate;
    }

    public void setPaidDate(String paidDate) {
        this.paidDate = paidDate;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
