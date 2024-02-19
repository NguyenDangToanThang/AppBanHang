package com.example.appbanhang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.appbanhang.R;
import com.example.appbanhang.database.Database;
import com.example.appbanhang.model.Bill;

import java.util.ArrayList;

public class BillManagerAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<Bill> bills = new ArrayList<>();
    private Database database;

    public BillManagerAdapter(Context context, int layout, ArrayList<Bill> bills) {
        this.context = context;
        this.layout = layout;
        this.bills = bills;
        database = new Database(context);
    }

    @Override
    public int getCount() {
        return bills.size();
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
        TextView txtUsername = (TextView) view.findViewById(R.id.txtUsername);
        TextView txtTotalPrice = (TextView) view.findViewById(R.id.txtTotalPrice);
        TextView txtDateCreated = (TextView) view.findViewById(R.id.txtDateCreated);

        Bill bill = bills.get(position);

        txtBillId.setText(bill.getId() + "");
        txtUsername.setText(database.getUsernameById(bill.getUser_id()));
        txtTotalPrice.setText(bill.getTotal_price() + "");
        txtDateCreated.setText(bill.getDate_created());
        return view;
    }
}
