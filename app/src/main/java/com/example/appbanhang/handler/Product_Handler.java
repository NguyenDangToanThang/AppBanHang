package com.example.appbanhang.handler;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import com.example.appbanhang.database.Database;
import com.example.appbanhang.model.Product;

import java.util.ArrayList;

public class Product_Handler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "APPBANHANGADR.sql";
    private static final int DATABASE_VERSION = 1;

    private Database database;
    private SQLiteDatabase db;

    public Product getProductById(int productId) {
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {"id", "name", "quantity", "price", "img"};
        String selection = "id = ?";
        String[] selectionArgs = {String.valueOf(productId)};
        Cursor cursor = db.query("products", columns, selection, selectionArgs, null, null, null);
        Product product = null;
        if (cursor.moveToFirst()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            int quantity = cursor.getInt(2);
            int price = cursor.getInt(3);
            byte[] image = cursor.getBlob(4);
            product = new Product(id, name, quantity, price, image);
        }
        cursor.close();
        return product;
    }
    public Product_Handler(Context context) {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
        database = new Database(context);
        db = database.getWritableDatabase();
    }

    public ArrayList<Product> getAll(){
        ArrayList<Product> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM products";
        Cursor cursor = db.rawQuery(sql , null);
        while(cursor.moveToNext()){
            Product product = new Product(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getInt(2),
                    cursor.getInt(3),
                    cursor.getBlob(4)
            );
            list.add(product);
        }
        cursor.close();
        return list;
    }

    public boolean createProduct(String name,int quantity,int price,byte[] image){
        SQLiteDatabase db = getWritableDatabase();
        String sql = "INSERT INTO products(name,quantity,price,img) VALUES(?,?,?,?)";
        SQLiteStatement statement = db.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1,name);
        statement.bindLong(2,quantity);
        statement.bindLong(3,price);
        statement.bindBlob(4,image);
        long rowID = statement.executeInsert();
        return rowID != -1;
    }
    public boolean deleteProduct(int id){
        int rowsAffected = db.delete("products", "id=?", new String[]{String.valueOf(id)});
        return rowsAffected > 0;
    }
    public boolean editProduct(int productId, String name , int quantity , int price , byte[] image){
        SQLiteDatabase db = getWritableDatabase();
        String sql = "UPDATE products SET name = ?,  quantity = ?,price = ? ,  img = ? WHERE id = ?";
        SQLiteStatement statement = db.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1,name);
        statement.bindLong(2,quantity);
        statement.bindLong(3,price);
        statement.bindBlob(4,image);
        statement.bindLong(5,productId);
        statement.executeUpdateDelete();
        long rowID = statement.executeUpdateDelete();
        return rowID != -1;
    }
    public int getQuantity(int product_id){
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT quantity FROM products WHERE id = " + product_id;
        Cursor cursor = db.rawQuery(sql , null);
        if(cursor.moveToFirst()){
            int quantity = cursor.getInt(0);
            cursor.close();
            return quantity;
        }
        return -1;
    }
    public void setQuantityById(int product_id , int product_quantity){
        SQLiteDatabase db = getReadableDatabase();
        String sql = "UPDATE products SET quantity = quantity - ? WHERE id = ?";
        SQLiteStatement stmt = db.compileStatement(sql);
        stmt.clearBindings();
        stmt.bindLong(1, product_quantity);
        stmt.bindLong(2, product_id);
        stmt.execute();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
