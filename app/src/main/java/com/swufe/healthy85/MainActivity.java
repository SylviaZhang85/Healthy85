package com.swufe.healthy85;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText amount;
    TextView show;
    TextView showFood;
    String title=null;
    String calorie=null;
    String TAG="Food";
    public static String date;
    String calor;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        amount=(EditText)findViewById(R.id.amount);
        show=(TextView)findViewById(R.id.showOut);
        showFood=(TextView)findViewById(R.id.showFood);



    }

    public void openOne(View btn){
        //打开food页面
        Intent food=new Intent(this,ConfigActivity.class);
        startActivity(food);
    }
    public void Click(View v){
        Calendar calendar=Calendar.getInstance();
        new DatePickerDialog( this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view,int year, int month, int dayOfMonth) {

                if(year!=0&&month!=0&&dayOfMonth!=0){
                    String text = "你选择了：" + year + "年" + (month + 1) + "月" + dayOfMonth + "日";

                    date=year+"-"+(month+1)+"-"+dayOfMonth;
                    Log.i(TAG,"date的值="+date);
                    Toast.makeText( MainActivity.this, text, Toast.LENGTH_SHORT ).show();}
                else{
                    Toast.makeText( MainActivity.this,"请选择日期", Toast.LENGTH_SHORT ).show();
                }}
        }
                ,calendar.get(Calendar.YEAR)
                ,calendar.get(Calendar.MONTH)
                ,calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    public void  onClick(View btn){
        title =getIntent().getStringExtra("title");
        calorie= getIntent().getStringExtra("calories");
        float calories=0;
        if(calorie!=null&&title!=null){
            calories=Float.parseFloat(calorie);
            showFood.setText(title);
        }else{

            showFood.setText("Haven't chosen food yet");
        }


        String str=amount.getText().toString();
        float am=0;
        if(str.length()>0){
            am=Float.parseFloat(str);
        }else{
            Toast.makeText(this,"Please input the food amounts",Toast.LENGTH_SHORT).show();
        }

        if(btn.getId()==R.id.btn_calculate){
            float cal=am*calories;
            calor=String.valueOf(cal);
            show.setText(calor);
        }
        Log.i(TAG,"王一博老婆的date="+date);
        if(date!=null&&title!=null&&calor!=null) {
            FoodItem fitem = new FoodItem(date, title, calor);
            FoodManager manager = new FoodManager(this);
            manager.add(fitem);

            Log.i(TAG, "onOptionItemSelected:写入数据完毕");

            //查询所有数据
            List<FoodItem>testList=manager.listAll();
            for(FoodItem i:testList){
                Log.i(TAG,"onOptionItemSelected:取出数据[id="+i.getId()+"]Date="+i.getDate()+"FoodType="+i.getFoodType()+"Calories="+i.getCalories());
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.history,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.menu_set){
            Intent history = new Intent(this, HistoryActivity.class);
            startActivity(history);
        }




        return super.onOptionsItemSelected(item);
    }}