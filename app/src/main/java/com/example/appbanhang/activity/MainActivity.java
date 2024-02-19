package com.example.appbanhang.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.appbanhang.R;
import com.example.appbanhang.activity.login.Activity_Login;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                }catch (Exception e){

                }finally {
                    Intent i = new Intent(MainActivity.this , Activity_Login.class);
                    startActivity(i);
                    finish();
                }
            }
        });
        thread.start();
    }
}