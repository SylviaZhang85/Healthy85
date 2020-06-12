package com.swufe.healthy85;

public class FoodItem {
    private int id;
    private String Date;
    private String FoodType;
    private String Calories;

    public FoodItem() {
        this.Date = "";
        this.FoodType = "";
        this.Calories= "";
    }

    public FoodItem(String date, String FoodType, String Calories) {
        this.Date = date;
        this.FoodType =FoodType;
        this.Calories = Calories;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getFoodType() {
        return FoodType;
    }

    public void setFoodType(String FoodType) {
        this.FoodType = FoodType;
    }

    public String getCalories() {
        return Calories;
    }

    public void setCalories(String Calories) {
        this.Calories = Calories;
    }
}
