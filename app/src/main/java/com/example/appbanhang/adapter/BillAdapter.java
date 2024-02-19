package com.example.appbanhang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.appbanhang.R;
import com.example.appbanhang.model.Bill;

import java.util.ArrayList;

public class BillAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<Bill> arrayList = new ArrayList<>();

    public BillAdapter(Context context, int layout, ArrayList<Bill> arrayList) {
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
        view = inflater.inflate(layout , null);

        TextView txtBillId = (TextView) view.findViewById(R.id.txtbillId);
        TextView txtTotalPrice = (TextView) view.findViewById(R.id.txtTotalPrice);
        TextView txtDateCreated = (TextView) view.findViewById(R.id.txtDateCreated);

        Bill bill = arrayList.get(position);

        txtBillId.setText(bill.getId() + "");
        txtTotalPrice.setText(bill.getTotal_price() + "");
        txtDateCreated.setText(bill.getDate_created());
        return view;
    }
}
