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

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AddSchedule extends AppCompatActivity {

    SmsSender sender;

    private Map<String, String> recipientsMap(String recipientsListString) {
        Map<String, String> dict = new LinkedHashMap<String, String>();
        String[] lines = recipientsListString.split( "\n" );

        for( int i = 0; i < lines.length; i++ ) {
            String[] line_elements = lines[i].split("\\s+");
            String str = "";
            for( int j = 0; j < line_elements.length - 1; j++){
                str += line_elements[j];
            }
            dict.put(line_elements[line_elements.length - 1], str);
        }
        return dict;
    }

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
                Map<String, String> recipientsDict = recipientsMap(recipientsListString);
                Log.d("mytag", recipientsDict.toString());
            }
        });

        sender = new SmsSender();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},1);
        }
    }

}
