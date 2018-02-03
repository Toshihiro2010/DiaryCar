package com.stecon.patipan_on.diarycar.model;

/**
 * Created by patipan_on on 1/31/2018.
 */

public class UserModel {

    private int id;
    private String employee_id;
    private String employee_name;
    private String pin_code;
    private String date_create;
    private String date_update;
    private String create_by;
    private String update_by;
    private int status;

    public UserModel(int id, String employee_id, String employee_name, String pin_code, String date_create, String date_update, String create_by, String update_by, int status) {
        this.id = id;
        this.employee_id = employee_id;
        this.employee_name = employee_name;
        this.pin_code = pin_code;
        this.date_create = date_create;
        this.date_update = date_update;
        this.create_by = create_by;
        this.update_by = update_by;
        this.status = status;
    }

    public UserModel(int id, String employee_id, String employee_name, String pin_code, String date_create, String date_update, String create_by, String update_by) {
        this.id = id;
        this.employee_id = employee_id;
        this.employee_name = employee_name;
        this.pin_code = pin_code;
        this.date_create = date_create;
        this.date_update = date_update;
        this.create_by = create_by;
        this.update_by = update_by;
    }

    public UserModel() {
    }

    public UserModel(int id, String employee_id, String employee_name, String pin_code) {
        this.id = id;
        this.employee_id = employee_id;
        this.employee_name = employee_name;
        this.pin_code = pin_code;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(String employee_id) {
        this.employee_id = employee_id;
    }

    public String getEmployee_name() {
        return employee_name;
    }

    public void setEmployee_name(String employee_name) {
        this.employee_name = employee_name;
    }

    public String getPin_code() {
        return pin_code;
    }

    public void setPin_code(String pin_code) {
        this.pin_code = pin_code;
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
