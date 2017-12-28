package com.stecon.patipan_on.diarycar.model;

/**
 * Created by patipan_on on 12/21/2017.
 */

public class TripCostModel {

    private int id;
    private int trip_id;
    private String price_type;
    private String title;
    private Double money;
    private String note;
    private String transaction_date;

    public TripCostModel(int id ,int trip_id, String price_type, String title, Double money, String note, String transaction_date) {
        this.id = id;
        this.trip_id = trip_id;
        this.price_type = price_type;
        this.title = title;
        this.money = money;
        this.note = note;
        this.transaction_date = transaction_date;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTrip_id() {
        return trip_id;
    }

    public void setTrip_id(int trip_id) {
        this.trip_id = trip_id;
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
}
