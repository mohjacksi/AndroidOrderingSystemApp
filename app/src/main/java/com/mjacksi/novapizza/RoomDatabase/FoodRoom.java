package com.mjacksi.novapizza.RoomDatabase;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * 1- Food object that will be used as model for room database
 */
@Entity(tableName = "food_table")
public class FoodRoom {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;

    private String description;

    private int count;

    private double price;

    private int image;

    public FoodRoom(String title, String description, double price, int image) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.image = image;
        this.count = 0;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCount(int count){
        this.count = count;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getCount() {
        return count;
    }

    public double getPrice() {
        return price;
    }

    public int getImage() {
        return image;
    }
}

