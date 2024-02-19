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
import android.widget.Toast;

import com.example.appbanhang.R;
import com.example.appbanhang.adapter.ProductManagerAdapter;
import com.example.appbanhang.handler.Product_Handler;
import com.example.appbanhang.model.Product;

import java.util.ArrayList;
import java.util.List;

public class Activity_Product_Manager extends AppCompatActivity {

    private int ProductId;
    private Button btnAddProduct , btnBack;
    private ListView lvProduct;
    private Product_Handler productHandler;
    private ProductManagerAdapter adapter;
    private ArrayList<Product> productArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_manager);

        btnAddProduct = (Button) findViewById(R.id.btnAddProduct);
        btnBack = (Button) findViewById(R.id.btnBack);
        lvProduct = (ListView) findViewById(R.id.lvProduct);
        productHandler = new Product_Handler(this);

        productArrayList = productHandler.getAll();
        adapter = new ProductManagerAdapter(Activity_Product_Manager.this , R.layout.item_manager_product , productArrayList);
        lvProduct.setAdapter(adapter);

        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Activity_Product_Manager.this , Activity_AddProduct.class);
                startActivity(i);
                finish();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Activity_Product_Manager.this , Activity_Admin.class);
                startActivity(i);
                finish();
            }
        });
        lvProduct.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(Activity_Product_Manager.this);
                dialog.setTitle("XÓA SẢN PHẨM NÀY?");
                dialog.setMessage("Bạn thật sự muốn xóa sản phẩm này?");
                dialog.setPositiveButton("Xóa", new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Product product = productArrayList.get(position);
                        int productId = product.getId();
                        productHandler.deleteProduct(productId);

                        Toast.makeText(Activity_Product_Manager.this, "Đã xóa!", Toast.LENGTH_SHORT).show();
                        productArrayList.remove(position);
                        adapter.notifyDataSetChanged();
                    }
                });
                dialog.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.show();

                return true;
            }
        });
        lvProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Product_Manager.this);
                builder.setTitle("Thông tin chi tiết sản phẩm");
                Product product = productArrayList.get(position);
                int productId = product.getId();
                String productName = product.getName();
                int quantity = product.getQuantity();
                int price = product.getPrice();
                builder.setMessage(
                        "ID: " + productId + "\nTên: " + productName + "\nSố lượng: " + quantity + "\nGiá: " + price
                );
                builder.setPositiveButton("Sửa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ProductId = product.getId();
                        Intent intent = new Intent(Activity_Product_Manager.this, Activity_EditProduct.class);
                        intent.putExtra("productId", ProductId);
                        startActivity(intent);
                        finish();
                    }
                });
                builder.setNegativeButton("Oke", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.create().show();
            }
        });
    }
}