package com.example.hackintosh.forcethemdoit;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

public class AddSchedule extends AppCompatActivity {

    SmsSender sender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);

        Button sendSms = (Button) findViewById(R.id.getCsvText);
        final TextView recipientsList = (TextView) findViewById(R.id.recipientsList);

        sendSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String recipientsListString = recipientsList.getText().toString();
                Log.d("myTag2", recipientsListString);

                sender.sendSms("068591082", "We are developing SMS sender now");
            }
        });

        sender = new SmsSender();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},1);
        }
    }

//    private Map<String, String> recipientsMap(String recipientsListString) {
//        String[] lines = recipientsListString.split( "\n" );
//        return 0;
//    }
}
