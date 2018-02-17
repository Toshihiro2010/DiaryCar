package com.stecon.patipan_on.diarycar.model;

/**
 * Created by patipan_on on 12/21/2017.
 */

public class PriceCostModel {

    private int id;
    private String license_plate;
    private String price_type;
    private String title;
    private Double money;
    private String note;
    private String transaction_date;
    private String date_create;
    private String date_update;
    private String create_by;
    private String update_by;
    private int status;

    public PriceCostModel(int id , String license_plate, String price_type, String title, Double money, String note, String transaction_date) {
        this.id = id;
        this.license_plate = license_plate;
        this.price_type = price_type;
        this.title = title;
        this.money = money;
        this.note = note;
        this.transaction_date = transaction_date;
    }

    public PriceCostModel(int id, String license_plate, String price_type, String title, Double money, String note, String transaction_date, String date_create, String date_update, String create_by, String update_by, int status) {
        this.id = id;
        this.license_plate = license_plate;
        this.price_type = price_type;
        this.title = title;
        this.money = money;
        this.note = note;
        this.transaction_date = transaction_date;
        this.date_create = date_create;
        this.date_update = date_update;
        this.create_by = create_by;
        this.update_by = update_by;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTrip_id() {
        return license_plate;
    }

    public void setTrip_id(String trip_id) {
        this.license_plate = trip_id;
    }

    public String getPrice_type() {
        return price_type;
    }

    public void setPrice_type(String price_type) {
        this.price_type = price_type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getTransaction_date() {
        return transaction_date;
    }

    public void setTransaction_date(String transaction_date) {
        this.transaction_date = transaction_date;
    }

    public String getDate_create() {
        return date_create;
    }

    public void setDate_create(String date_create) {
        this.date_create = date_create;
    }

    public String getDate_update() {
        return date_update;
    }

    public void setDate_update(String date_update) {
        this.date_update = date_update;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
