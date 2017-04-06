package com.example.hackintosh.forcethemdoit;

import android.Manifest;
import android.app.AlarmManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.icu.util.Calendar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AddSchedule extends AppCompatActivity {

    SmsSender sender;

    private ArrayList<ArrayList> recipientsBigList(String recipientsListString, String defaultMessage) {
        String[] lines = recipientsListString.split( "\n" );
        ArrayList<ArrayList> recList = new ArrayList<ArrayList>();

        for( int i = 0; i < lines.length; i++ ) {
            String[] line_elements = lines[i].split("\\s+");
            String message = "Hi ";
            for( int j = 0; j < line_elements.length - 1; j++){
                message += line_elements[j] + " ";
            }
            ArrayList<String> list = new ArrayList<String>();
            list.add(line_elements[line_elements.length - 1]);
            message += "! " + defaultMessage;
            list.add(message);
            recList.add(list);
        }
        return recList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);

        Button sendSms = (Button) findViewById(R.id.sendButton);
        final TextView recipientsList = (TextView) findViewById(R.id.recipientsList);
        final TextView smsText = (TextView) findViewById(R.id.recipientsText);
        final TextView smsInterval = (TextView) findViewById(R.id.sendDelay);

        sendSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String recipientsListString = recipientsList.getText().toString();
                String defaultMessage = smsText.getText().toString();
                int smsIntervalMsec = Integer.parseInt(smsInterval.getText().toString()) * 60 * 1000;
                Toast.makeText(AddSchedule.this, "Messages are sending now every " + String.valueOf(smsIntervalMsec) + " msec", Toast.LENGTH_LONG).show();
                ArrayList<ArrayList> recipients2Lists = recipientsBigList(recipientsListString, defaultMessage);
            }
        });

        sender = new SmsSender();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},1);
        }
    }

}
