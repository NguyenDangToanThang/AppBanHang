package com.example.appbanhang.activity.admin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.appbanhang.R;
import com.example.appbanhang.adapter.UserManagerAdapter;
import com.example.appbanhang.database.Database;
import com.example.appbanhang.model.User;

import java.util.ArrayList;

public class Activity_User_Manager extends AppCompatActivity {
    private ListView lvUser;
    private Button btnBack;
    private Database database;
    private UserManagerAdapter adapter;
    private ArrayList<User> users = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_manager);

        lvUser = (ListView) findViewById(R.id.lvUserManager);
        btnBack = (Button) findViewById(R.id.btnBack);
        database = new Database(this);

        users = database.getAllUser();
        adapter = new UserManagerAdapter(Activity_User_Manager.this , R.layout.item_user , users);
        lvUser.setAdapter(adapter);

        lvUser.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Activity_User_Manager.this);
                builder.setTitle("Thông tin");
                String message = "Mã tài khoản: " + users.get(position).getId() +
                        "\nTên tài khoản: " + users.get(position).getUsername() +
                        "\nEmail: " + users.get(position).getEmail() +
                        "\nSố điện thoại: " + users.get(position).getPhone();
                builder.setMessage(message);
                builder.setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Activity_User_Manager.this , Activity_Admin.class);
                startActivity(i);
                finish();
            }
        });
    }
}