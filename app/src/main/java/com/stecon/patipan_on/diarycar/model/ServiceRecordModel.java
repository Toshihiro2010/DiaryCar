package com.stecon.patipan_on.diarycar.model;

/**
 * Created by patipan_on on 1/9/2018.
 */

public class ServiceRecordModel {

    private int id;
    private int service_id;
    private String license_plate;
    private Double odometer;
    private Double fuel_level;
    private Double service_cost;
    private Double latitude;
    private Double longitude;
    private String location_name;
    private String note;
    private String transaction_date;
    private String date_create;
    private String date_update;
    private String create_by;
    private String update_by;
    private int status;

    public ServiceRecordModel(int id, int service_id, String license_plate, Double odometer, Double fuel_level, Double service_cost, Double latitude, Double longitude, String location_name, String note, String transaction_date, String date_create, String date_update, String create_by, String update_by, int status) {
        this.id = id;
        this.service_id = service_id;
        this.license_plate = license_plate;
        this.odometer = odometer;
        this.fuel_level = fuel_level;
        this.service_cost = service_cost;
        this.latitude = latitude;
        this.longitude = longitude;
        this.location_name = location_name;
        this.note = note;
        this.transaction_date = transaction_date;
        this.date_create = date_create;
        this.date_update = date_update;
        this.create_by = create_by;
        this.update_by = update_by;
        this.status = status;
    }

    public ServiceRecordModel(int id, int service_id, String license_plate, Double odometer, Double fuel_level, Double service_cost, Double latitude, Double longitude, String note, String transaction_date, String date_create, String date_update, String create_by, String update_by) {
        this.id = id;
        this.service_id = service_id;
        this.license_plate = license_plate;
        this.odometer = odometer;
        this.fuel_level = fuel_level;
        this.service_cost = service_cost;
        this.latitude = latitude;
        this.longitude = longitude;
        this.note = note;
        this.transaction_date = transaction_date;
        this.date_create = date_create;
        this.date_update = date_update;
        this.create_by = create_by;
        this.update_by = update_by;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getService_id() {
        return service_id;
    }

    public void setService_id(int service_id) {
        this.service_id = service_id;
    }

    public String getLicense_plate() {
        return license_plate;
    }

    public void setLicense_plate(String license_plate) {
        this.license_plate = license_plate;
    }

    public Double getOdometer() {
        return odometer;
    }

    public void setOdometer(Double odometer) {
        this.odometer = odometer;
    }

    public Double getFuel_level() {
        return fuel_level;
    }

    public void setFuel_level(Double fuel_level) {
        this.fuel_level = fuel_level;
    }

    public Double getService_cost() {
        return service_cost;
    }

    public void setService_cost(Double service_cost) {
        this.service_cost = service_cost;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
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

    public String getLocation_name() {
        return location_name;
    }

    public void setLocation_name(String location_name) {
        this.location_name = location_name;
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
