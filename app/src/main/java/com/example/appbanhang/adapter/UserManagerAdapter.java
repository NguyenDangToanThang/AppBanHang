package com.example.appbanhang.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.appbanhang.R;
import com.example.appbanhang.handler.Bill_Handler;
import com.example.appbanhang.model.Bill;
import com.example.appbanhang.model.User;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class UserManagerAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<User> arrayUser = new ArrayList<>();
    private Bill_Handler billHandler;

    public UserManagerAdapter(Context context, int layout, ArrayList<User> arrayUser) {
        this.context = context;
        this.layout = layout;
        this.arrayUser = arrayUser;
        this.billHandler = new Bill_Handler(context);
    }

    @Override
    public int getCount() {
        return arrayUser.size();
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

        TextView txtId = (TextView) view.findViewById(R.id.txtId);
        TextView txtName = (TextView) view.findViewById(R.id.txtUsername);
        Button btnBill = (Button) view.findViewById(R.id.btnBill);

        User user = arrayUser.get(position);

        txtId.setText(user.getId() + "");
        txtName.setText(user.getUsername());

        btnBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Danh sách hóa đơn");
                ArrayList<Bill> bills = billHandler.getAllBillById(user.getId());
                if (bills.size() > 0){
                    String message = "Mã hóa đơn: " + bills.get(0).getId() + "\n";
                    for (int i = 0; i < bills.size(); i++) {
                        message += "Ngày thanh toán: " + bills.get(i).getDate_created() +
                                "\nTổng tiền(VAT 10%): " + bills.get(i).getTotal_price() + "\n\n";
                    }
                    builder.setMessage(message);
                }else{
                    builder.setMessage("Không có hóa đơn nào!");
                }
                builder.setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }
        });
        return view;
    }
}
