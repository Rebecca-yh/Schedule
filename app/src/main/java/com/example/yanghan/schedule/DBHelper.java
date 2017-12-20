package com.example.yanghan.schedule;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.yanghan.schedule.MyBean;

import java.util.Date;

/**
 * Created by yanghan on 2017/12/20.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION=4;

    //数据库名称
    private static final String DATABASE_NAME="crud.db";
    private String DATE;

    public DBHelper(Context context,String date){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);

        DATE=date;
        String CREATE_TABLE_DATE="CREATE TABLE IF NOT EXISTS "+ "T"+DATE+
                "(" +MyBean.KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT ,"
                +MyBean.HOUR1+" INTEGER, "
                +MyBean.HOUR2+" INTEGER, "
                +MyBean.MINUTE1+" INTEGER, "
                +MyBean.MINUTE2+" INTEGER, "
                +MyBean.TEXT+" TEXT)";

        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL(CREATE_TABLE_DATE);
        Log.i("create",CREATE_TABLE_DATE);

    }
    public void change(String date)
    {
        DATE=date;
        String CREATE_TABLE_DATE="CREATE TABLE IF NOT EXISTS "+ "T"+DATE+
                "(" +MyBean.KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT ,"
                +MyBean.HOUR1+" INTEGER, "
                +MyBean.HOUR2+" INTEGER, "
                +MyBean.MINUTE1+" INTEGER, "
                +MyBean.MINUTE2+" INTEGER, "
                +MyBean.TEXT+" TEXT)";

        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL(CREATE_TABLE_DATE);
        Log.i("create",CREATE_TABLE_DATE);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("???","!");
        //创建数据表
        String CREATE_TABLE_DATE="CREATE TABLE IF NOT EXISTS "+ "T"+DATE+
                "(" +MyBean.KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT ,"
                +MyBean.HOUR1+" INTEGER, "
                +MyBean.HOUR2+" INTEGER, "
                +MyBean.MINUTE1+" INTEGER, "
                +MyBean.MINUTE2+" INTEGER, "
                +MyBean.TEXT+" TEXT)";

        db.execSQL(CREATE_TABLE_DATE);
        Log.i("create",CREATE_TABLE_DATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("create","exist");

    }
}
