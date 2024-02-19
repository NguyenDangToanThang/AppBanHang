package com.example.appbanhang.activity.login;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appbanhang.R;
import com.example.appbanhang.database.Database;

public class Activity_Forgot_Password extends AppCompatActivity {

    private EditText edtUsername , edtEmail;
    private Button btnBack , btnPassword;
    private Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        edtUsername = (EditText) findViewById(R.id.edtUsername);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        btnBack = (Button) findViewById(R.id.btnBack);
        btnPassword = (Button) findViewById(R.id.btnPassword);
        database = new Database(this);

        btnPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtUsername.getText().toString().trim();
                String email = edtEmail.getText().toString().trim();
                if(!database.GivePassword(name,email).equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Forgot_Password.this);
                    builder.setTitle("Lấy lại mật khẩu");
                    builder.setMessage("Mật khẩu của bạn: " + database.GivePassword(name,email));
                    builder.setPositiveButton("Đăng nhập", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent i = new Intent(Activity_Forgot_Password.this , Activity_Login.class);
                            startActivity(i);
                            finish();
                        }
                    });
                    builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }else{
                    Toast.makeText(Activity_Forgot_Password.this, "Tài khoản hoặc email không đúng!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Activity_Forgot_Password.this , Activity_Login.class);
                startActivity(i);
                finish();
            }
        });
    }
}