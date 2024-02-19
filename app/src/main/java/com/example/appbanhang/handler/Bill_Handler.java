package com.example.appbanhang.handler;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

import com.example.appbanhang.database.Database;
import com.example.appbanhang.model.Bill;
import com.example.appbanhang.model.Bill_Details;
import com.example.appbanhang.model.User;

import java.util.ArrayList;

public class Bill_Handler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "APPBANHANGADR.sql";
    private static final int DATABASE_VERSION = 1;
    private Database database;
    private SQLiteDatabase db;

    public Bill_Handler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        database = new Database(context);
        db = database.getWritableDatabase();
    }

    public ArrayList<Bill> getAllBill(){
        ArrayList<Bill> bills = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM bills";
        Cursor cursor = db.rawQuery(sql , null);
        while (cursor.moveToNext()){
            Bill bill = new Bill(
                    cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getDouble(2),
                    cursor.getString(3)
            );
            bills.add(bill);
        }
        return bills;
    }
    public ArrayList<Bill> getAllBillById(int user_id){
        ArrayList<Bill> arrayList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM bills WHERE user_id = " + user_id;
        Cursor cursor = db.rawQuery(sql , null);
        while(cursor.moveToNext()){
            Bill bill = new Bill(
                    cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getDouble(2),
                    cursor.getString(3)
            );
            arrayList.add(bill);
        }
        cursor.close();
        return arrayList;
    }
    public double getTotalPriceUserById(int user_id){
        double sum = 0;
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT total_price FROM bills WHERE user_id = " + user_id;
        Cursor cursor = db.rawQuery(sql , null);
        while(cursor.moveToNext()){
            sum += cursor.getDouble(0);
        }
        cursor.close();
        return sum;
    }
    public ArrayList<Bill_Details> getBillDetailsByBillId(int bill_id){
        ArrayList<Bill_Details> arrayList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM bill_details WHERE bill_id = " + bill_id;
        Cursor cursor = db.rawQuery(sql , null);
        while(cursor.moveToNext()){
            Bill_Details bill_details = new Bill_Details(
                    cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getString(2),
                    cursor.getInt(3),
                    cursor.getInt(4)
            );
            arrayList.add(bill_details);
        }
        cursor.close();
        return arrayList;
    }

    public boolean Add_Bill(Bill bill){
        SQLiteDatabase db = getWritableDatabase();
        String sql = "INSERT INTO bills VALUES(null , ? , ? , ?)";
        SQLiteStatement stmt = db.compileStatement(sql);
        stmt.clearBindings();
        stmt.bindLong(1 , bill.getUser_id());
        stmt.bindDouble(2, bill.getTotal_price());
        stmt.bindString(3 , bill.getDate_created());
        long rowID = stmt.executeInsert();
        return rowID != -1;
    }
    public int get_LastBill(){
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT id FROM bills ORDER BY id DESC";
        Cursor cursor = db.rawQuery(sql , null);
        if(cursor.moveToFirst()){
            int bill_id = cursor.getInt(0);
            cursor.close();
            return bill_id;
        }
        return -1;
    }
    public void Add_BillDetails(Bill_Details bill_details){
        SQLiteDatabase db = getWritableDatabase();
        String sql = "INSERT INTO bill_details VALUES(null , ? , ? , ? , ?)";
        SQLiteStatement stmt = db.compileStatement(sql);
        stmt.clearBindings();
        stmt.bindLong(1 , bill_details.getBill_id());
        stmt.bindString(2 , bill_details.getProduct_name());
        stmt.bindLong(3 , bill_details.getProduct_quantity());
        stmt.bindLong(4 , bill_details.getProduct_price());
        stmt.execute();
    }
    public double getTotalPrice(){
        double sum = 0;
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT total_price FROM bills";
        Cursor cursor = db.rawQuery(sql , null);
        while(cursor.moveToNext()){
            sum += cursor.getDouble(0);
        }
        cursor.close();
        return sum;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
