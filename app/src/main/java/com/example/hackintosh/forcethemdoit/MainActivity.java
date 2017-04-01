package com.example.hackintosh.forcethemdoit;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    SmsSender sender;
    Intent sendSMS;
    private HashMap<String,String> victims;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        victims = new HashMap<String, String>();
        victims.put("069707576","Hi, We are developing and testing SMS Sender Now.\nYou are our victim");
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},1);
        }
        //sender.sendSms("068591082", "Test Message");
        sendSMS = new Intent(this, SmsSender.class);
        sendSMS.putExtra("victims",victims);
        startService(sendSMS);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("SMS_permission","Permitted");
                } else {
                    Log.d("SMS_permission","Failed");
                }
                return;
            }
        }
    }
}
