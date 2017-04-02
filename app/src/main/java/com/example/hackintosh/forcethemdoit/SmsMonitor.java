package com.example.hackintosh.forcethemdoit;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;
import android.util.Log;

import java.util.HashMap;
import java.util.List;

/**
 * Created by hackintosh on 4/1/17.
 */

public class SmsMonitor extends BroadcastReceiver {
    private static final String ACTION = "android.provider.Telephony.SMS_RECEIVED";

    private SmsMessage messages;
    private HashMap<String,String> victims;

    public SmsMonitor() {}

    public SmsMonitor(HashMap<String,String> victims) {
        this.victims = victims;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("BroadcastReceiver","Receive");
        if (intent != null && intent.getAction() != null &&
                ACTION.compareToIgnoreCase(intent.getAction()) == 0) {
            Object[] pduArray = (Object[]) intent.getExtras().get("pdus");
            for (Object aPduArray : pduArray) {
                messages = SmsMessage.createFromPdu((byte[]) aPduArray);
//                if(victims.keySet().contains(messages.getDisplayOriginatingAddress())) {
//                    Log.d("currentNumbers","" + victims);
//                    //currentNumbers.remove(messages.getDisplayOriginatingAddress());
//                }
                Log.d("Message",messages.getMessageBody().toString());
            }
        }
    }
}
