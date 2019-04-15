package com.mjacksi.novapizza.Models;

public class Food {
    String title;
    String description;
    double price;
    int count;
    int image;

    public Food(String title, String description, double price, int image) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.image = image;
        this.count = 0;
    }


    public Food(int count) {
        this.count = count;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public int getCount() {
        return count;
    }

    public void increaseCount() {
        this.count++;
    }
    public void decreaseCount() {
        if(this.count > 0)
            this.count--;
    }

    public int getImage() {
        return image;
    }
}
