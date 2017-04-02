package com.example.hackintosh.forcethemdoit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public void populateDB(List<String[]> victims) {
        deleteTable(DataBaseHelper.ReceiverModel.TABLE_NAME);
        if(!checkIfExist(DataBaseHelper.ReceiverModel.TABLE_NAME)) {
            dbHelper.addNewReceiversTable(db);
        }
        db = dbHelper.getWritableDatabase();

        for(String[] victim : victims) {
            ContentValues values = new ContentValues();
            values.put(DataBaseHelper.ReceiverModel.NUMBER, victim[0]);
            values.put(DataBaseHelper.ReceiverModel.MESSAGE, victim[1]);
            db.insert(DataBaseHelper.ReceiverModel.TABLE_NAME, null, values);
        }
        showTable(DataBaseHelper.ReceiverModel.TABLE_NAME,
                DataBaseHelper.ReceiverModel.NUMBER,
                DataBaseHelper.ReceiverModel.MESSAGE);

    }

    public void populateFlagTable(String flag) {
        deleteTable(DataBaseHelper.FlagModel.TABLE_NAME);
        if(!checkIfExist(DataBaseHelper.FlagModel.TABLE_NAME)) {
            dbHelper.addNewFlagTable(db);
        }
        ContentValues values = new ContentValues();
        values.put(DataBaseHelper.FlagModel.FLAG, flag);
        db.insert(DataBaseHelper.FlagModel.TABLE_NAME, null, values);
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

    public String getFlag() {
        if(checkIfExist(DataBaseHelper.FlagModel.TABLE_NAME)) {
            Cursor cursor = db.rawQuery("select * from " + DataBaseHelper.FlagModel.TABLE_NAME, null);

            String flag = null;
            while (cursor.moveToNext()) {
                flag = cursor.getString(
                        cursor.getColumnIndexOrThrow(DataBaseHelper.FlagModel.FLAG));
            }
            cursor.close();
            return flag;
        }

        return null;
    }

    public List<String[]> getTable(String tableName) {
        if(checkIfExist(tableName)) {
            Cursor cursor = db.rawQuery("select * from " + tableName, null);

            List<String[]> items = new ArrayList<String[]>();
            while (cursor.moveToNext()) {
                String[] item = new String[2];
                item[0] = cursor.getString(
                        cursor.getColumnIndexOrThrow(DataBaseHelper.ReceiverModel.NUMBER));
                item[1] = cursor.getString(
                        cursor.getColumnIndexOrThrow(DataBaseHelper.ReceiverModel.MESSAGE));
                items.add(item);
            }
            cursor.close();

            Log.d("DataBase", "" + items);

            return items;
        }

        return null;
    }

    public void deleteTable(String tableName) {
        String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + tableName;
        db.execSQL(SQL_DELETE_ENTRIES);
    }
}
