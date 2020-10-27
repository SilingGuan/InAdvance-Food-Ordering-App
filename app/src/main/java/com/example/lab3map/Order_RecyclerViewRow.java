package com.example.lab3map;

public class Order_RecyclerViewRow {

    private String orderList,orderTime, priceTotal, restaurantAddress, restaurantName,orderID;
    public Order_RecyclerViewRow(String orderList, String orderID, String orderTime, String priceTotal, String restaurantName,String restaurantAddress){
        this.orderList = orderList;
        this.orderID = orderID;
        this.orderTime = orderTime;
        this.priceTotal = priceTotal;
        this.restaurantAddress = restaurantAddress;
        this.restaurantName = restaurantName;
    }
    public Order_RecyclerViewRow(){}

    public void setOrderList(String orderList) {
        this.orderList = orderList;
    }
    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }
    public void setPriceTotal(String priceTotal) {
        this.priceTotal = priceTotal;
    }
    public void setRestaurantAddress(String restaurantAddress) {
        this.restaurantAddress = restaurantAddress;
    }
    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }
    public void setOrderID(String orderID) {
            this.orderID = orderID;
    }


    public String getOrderList(){
        return orderList;
    }
    public String getOrderTime() {
        return orderTime;
    }

    public String getPriceTotal() {
        return priceTotal;
    }

    public String getRestaurantAddress() {
        return restaurantAddress;
    }

    public String getRestaurantName() {
        return restaurantName;
    }
    public String getOrderID() {
        return orderID;
    }
}
