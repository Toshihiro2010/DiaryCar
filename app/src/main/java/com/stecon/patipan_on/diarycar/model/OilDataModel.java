package com.stecon.patipan_on.diarycar.model;

import android.database.Cursor;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by patipan_on on 12/11/2017.
 */

public class OilDataModel {

    private int id;


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


    private String strLocation;


    private String date_create;


    private String date_update;

    private String create_by;


    private String update_by;

    private int status;

    public OilDataModel(int id, double odometer, double unit_price, double volume,String fuel_type,double total_price, int partial_fillup, String payment_type, double latitude, double longitude, String note, String transaction_date) {
        this.id = id;
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

    public OilDataModel(int id, double odometer, double unit_price, double volume, String fuel_type, double total_price, int partial_fillup, String payment_type, double latitude, double longitude, String note, String transaction_date, String strLocation, String date_create, String date_update, String create_by, String update_by , int status) {
        this.id = id;
        this.odometer = odometer;
        this.unit_price = unit_price;
        this.volume = volume;
        this.fuel_type = fuel_type;
        this.total_price = total_price;
        this.partial_fillup = partial_fillup;
        this.payment_type = payment_type;
        this.latitude = latitude;
        this.longitude = longitude;
        this.note = note;
        this.transaction_date = transaction_date;
        this.strLocation = strLocation;
        this.date_create = date_create;
        this.date_update = date_update;
        this.create_by = create_by;
        this.update_by = update_by;
        this.status = status;
    }


    public String getStrLocation() {
        return strLocation;
    }

    public void setStrLocation(String strLocation) {
        this.strLocation = strLocation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
