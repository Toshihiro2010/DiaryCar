package com.stecon.patipan_on.diarycar.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by patipan_on on 2/22/2018.
 */

public class UserTemp {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("employee_id")
    @Expose
    private String employeeId;
    @SerializedName("idCard")
    @Expose
    private String idCard;
    @SerializedName("birth")
    @Expose
    private String birth;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }
}
