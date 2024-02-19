package com.example.appbanhang.activity.user;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.appbanhang.R;
import com.example.appbanhang.adapter.ProductAdapter;
import com.example.appbanhang.handler.Product_Handler;
import com.example.appbanhang.model.Product;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class Activity_User extends AppCompatActivity{

    BottomNavigationView bottomNavigationView;
    ListView listView;
    ArrayList<Product> productArrayList = new ArrayList<>();
    ProductAdapter productAdapter;
    Product_Handler productHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        listView = (ListView) findViewById(R.id.lvProduct_user);
        productHandler = new Product_Handler(this);

        productArrayList = productHandler.getAll();
        productAdapter = new ProductAdapter(this, R.layout.item_user_product , productArrayList);
        listView.setAdapter(productAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Product product = productArrayList.get(position);
                int product_id = product.getId();
                String product_name = product.getName();
                int product_quantity = productHandler.getQuantity(product_id);
                int product_price = product.getPrice();
                AlertDialog.Builder builder = new AlertDialog.Builder(Activity_User.this);
                builder.setTitle("Thông tin chi tiết sản phẩm");
                builder.setMessage(
                        "ID: " + product_id +
                        "\nTên: " + product_name +
                        "\nSố lượng còn: " + product_quantity +
                        "\nGiá: " + product_price +
                        "\nThuế: 10%");
                builder.setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }
        });

        //noinspection deprecation
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int i = item.getItemId();
                if(i == R.id.home){
                    return true;
                }else if(i == R.id.bill){
                    Intent intent = new Intent(Activity_User.this , Activity_Bill.class);
                    startActivity(intent);
                    finish();
                    return true;
                }else if(i == R.id.person){
                    Intent intent = new Intent(Activity_User.this , Activity_Profile.class);
                    startActivity(intent);
                    finish();
                    return true;
                }else if(i == R.id.cart){
                    Intent intent = new Intent(Activity_User.this , Activity_Cart.class);
                    startActivity(intent);
                    finish();
                    return true;
                }
                return false;
            }
        });
    }
}