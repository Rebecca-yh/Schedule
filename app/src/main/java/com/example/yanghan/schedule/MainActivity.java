package com.example.yanghan.schedule;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.widget.BaseAdapter;

public class MainActivity extends AppCompatActivity {

    private ListView lv_main;
    private ArrayList<MyBean> myBeans;
    private MyAdapter myAdapter;

    SimpleDateFormat curDate ;
    String date;
    DateRepo repo;
    TextView dateTV;

    @Override
   protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==0&resultCode== Activity.RESULT_OK)
        {

            MyBean new_bean=new MyBean();
            new_bean.TABLE=new String("T"+date);

            Log.i("insert","!");
            new_bean.hour1=data.getIntExtra("hour1",-1);
            new_bean.hour2=data.getIntExtra("hour2",-1);
            new_bean.minute1=data.getIntExtra("minute1",-1);
            new_bean.minute2=data.getIntExtra("minute2",-1);
            new_bean.text=data.getStringExtra("text");
            new_bean.key_id=repo.insert(new_bean);
            myBeans.add(new_bean);
            myAdapter.notifyDataSetChanged();
            /*Intent intent = new Intent(this, AlarmReceiver.class);
            intent.setAction("NOTIFICATION");
            PendingIntent pi = PendingIntent.getBroadcast(this, 0, intent, 0);

            AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            int type = AlarmManager.RTC_WAKEUP;
            //new Date()：表示当前日期，可以根据项目需求替换成所求日期
            //getTime()：日期的该方法同样可以表示从1970年1月1日0点至今所经历的毫秒数
            long triggerAtMillis = new Date().getTime();
            //修改！！！
            long intervalMillis = 1000 * 60;
            manager.setInexactRepeating(type, triggerAtMillis, intervalMillis, pi);*/
            //储存

        }

        if(requestCode==1&resultCode== Activity.RESULT_OK)
        {
            int year=data.getIntExtra("year",-1);
            int month=data.getIntExtra("month",-1);
            int day=data.getIntExtra("day",-1);
            if(year==0)
            {
                date=curDate.format(new java.util.Date());
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


            }
            else{
                date=Integer.toString(year)+"_"+Integer.toString(month)+"_"+Integer.toString(day);
                dateTV.setText(" "+year+"年"+month+"月"+day+"日");
            }

            repo.change(date);


            myBeans = repo.getItemList();
            myAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        curDate =  new SimpleDateFormat("yyyy_MM_dd");
        date=curDate.format(new java.util.Date());
        Log.i("date",date);
        lv_main = (ListView) findViewById(R.id.lv_main);

        dateTV=(TextView)findViewById(R.id.dateTV);


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
        repo = new DateRepo(this,date);
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
        Button calendar=(Button)findViewById(R.id.calendar);
        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,CalendarActivity.class);
                startActivityForResult(intent,1);

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
                /**
                 * setTag
                 * 把查找的view缓存起来方便多次重用
                 * 相当于给View对象的一个标签。XX标签可以是任何内容，我们这里把他设置成了一个对象XX。
                 *
                 * Tag的作用就是设置标签，标签可以是任意玩意。
                 * 以及convertView是如何在程序中使代码运行变的效率的：利用缓存convertView尽可能少实例化
                 * 同样结构体的对象；
                 */
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
                    /**
                     * getParent() 获取父组件
                     * 获取爷爷组件，可以使用getParent().getParent()
                     */
                    SlideLayout slideLayout = (SlideLayout) view.getParent();
                    slideLayout.closeMenu();
                    //删除
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
