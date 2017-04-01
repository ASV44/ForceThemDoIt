package com.example.hackintosh.forcethemdoit;

import android.telephony.SmsManager;

/**
 * Created by hackintosh on 4/1/17.
 */

public class SmsSender {
    SmsManager smsManager;

    public SmsSender() {
        smsManager = SmsManager.getDefault();
    }

    public void sendSms(String number, String message) {
        smsManager.sendTextMessage(number, null, message, null, null);
    }
}
