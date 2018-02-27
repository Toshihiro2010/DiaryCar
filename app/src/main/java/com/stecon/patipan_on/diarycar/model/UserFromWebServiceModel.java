package com.stecon.patipan_on.diarycar.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by patipan_on on 2/23/2018.
 */

public class UserFromWebServiceModel {

    @SerializedName("JobEng")
    @Expose
    private String jobEng;
    @SerializedName("LastNameEng")
    @Expose
    private String lastNameEng;
    @SerializedName("xmlns:xsd")
    @Expose
    private String xmlnsXsd;
    @SerializedName("PositionEng")
    @Expose
    private String positionEng;
    @SerializedName("LoginName")
    @Expose
    private String loginName;
    @SerializedName("EmpNo")
    @Expose
    private String empNo;
    @SerializedName("AuthenType")
    @Expose
    private String authenType;
    @SerializedName("IDCard")
    @Expose
    private String iDCard;
    @SerializedName("xmlns")
    @Expose
    private String xmlns;
    @SerializedName("UserId")
    @Expose
    private String userId;
    @SerializedName("LastName")
    @Expose
    private String lastName;
    @SerializedName("DisplayName")
    @Expose
    private String displayName;
    @SerializedName("JobNo")
    @Expose
    private String jobNo;
    @SerializedName("JobCode")
    @Expose
    private String jobCode;
    @SerializedName("BirthDate")
    @Expose
    private String birthDate;
    @SerializedName("PositionCode")
    @Expose
    private String positionCode;
    @SerializedName("JobThai")
    @Expose
    private String jobThai;
    @SerializedName("ExpireDate")
    @Expose
    private String expireDate;
    @SerializedName("FirstNameEng")
    @Expose
    private String firstNameEng;
    @SerializedName("EmailAddress")
    @Expose
    private String emailAddress;
    @SerializedName("PositionThai")
    @Expose
    private String positionThai;
    @SerializedName("xmlns:xsi")
    @Expose
    private String xmlnsXsi;
    @SerializedName("FirstName")
    @Expose
    private String firstName;
    @SerializedName("Department")
    @Expose
    private String department;

    public UserFromWebServiceModel(String jobEng, String lastNameEng, String xmlnsXsd, String positionEng, String loginName, String empNo, String authenType, String iDCard, String xmlns, String userId, String lastName, String displayName, String jobNo, String jobCode, String birthDate, String positionCode, String jobThai, String expireDate, String firstNameEng, String emailAddress, String positionThai, String xmlnsXsi, String firstName, String department) {
        this.jobEng = jobEng;
        this.lastNameEng = lastNameEng;
        this.xmlnsXsd = xmlnsXsd;
        this.positionEng = positionEng;
        this.loginName = loginName;
        this.empNo = empNo;
        this.authenType = authenType;
        this.iDCard = iDCard;
        this.xmlns = xmlns;
        this.userId = userId;
        this.lastName = lastName;
        this.displayName = displayName;
        this.jobNo = jobNo;
        this.jobCode = jobCode;
        this.birthDate = birthDate;
        this.positionCode = positionCode;
        this.jobThai = jobThai;
        this.expireDate = expireDate;
        this.firstNameEng = firstNameEng;
        this.emailAddress = emailAddress;
        this.positionThai = positionThai;
        this.xmlnsXsi = xmlnsXsi;
        this.firstName = firstName;
        this.department = department;
    }

    public String getJobEng() {
        return jobEng;
    }

    public void setJobEng(String jobEng) {
        this.jobEng = jobEng;
    }

    public String getLastNameEng() {
        return lastNameEng;
    }

    public void setLastNameEng(String lastNameEng) {
        this.lastNameEng = lastNameEng;
    }

    public String getXmlnsXsd() {
        return xmlnsXsd;
    }

    public void setXmlnsXsd(String xmlnsXsd) {
        this.xmlnsXsd = xmlnsXsd;
    }

    public String getPositionEng() {
        return positionEng;
    }

    public void setPositionEng(String positionEng) {
        this.positionEng = positionEng;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public String getAuthenType() {
        return authenType;
    }

    public void setAuthenType(String authenType) {
        this.authenType = authenType;
    }

    public String getiDCard() {
        return iDCard;
    }

    public void setiDCard(String iDCard) {
        this.iDCard = iDCard;
    }

    public String getXmlns() {
        return xmlns;
    }

    public void setXmlns(String xmlns) {
        this.xmlns = xmlns;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getJobNo() {
        return jobNo;
    }

    public void setJobNo(String jobNo) {
        this.jobNo = jobNo;
    }

    public String getJobCode() {
        return jobCode;
    }

    public void setJobCode(String jobCode) {
        this.jobCode = jobCode;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getPositionCode() {
        return positionCode;
    }

    public void setPositionCode(String positionCode) {
        this.positionCode = positionCode;
    }

    public String getJobThai() {
        return jobThai;
    }

    public void setJobThai(String jobThai) {
        this.jobThai = jobThai;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public String getFirstNameEng() {
        return firstNameEng;
    }

    public void setFirstNameEng(String firstNameEng) {
        this.firstNameEng = firstNameEng;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPositionThai() {
        return positionThai;
    }

    public void setPositionThai(String positionThai) {
        this.positionThai = positionThai;
    }

    public String getXmlnsXsi() {
        return xmlnsXsi;
    }

    public void setXmlnsXsi(String xmlnsXsi) {
        this.xmlnsXsi = xmlnsXsi;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
