package com.example.lab3map;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class BusinessOwner extends AppCompatActivity {
    TextView tv_businessOwner;
    Button bn_signout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_owner);
        tv_businessOwner = findViewById(R.id.tv_businessOwner);
        bn_signout = findViewById(R.id.bn_bus_signout);
    }
    public void logoutBusinessOwner(View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();
    }
}