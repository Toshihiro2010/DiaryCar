package com.stecon.patipan_on.diarycar.common_class;

import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

/**
 * Created by patipan_on on 4/18/2018.
 */

public class MacAddressCommon {

    public MacAddressCommon() {
    }

    public String getMacAddress() {
        try {
            List<NetworkInterface> networkInterfaceList = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface networkInterface : networkInterfaceList) {
                if (!networkInterface.getName().equalsIgnoreCase("wlan0")) {
                    continue;
                }

                byte[] macAddress = networkInterface.getHardwareAddress();
                if (macAddress == null) {
                    return "";
                }

                StringBuilder result = new StringBuilder();
                for (byte data : macAddress) {
                    result.append(Integer.toHexString(data & 0xFF)).append(":");
                }

                if (result.length() > 0) {
                    result.deleteCharAt(result.length() - 1);
                }
                return result.toString();
            }
        } catch (Exception ignored) {
        }
        return "02:00:00:00:00:00";
    }
}
