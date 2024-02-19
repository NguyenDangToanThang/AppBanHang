package com.example.appbanhang.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

import com.example.appbanhang.model.User;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "APPBANHANGADR.sql";
    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_TABLE_USER = "CREATE TABLE users (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "username TEXT UNIQUE ," +
            "password TEXT," +
            "email TEXT UNIQUE," +
            "phone TEXT," +
            "role TEXT" +
            ")";
    private static final String CREATE_TABLE_PRODUCTS = "CREATE TABLE products (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "name TEXT," +
            "quantity INTEGER," +
            "price INTEGER," +
            "img BLOB" +
            ")";
    private static final String CREATE_TABLE_BILLS = "CREATE TABLE bills (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "user_id INTEGER," +
            "total_price REAL," +
            "date_created TEXT," +
            "FOREIGN KEY(user_id) REFERENCES users(id))";
    private static final String CREATE_TABLE_BILL_DETAILS = "CREATE TABLE bill_details (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "bill_id INTEGER," +
            "product TEXT," +
            "quantity INTEGER," +
            "price REAL," +
            "FOREIGN KEY(bill_id) REFERENCES bills(id))";
    private static final String INSERT_ADMIN =
            "INSERT INTO users VALUES(null,'admin','password','admin@gmail.com','0398293043','admin')";
    private static final String INSERT_USER =
            "INSERT INTO users VALUES(null,'thang123','password','thang@gmail.com','0387185045','user')";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_PRODUCTS);
        db.execSQL(CREATE_TABLE_BILLS);
        db.execSQL(CREATE_TABLE_BILL_DETAILS);
        db.execSQL(INSERT_ADMIN);
        db.execSQL(INSERT_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS products");
        db.execSQL("DROP TABLE IF EXISTS bills");
        db.execSQL("DROP TABLE IF EXISTS bill_details");
        onCreate(db);
    }
    //Login
    public String checkLogin(String username , String password){
        SQLiteDatabase db = getReadableDatabase();
        String selection = "username = ? AND password = ?";
        Cursor cursor = db.query(
                "users",
                new String[]{"role"},
                selection,
                new String[]{username,password},
                null,null,null);
        if(cursor.moveToFirst()){
            if(cursor.getString(0).equals("admin")) {
                cursor.close();
                return "admin";
            }
            else{
                cursor.close();
                return "user";
            }
        }
        cursor.close();
        return "null";
    }
    //Register
    public boolean InsertUser(User user){
        SQLiteDatabase db = getWritableDatabase();
        String sql = "INSERT INTO users VALUES(null , ? , ? , ? , ? , 'user')";
        SQLiteStatement stmt = db.compileStatement(sql);
        stmt.clearBindings();
        stmt.bindString(1, user.getUsername());
        stmt.bindString(2, user.getPassword());
        stmt.bindString(3, user.getEmail());
        stmt.bindString(4, user.getPhone());
        long rowID = stmt.executeInsert();
        return rowID != -1;
    }
    //Forgot Password
    public String GivePassword(String username , String email){
        SQLiteDatabase db = getReadableDatabase();
        String selection = "username = ? AND email = ?";
        Cursor cursor = db.query(
                "users",
                new String[]{"password"},
                selection,
                new String[]{username,email},
                null,null,null);
        if(cursor.moveToFirst()){
            String password = cursor.getString(0);
            cursor.close();
            return password;
        }
        return "";
    }
    public int giveUserID(String username){
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT id FROM users WHERE username = ?";
        Cursor cursor = db.rawQuery(sql , new String[]{username});
        if(cursor.moveToFirst()){
            int id = cursor.getInt(0);
            cursor.close();
            return id;
        }
        return -1;
    }
    public Cursor getUserById(int user_id){
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM users WHERE id = " + user_id;
        Cursor cursor = db.rawQuery(sql , null);
        return cursor;
    }
    public String getUsernameById(int user_id){
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT username FROM users WHERE id = " + user_id;
        Cursor cursor = db.rawQuery(sql , null);
        String name = "";
        if(cursor.moveToFirst()){
             name = cursor.getString(0);
        }
        cursor.close();
        return name;
    }
    public boolean editUser(User user){
        SQLiteDatabase db = getWritableDatabase();
        String sql = "UPDATE users SET password = ? , email = ? , phone = ? WHERE id = ?";
        SQLiteStatement stmt = db.compileStatement(sql);
        stmt.clearBindings();
        stmt.bindString(1, user.getPassword());
        stmt.bindString(2 , user.getEmail());
        stmt.bindString(3 , user.getPhone());
        stmt.bindLong(4, user.getId());
        long rowID = stmt.executeUpdateDelete();
        return rowID != -1;
    }
    public ArrayList<User> getAllUser(){
        ArrayList<User> users = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM users WHERE role = 'user'";
        Cursor cursor = db.rawQuery(sql , null);
        while (cursor.moveToNext()){
            User user = new User(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5)
            );
            users.add(user);
        }
        return users;
    }
}
