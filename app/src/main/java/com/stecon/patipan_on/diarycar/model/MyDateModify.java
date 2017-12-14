package com.stecon.patipan_on.diarycar.model;

/**
 * Created by patipan_on on 11/14/2017.
 */

public class MyDateModify {

    private int day;
    private int month;
    private int year;

    private String strDate;

    public MyDateModify(String strInput) {
        int first = strInput.indexOf("/");
        int last = strInput.lastIndexOf("/");

        this.day = Integer.parseInt(strInput.substring(0, first));
        this.month = Integer.parseInt(strInput.substring(first + 1, last));
        this.year = Integer.parseInt(strInput.substring(last+1));
    }

    public MyDateModify() {
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public String getStrDate(){
        strDate = day + "/" + month + "/" + year;
        return strDate;
    }

    public String getStrDateToServer(String str){
        int first = str.indexOf("/");
        int last = str.lastIndexOf("/");

        String svDay = str.substring(0, first);
        String svMonth = str.substring(first + 1, last);
        if (svMonth.length() == 1) {
            svMonth = "0" + svMonth;
        }
        String svYear = str.substring(last+1);

        String strToserver = svDay + svMonth + svYear;
        return strToserver;
    }

    public String getStrDateTimeModify(int day, int month, int year, int hour, int minute) {
        //String myDateTime = day + "/" + month + "/" + year + " " + hour + ":" + minute;
        String tempHour;
        String tempMinute;
        if (hour < 10) {
            tempHour = "0" + hour;
        } else {
            tempHour = String.valueOf(hour);
        }

        if (minute < 10) {
            tempMinute = "0" + minute;
        } else {
            tempMinute = String.valueOf(minute);
        }
        String myDateTime = year + "-" + month + "-" + day + " " + tempHour + ":" + tempMinute + ":00";
        return myDateTime;
    }


}
