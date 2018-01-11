package com.stecon.patipan_on.diarycar.model;

/**
 * Created by patipan_on on 1/9/2018.
 */

public class StatustoServerModel {
    private int id;
    private int status;
    private String message;
    private String transaction_date;
    private String create_date;
    private String update_date;
    private String create_by;
    private String update_by;

    public StatustoServerModel(int id, int status, String message, String transaction_date, String create_date, String update_date, String create_by, String update_by) {
        this.id = id;
        this.status = status;
        this.message = message;
        this.transaction_date = transaction_date;
        this.create_date = create_date;
        this.update_date = update_date;
        this.create_by = create_by;
        this.update_by = update_by;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTransaction_date() {
        return transaction_date;
    }

    public void setTransaction_date(String transaction_date) {
        this.transaction_date = transaction_date;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getUpdate_date() {
        return update_date;
    }

    public void setUpdate_date(String update_date) {
        this.update_date = update_date;
    }

    public String getCreate_by() {
        return create_by;
    }

    public void setCreate_by(String create_by) {
        this.create_by = create_by;
    }

    public String getUpdate_by() {
        return update_by;
    }

    public void setUpdate_by(String update_by) {
        this.update_by = update_by;
    }
}
