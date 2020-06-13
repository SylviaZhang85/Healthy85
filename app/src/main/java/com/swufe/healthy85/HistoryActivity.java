package com.swufe.healthy85;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HistoryActivity extends ListActivity implements Runnable, AdapterView.OnItemClickListener{



    Handler handler;
    private ArrayList<HashMap<String,String>> listItems;
    private SimpleAdapter listItemAdapter;
    private final String TAG="App";
    protected void onCreate(Bundle savedInstanceState) {


            super.onCreate(savedInstanceState);
            //setContentView(R.layout.activity_history);



        initListView();
        this.setListAdapter(listItemAdapter);

        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == 7) {
                    List<HashMap<String, String>> list2 = (List<HashMap<String, String>>) msg.obj;
                    listItemAdapter = new SimpleAdapter(HistoryActivity.this, list2,
                            R.layout.list_item,
                            new String[]{"ItemTitle", "ItemDetail"},
                            new int[]{R.id.Datee, R.id.foodDetail}
                    );
                    setListAdapter(listItemAdapter);
                }
                super.handleMessage(msg);
            };


        };
        getListView().setOnItemClickListener(this);

    }

    private void initListView(){
        listItems = new ArrayList<HashMap<String, String>>();
        for(int i = 0;i<10;i++){
            HashMap<String,String>map= new HashMap<String,String>();
            map.put("ItemTitle","Rate:"+i);//标题文字
            map.put("ItemDetail","detail"+i);//详情描述
            listItems.add(map);
        }
        //生成适配器的Item和动态数组对应的元素
        listItemAdapter = new SimpleAdapter(this,listItems,
                R.layout.list_item,
                new String[]{"ItemTitle","ItemDetail"},
                new int[]{R.id.Datee,R.id.foodDetail}
        );
    }

    @Override
    public void run() {
        List<HashMap<String,String>>fooList = new ArrayList<HashMap<String, String>>();
        FoodManager manager = new FoodManager(this);

        for(FoodItem item :manager.listAll())

        {
            HashMap<String,String>map =new HashMap<String,String>();
            String da=item.getDate();
            String foodDe=item.getFoodType()+item.getCalories();
            map.put("ItemTitle",da);
            map.put("ItemDetail",foodDe);

            fooList.add(map);

            Log.i("TAG","onOptionItemSelected:取出数据[id="+item.getId()+"]Date="+item.getDate()+"FoodType="+item.getFoodType()+"Calories="+item.getCalories());
        }
        Message msg = handler.obtainMessage(7);
        //msg.what = 7;
        msg.obj =fooList;
        handler.sendMessage(msg);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        getListView().getItemAtPosition(position);
        HashMap<String,String> map =(HashMap<String,String>)getListView().getItemAtPosition(position);
    }
}

