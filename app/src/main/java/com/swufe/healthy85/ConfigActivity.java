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
        //打开food页面
        Intent foodList=new Intent(this,FoodActivity.class);
        startActivity(foodList);
    }
}
