package com.example.appbanhang.activity.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appbanhang.R;
import com.example.appbanhang.database.Database;
import com.example.appbanhang.model.User;

public class Activity_Register extends AppCompatActivity {

    private EditText edtUsername , edtPassword , edtEmail , edtPhone;
    private Button btnBack , btnRegister;
    private Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtUsername = (EditText) findViewById(R.id.edtUsername);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtPhone = (EditText) findViewById(R.id.edtPhone);
        btnBack = (Button) findViewById(R.id.btnBack);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        database = new Database(this);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtUsername.getText().toString().trim();
                String pass = edtPassword.getText().toString().trim();
                String email = edtEmail.getText().toString().trim();
                String phone = edtPhone.getText().toString().trim();
                User user = new User(name,pass,email,phone);
                if(database.InsertUser(user)){
                    Intent i = new Intent(Activity_Register.this , Activity_Login.class);
                    startActivity(i);
                    Toast.makeText(Activity_Register.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(Activity_Register.this, "Đăng ký không thành công!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Activity_Register.this , Activity_Login.class);
                startActivity(i);
                finish();
            }
        });
    }
}