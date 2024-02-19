package com.example.appbanhang.activity.admin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.appbanhang.R;
import com.example.appbanhang.handler.Product_Handler;
import com.example.appbanhang.model.Product;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
public class Activity_EditProduct extends AppCompatActivity {
    int REQUEST_CODE_CAMERA = 1111;
    int REQUEST_CODE_FOLDER = 1112;
    Product_Handler productHandler;
    Product product;
    EditText edtNameProductUD,edtQuantityUD,edtPriceUD,edtIDProductUD;
    ImageButton imgCameraUD , imgFolderUD;
    ImageView imgUD;
    Button btnSaveUD;
    private Bitmap convertToBitmap(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        btnSaveUD = (Button) findViewById(R.id.btnSaveUD);
        edtIDProductUD = (EditText) findViewById(R.id.edtIDProductUD);
        edtNameProductUD = (EditText) findViewById(R.id.edtNameProductUD);
        edtQuantityUD = (EditText) findViewById(R.id.edtQuantityUD);
        edtPriceUD = (EditText) findViewById(R.id.edtPriceUD);
        imgCameraUD = (ImageButton) findViewById(R.id.imgCameraUD);
        imgFolderUD = (ImageButton) findViewById(R.id.imgFolderUD);
        imgUD = (ImageView) findViewById(R.id.imgUD);
        productHandler = new Product_Handler(this);

        int productId = getIntent().getIntExtra("productId", -1);

        product = productHandler.getProductById(productId);
        if (product != null) {
            edtIDProductUD.setText(String.valueOf(product.getId()));
            edtNameProductUD.setText(product.getName());
            edtQuantityUD.setText(String.valueOf(product.getQuantity()));
            edtPriceUD.setText(String.valueOf(product.getPrice()));
            imgUD.setImageBitmap(convertToBitmap(product.getImg()));
        }

        btnSaveUD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int productId = Integer.parseInt(edtIDProductUD.getText().toString().trim());
                String name = edtNameProductUD.getText().toString().trim();
                int quantity = Integer.parseInt(edtQuantityUD.getText().toString().trim());
                int price = Integer.parseInt(edtPriceUD.getText().toString().trim());
                byte[] image = convertToArrayByte(imgUD);
                if(productHandler.editProduct(productId,name,quantity,price,image)){
                    Toast.makeText(Activity_EditProduct.this, "Sửa thành công", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Activity_EditProduct.this , Activity_Product_Manager.class);

                    startActivity(i);
                    finish();
                }
            }
        });
        imgFolderUD.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("deprecation")
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK , MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, REQUEST_CODE_FOLDER);
            }
        });
        imgCameraUD.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("deprecation")
            @Override
            public void onClick(View view) {
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
            imgUD.setImageBitmap(bitmap);
        }
        if (requestCode==REQUEST_CODE_FOLDER&&resultCode==RESULT_OK & data!=null){
            Uri uri = data.getData();
            try {
                InputStream iostream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(iostream);
                imgUD.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                //TODO Auto-generated catch block
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