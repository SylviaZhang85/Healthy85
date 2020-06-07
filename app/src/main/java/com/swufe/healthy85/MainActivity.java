package com.swufe.healthy85;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    EditText amount;
    TextView show;
    TextView showFood;



    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        amount=(EditText)findViewById(R.id.amount);
        show=(TextView)findViewById(R.id.showOut);
        showFood=(TextView)findViewById(R.id.showFood);



    }
    public void  onClick(View btn){
        String title =getIntent().getStringExtra("title");
        String calorie= getIntent().getStringExtra("calories");
        float calories=0;
        if(calorie!=null&&title!=null){
            String[] strArr = calorie.split("\\/");
            calories = Float.parseFloat(strArr[0])/Float.parseFloat(strArr[1]);
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
             show.setText(String.valueOf(cal));
         }

    }
    public void openOne(View btn){
        //打开food页面
        Intent food=new Intent(this,FoodActivity.class);
        startActivity(food);
    }
    public void Click(View v){
        Calendar calendar=Calendar.getInstance();
        new DatePickerDialog( this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String text = "你选择了：" + year + "年" + (month + 1) + "月" + dayOfMonth + "日";
                Toast.makeText( MainActivity.this, text, Toast.LENGTH_SHORT ).show();
            }
        }
                ,calendar.get(Calendar.YEAR)
                ,calendar.get(Calendar.MONTH)
                ,calendar.get(Calendar.DAY_OF_MONTH)).show();
    }



}
