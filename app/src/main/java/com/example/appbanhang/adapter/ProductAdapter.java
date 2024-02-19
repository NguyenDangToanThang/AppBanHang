package com.example.appbanhang.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appbanhang.R;
import com.example.appbanhang.activity.user.Activity_Cart;
import com.example.appbanhang.model.Cart;
import com.example.appbanhang.model.Product;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class ProductAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<Product> arrayList = new ArrayList<>();

    public ProductAdapter(Context context, int layout, ArrayList<Product> arrayList) {
        this.context = context;
        this.layout = layout;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(layout,null);
        // ánh xạ view
        ImageView imageView = (ImageView) view.findViewById(R.id.imgProduct);
        TextView txtName = (TextView) view.findViewById(R.id.txtName);
        TextView txtPrice = (TextView) view.findViewById(R.id.txtPriceProduct);
        ImageButton btnAdd = (ImageButton) view.findViewById(R.id.btnAdd);
        // gán giá trị
        Product product = arrayList.get(position);
        byte[] productImage = product.getImg();
        Bitmap bitmap = BitmapFactory.decodeByteArray(productImage, 0, productImage.length);
        imageView.setImageBitmap(bitmap);
        txtName.setText("Tên: " + product.getName());
        txtPrice.setText("Giá: " + product.getPrice());
        product.setQuantity(1);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cart cart = new Cart(product.getId() , product.getName() , product.getQuantity() , product.getPrice() , product.getImg());
                for (Cart cartCheck : Activity_Cart.carts) {
                    if(product.getId() == cartCheck.getId()){
                        product.setQuantity(product.getQuantity()+1);
                        cart.setQuantity(product.getQuantity());
                    }
                }
                if(cart.getQuantity() > 1){
                    for (int i = 0; i < Activity_Cart.carts.size(); i++) {
                        if(Activity_Cart.carts.get(i).getId() == cart.getId()){
                            Activity_Cart.carts.set(i,cart);
                        }
                    }
                }else{
                    Activity_Cart.carts.add(cart);
                }
                Snackbar snackbar = Snackbar.make(context , view , "Thêm thành công" , Snackbar.LENGTH_LONG )
                        .setAction("Giỏ hàng", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent i = new Intent(context , Activity_Cart.class);
                                context.startActivity(i);
                            }
                        });
                snackbar.show();
            }
        });
        return view;
    }
}
