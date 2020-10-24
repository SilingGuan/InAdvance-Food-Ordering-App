package com.example.lab3map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

public class MenuActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    private Button check_out;
    RecyclerView recycler_cart;
    TextView price_total;
    String s1[],s2[],number;
    int image[] = {R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image4, R.drawable.image5, R.drawable.image6, R.drawable.image7, R.drawable.image8, R.drawable.image9};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        recycler_cart = findViewById(R.id.recycler_cart);
        price_total = (TextView) findViewById(R.id.price_total);
        check_out = (Button) findViewById(R.id.btn_place_order);
        sharedPreferences = this.getSharedPreferences("com.example.lab3map", Context.MODE_PRIVATE);

        s1 = getResources().getStringArray(R.array.food_name);
        s2 = getResources().getStringArray(R.array.food_price);


        CartAdapter cartAdapter = new CartAdapter(this, s1, s2, image, number);
        recycler_cart.setAdapter(cartAdapter);
        recycler_cart.setLayoutManager(new LinearLayoutManager(recycler_cart.getContext()));
        price_total.setText("0");


        check_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPreferences.edit().putString("priceTotal",price_total.getText().toString()).apply();
                Intent intent = new Intent(MenuActivity.this, OrderActivity.class);
                startActivity(intent);
            }
        });
//        getSupportActionBar().setTitle("Sign Up Page");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        cartButton = (Button) findViewById(R.id.cartButton);
//        cartButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MenuActivity.this, SecondActivity.class);
//                startActivity(intent);
//            }
//        });
    }


}