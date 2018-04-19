package com.stecon.patipan_on.diarycar.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by patipan_on on 4/11/2018.
 */

public class StatusResultServerModel {

    @SerializedName("fuel")
    @Expose
    private Boolean fuel;
    @SerializedName("service")
    @Expose
    private Boolean service;
    @SerializedName("tripCost")
    @Expose
    private Boolean tripCost;
    @SerializedName("tripDetail")
    @Expose
    private Boolean tripDetail;

    public Boolean getFuel() {
        return fuel;
    }

    public void setFuel(Boolean fuel) {
        this.fuel = fuel;
    }

    public Boolean getService() {
        return service;
    }

    public void setService(Boolean service) {
        this.service = service;
    }

    public Boolean getTripCost() {
        return tripCost;
    }

    public void setTripCost(Boolean tripCost) {
        this.tripCost = tripCost;
    }

    public Boolean getTripDetail() {
        return tripDetail;
    }

    public void setTripDetail(Boolean tripDetail) {
        this.tripDetail = tripDetail;
    }

}