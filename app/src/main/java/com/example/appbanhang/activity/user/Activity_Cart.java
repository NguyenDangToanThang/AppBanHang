package com.example.appbanhang.activity.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.appbanhang.R;
import com.example.appbanhang.activity.login.Activity_Login;
import com.example.appbanhang.adapter.CartAdapter;
import com.example.appbanhang.handler.Bill_Handler;
import com.example.appbanhang.handler.Product_Handler;
import com.example.appbanhang.model.Bill;
import com.example.appbanhang.model.Bill_Details;
import com.example.appbanhang.model.Cart;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Activity_Cart extends AppCompatActivity {

    private DecimalFormat decimalFormat = new DecimalFormat("#.#");
    private BottomNavigationView bottomNavigationView;
    public static final ArrayList<Cart> carts = new ArrayList<>();
    private EditText edtTotal;
    private ListView lvCart;
    private Button btnPay;
    private Bill_Handler billHandler;
    private Product_Handler productHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        edtTotal = (EditText) findViewById(R.id.txtTotal);
        lvCart = (ListView) findViewById(R.id.lvCart);
        btnPay = (Button) findViewById(R.id.btnPay);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        bottomNavigationView.findViewById(R.id.cart).callOnClick();
        billHandler = new Bill_Handler(this);
        productHandler = new Product_Handler(this);

        CartAdapter cartAdapter = new CartAdapter(this , R.layout.item_cart , carts);
        lvCart.setAdapter(cartAdapter);

        edtTotal.setText(decimalFormat.format(cartAdapter.totals()));

        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int user_id = Activity_Login.user_id;
                if(carts.size() > 0){
                    Double total_price = Double.parseDouble(edtTotal.getText().toString().trim());
                    Date dateNow = new Date();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    Bill bill = new Bill(user_id , total_price , dateFormat.format(dateNow));
                    if(billHandler.Add_Bill(bill)){
                        int bill_id = billHandler.get_LastBill();
                        for (int i = 0; i < carts.size(); i++) {
                            Bill_Details bill_details = new Bill_Details(
                                    bill_id,
                                    carts.get(i).getName(),
                                    carts.get(i).getQuantity(),
                                    carts.get(i).getPrice());
                            billHandler.Add_BillDetails(bill_details);
                            productHandler.setQuantityById(carts.get(i).getId() , carts.get(i).getQuantity());
                        }
                        carts.clear();
                        edtTotal.setText("0.0");
                        cartAdapter.notifyDataSetChanged();
                    }
                    Snackbar snackbar = Snackbar.make(v , "Thanh toán thành công!" , Snackbar.LENGTH_LONG )
                            .setAction("Hóa đơn", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent i = new Intent(Activity_Cart.this , Activity_Bill.class);
                                    startActivity(i);
                                    finish();
                                }
                            });
                    snackbar.show();
                }
                else {
                    Snackbar snackbar = Snackbar.make(v, "Không có món hàng nào để thanh toán!" , Snackbar.LENGTH_LONG)
                            .setAction("Mua hàng", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent i = new Intent(Activity_Cart.this , Activity_User.class);
                                    startActivity(i);
                                    finish();
                                }
                            });
                    snackbar.show();
                }
            }
        });
        //noinspection deprecation
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int i = item.getItemId();
                if(i == R.id.home){
                    Intent intent = new Intent(Activity_Cart.this , Activity_User.class);
                    startActivity(intent);
                    finish();
                    return true;
                }else if(i == R.id.bill){
                    Intent intent = new Intent(Activity_Cart.this , Activity_Bill.class);
                    startActivity(intent);
                    finish();
                    return true;
                }else if(i == R.id.person){
                    Intent intent = new Intent(Activity_Cart.this , Activity_Profile.class);
                    startActivity(intent);
                    finish();
                    return true;
                }else if(i == R.id.cart){
                    return true;
                }
                return false;
            }
        });
    }
    public void setEdtTongTien(String total){
        edtTotal.setText(total);
    }
}