package com.mjacksi.novapizza.Models;

import java.util.List;

public class FirebaseOrderList {
    private List<FirebaseOrder> orders;

    public FirebaseOrderList() {
    }

    public List<FirebaseOrder> getOrders() {
        return orders;
    }

    public void setOrders(List<FirebaseOrder> orders) {
        this.orders = orders;
    }
}
