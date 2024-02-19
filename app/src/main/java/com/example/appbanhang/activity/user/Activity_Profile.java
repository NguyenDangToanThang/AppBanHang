package com.example.appbanhang.activity.user;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.appbanhang.R;
import com.example.appbanhang.activity.login.Activity_Login;
import com.example.appbanhang.database.Database;
import com.example.appbanhang.handler.Bill_Handler;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Activity_Profile extends AppCompatActivity {

    private DecimalFormat decimalFormat = new DecimalFormat("#.#");
    private TextView txtUsername;
    private Button btnHome , btnProfile , btnLogout , btnTotalSpend;
    private Database database;
    private Bill_Handler billHandler;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        txtUsername = (TextView) findViewById(R.id.txtUsername);
        btnHome = (Button) findViewById(R.id.btnHome);
        btnProfile = (Button) findViewById(R.id.btnProfile);
        btnLogout = (Button) findViewById(R.id.btnLogout);
        btnTotalSpend = (Button) findViewById(R.id.btnTotalSpend);
        billHandler = new Bill_Handler(this);
        database = new Database(this);

        cursor = database.getUserById(Activity_Login.user_id);
        int index = cursor.getColumnIndex("username");
        if(cursor.moveToFirst()){
            txtUsername.setText("Tài khoản: " + cursor.getString(index));
        }

        btnTotalSpend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Profile.this);
                builder.setMessage(
                        "Tổng chi tiêu: " + decimalFormat.format(billHandler.getTotalPriceUserById(Activity_Login.user_id)) + " VND" +
                        "\n\nXem chi tiết các món hàng đã mua hãy nhấn vào hóa đơn!"
                );
                builder.setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setPositiveButton("Hóa đơn", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(Activity_Profile.this , Activity_Bill.class);
                        startActivity(i);
                        finish();
                    }
                });
                builder.create().show();
            }
        });

        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Activity_Profile.this , Activity_Edit_User.class);
                startActivity(i);
                finish();
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Activity_Profile.this , Activity_User.class);
                startActivity(i);
                finish();
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Activity_Profile.this , Activity_Login.class);
                Activity_Cart.carts.clear();
                startActivity(i);
                finish();
            }
        });
    }
}