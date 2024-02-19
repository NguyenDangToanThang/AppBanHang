package com.example.appbanhang.activity.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appbanhang.R;
import com.example.appbanhang.activity.login.Activity_Login;
import com.example.appbanhang.database.Database;
import com.example.appbanhang.model.User;

public class Activity_Edit_User extends AppCompatActivity {
    private EditText edtId , edtUsername , edtPassword , edtEmail , edtPhone;
    private Button btnSave, btnBack;
    private Database database;
    private Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        edtId = (EditText) findViewById(R.id.edtId);
        edtUsername = (EditText) findViewById(R.id.edtUsername);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtPhone = (EditText) findViewById(R.id.edtPhone);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnBack = (Button) findViewById(R.id.btnBack);
        database = new Database(this);

        cursor = database.getUserById(Activity_Login.user_id);
        if(cursor.moveToFirst()){
            edtId.setText(cursor.getInt(0) + "");
            edtUsername.setText(cursor.getString(1));
            edtPassword.setText(cursor.getString(2));
            edtEmail.setText(cursor.getString(3));
            edtPhone.setText(cursor.getString(4));
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = Integer.parseInt(edtId.getText().toString().trim());
                String username = edtUsername.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();
                String email = edtEmail.getText().toString().trim();
                String phone = edtPhone.getText().toString().trim();
                User user = new User(id,username,password,email,phone);
                if(database.editUser(user)){
                    Intent i = new Intent(Activity_Edit_User.this , Activity_Profile.class);
                    startActivity(i);
                    Toast.makeText(Activity_Edit_User.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(Activity_Edit_User.this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Activity_Edit_User.this , Activity_Profile.class);
                startActivity(i);
                finish();
            }
        });
    }
}