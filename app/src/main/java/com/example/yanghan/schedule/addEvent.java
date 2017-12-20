package com.example.yanghan.schedule;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

public class addEvent extends AppCompatActivity {


    private static final String DYNAMICACTION="action.noTimeCrash";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        Button back=(Button)findViewById(R.id.back);
        Button cancel=(Button)findViewById(R.id.cancel);
        final TimePicker timePicker1 = (TimePicker)findViewById(R.id.new_act_time_picker);
        final TimePicker timePicker2 = (TimePicker)findViewById(R.id.timePicker2);

        timePicker1.setIs24HourView(true);
        timePicker2.setIs24HourView(true);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button btn=(Button)findViewById(R.id.back);
                btn.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v) {
                        int hour1=timePicker1.getCurrentHour();
                        int minute1=timePicker1.getCurrentMinute();
                        int hour2=timePicker2.getCurrentHour();
                        int minute2=timePicker2.getCurrentMinute();
                        //须检测时间输入是否合法

                        back(hour1,minute1,hour2,minute2);


                }

                });
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                setResult(Activity.RESULT_CANCELED,intent);
                finish();
            }
        });

    }
    private void back(int hour1,int minute1,int hour2,int minute2)
    {
        EditText text=(EditText)findViewById(R.id.text);

        Intent intent=new Intent();
        intent.putExtra("text",text.getText().toString());
        intent.putExtra("hour1",hour1);
        intent.putExtra("minute1",minute1);
        intent.putExtra("hour2",hour2);
        intent.putExtra("minute2",minute2);


        setResult(Activity.RESULT_OK,intent);
        finish();
    }




}
