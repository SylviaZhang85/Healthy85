package com.swufe.healthy85;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    EditText amount;
    TextView show;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        amount=(EditText)findViewById(R.id.amount);
        show=(TextView)findViewById(R.id.showOut);


    }
    public void  onClick(View btn){
         String str=amount.getText().toString();
         float am=0;
         if(str.length()>0){
             am=Float.parseFloat(str);
         }else{
             Toast.makeText(this,"Please input the food amounts",Toast.LENGTH_SHORT).show();
         }

             if(btn.getId()==R.id.btn_calculate){
             float cal=am*11.2f;
             show.setText(String.valueOf(cal));
         }

    }
    public void openOne(View btn){
        //打开food页面
        Intent food=new Intent(this,FoodActivity.class);
        startActivity(food);
    }

}
