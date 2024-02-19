package com.example.appbanhang.model;

public class Bill_Details {
    private int id;
    private int bill_id;
    private String product_name;
    private int product_quantity;
    private int product_price;

    public Bill_Details(int bill_id, String product_name, int product_quantity, int product_price) {
        this.bill_id = bill_id;
        this.product_name = product_name;
        this.product_quantity = product_quantity;
        this.product_price = product_price;
    }

    public Bill_Details(int id, int bill_id, String product_name, int product_quantity, int product_price) {
        this.id = id;
        this.bill_id = bill_id;
        this.product_name = product_name;
        this.product_quantity = product_quantity;
        this.product_price = product_price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBill_id() {
        return bill_id;
    }

    public void setBill_id(int bill_id) {
        this.bill_id = bill_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public int getProduct_quantity() {
        return product_quantity;
    }

    public void setProduct_quantity(int product_quantity) {
        this.product_quantity = product_quantity;
    }

    public int getProduct_price() {
        return product_price;
    }

    public void setProduct_price(int product_price) {
        this.product_price = product_price;
    }
}
