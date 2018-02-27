package com.stecon.patipan_on.diarycar.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by patipan_on on 2/26/2018.
 */

public class UserByEmpNoModel {

    @SerializedName("User")
    @Expose
    private UserFromWebServiceModel userFromWebServiceModel;

    public UserFromWebServiceModel getUserFromWebServiceModel() {
        return userFromWebServiceModel;
    }

    public void setUserFromWebServiceModel(UserFromWebServiceModel userFromWebServiceModel) {
        this.userFromWebServiceModel = userFromWebServiceModel;
    }
}
