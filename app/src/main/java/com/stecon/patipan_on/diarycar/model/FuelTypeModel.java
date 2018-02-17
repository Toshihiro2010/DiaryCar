package com.stecon.patipan_on.diarycar.model;

/**
 * Created by patipan_on on 2/12/2018.
 */

public class FuelTypeModel {

    private int id;
    private int type_id;
    private String type_name_th;
    private String type_name_en;
    private String create_date;
    private String update_date;
    private String create_by;
    private String update_by;
    private String status;

    public FuelTypeModel(int id, int type_id, String type_name_th, String type_name_en, String create_date, String update_date, String create_by, String update_by, String status) {
        this.id = id;
        this.type_id = type_id;
        this.type_name_th = type_name_th;
        this.type_name_en = type_name_en;
        this.create_date = create_date;
        this.update_date = update_date;
        this.create_by = create_by;
        this.update_by = update_by;
        this.status = status;
    }

    public FuelTypeModel(int id, int type_id, String type_name_th, String type_name_en, String create_date, String update_date, String create_by, String update_by) {
        this.id = id;
        this.type_id = type_id;
        this.type_name_th = type_name_th;
        this.type_name_en = type_name_en;
        this.create_date = create_date;
        this.update_date = update_date;
        this.create_by = create_by;
        this.update_by = update_by;
    }

    public FuelTypeModel(int id, int type_id, String type_name_th, String type_name_en) {
        this.id = id;
        this.type_id = type_id;
        this.type_name_th = type_name_th;
        this.type_name_en = type_name_en;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    public String getType_name_th() {
        return type_name_th;
    }

    public void setType_name_th(String type_name_th) {
        this.type_name_th = type_name_th;
    }

    public String getType_name_en() {
        return type_name_en;
    }

    public void setType_name_en(String type_name_en) {
        this.type_name_en = type_name_en;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
