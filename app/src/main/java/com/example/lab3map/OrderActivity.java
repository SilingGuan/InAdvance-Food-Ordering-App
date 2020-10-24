package com.example.lab3map;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class OrderActivity extends AppCompatActivity {
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Button generate = (Button) findViewById(R.id.bt_generate);

        setContentView(R.layout.activity_order);

        SharedPreferences sharedPreferences = this.getSharedPreferences("com.example.lab3map", MODE_PRIVATE);
        String text = sharedPreferences.getString("priceTotal","");
        String text1 = sharedPreferences.getString("restaurantname","");
        String text2 = sharedPreferences.getString("restaurantaddress","");
        String text3 = sharedPreferences.getString("itemname","");
        TextView textView = (TextView)findViewById(R.id.tv);
        TextView textView1 = (TextView)findViewById(R.id.tv1);
        TextView textView2 = (TextView)findViewById(R.id.tv2);
        textView.setText("Total: $"+text);
        textView1.setText("Restaurant: "+text1+"\nAddress: "+text2);
        textView2.setText("Orders: \n"+ text3 +"       " +"x");


//        generate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(OrderActivity.this, MainActivity.class);
//                startActivity(intent,savedInstanceState);
//            }
//        });
    }
}

