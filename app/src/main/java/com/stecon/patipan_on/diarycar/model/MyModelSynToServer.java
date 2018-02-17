package com.stecon.patipan_on.diarycar.model;

import java.util.ArrayList;

/**
 * Created by patipan_on on 1/9/2018.
 */

public class MyModelSynToServer {

    private String date_sync;
    private String sync_by;
    private ArrayList<TripDetailModel> tripDetail;
    private ArrayList<PriceCostModel> tripCost;
    private ArrayList<OilDataModel> fuelData;
    private ArrayList<LogModel> logData;
    private ArrayList<ServiceRecordModel> serviceData;


    public MyModelSynToServer(String date_sync, String sync_by, ArrayList<TripDetailModel> tripDetail, ArrayList<PriceCostModel> tripCost, ArrayList<OilDataModel> fuelData, ArrayList<LogModel> logData, ArrayList<ServiceRecordModel> serviceData) {
        this.date_sync = date_sync;
        this.sync_by = sync_by;
        this.tripDetail = tripDetail;
        this.tripCost = tripCost;
        this.fuelData = fuelData;
        this.logData = logData;
        this.serviceData = serviceData;
    }

    public MyModelSynToServer() {
    }

    public String getDate_sync() {
        return date_sync;
    }

    public void setDate_sync(String date_sync) {
        this.date_sync = date_sync;
    }

    public String getSync_by() {
        return sync_by;
    }

    public void setSync_by(String sync_by) {
        this.sync_by = sync_by;
    }

    public ArrayList<TripDetailModel> getTripDetail() {
        return tripDetail;
    }

    public void setTripDetail(ArrayList<TripDetailModel> tripDetail) {
        this.tripDetail = tripDetail;
    }

    public ArrayList<PriceCostModel> getTripCost() {
        return tripCost;
    }

    public void setTripCost(ArrayList<PriceCostModel> tripCost) {
        this.tripCost = tripCost;
    }

    public ArrayList<OilDataModel> getFuelData() {
        return fuelData;
    }

    public void setFuelData(ArrayList<OilDataModel> fuelData) {
        this.fuelData = fuelData;
    }

    public ArrayList<LogModel> getLogData() {
        return logData;
    }

    public void setLogData(ArrayList<LogModel> logData) {
        this.logData = logData;
    }

    public ArrayList<ServiceRecordModel> getServiceData() {
        return serviceData;
    }

    public void setServiceData(ArrayList<ServiceRecordModel> serviceData) {
        this.serviceData = serviceData;
    }
}

