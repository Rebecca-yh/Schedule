package com.example.yanghan.schedule;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by yanghan on 2017/12/20.
 */

public class Repo {
    private DBHelper dbHelper;

    private String info= MyBean.KEY_ID+","+
            MyBean.SUBJECT+","+
            MyBean.HOUR1+","+
            MyBean.HOUR2+","+
            MyBean.MINUTE1+","+
            MyBean.MINUTE2+","+
            MyBean.TEXT+","+
            MyBean.NOTIFICATION;
    private String DATE;
    public Repo(Context context, String date){
        dbHelper=new DBHelper(context,date);

        DATE=date;
    }

    public void change(String date)
    {
        DATE=date;
    }
    public int insert(MyBean myBean){
        //插入
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(MyBean.HOUR1,myBean.hour1);
        values.put(MyBean.HOUR2,myBean.hour2);
        values.put(MyBean.MINUTE1,myBean.minute1);
        values.put(MyBean.MINUTE2,myBean.minute2);
        values.put(MyBean.TEXT,myBean.text);
        values.put(MyBean.SUBJECT,myBean.subject);
        values.put(MyBean.NOTIFICATION,myBean.notification?1:0);
        values.put(MyBean.DATE,myBean.date);
        long item_Id=db.insert("T",null,values);
        db.close();
        return (int)item_Id;
    }

    public void delete(int item_Id){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        db.delete("T",MyBean.KEY_ID+"=?", new String[]{String.valueOf(item_Id)});
        db.close();
    }
    public void update(MyBean myBean){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();

        values.put(MyBean.HOUR1,myBean.hour1);
        values.put(MyBean.HOUR2,myBean.hour2);
        values.put(MyBean.MINUTE1,myBean.minute1);
        values.put(MyBean.MINUTE2,myBean.minute2);
        values.put(MyBean.TEXT,myBean.text);

        values.put(MyBean.SUBJECT,myBean.subject);
        values.put(MyBean.NOTIFICATION,myBean.notification?1:0);
        values.put(MyBean.DATE,myBean.date);
        db.update("T",values,MyBean.KEY_ID+"=?",new String[] { String.valueOf(myBean.key_id) });
        db.close();
    }

    public ArrayList<MyBean> getItemList(){
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        String selectQuery="SELECT "+ info+
              " FROM "+"T"+" WHERE date='"+DATE+"'";
        ArrayList<MyBean> itemList=new ArrayList<MyBean>();
       Cursor cursor=db.rawQuery(selectQuery,null);

        if(cursor.moveToFirst()){
            do{
                MyBean myBean=new MyBean();
                myBean.key_id=cursor.getInt(cursor.getColumnIndex(MyBean.KEY_ID));
                myBean.hour1=cursor.getInt(cursor.getColumnIndex(MyBean.HOUR1));
                myBean.minute1=cursor.getInt(cursor.getColumnIndex(MyBean.MINUTE1));
                myBean.minute2=cursor.getInt(cursor.getColumnIndex(MyBean.MINUTE2));
                myBean.hour2=cursor.getInt(cursor.getColumnIndex(MyBean.HOUR2));
                myBean.text=cursor.getString(cursor.getColumnIndex(MyBean.TEXT));
                myBean.subject=cursor.getString(cursor.getColumnIndex(MyBean.SUBJECT));
                myBean.notification=(cursor.getInt(cursor.getColumnIndex(MyBean.NOTIFICATION))==1);
                myBean.date=DATE;
                itemList.add(myBean);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return itemList;
    }

    public MyBean getItemById(int Id){
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        String selectQuery="SELECT "+ info+
                " FROM " + "T"
                + " WHERE " +
                MyBean.KEY_ID + "=?";

        MyBean myBean=new MyBean();
       Cursor cursor=db.rawQuery(selectQuery,new String[]{String.valueOf(Id)});
        if(cursor.moveToFirst()){
            do{
                myBean.hour1 =cursor.getInt(cursor.getColumnIndex(MyBean.HOUR1));
                myBean.hour2 =cursor.getInt(cursor.getColumnIndex(MyBean.HOUR2));
                myBean.minute1 =cursor.getInt(cursor.getColumnIndex(MyBean.MINUTE1));
                myBean.minute2 =cursor.getInt(cursor.getColumnIndex(MyBean.MINUTE2));
                myBean.key_id =cursor.getInt(cursor.getColumnIndex(MyBean.KEY_ID));
                myBean.subject=cursor.getString(cursor.getColumnIndex(MyBean.SUBJECT));
                myBean.text =cursor.getString(cursor.getColumnIndex(MyBean.TEXT));
                myBean.subject=cursor.getString(cursor.getColumnIndex(MyBean.SUBJECT));
                myBean.notification=(cursor.getInt(cursor.getColumnIndex(MyBean.NOTIFICATION))==1);
                myBean.date=DATE;

            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return myBean;
    }
}

