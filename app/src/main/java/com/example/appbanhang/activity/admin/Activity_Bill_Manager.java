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
import android.widget.TextView;

import com.example.appbanhang.R;
import com.example.appbanhang.adapter.BillManagerAdapter;
import com.example.appbanhang.handler.Bill_Handler;
import com.example.appbanhang.model.Bill;
import com.example.appbanhang.model.Bill_Details;

import java.util.ArrayList;

public class Activity_Bill_Manager extends AppCompatActivity {
    private ListView lvBill;
    private TextView txtRevenue;
    private Button btnBack;
    private Bill_Handler billHandler;
    private BillManagerAdapter adapter;
    private ArrayList<Bill> bills = new ArrayList<>();
    private int sum = 0;
    private ArrayList<Bill_Details> bill_details = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_manager);

        lvBill = (ListView) findViewById(R.id.lvBillManager);
        txtRevenue = (TextView) findViewById(R.id.txtRevenue);
        btnBack = (Button) findViewById(R.id.btnBack);
        billHandler = new Bill_Handler(this);

        bills = billHandler.getAllBill();
        adapter = new BillManagerAdapter(Activity_Bill_Manager.this , R.layout.item_manager_bill , bills);
        lvBill.setAdapter(adapter);
        for (int i = 0; i < bills.size(); i++) {
            sum += bills.get(i).getTotal_price();
        }

        lvBill.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                bill_details = billHandler.getBillDetailsByBillId(bills.get(position).getId());
                AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Bill_Manager.this);
                builder.setTitle("Chi tiết hóa đơn");
                String message = "Mã hóa đơn: " + bill_details.get(0).getBill_id() + "\n\n";
                for (int i = 0; i < bill_details.size(); i++) {
                    message += "Tên sản phẩm: " + bill_details.get(i).getProduct_name() + "\n" +
                               "Số lượng: " + bill_details.get(i).getProduct_quantity() + "\n" +
                               "Giá: " + bill_details.get(i).getProduct_price() + "\n" +
                               "Thuế: 10%\n\n";
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

        txtRevenue.setText(sum + "");

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Activity_Bill_Manager.this , Activity_Admin.class);
                startActivity(i);
                finish();
            }
        });
    }
}