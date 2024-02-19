package com.example.appbanhang.activity.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.appbanhang.R;
import com.example.appbanhang.activity.login.Activity_Login;

public class Activity_Admin extends AppCompatActivity {

    private Button btnProductManager , btnUserManager , btnBillManager , btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        btnProductManager = (Button) findViewById(R.id.btnProductManager);
        btnUserManager = (Button) findViewById(R.id.btnUserManager);
        btnBillManager = (Button) findViewById(R.id.btnBillManager);
        btnLogout = (Button) findViewById(R.id.btnLogout);

        btnProductManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Activity_Admin.this , Activity_Product_Manager.class);
                startActivity(i);
                finish();
            }
        });

        btnUserManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Activity_Admin.this , Activity_User_Manager.class);
                startActivity(i);
                finish();
            }
        });

        btnBillManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Activity_Admin.this , Activity_Bill_Manager.class);
                startActivity(i);
                finish();
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Activity_Admin.this , Activity_Login.class);
                startActivity(i);
                finish();
            }
        });
    }
}