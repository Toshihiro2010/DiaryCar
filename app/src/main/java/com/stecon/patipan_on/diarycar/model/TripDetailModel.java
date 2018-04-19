package com.stecon.patipan_on.diarycar.model;

/**
 * Created by patipan_on on 1/9/2018.
 */

public class TripDetailModel {
    private int id;
    private String license_plate;
    private String reservation_number;
    private String purpose;
    private String departure_date;
    private Double departure_odometer;
    private Double departure_latitude;
    private Double departure_longitude;
    private String departure_location_name;
    private String arrival_date;
    private Double arrival_odometer;
    private Double arrival_latitude;
    private Double arrival_longitude;
    private String arrival_location_name;
    private String arrival_parking_location;
    private Double fuel_level;
    private String note;
    private String transaction_date;
    private String date_create;
    private String date_update;
    private String create_by;
    private String update_by;
    private int status;

    public TripDetailModel(
            int id,
            String licen_plate,
            String reservation_number,
            String purpose,
            String depatrture_date,
            Double departure_odometer,
            Double departure_latitude,
            Double departure_longitude,
            String departure_location_name,
            String arrival_date,
            Double arrival_odometer,
            Double arrival_latitude,
            Double arrival_longitude,
            String arrival_location_name,
            String arrival_parking_location,
            Double fuel_level,
            String note,
            String transaction_date,
            String date_create,
            String date_update,
            String create_by,
            String update_by,
            int status) {
        this.id = id;
        this.license_plate = licen_plate;
        this.reservation_number = reservation_number;
        this.purpose = purpose;
        this.departure_date = depatrture_date;
        this.departure_odometer = departure_odometer;
        this.departure_latitude = departure_latitude;
        this.departure_longitude = departure_longitude;
        this.departure_location_name = departure_location_name;
        this.arrival_date = arrival_date;
        this.arrival_odometer = arrival_odometer;
        this.arrival_latitude = arrival_latitude;
        this.arrival_longitude = arrival_longitude;
        this.arrival_location_name = arrival_location_name;
        this.arrival_parking_location = arrival_parking_location;
        this.fuel_level = fuel_level;
        this.note = note;
        this.transaction_date = transaction_date;
        this.date_create = date_create;
        this.date_update = date_update;
        this.create_by = create_by;
        this.update_by = update_by;
        this.status = status;
    }

    public TripDetailModel(int id) {
        this.id = id;
    }

    public TripDetailModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLicen_plate() {
        return license_plate;
    }

    public void setLicen_plate(String licen_plate) {
        this.license_plate = licen_plate;
    }

    public String getReservation_number() {
        return reservation_number;
    }

    public void setReservation_number(String reservation_number) {
        this.reservation_number = reservation_number;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getDepatrture_date() {
        return departure_date;
    }

    public void setDepatrture_date(String depatrture_date) {
        this.departure_date = depatrture_date;
    }

    public Double getDeparture_odometer() {
        return departure_odometer;
    }

    public void setDeparture_odometer(Double departure_odometer) {
        this.departure_odometer = departure_odometer;
    }

    public Double getDeparture_latitude() {
        return departure_latitude;
    }

    public void setDeparture_latitude(Double departure_latitude) {
        this.departure_latitude = departure_latitude;
    }

    public Double getDeparture_longitude() {
        return departure_longitude;
    }

    public void setDeparture_longitude(Double departure_longitude) {
        this.departure_longitude = departure_longitude;
    }

    public String getDeparture_location_name() {
        return departure_location_name;
    }

    public void setDeparture_location_name(String departure_location_name) {
        this.departure_location_name = departure_location_name;
    }

    public String getArrival_date() {
        return arrival_date;
    }

    public void setArrival_date(String arrival_date) {
        this.arrival_date = arrival_date;
    }

    public Double getArrival_odometer() {
        return arrival_odometer;
    }

    public void setArrival_odometer(Double arrival_odometer) {
        this.arrival_odometer = arrival_odometer;
    }

    public Double getArrival_latitude() {
        return arrival_latitude;
    }

    public void setArrival_latitude(Double arrival_latitude) {
        this.arrival_latitude = arrival_latitude;
    }

    public Double getArrival_longitude() {
        return arrival_longitude;
    }

    public void setArrival_longitude(Double arrival_longitude) {
        this.arrival_longitude = arrival_longitude;
    }

    public String getArrival_location_name() {
        return arrival_location_name;
    }

    public void setArrival_location_name(String arrival_location_name) {
        this.arrival_location_name = arrival_location_name;
    }

    public String getArrival_parking_location() {
        return arrival_parking_location;
    }

    public void setArrival_parking_location(String arrival_parking_location) {
        this.arrival_parking_location = arrival_parking_location;
    }

    public Double getFuel_level() {
        return fuel_level;
    }

    public void setFuel_level(Double fuel_level) {
        this.fuel_level = fuel_level;
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
