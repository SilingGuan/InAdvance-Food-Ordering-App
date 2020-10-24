package com.example.lab3map;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.common.reflect.TypeToken;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.Calendar;

public class QR_CodeActivity extends AppCompatActivity {

    public final static int QRCodeWidth = 500;
    Bitmap bitmap;
    private EditText text;
    private Button download,generate,return_home,share_qr;
    private ImageView iv;
    FirebaseAuth mAuth;
    FirebaseFirestore firestore;
    StorageReference storageReference;
    String orderDetail;
    DocumentReference documentReference;
    String userID;
    String orderID;
    final String TAG ="ABC";
    String price;
    String restaurantName;
    String restaurantAddress;
    String orderList;
    String priceWithSign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q_r__code);

        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        text = findViewById(R.id.text);
        download = findViewById(R.id.download);
        download.setVisibility(View.INVISIBLE);
        generate = findViewById(R.id.generate);
        iv = findViewById(R.id.image);
        return_home = findViewById(R.id.qr_return_home);
        return_home.setVisibility(View.INVISIBLE);
        share_qr = findViewById(R.id.qr_share);
        share_qr.setVisibility(View.INVISIBLE);

        displayOrder();
        saveOrderToDatabase();


        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (orderID.length() == 0){
                    Toast.makeText(QR_CodeActivity.this, "Enter Text", Toast.LENGTH_SHORT).show();
                }
                else{
                    try{
                        bitmap = textToImageEncode(orderID);
                        iv.setImageBitmap(bitmap);
                        generate.setVisibility(View.INVISIBLE);
                        download.setVisibility(View.VISIBLE);
                        download.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "code_scanner"
                                        , null);
                                Toast.makeText(QR_CodeActivity.this, "Saved to Gralary", Toast.LENGTH_SHORT)
                                        .show();
                            }
                        });

                        // return to home page
                        return_home.setVisibility(View.VISIBLE);
                        return_home.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent;
                                intent = new Intent(QR_CodeActivity.this,SecondActivity.class);
                                startActivity(intent);
                            }
                        });

                        //share qr code
                        share_qr.setVisibility(View.VISIBLE);
                        share_qr.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                shareQR_Code();
                            }
                        });

                    }catch (WriterException e){
                        e.printStackTrace();
                    }
                }
            }
        });
    }


    private Bitmap textToImageEncode(String value) throws WriterException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(value,
                    BarcodeFormat.DATA_MATRIX.QR_CODE, QRCodeWidth, QRCodeWidth, null);
        } catch (IllegalArgumentException e) {
            return null;
        }

        int bitMatrixWidth = bitMatrix.getWidth();
        int bitMatrixHeight = bitMatrix.getHeight();
        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offSet = y * bitMatrixWidth;
            for (int x = 0; x < bitMatrixWidth; x++) {
                pixels[offSet + x] = bitMatrix.get(x, y) ?
                        getResources().getColor(R.color.black) : getResources().getColor(R.color.white);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);
        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }

    // share QR Code
    private void shareQR_Code() {

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        BitmapDrawable drawable = (BitmapDrawable) iv.getDrawable();
        bitmap = drawable.getBitmap();
        File file = new File(getExternalCacheDir()+"/" +getResources().getString(R.string.app_name)+".png");
        Intent shareInt;

        try{
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG,100,fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            shareInt = new Intent(Intent.ACTION_SEND);
            shareInt.setType("image/*");
            shareInt.putExtra(Intent.EXTRA_STREAM,Uri.fromFile(file));
            shareInt.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        startActivity(Intent.createChooser(shareInt,"Share QR Code via"));
    }

    //display order detail
    private void displayOrder(){
        Gson gson = new Gson();
        SharedPreferences sharedPreferences = this.getSharedPreferences("com.example.lab3map", MODE_PRIVATE);
        price = sharedPreferences.getString("priceTotal","");
        priceWithSign = "$ " + price;
        restaurantName = sharedPreferences.getString("restaurantname","");
        restaurantAddress = sharedPreferences.getString("restaurantaddress","");
        orderList = "Orders: \n";
        String storedHashMapString = sharedPreferences.getString("hashString", "");
        java.lang.reflect.Type type = new TypeToken<LinkedHashMap<String, String>>(){}.getType();
        LinkedHashMap<String, String> HashMap2 = gson.fromJson(storedHashMapString, type);
        TextView textView = (TextView)findViewById(R.id.tv);
//        TextView textView1 = (TextView)findViewById(R.id.tv1);
//        TextView textView2 = (TextView)findViewById(R.id.tv2);
        Set<String> keys = HashMap2.keySet();
        int i = 1;
        for(String key: keys){
            orderList+= " " + i +". Food Name: "+ key + "       Food Amount: " + HashMap2.get(key)+";\n";
            i++;
        }
        orderDetail = "Total: $"+price+"\n"+"\n"+"Restaurant: "+restaurantName+"\nAddress: "+restaurantAddress+"\n"+"\n"+orderList;
        textView.setText(orderDetail);
    }

    // upload order detail to database
    private void saveOrderToDatabase(){

        userID = mAuth.getCurrentUser().getUid();
        documentReference = firestore.collection("orders").document();
        Date currentTime = Calendar.getInstance().getTime();

        Map<String,Object> order = new HashMap<>();
        order.put("userId",userID);
        order.put("orderTime",currentTime);
        order.put("priceTotal",priceWithSign);
        order.put("restaurantName",restaurantName);
        order.put("restaurantAddress",restaurantAddress);
        order.put("orderList",orderList);
        documentReference.set(order).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "onSuccess: order info is saved" + orderID);
            }
        });
        orderID = firestore.collection("orders").document().getId();
        Log.d(TAG,"ORDER ID: " + orderID);
        Toast.makeText(getApplicationContext(),"Saved the Order info",Toast.LENGTH_SHORT).show();
    }

}