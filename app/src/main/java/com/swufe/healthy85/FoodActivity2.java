package com.swufe.healthy85;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FoodActivity2 extends ListActivity implements Runnable, AdapterView.OnItemClickListener {
    //蛋类




    Handler handler;
    private ArrayList<HashMap<String,String>> listItems;
    private SimpleAdapter listItemAdapter;
    private final String TAG="App";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initListView();



        this.setListAdapter(listItemAdapter);


        Thread t = new Thread(this);
        t.start();
        handler = new Handler() {
            public void handleMessage(Message msg) {


                if (msg.what == 7) {
                    List<HashMap<String, String>> list2 = (List<HashMap<String, String>>) msg.obj;
                    listItemAdapter = new SimpleAdapter(FoodActivity2.this, list2,
                            R.layout.list_item,
                            new String[]{"ItemTitle", "ItemDetail"},
                            new int[]{R.id.itemTitle, R.id.itemDetail}
                    );
                    setListAdapter(listItemAdapter);
                }
                super.handleMessage(msg);
            };


        };
        getListView().setOnItemClickListener(this);

    }











    /*public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i(TAG,"onItemClick:parent="+parent);
        Log.i(TAG,"onItemClick:view="+view);
        Log.i(TAG,"onItemClick:position="+position);
        Log.i(TAG,"onItemClick:id="+id);
        getListView().getItemAtPosition(position);
        HashMap<String,String> map =(HashMap<String,String>)getListView().getItemAtPosition(position);
        String titleStr = map.get("ItemTitle");
        String detailStr = map.get("ItemDetail");
        Log.i(TAG,"onItemClick:titleStr="+ titleStr);
        Log.i(TAG,"onItemClick:detailStr="+ detailStr);
        TextView title=(TextView)view.findViewById(R.id.itemTitle);
        TextView detail=(TextView)view.findViewById(R.id.itemDetail);
        String title2 = String.valueOf(title.getText());
        String detail2 = String.valueOf(detail.getText());
        Log.i(TAG,"onItemClick:title2="+title2);
        Log.i(TAG,"onItemClick:detail2="+detail2);
        //打开新的页面传入参数
        Intent rateCalc = new Intent(this,RateCalcActivity.class);
        rateCalc.putExtra("title",titleStr);
        rateCalc.putExtra("rate",Float.parseFloat(detailStr));
        startActivity(rateCalc);
    }*/






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
                new int[]{R.id.itemTitle,R.id.itemDetail}
        );
    }

    @Override
    public void run(){
        //获取网络数据，放入List带回主线程
        List<HashMap<String,String>>retList = new ArrayList<HashMap<String, String>>();
        Document doc=null;
        try{
            Thread.sleep(3);
            doc = Jsoup.connect("https://www.meishichina.com/Topic/ReLiang/").get();
            Log.i(TAG,"run:"+ doc.title());
            Elements uls=doc.getElementsByTag("ul");

            Element table7= uls.get(6);
            //获取TD中数据

            Elements lis=table7.getElementsByTag("li");
            for(int i=4;i<lis.size();i++){

                Element td1=lis.get(i);


                String str=td1.text();
                String strs=td1.text();
                String reg = "[^\u4e00-\u9fa5]";
                String str1 = str.replaceAll(reg, "");

                String abc2 = strs.replaceAll("[\u4e00-\u9fa5]+", "");
                String str2 = abc2.replaceAll("\\(|\\)", "");
                HashMap<String,String>map =new HashMap<String,String>();

                map.put("ItemTitle",str1);
                map.put("ItemDetail",str2);

                retList.add(map);
            }
        }catch(IOException e){
            e.printStackTrace();
        }catch(InterruptedException e){
            e.printStackTrace();
        }

        Message msg=handler.obtainMessage(7);
        msg.obj=retList;
        handler.sendMessage(msg);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i(TAG,"onItemClick:parent="+parent);
        Log.i(TAG,"onItemClick:view="+view);
        Log.i(TAG,"onItemClick:position="+position);
        Log.i(TAG,"onItemClick:id="+id);
        getListView().getItemAtPosition(position);
        HashMap<String,String> map =(HashMap<String,String>)getListView().getItemAtPosition(position);
        String titleStr = map.get("ItemTitle");
        String detailStr = map.get("ItemDetail");
        Log.i(TAG,"onItemClick:titleStr="+ titleStr);
        Log.i(TAG,"onItemClick:detailStr="+ detailStr);

        TextView title=(TextView)view.findViewById(R.id.itemTitle);
        TextView detail=(TextView)view.findViewById(R.id.itemDetail);
        String title2 = String.valueOf(title.getText());
        String detail2 = String.valueOf(detail.getText());
        Log.i(TAG,"onItemClick:title2="+title2);
        Log.i(TAG,"onItemClick:detail2="+detail2);
        //打开新的页面传入参数
        Intent ConfigActivity = new Intent(this,ConfigActivity.class);
        ConfigActivity.putExtra("title",titleStr);
        ConfigActivity.putExtra("calories",detailStr);
        startActivity(ConfigActivity);

    }


}
