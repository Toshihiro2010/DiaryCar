package com.stecon.patipan_on.diarycar.model;

/**
 * Created by patipan_on on 1/9/2018.
 */

public class LogModel {
    private int id;
    private String message;
    private String transaction_datetime;

    public LogModel(int id, String message, String transaction_datetime) {
        this.id = id;
        this.message = message;
        this.transaction_datetime = transaction_datetime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTransaction_datetime() {
        return transaction_datetime;
    }

    public void setTransaction_datetime(String transaction_datetime) {
        this.transaction_datetime = transaction_datetime;
    }
}
