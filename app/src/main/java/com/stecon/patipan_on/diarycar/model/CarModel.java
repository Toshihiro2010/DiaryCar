package com.stecon.patipan_on.diarycar.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by patipan_on on 3/3/2018.
 */

public class CarModel {

    @SerializedName("recordsets")
    @Expose
    private ArrayList<ArrayList<Recordset>> recordsets = null;
    @SerializedName("recordset")
    @Expose
    private ArrayList<Recordset_> recordset = null;
    @SerializedName("output")
    @Expose
    private Output output;
    @SerializedName("rowsAffected")
    @Expose
    private ArrayList<Integer> rowsAffected = null;

    public ArrayList<ArrayList<Recordset>> getRecordsets() {
        return recordsets;
    }

    public void setRecordsets(ArrayList<ArrayList<Recordset>> recordsets) {
        this.recordsets = recordsets;
    }

    public ArrayList<Recordset_> getRecordset() {
        return recordset;
    }

    public void setRecordset(ArrayList<Recordset_> recordset) {
        this.recordset = recordset;
    }

    public Output getOutput() {
        return output;
    }

    public void setOutput(Output output) {
        this.output = output;
    }

    public ArrayList<Integer> getRowsAffected() {
        return rowsAffected;
    }

    public void setRowsAffected(ArrayList<Integer> rowsAffected) {
        this.rowsAffected = rowsAffected;
    }
}
