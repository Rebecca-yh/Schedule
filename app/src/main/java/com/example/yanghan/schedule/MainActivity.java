package com.example.yanghan.schedule;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import android.widget.BaseAdapter;

public class MainActivity extends AppCompatActivity {

    private ListView lv_main;
    private ArrayList<MyBean> myBeans;
    private MyAdapter myAdapter;
    private int year;
    private int month;
    private int day;
    private Context con;

    SimpleDateFormat curDate ;
    String date;
    Repo repo;
    TextView dateTV;
    LinearLayout list;
    boolean opencalendar;
    ConstraintLayout.LayoutParams lp;

    @Override
   protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==0&resultCode== Activity.RESULT_OK)
        {

            MyBean new_bean=new MyBean();
            new_bean.date=new String(date);

            Log.i("insert","!");
            new_bean.hour1=data.getIntExtra("hour1",-1);
            new_bean.hour2=data.getIntExtra("hour2",-1);
            new_bean.minute1=data.getIntExtra("minute1",-1);
            new_bean.minute2=data.getIntExtra("minute2",-1);
            new_bean.text=data.getStringExtra("text");
            new_bean.subject=data.getStringExtra("subject");
            new_bean.key_id=repo.insert(new_bean);
            new_bean.notification=data.getBooleanExtra("notice",false);
            myBeans.add(new_bean);
            Collections.sort(myBeans);
            myAdapter.notifyDataSetChanged();
            if(data.getBooleanExtra("notice",false))
            {
                String timeString = " "+new_bean.getTime();
                Date DATE=new Date();
                try
                {
                    Log.i("get","date");
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd hh:mm");
                    DATE= sdf.parse(date+timeString);
                }
                catch (ParseException e)
                {
                    System.out.println(e.getMessage());
                }
                Intent intent = new Intent(this, AlarmReceiver.class);
                intent.setAction("NOTIFICATION");
                intent.putExtra("subject",new_bean.subject);
                intent.putExtra("key_id",new_bean.key_id);
                PendingIntent pi = PendingIntent.getBroadcast(this, 0, intent, 0);

                AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                int type = AlarmManager.RTC_WAKEUP;
                long triggerAtMillis = DATE.getTime();
                //修改！！！
                long intervalMillis = 1000 * 60;

                manager.set(type, triggerAtMillis, pi);
            }

            //储存

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        con=this;
        curDate =  new SimpleDateFormat("yyyy_MM_dd");
        date=curDate.format(new java.util.Date());
        Log.i("date",date);
        lv_main = (ListView) findViewById(R.id.lv_main);

        dateTV=(TextView)findViewById(R.id.dateTV);

        final CalendarView calendarView= (CalendarView)findViewById(R.id.calendarView);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                year=i;
                month=i1+1;
                day=i2;
                date=Integer.toString(year)+"_"+Integer.toString(month)+"_"+Integer.toString(day);
                dateTV.setText(" "+year+"年"+month+"月"+day+"日");
                repo.change(date);


                myBeans = repo.getItemList();
                Collections.sort(myBeans);

                myAdapter.notifyDataSetChanged();
            }
        });

        String s=new String();
        ArrayList<String> tmp=new ArrayList<String>();
        for(int i=0;i<date.length();i++){
            if(date.charAt(i)!=95 ){
                s+=date.charAt(i);
            }
            else
            {
                tmp.add(s);
                s="";
            }
        }
        tmp.add(s);
        dateTV.setText(" "+tmp.get(0)+"年"+tmp.get(1)+"月"+tmp.get(2)+"日");

        // 读取
        repo = new Repo(this,date);
        myBeans = repo.getItemList();

        Log.i("size",Integer.toString(myBeans.size()));

        myAdapter = new MyAdapter();
        lv_main.setAdapter(myAdapter);

        Button add=(Button)findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent=new Intent(MainActivity.this,addEvent.class);
                startActivityForResult(intent,0);

            }
        });

        opencalendar=true;
        list=(LinearLayout)findViewById(R.id.list);
        lp=(ConstraintLayout.LayoutParams)list.getLayoutParams();
        final Button upDown=(Button) findViewById(R.id.updown);
        final Resources resources = this.getResources();
        final Drawable bg1=resources.getDrawable(R.drawable.down);
        final Drawable bg2=resources.getDrawable(R.drawable.up);
        upDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.i("click","!");
                if(opencalendar)
                {
                    Log.i("close","calendar");
                    lp.setMargins(0,0,0,0);
                    list.setLayoutParams(lp);
                    opencalendar=false;
                    upDown.setBackground(bg1);
                }
                else
                {
                    Log.i("open","calendar");
                    lp.setMargins(0,800,0,0);
                    list.setLayoutParams(lp);
                    opencalendar=true;
                    upDown.setBackground(bg2);
                }


            }
        });

    }
    class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return myBeans.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        /**
         * ViewHolder的解释：
         * 1、只是一个静态类，不是Android的API方法。
         * 2、它的作用就在于减少不必要的调用findViewById，然后把对底下的控件引用存在ViewHolder里面，再在
         * View.setTag(holder)把它放在view里，下次就可以直接取了。
         */
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;

            if(convertView == null){
                convertView = View.inflate(MainActivity.this,R.layout.item_main,null);
                viewHolder = new ViewHolder();
                viewHolder.time = (TextView) convertView.findViewById(R.id.time);
                viewHolder.dur = (TextView) convertView.findViewById(R.id.dur);
                viewHolder.subject = (TextView) convertView.findViewById(R.id.subject);
                viewHolder.item_content=(RelativeLayout)convertView.findViewById(R.id.item_content) ;
                viewHolder.item_menu = (TextView) convertView.findViewById(R.id.item_menu);

                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder) convertView.getTag();
            }

            // 根据位置得到内容
            final MyBean myBean = myBeans.get(position);
            //内容添加
            viewHolder.time.setText(myBean.getTime());
            viewHolder.dur.setText(myBean.getDur());
            viewHolder.subject.setText(myBean.getSubject());

            viewHolder.item_content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyBean myBean1 = myBeans.get(position);

                    System.out.println("MainActivity---onClick");
                }
            });

            viewHolder.item_menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    SlideLayout slideLayout = (SlideLayout) view.getParent();
                    slideLayout.closeMenu();
                    //删除
                    if(myBean.notification)
                    {
                        NotificationManager manager = (NotificationManager) con
                                .getSystemService(Context.NOTIFICATION_SERVICE);
                        manager.cancel(myBean.key_id);
                    }

                    repo.delete(myBean.key_id);
                    myBeans.remove(myBean);


                    notifyDataSetChanged();
                    System.out.println("MainActivity---remove");
                }
            });

            SlideLayout slideLayout = (SlideLayout) convertView;
            slideLayout.setOnStateChangeListener(new MyOnStateChangeListener());
            return convertView;
        }
    }

    private SlideLayout slideLayout;

     class MyOnStateChangeListener implements SlideLayout.OnStateChangeListener {

        @Override
        public void onClose(SlideLayout layout) {
            if(slideLayout == layout){
                slideLayout = null;
            }
        }

        @Override
        public void onDown(SlideLayout layout) {
            if(slideLayout!=null && slideLayout!=layout){
                slideLayout.closeMenu();
            }
        }

        @Override
        public void onOpen(SlideLayout layout) {
            slideLayout = layout;
        }

    }

    /**
     * ViewHolder的解释：
     * 1、只是一个静态类，不是Android的API方法。
     * 2、它的作用就在于减少不必要的调用findViewById，然后把对底下的控件引用存在ViewHolder里面，再在
     * View.setTag(holder)把它放在view里，下次就可以直接取了。
     */
    static class ViewHolder{
        RelativeLayout item_content;
        TextView time;
        TextView dur;
        TextView subject;
        TextView item_menu;
    }

}
