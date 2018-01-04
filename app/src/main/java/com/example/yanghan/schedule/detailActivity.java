package com.example.yanghan.schedule;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class detailActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        int id=getIntent().getIntExtra("id",-1);
        String date=getIntent().getStringExtra("date");
        Repo repo = new Repo(this,date);
        MyBean myBean=repo.getItemById(id);//根据id从数据库中得到事件详情。

        //显示详情
        TextView begin=(TextView)findViewById(R.id.begin);
        TextView end=(TextView)findViewById(R.id.end);
        TextView subject=(TextView)findViewById(R.id.subject);
        TextView text=(TextView)findViewById(R.id.text);

        begin.setText(myBean.getTime());

        String hour;
        String minute;

        if(myBean.hour2<10)
            hour="0"+Integer.toString(myBean.hour2);
        else
            hour=Integer.toString(myBean.hour2);

        if(myBean.minute2<10)
            minute=":0"+Integer.toString(myBean.minute2);
        else
            minute=":"+Integer.toString(myBean.minute2);

        end.setText(hour+minute);
        subject.setText(myBean.subject);
        text.setText(myBean.text);
        Button notice=(Button)findViewById(R.id.notice);
        Resources r=this.getResources();
        Drawable yes=r.getDrawable(R.drawable.yes);
        Drawable no=r.getDrawable(R.drawable.no);
        if(myBean.notification)
            notice.setBackground(yes);
        else
            notice.setBackground(no);

        Button back=(Button)findViewById(R.id.back);

        //返回主界面
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
