package com.example.appbanhang.activity.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.appbanhang.R;
import com.example.appbanhang.activity.login.Activity_Login;
import com.example.appbanhang.adapter.BillAdapter;
import com.example.appbanhang.handler.Bill_Handler;
import com.example.appbanhang.model.Bill;
import com.example.appbanhang.model.Bill_Details;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class Activity_Bill extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private ListView lvBill;
    private Bill_Handler billHandler;
    private ArrayList<Bill> arrayList = new ArrayList<>();
    private ArrayList<Bill_Details> bill_detailsArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);

        lvBill = (ListView) findViewById(R.id.lvBill);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        billHandler = new Bill_Handler(this);

        bottomNavigationView.findViewById(R.id.bill).callOnClick();

        arrayList = billHandler.getAllBillById(Activity_Login.user_id);
        BillAdapter adapter = new BillAdapter(Activity_Bill.this , R.layout.item_bill , arrayList);
        lvBill.setAdapter(adapter);

        lvBill.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bill bill = arrayList.get(position);
                bill_detailsArrayList  = billHandler.getBillDetailsByBillId(bill.getId());
                AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Bill.this);
                builder.setTitle("Chi tiết hóa đơn");
                String message = "Mã hóa đơn: " + bill_detailsArrayList.get(0).getBill_id() + "\n";
                for (int i = 0; i < bill_detailsArrayList.size() ; i++) {
                    message += "\nTên sản phẩm: " + bill_detailsArrayList.get(i).getProduct_name() +
                               "\nSố lượng: " + bill_detailsArrayList.get(i).getProduct_quantity() +
                               "\nGiá: " + bill_detailsArrayList.get(i).getProduct_price() +
                               "\nThuế: 10%\n";
                }
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

        //noinspection deprecation
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int i = item.getItemId();
                if(i == R.id.home){
                    Intent intent = new Intent(Activity_Bill.this , Activity_User.class);
                    startActivity(intent);
                    finish();
                    return true;
                }else if(i == R.id.bill){
                    return true;
                }else if(i == R.id.person){
                    Intent intent = new Intent(Activity_Bill.this , Activity_Profile.class);
                    startActivity(intent);
                    finish();
                    return true;
                }else if(i == R.id.cart){
                    Intent intent = new Intent(Activity_Bill.this , Activity_Cart.class);
                    startActivity(intent);
                    finish();
                    return true;
                }
                return false;
            }
        });
    }
}