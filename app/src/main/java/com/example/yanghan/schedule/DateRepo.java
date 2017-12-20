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

public class DateRepo {
    private DBHelper dbHelper;

    private String DATE;
    public DateRepo(Context context, String date){
        dbHelper=new DBHelper(context,date);

        DATE=date;
    }

    public void change(String date)
    {
        DATE=date;
        dbHelper.change(date);
    }
    public int insert(MyBean myBean){
        //打开连接，写入数据
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(MyBean.HOUR1,myBean.hour1);
        values.put(MyBean.HOUR2,myBean.hour2);
        values.put(MyBean.MINUTE1,myBean.minute1);
        values.put(MyBean.MINUTE2,myBean.minute2);
        values.put(MyBean.TEXT,myBean.text);
        //
        long item_Id=db.insert(myBean.TABLE,null,values);
        db.close();
        return (int)item_Id;
    }

    public void delete(int item_Id){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        db.delete("T"+DATE,MyBean.KEY_ID+"=?", new String[]{String.valueOf(item_Id)});
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
        db.update("T"+DATE,values,MyBean.KEY_ID+"=?",new String[] { String.valueOf(myBean.key_id) });
        db.close();
    }

    public ArrayList<MyBean> getItemList(){
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        String selectQuery="SELECT "+
                MyBean.KEY_ID+","+
                MyBean.HOUR1+","+
                MyBean.HOUR2+","+
                MyBean.MINUTE1+","+
                MyBean.MINUTE2+","+
                MyBean.TEXT+

              " FROM "+"T"+DATE;
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
                itemList.add(myBean);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return itemList;
    }

    public MyBean getItemById(int Id){
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        String selectQuery="SELECT "+
                MyBean.KEY_ID+","+
                MyBean.HOUR1+","+
                MyBean.HOUR2+","+
                MyBean.MINUTE1+","+
                MyBean.MINUTE2+","+
                MyBean.TEXT+
                " FROM " + "T"+DATE
                + " WHERE " +
                MyBean.KEY_ID + "=?";
        int iCount=0;
        MyBean myBean=new MyBean();
        Cursor cursor=db.rawQuery(selectQuery,new String[]{String.valueOf(Id)});
        if(cursor.moveToFirst()){
            do{
                myBean.hour1 =cursor.getInt(cursor.getColumnIndex(MyBean.HOUR1));
                myBean.hour2 =cursor.getInt(cursor.getColumnIndex(MyBean.HOUR2));
                myBean.minute1 =cursor.getInt(cursor.getColumnIndex(MyBean.MINUTE1));
                myBean.minute2 =cursor.getInt(cursor.getColumnIndex(MyBean.MINUTE2));
                myBean.key_id =cursor.getInt(cursor.getColumnIndex(MyBean.KEY_ID));
                myBean.text =cursor.getString(cursor.getColumnIndex(MyBean.TEXT));

            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return myBean;
    }
}

