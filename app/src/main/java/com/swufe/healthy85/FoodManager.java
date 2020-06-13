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

    public void addAll(List<FoodItem> list){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        for (FoodItem item : list) {
            ContentValues values = new ContentValues();
            values.put("Date", item.getDate());
            values.put("FoodType", item.getFoodType());
            values.put("Calories", item.getCalories());
            db.insert(TBNAME, null, values);
        }
        db.close();
    }

    public void deleteAll(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TBNAME,null,null);
        db.close();
    }

    public void delete(int id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TBNAME, "ID=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void update(FoodItem item){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Date", item.getDate());
        values.put("FoodType", item.getFoodType());
        values.put("Calories", item.getCalories());
        db.update(TBNAME, values, "ID=?", new String[]{String.valueOf(item.getId())});
        db.close();
    }


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
    public FoodItem findById(int id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TBNAME, null, "ID=?", new String[]{String.valueOf(id)}, null, null, null);
        FoodItem foodItem = null;
        if(cursor!=null && cursor.moveToFirst()){
            foodItem = new FoodItem();
            foodItem.setId(cursor.getInt(cursor.getColumnIndex("ID")));
            foodItem.setDate(cursor.getString(cursor.getColumnIndex("DATE")));
            foodItem.setFoodType(cursor.getString(cursor.getColumnIndex("FOODTYPE")));
            foodItem.setCalories(cursor.getString(cursor.getColumnIndex("CALORIES")));
            cursor.close();
        }
        db.close();
        return foodItem;
    }
}
