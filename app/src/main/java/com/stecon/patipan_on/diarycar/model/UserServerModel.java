package com.stecon.patipan_on.diarycar.model;

/**
 * Created by patipan_on on 2/1/2018.
 */

public class UserServerModel {

    private int UserId;
    private String LoginName;
    private String FirstName;
    private String LastName;
    private String FirstNameEng;
    private String LastNameEng;
    private String DisplayName;
    private String EmpNo;
    private String EmailAddress;
    private String Department;
    private String JobNo;
    private String AuthenType;
    private String PositionCode;
    private String PositionThai;
    private String JobCode;
    private String JobThai;
    private String JobEng;

    public UserServerModel(int userId, String loginName, String firstName, String lastName, String firstNameEng, String lastNameEng, String displayName, String empNo, String emailAddress, String department, String jobNo, String authenType, String positionCode, String positionThai, String jobCode, String jobThai, String jobEng) {
        UserId = userId;
        LoginName = loginName;
        FirstName = firstName;
        LastName = lastName;
        FirstNameEng = firstNameEng;
        LastNameEng = lastNameEng;
        DisplayName = displayName;
        EmpNo = empNo;
        EmailAddress = emailAddress;
        Department = department;
        JobNo = jobNo;
        AuthenType = authenType;
        PositionCode = positionCode;
        PositionThai = positionThai;
        JobCode = jobCode;
        JobThai = jobThai;
        JobEng = jobEng;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public String getLoginName() {
        return LoginName;
    }

    public void setLoginName(String loginName) {
        LoginName = loginName;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getFirstNameEng() {
        return FirstNameEng;
    }

    public void setFirstNameEng(String firstNameEng) {
        FirstNameEng = firstNameEng;
    }

    public String getLastNameEng() {
        return LastNameEng;
    }

    public void setLastNameEng(String lastNameEng) {
        LastNameEng = lastNameEng;
    }

    public String getDisplayName() {
        return DisplayName;
    }

    public void setDisplayName(String displayName) {
        DisplayName = displayName;
    }

    public String getEmpNo() {
        return EmpNo;
    }

    public void setEmpNo(String empNo) {
        EmpNo = empNo;
    }

    public String getEmailAddress() {
        return EmailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        EmailAddress = emailAddress;
    }

    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String department) {
        Department = department;
    }

    public String getJobNo() {
        return JobNo;
    }

    public void setJobNo(String jobNo) {
        JobNo = jobNo;
    }

    public String getAuthenType() {
        return AuthenType;
    }

    public void setAuthenType(String authenType) {
        AuthenType = authenType;
    }

    public String getPositionCode() {
        return PositionCode;
    }

    public void setPositionCode(String positionCode) {
        PositionCode = positionCode;
    }

    public String getPositionThai() {
        return PositionThai;
    }

    public void setPositionThai(String positionThai) {
        PositionThai = positionThai;
    }

    public String getJobCode() {
        return JobCode;
    }

    public void setJobCode(String jobCode) {
        JobCode = jobCode;
    }

    public String getJobThai() {
        return JobThai;
    }

    public void setJobThai(String jobThai) {
        JobThai = jobThai;
    }

    public String getJobEng() {
        return JobEng;
    }

    public void setJobEng(String jobEng) {
        JobEng = jobEng;
    }
}
