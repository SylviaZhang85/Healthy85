package com.swufe.healthy85;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

public class ConfigActivity extends AppCompatActivity {
    EditText fName;
    EditText fCalorie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        fName=(EditText)findViewById(R.id.change_food_name);
        fCalorie=(EditText)findViewById(R.id.change_food_clories);

        String title =getIntent().getStringExtra("title");
        String calorie= getIntent().getStringExtra("calories");
        float calories=0;
        if(calorie!=null&&title!=null){
            String[] strArr = calorie.split("\\/");
            calories = Float.parseFloat(strArr[0])/Float.parseFloat(strArr[1]);
            DecimalFormat format = new DecimalFormat("#.00");
            String realCalories = format.format(calories);

            fName.setText(title);
            fCalorie.setText(realCalories);
        }
    }

    public void openList(View btn){
        //打开谷物页面
        Intent foodList=new Intent(this,FoodActivity6.class);
        startActivity(foodList);
    }
    public void openList_veg(View btn){
        //打开蔬菜页面
        Intent foodList=new Intent(this,FoodActivity5.class);
        startActivity(foodList);
    }
    public void openList_fui(View btn){
        //打开水果页面
        Intent foodList=new Intent(this,FoodActivity4.class);
        startActivity(foodList);
    }
    public void openList_mea(View btn){
        //打开肉类页面
        Intent foodList=new Intent(this,FoodActivity3.class);
        startActivity(foodList);
    }
    public void openList_egg(View btn){
        //打开水果页面
        Intent foodList=new Intent(this,FoodActivity2.class);
        startActivity(foodList);
    }
    public void openList_aqu(View btn){
        //打开水产页面
        Intent foodList=new Intent(this,FoodActivity.class);
        startActivity(foodList);
    }
    public void openList_dairy(View btn){
        //打开奶类页面
        Intent foodList=new Intent(this,FoodActivity7.class);
        startActivity(foodList);
    }
    public void openList_oil(View btn){
        //打开油脂类页面
        Intent foodList=new Intent(this,FoodActivity8.class);
        startActivity(foodList);
    }
    public void openList_sna(View btn){
        //打开糕点页面
        Intent foodList=new Intent(this,FoodActivity9.class);
        startActivity(foodList);
    }
    public void openList_sug(View btn){
        //打开糖页面
        Intent foodList=new Intent(this,FoodActivity10.class);
        startActivity(foodList);
    }
    public void openList_drink(View btn){
        //打开饮料页面
        Intent foodList=new Intent(this,FoodActivity11.class);
        startActivity(foodList);
    }
    public void openList_fung(View btn){
        //打开饮料页面
        Intent foodList=new Intent(this,FoodActivity12.class);
        startActivity(foodList);
    }
    public void openList_elsee(View btn){
        //打开饮料页面
        Intent foodList=new Intent(this,FoodActivity13.class);
        startActivity(foodList);
    }

    public void  onClickMain(View btn){

        fName=(EditText)findViewById(R.id.change_food_name);
        fCalorie=(EditText)findViewById(R.id.change_food_clories);

        String foodN = String.valueOf(fName.getText());
        String foodC = String.valueOf(fCalorie.getText());

        Intent MainActivity = new Intent(this,MainActivity.class);
        MainActivity.putExtra("title",foodN);
        MainActivity.putExtra("calories",foodC);
        startActivity(MainActivity);

    }}
