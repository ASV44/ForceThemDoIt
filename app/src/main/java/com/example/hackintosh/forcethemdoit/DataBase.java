package com.example.hackintosh.forcethemdoit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by hackintosh on 4/2/17.
 */

public class DataBase {
    private SQLiteDatabase db;
    private DataBaseHelper dbHelper;
    private Context context;

    public DataBase(Context context) {
        this.context = context;
        dbHelper = new DataBaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public void populateDB(LinkedHashMap<String,String> victims) {
        if(!checkIfExist(DataBaseHelper.ReceiverModel.TABLE_NAME)) {
            dbHelper.addNewReceiversTable(db);
        }
        db = dbHelper.getWritableDatabase();

        for(String number : victims.keySet()) {
            ContentValues values = new ContentValues();
            values.put(DataBaseHelper.ReceiverModel.NUMBER, number);
            values.put(DataBaseHelper.ReceiverModel.MESSAGE, victims.get(number));
            db.insert(DataBaseHelper.ReceiverModel.TABLE_NAME, null, values);
        }
        showTable(DataBaseHelper.ReceiverModel.TABLE_NAME,
                DataBaseHelper.ReceiverModel.NUMBER,
                DataBaseHelper.ReceiverModel.TABLE_NAME);

    }

    public boolean checkIfExist(String tableName) {
        Cursor cursor = db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '"+ tableName +"'", null);

        if(cursor.getCount() <= 0) { return false; }
        else { return true; }
    }

    public void showTable(String tableName, String tableColumn1, String tableColumn2) {
        if(checkIfExist(tableName)) {
            Cursor cursor = db.rawQuery("select * from " + tableName, null);
            Log.d("Show table","table exist");
            List<String[]> items = new ArrayList<String[]>();
            while (cursor.moveToNext()) {
                String[] item = new String[2];
                item[0] = cursor.getString(
                        cursor.getColumnIndexOrThrow(tableColumn1));
                item[1] = cursor.getString(
                        cursor.getColumnIndexOrThrow(tableColumn2));
                items.add(0, item);
            }
            cursor.close();

            for(String[] item: items) {
                Log.d("DataBase item_0", "" + item[0]);
                Log.d("DataBase item_1", "" + item[1]);
            }

        }
        else {
            Log.d("Show Table", "table doesn't exit");
        }
    }
}
