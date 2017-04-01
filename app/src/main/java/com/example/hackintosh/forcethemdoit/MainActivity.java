package com.example.hackintosh.forcethemdoit;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<String> projectsList = new ArrayList<>();

    private RecyclerView scheduleListView;
    private RecyclerView.Adapter scheduleListAdapter;
    private RecyclerView.LayoutManager scheduleListManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton newSchedule = (FloatingActionButton) findViewById(R.id.addSchedule);

        newSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AddSchedule.class);
                startActivity(intent);
            }
        });

        dummyList();

        scheduleListView = (RecyclerView) findViewById(R.id.scheduleList);

        scheduleListManager = new LinearLayoutManager(this);
        scheduleListView.setLayoutManager(scheduleListManager);

        scheduleListAdapter = new ScheduleListAdapter(projectsList);
        scheduleListView.setAdapter(scheduleListAdapter);
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

    public void dummyList() {
        for(int i = 0; i < 10; i++) {
            projectsList.add(i,"Element" + i);
        }
    }
}
