package com.example.appbanhang.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appbanhang.R;
import com.example.appbanhang.model.Product;

import java.util.ArrayList;

public class ProductManagerAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<Product> arrayList = new ArrayList<>();

    public ProductManagerAdapter(Context context, int layout, ArrayList<Product> arrayList) {
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
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(layout,null);
        //ánh xạ view
        ImageView imgView = (ImageView) view.findViewById(R.id.imgProductManager);
        TextView txtNameProduct = (TextView) view.findViewById(R.id.txtNameProduct);
        TextView txtQuantity = (TextView) view.findViewById(R.id.txtQuantityProduct);
        TextView txtPrice = (TextView) view.findViewById(R.id.txtPriceProduct);

        Product product = arrayList.get(position);
        byte[] productImage = product.getImg();
        Bitmap bitmap = BitmapFactory.decodeByteArray(productImage, 0, productImage.length);
        imgView.setImageBitmap(bitmap);
        txtNameProduct.setText(product.getName());
        txtQuantity.setText(product.getQuantity() + "");
        txtPrice.setText(product.getPrice() + "");
        return view;
    }
}
