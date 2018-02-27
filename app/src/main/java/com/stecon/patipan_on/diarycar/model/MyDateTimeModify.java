package com.stecon.patipan_on.diarycar.model;

import android.util.Log;

import java.util.concurrent.ExecutionException;

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
        this.day = Integer.parseInt(strDate.substring(0, first));
        this.month = Integer.parseInt(strDate.substring(first + 1, last));
        this.year = Integer.parseInt(strDate.substring(last+1 , dateTimeIndex));

        int cutIndexTime = strTime.indexOf(":");

        this.hour = Integer.parseInt(strTime.substring(0, cutIndexTime));
        this.minute = Integer.parseInt(strTime.substring(cutIndexTime + 1));


    }

    public MyDateTimeModify() {

    }

    public void customDateTimeModifySqlite(String strInput) {
        //format (yyyy-mm-dd ##:##)
        int dateTimeIndex = strInput.indexOf(" ");
        String strDate = strInput.substring(0, dateTimeIndex);
        String strTime = strInput.substring(dateTimeIndex + 1);

        int first = strDate.indexOf("-");
        int last = strDate.lastIndexOf("-");

        this.year = Integer.parseInt(strDate.substring(0, first));
        this.month = Integer.parseInt(strDate.substring(first + 1, last));
        this.day = Integer.parseInt(strDate.substring(last + 1));

        int firstTime = strTime.indexOf(":");
        int lastTime = strTime.lastIndexOf(":");

        this.hour = Integer.parseInt(strTime.substring(0, firstTime));
        this.minute = Integer.parseInt(strTime.substring(firstTime + 1, lastTime));


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


    public static String[] getStrDateTimeFromSqlite(String str) {
        String[] dateTime = new String[2];

        int myIndexOf = str.indexOf(" ");
        String day = str.substring(0, myIndexOf);
        String time = str.substring(myIndexOf + 1);
        dateTime[0] = day;
        dateTime[1] = time;


        return dateTime;
    }

    public static String getCustomMonthDateYear(String strDate) { // input MM/DD/YYYYY example 2/16/1995

        int firstIndexOf = strDate.indexOf("/");
        int lastIndexOf = strDate.lastIndexOf("/");

        String day = strDate.substring(firstIndexOf + 1, lastIndexOf);
        String month = strDate.substring(0, firstIndexOf);
        String year = strDate.substring(lastIndexOf + 1);

        String date = day + "/" + month + "/" + year;
        Log.d("date => ", date);
        return date;
    }
}
