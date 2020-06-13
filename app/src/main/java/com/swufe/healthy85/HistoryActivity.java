package com.swufe.healthy85;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends ListActivity implements Runnable{

    Handler handler;
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);




        Thread t = new Thread(this);
        t.start();
        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == 7) {
                    @SuppressLint("HandlerLeak") List<String> list2 = (List<String>) msg.obj;
                    ListAdapter adapter = new ArrayAdapter<>(HistoryActivity.this, android.R.layout.simple_list_item_1, list2);
                    setListAdapter(adapter);
                }
                super.handleMessage(msg);
            }
        };
    }

    @Override
    public void run() {
        List<String> fooList=new ArrayList<>();
        FoodManager manager = new FoodManager(this);
        for(FoodItem item :manager.listAll())

        {
            fooList.add("日期:"+item.getDate()+"\n"+
                    "食物:" +item.getFoodType()+"\n" +
                    "卡路里:"+item.getCalories());
        }
        Message msg = handler.obtainMessage(7);
        //msg.what = 7;
        msg.obj =fooList;
        handler.sendMessage(msg);
    }
}
