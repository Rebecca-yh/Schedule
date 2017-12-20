package com.example.yanghan.schedule;

import android.content.Intent;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyBean {

    public static final String KEY_ID="key_id";
    public static final String HOUR1="hour1";
    public static final String HOUR2="hour2";
    public static final String MINUTE1="minute1";
    public static final String MINUTE2="minute2";
    public static final String TEXT="text";


   public int hour1;
    public int minute1;
    public int hour2;
    public int minute2;
    public String text;
    public String TABLE;
    public int key_id;

    public MyBean(Intent data, String date) {

        TABLE=new String("T"+date);

        hour1=data.getIntExtra("hour1",-1);
        hour2=data.getIntExtra("hour2",-1);
        minute1=data.getIntExtra("minute1",-1);
        minute2=data.getIntExtra("minute2",-1);
        text=data.getStringExtra("text");
    }
    public MyBean()
    {

    }

    public String getTime()
    {
        String hour;
        String minute;

        if(hour1<10)
            hour="0"+Integer.toString(hour1);
        else
            hour=Integer.toString(hour1);

        if(minute1<10)
            minute=":0"+Integer.toString(minute1);
        else
            minute=":"+Integer.toString(minute1);
        return hour+minute;
    }
    public String getDur()
    {
        if(minute2>minute1)
        {
            String tmp=new String(Integer.toString(hour2-hour1)+"h"+Integer.toString(minute2-minute1)+"m");
            return tmp;
        }
        else if(minute2<minute1)
        {
            String tmp=new String(Integer.toString(hour2-1-hour1)+"h"+Integer.toString(60+minute2-minute1)+"m");
            return tmp;
        }
        else if(minute2==minute1)
        {
            String tmp=new String(Integer.toString(hour2-hour1)+"h");
            return tmp;
        }
        return null;
    }
    public String getSubject()
    {
        return text;
    }

}