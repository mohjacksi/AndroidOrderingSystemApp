package com.mjacksi.novapizza.Models;

public class Food {
    String name;
    String description;
    double price;
    int count;
    int image;

    public Food(String name, String description, double price, int image) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
        this.count = 0;
    }

    public String getName() {
        return name;
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
