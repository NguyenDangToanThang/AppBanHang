package com.example.appbanhang.activity.admin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.appbanhang.R;
import com.example.appbanhang.handler.Product_Handler;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class Activity_AddProduct extends AppCompatActivity {

    private int REQUEST_CODE_CAMERA = 1111;
    private int REQUEST_CODE_FOLDER = 1112;
    private Product_Handler productHandler;
    private EditText edtNameProduct,edtQuantity,edtPrice;
    private ImageButton imgCamera , imgFolder;
    private ImageView img;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        btnSave = (Button) findViewById(R.id.btnSave);
        edtNameProduct = (EditText) findViewById(R.id.edtNameProduct);
        edtQuantity = (EditText) findViewById(R.id.edtQuantity);
        edtPrice = (EditText) findViewById(R.id.edtPrice);
        imgCamera = (ImageButton) findViewById(R.id.imgCamera);
        imgFolder = (ImageButton) findViewById(R.id.imgFolder);
        img = (ImageView) findViewById(R.id.img);
        productHandler = new Product_Handler(this);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtNameProduct.getText().toString().trim();
                int quantity = Integer.parseInt(edtQuantity.getText().toString().trim());
                int price = Integer.parseInt(edtPrice.getText().toString().trim());
                byte[] image = convertToArrayByte(img);
                if(productHandler.createProduct(name,quantity,price,image)){
                    Toast.makeText(Activity_AddProduct.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Activity_AddProduct.this , Activity_Product_Manager.class);
                    startActivity(i);
                    finish();
                }
            }
        });

        imgFolder.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("deprecation")
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK , android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, REQUEST_CODE_FOLDER);
            }
        });

        imgCamera.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("deprecation")
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i, REQUEST_CODE_CAMERA);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUEST_CODE_CAMERA&&resultCode==RESULT_OK & data!=null){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            img.setImageBitmap(bitmap);
        }
        if (requestCode==REQUEST_CODE_FOLDER&&resultCode==RESULT_OK & data!=null){
            Uri uri = data.getData();
            try {
                InputStream iostream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(iostream);
                img.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public byte[] convertToArrayByte(ImageView img){
        BitmapDrawable bitmapDrawable = (BitmapDrawable) img.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }
}