package com.stecon.patipan_on.diarycar.model;

import android.util.Log;

/**
 * Created by patipan_on on 1/13/2018.
 */

public class MyDateTimeModify {


    private int day;
    private int month;
    private int year;

    private int hour;
    private int minute;

    public MyDateTimeModify(String strInput) {

        int dateTimeIndex = strInput.indexOf(" ");
        String strDate = strInput.substring(0, dateTimeIndex);
        String strTime = strInput.substring(dateTimeIndex + 1);

        int first = strDate.indexOf("/");
        int last = strDate.lastIndexOf("/");
        this.day = Integer.parseInt(strInput.substring(0, first));
        this.month = Integer.parseInt(strInput.substring(first + 1, last));
        this.year = Integer.parseInt(strInput.substring(last+1 , dateTimeIndex));

        int cutIndexTime = strTime.indexOf(":");

        this.hour = Integer.parseInt(strTime.substring(0, cutIndexTime));
        this.minute = Integer.parseInt(strTime.substring(cutIndexTime + 1));


    }

    public MyDateTimeModify() {

    }


    public String getStrDate() {
        String tempDate = this.day + "/" + this.month + "/" + this.year;
        return tempDate;
    }

    public String getStrTime() {

        String tempTime;

        if (minute < 10) {
            tempTime = 0 + String.valueOf(this.minute);
        } else {
            tempTime = String.valueOf(this.minute);
        }
        tempTime = this.hour + ":" + tempTime;

        return tempTime;
    }

    public String getDateTimeToserver() {
        String myDateTime = this.year + "-" + this.month + "-" + this.day + " " + hour + ":" + minute + ":00";


        return myDateTime;
    }




    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }
}
