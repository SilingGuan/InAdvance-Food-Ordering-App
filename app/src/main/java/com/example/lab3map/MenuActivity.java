package com.example.lab3map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {
//    private Button cartButton;
    RecyclerView recycler_cart;
    String s1[],s2[];
    int image[] = {R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image4, R.drawable.image5, R.drawable.image6, R.drawable.image7, R.drawable.image8, R.drawable.image9};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        recycler_cart = findViewById(R.id.recycler_cart);

        s1 = getResources().getStringArray(R.array.food_name);
        s2 = getResources().getStringArray(R.array.food_price);

        CartAdapter cartAdapter = new CartAdapter(this, s1, s2, image);
        recycler_cart.setAdapter(cartAdapter);
        recycler_cart.setLayoutManager(new LinearLayoutManager(recycler_cart.getContext()));
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