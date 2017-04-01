package com.example.hackintosh.forcethemdoit;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.telephony.SmsManager;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hackintosh on 4/1/17.
 */

public class SmsSender extends Service {
    SmsManager smsManager;
    Intent sendSMSIntent;
    HashMap<String, String> victims = new HashMap<String,String>();
    private int time;
    private CountDownTimer timer;

    @Override
    public void onCreate() {
        Log.d("Service","create");
        super.onCreate();
        smsManager = SmsManager.getDefault();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("Service", "start");
        sendSMSIntent = intent;
        victims = (HashMap<String, String>) intent.getSerializableExtra("victims");
        sendMessagePeriodically();
        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        // STOP YOUR TASKS
        Log.d("Service", "Stop and Destroy");
        stopSelf();
        restartService();
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Log.d("Service", "TASK REMOVED");
        stopSelf();
        super.onTaskRemoved(rootIntent);
    }



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void sendMessagePeriodically() {
        final int tick = 1000;

        timer = new CountDownTimer(time,tick) {
            @Override
            public void onTick(long l) {
//                time -= tick;
//                Log.d("Time_tick","" + time);
            }

            @Override
            public void onFinish() {
                time = 60000;
                sendMessagePeriodically();
                Log.d("Time","" + time);
                for (String victimNumber : victims.keySet()) {
                    Log.d("Send SMS to " + victimNumber, "Message:" + victims.get(victimNumber));
                    sendSms(victimNumber,victims.get(victimNumber));
                }
            }
        }.start();
    }

    public void sendSms(String number, String message) {
        smsManager.sendTextMessage(number, null, message, null, null);
    }

    public void restartService() {
        startService(sendSMSIntent);
    }
}
