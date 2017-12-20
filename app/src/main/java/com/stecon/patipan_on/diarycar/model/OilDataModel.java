package com.stecon.patipan_on.diarycar.model;

import android.database.Cursor;

/**
 * Created by patipan_on on 12/11/2017.
 */

public class OilDataModel {

    private double odometer;
    private double unit_price;
    private double volume;
    private String fuel_type;
    private double total_price;
    private int partial_fillup;
    private String payment_type;
    private double latitude;
    private double longitude;
    private String note;
    private String transaction_date;

    public OilDataModel(double odometer, double unit_price, double volume,String fuel_type,double total_price, int partial_fillup, String payment_type, double latitude, double longitude, String note, String transaction_date) {
        this.odometer = odometer;
        this.unit_price = unit_price;
        this.volume = volume;
        this.total_price = total_price;
        this.partial_fillup = partial_fillup;
        this.payment_type = payment_type;
        this.latitude = latitude;
        this.longitude = longitude;
        this.note = note;
        this.transaction_date = transaction_date;
        this.fuel_type = fuel_type;
    }


    public OilDataModel() {

    }

    public String getFuel_type() {
        return fuel_type;
    }

    public void setFuel_type(String fuel_type) {
        this.fuel_type = fuel_type;
    }

    public double getOdometer() {
        return odometer;
    }

    public double getUnit_price() {
        return unit_price;
    }

    public double getVolume() {
        return volume;
    }

    public double getTotal_price() {
        return total_price;
    }

    public int getPartial_fillup() {
        return partial_fillup;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getNote() {
        return note;
    }

    public String getTransaction_date() {
        return transaction_date;
    }

    public void setOdometer(double odometer) {
        this.odometer = odometer;
    }

    public void setUnit_price(double unit_price) {
        this.unit_price = unit_price;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }

    public void setPartial_fillup(int partial_fillup) {
        this.partial_fillup = partial_fillup;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setTransaction_date(String transaction_date) {
        this.transaction_date = transaction_date;
    }
}
