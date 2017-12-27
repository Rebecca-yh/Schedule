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
    private static final String DATABASE_NAME="Schedule";


    public DBHelper(Context context,String date){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("???","!");

        String CREATE_TABLE_DATE="CREATE TABLE IF NOT EXISTS "+ "T"+
                "(" +MyBean.KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT ,"
                +MyBean.HOUR1+" INTEGER, "
                +MyBean.HOUR2+" INTEGER, "
                +MyBean.MINUTE1+" INTEGER, "
                +MyBean.MINUTE2+" INTEGER, "
                +MyBean.DATE+" TEXT, "
                +MyBean.SUBJECT+" TEXT,"
                +MyBean.NOTIFICATION+" INTEGER,"
                +MyBean.TEXT+" TEXT)";

        db.execSQL(CREATE_TABLE_DATE);
        Log.i("create",CREATE_TABLE_DATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS T");
        onCreate(db);

    }
}
