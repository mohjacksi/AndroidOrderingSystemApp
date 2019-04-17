package com.mjacksi.novapizza.Models;


import java.util.Map;

public class FirebaseOrder {
    public Map<String, Integer> orders;
    String userId;
    String userPhone;
    String location;
    String status;
    double total;
    long time = 0;

    public FirebaseOrder() {
    }

    public FirebaseOrder(String userId, String userPhone, String location, String status, double total, long time, Map<String, Integer> orders) {
        this.userId = userId;
        this.userPhone = userPhone;
        this.location = location;
        this.status = status;
        this.total = total;
        this.time = time;
        this.orders = orders;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public Map<String, Integer> getOrders() {
        return orders;
    }

    public void setOrders(Map<String, Integer> orders) {
        this.orders = orders;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }




}
