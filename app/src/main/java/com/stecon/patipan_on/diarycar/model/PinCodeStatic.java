package com.stecon.patipan_on.diarycar.model;

/**
 * Created by patipan_on on 1/31/2018.
 */

public class PinCodeStatic {

    private static String pinNumber;

    public static String getPinNumber() {
        return pinNumber;
    }

    public static void setPinNumber(String pinNumber) {
        PinCodeStatic.pinNumber = pinNumber;
    }
}
