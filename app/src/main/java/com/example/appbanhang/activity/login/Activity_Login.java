package com.example.appbanhang.activity.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appbanhang.R;
import com.example.appbanhang.activity.admin.Activity_Admin;
import com.example.appbanhang.activity.user.Activity_User;
import com.example.appbanhang.database.Database;

public class Activity_Login extends AppCompatActivity {

    public static int user_id = 1;
    EditText edtUsername , edtPassword;
    private TextView txtForgotPassword;
    private Button btnLogin , btnRegister;
    private Database database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUsername = (EditText) findViewById(R.id.edtUsername);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        txtForgotPassword = (TextView) findViewById(R.id.txtForgotPassword);
        btnLogin = (Button) findViewById(R.id.btnDangNhap);
        btnRegister = (Button) findViewById(R.id.btnDangKy);
        database = new Database(this);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Activity_Login.this , Activity_Register.class);
                startActivity(i);
                finish();
            }
        });
        txtForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Activity_Login.this , Activity_Forgot_Password.class);
                startActivity(i);
                finish();
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUsername.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();
                if(database.checkLogin(username,password).equals("admin")){
                    Intent i = new Intent(Activity_Login.this , Activity_Admin.class);
                    startActivity(i);
                    Toast.makeText(Activity_Login.this, "Đăng nhập Admin thành công!", Toast.LENGTH_SHORT).show();
                    finish();
                }else if (database.checkLogin(username,password).equals("user")){
                    int id = database.giveUserID(username);
                    if (id > 0){
                        user_id = id;
                    }
                    Intent i = new Intent(Activity_Login.this , Activity_User.class);
                    startActivity(i);
                    Toast.makeText(Activity_Login.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(Activity_Login.this, "Tài khoản hoặc mật khẩu không đúng!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}