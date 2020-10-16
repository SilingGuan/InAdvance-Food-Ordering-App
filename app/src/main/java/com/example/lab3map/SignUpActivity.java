package com.example.lab3map;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {

    private EditText username1, email1, password1, password2;
    private Button signupbutton;
    private TextView userlogin;
    private database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setupUIViews2();
        getSupportActionBar().setTitle("Sign Up Page");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        signupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username1.getText().toString().trim();
                String email = email1.getText().toString().trim();
                String pwd = password1.getText().toString().trim();
                String pwd1 = password2.getText().toString().trim();

                if(pwd.equals(pwd1)){
                    long val = db.newUser(user, email, pwd);
                    if(val > 0){
                        Toast.makeText(SignUpActivity.this,"Successfully Signed up", Toast.LENGTH_SHORT).show();
                        Intent moveToLogin = new Intent(SignUpActivity.this, MainActivity.class);
                        startActivity(moveToLogin);
                    }else{
                        Toast.makeText(SignUpActivity.this,"Sign Up Error", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(SignUpActivity.this,"Password Not matching", Toast.LENGTH_SHORT).show();
                }

            }
        });

        userlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setupUIViews2(){
        db = new database(this);
        username1 = (EditText) findViewById(R.id.susername);
        email1 = (EditText) findViewById(R.id.semail);
        password1 = (EditText) findViewById(R.id.spassword1);
        password2 = (EditText) findViewById(R.id.spassword2);
        signupbutton = (Button) findViewById(R.id.bsignup);
        userlogin = (TextView) findViewById(R.id.tvlogin);
    }


}