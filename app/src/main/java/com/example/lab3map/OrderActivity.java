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
    private Button generate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        generate = (Button) findViewById(R.id.bt_generate);

        setContentView(R.layout.activity_order);

        SharedPreferences sharedPreferences = this.getSharedPreferences("com.example.lab3map", MODE_PRIVATE);
        String text = sharedPreferences.getString("priceTotal","");
        TextView textView = (TextView)findViewById(R.id.tv);
        textView.setText("Your Order: $"+text);


//        generate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(OrderActivity.this, SecondActivity.class);
//                startActivity(intent);
//            }
//        });
    }
}

