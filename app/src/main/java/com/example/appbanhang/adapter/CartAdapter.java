package com.example.appbanhang.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.appbanhang.R;
import com.example.appbanhang.activity.user.Activity_Cart;
import com.example.appbanhang.model.Cart;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CartAdapter extends BaseAdapter {
    private DecimalFormat decimalFormat = new DecimalFormat("#.#");
    private Context context;
    private int layout;
    private ArrayList<Cart> arrayList = new ArrayList<>();

    public CartAdapter(Context context, int layout, ArrayList<Cart> arrayList) {
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
        view = inflater.inflate(layout , null);

        TextView txtName = (TextView) view.findViewById(R.id.txtName);
        TextView txtPrice = (TextView) view.findViewById(R.id.txtPrice);
        TextView edtQuantity = (TextView) view.findViewById(R.id.edtQuantity);
        ImageView imgCart = (ImageView) view.findViewById(R.id.imgCart);
        TextView txtTotal = (TextView) view.findViewById(R.id.txtThanhTien);
        ImageButton btnLeft = (ImageButton) view.findViewById(R.id.btnLeft);
        ImageButton btnRight = (ImageButton) view.findViewById(R.id.btnRight);

        Cart cart = arrayList.get(position);
        byte[] imageCart = cart.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageCart , 0 , imageCart.length);
        imgCart.setImageBitmap(bitmap);
        txtName.setText("Tên: " + cart.getName());
        txtPrice.setText("Giá: " + cart.getPrice());
        edtQuantity.setText(cart.getQuantity() + "");
        txtTotal.setText("Tổng tiền(10% VAT): " + decimalFormat.format(cart.getQuantity()*cart.getPrice()*1.1));

        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cart.getQuantity() < 2){
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Xác nhận xóa").setMessage("Bạn có chắc muốn xóa không?");
                    builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Activity_Cart.carts.remove(position);
                            ((Activity_Cart)context).setEdtTongTien(decimalFormat.format(totals()));
                            notifyDataSetChanged();
                        }
                    });
                    builder.create().show();
                }else {
                    cart.setQuantity(cart.getQuantity() - 1);
                    edtQuantity.setText((cart.getQuantity()) + "");
                    txtTotal.setText("Tổng tiền(10% VAT):" + decimalFormat.format(cart.getQuantity()*cart.getPrice()*1.1));
                }
                ((Activity_Cart)context).setEdtTongTien(decimalFormat.format(totals()));
                notifyDataSetChanged();
            }
        });
        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cart.setQuantity(cart.getQuantity() + 1);
                edtQuantity.setText(cart.getQuantity() + "");
                txtTotal.setText("Tổng tiền(10% VAT):" + decimalFormat.format(cart.getQuantity()*cart.getPrice()*1.1));
                ((Activity_Cart)context).setEdtTongTien(decimalFormat.format(totals()));
                notifyDataSetChanged();
            }
        });
        return view;
    }
    public double totals(){
        int total = 0;
        for (Cart cart: arrayList) {
            total += cart.getQuantity() * cart.getPrice() ;
        }
        return total * 1.1;
    }
}
