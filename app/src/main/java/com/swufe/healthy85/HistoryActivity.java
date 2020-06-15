package com.swufe.healthy85;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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




public class HistoryActivity extends ListActivity implements Runnable, AdapterView.OnItemLongClickListener {

    Handler handler;
    private List<HashMap<String, String>> listItems;
    private SimpleAdapter listItemAdapter;
    private final String TAG = "App";
    FoodManager manager;
    int index=0;


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        initListView();
        this.setListAdapter(listItemAdapter);


        Thread t = new Thread(this);
        t.start();
        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == 7) {
                    listItems = (List<HashMap<String, String>>) msg.obj;
                    listItemAdapter = new SimpleAdapter(HistoryActivity.this, listItems,
                            R.layout.list_item,
                            new String[]{"ItemTitle", "ItemDetail"},
                            new int[]{R.id.itemTitle, R.id.itemDetail}
                    );
                    setListAdapter(listItemAdapter);
                }
                super.handleMessage(msg);
            }

            ;


        };
        getListView().setOnItemLongClickListener(this);

    }

    private void initListView() {
        listItems = new ArrayList<HashMap<String, String>>();
        for (int i = 0; i < 10; i++) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("ItemTitle", "Rate:" + i);//标题文字
            map.put("ItemDetail", "detail" + i);//详情描述
            listItems.add(map);
        }
        //生成适配器的Item和动态数组对应的元素
        listItemAdapter = new SimpleAdapter(this, listItems,
                R.layout.list_item,
                new String[]{"ItemTitle", "ItemDetail"},
                new int[]{R.id.itemTitle, R.id.itemDetail}
        );
    }

    @Override
    public void run() {
        List<HashMap<String, String>> fooList = new ArrayList<HashMap<String, String>>();
        listItems = new ArrayList<HashMap<String, String>>();
        manager= new FoodManager(this);
        for (FoodItem item : manager.listAll()) {

            String str1 = item.getDate();
            String str2 = item.getFoodType() + "  " + item.getCalories();

            HashMap<String, String> map = new HashMap<String, String>();

            map.put("ItemTitle", str1);
            map.put("ItemDetail", str2);

            fooList.add(map);
        }
        Message msg = handler.obtainMessage(7);
        //msg.what = 7;
        msg.obj = fooList;
        handler.sendMessage(msg);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, final long id) {
        Log.i(TAG,"onItemLongClick:长按列表项position"+position);
        Log.i(TAG,"onItemLongClick:长按列表项id"+id);
        //删除操作
        //listItems.remove((position));
        //listItemAdapter.notifyDataSetChanged();
        //构造对话框进行确认操作
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("提示").setMessage("请确认是否删除当前数据").setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i(TAG, "onClick:对话框事件处理");

                String deleteText = listItems.get(position).get("ItemTitle").toString();
                Log.i("TAG","Text的值"+deleteText);
                String det = listItems.get(position).get("ItemDetail").toString();
                Log.i("TAG","Text的值"+det);
                String[] spString = det.split("\\s+");
                String deleteName = spString[0];
                String deleteCal = spString[1];
                Log.i("TAG","dN和dC"+deleteName);
                Log.i("TAG","dN和dC"+deleteCal);

                List<FoodItem>testList=manager.listAll();
                for(FoodItem i:testList){
                    if(deleteText.equals(i.getDate())&&deleteName.equals(i.getFoodType())&&deleteCal.equals(i.getCalories())){
                        index=i.getId();
                        Log.i("TAG","index"+index);
                        manager.delete(index);

                    }
                }

                listItems.remove(position);
                listItemAdapter.notifyDataSetChanged();

            }})
                .setNegativeButton("否",null);
        builder.create().show();


        Log.i(TAG,"onItemLongClick:size="+listItems.size());
        return true;

}}