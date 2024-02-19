package com.example.appbanhang.model;

public class Bill {
    private int id;
    private int user_id;
    private double total_price;
    private String date_created;
    private String username;

    public Bill(int id, int user_id, double total_price, String date_created) {
        this.id = id;
        this.user_id = user_id;
        this.total_price = total_price;
        this.date_created = date_created;
    }
    public Bill(int id, String username, double total_price, String date_created) {
        this.id = id;
        this.username = username;
        this.total_price = total_price;
        this.date_created = date_created;
    }
    public Bill(int user_id, double total_price, String date_created) {
        this.user_id = user_id;
        this.total_price = total_price;
        this.date_created = date_created;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }

    public String getDate_created() {
        return date_created;
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
