package com.swufe.healthy85;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class FoodManager {

    private  DBHelper dbHelper;
    private String TBNAME;

    public FoodManager(Context context){
        dbHelper =new DBHelper(context);
        TBNAME=DBHelper.TB_NAME;
    }
    public void add(FoodItem item){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("date",item.getDate());
        values.put("foodtype",item.getFoodType());
        values.put("calories",item.getCalories());
        db.insert(TBNAME,null,values);
        db.close();}

    public List<FoodItem> listAll(){
        List<FoodItem>foodList=null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor=db.query(TBNAME,null,null,null,null,null,null);
        if(cursor!=null){
            foodList = new ArrayList<FoodItem>();
            while(cursor.moveToNext()){
                FoodItem item=new FoodItem();
                item.setId(cursor.getInt(cursor.getColumnIndex("ID")));
                item.setDate(cursor.getString(cursor.getColumnIndex("DATE")));
                item.setFoodType(cursor.getString(cursor.getColumnIndex("FOODTYPE")));
                item.setCalories(cursor.getString(cursor.getColumnIndex("CALORIES")));
                foodList.add(item);

            }
            cursor.close();
        }
        db.close();
        return foodList;
    }
}
