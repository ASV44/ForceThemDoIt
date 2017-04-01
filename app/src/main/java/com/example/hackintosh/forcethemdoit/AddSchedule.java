package com.example.hackintosh.forcethemdoit;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AddSchedule extends AppCompatActivity {

    SmsSender sender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);

        Button sendSms = (Button) findViewById(R.id.getCsvText);

        sendSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sender.sendSms("068591082", "We are developing SMS sender now");
            }
        });

        sender = new SmsSender();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},1);
        }
    }
}
