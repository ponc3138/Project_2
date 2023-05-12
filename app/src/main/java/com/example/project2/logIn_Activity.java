package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class logIn_Activity extends AppCompatActivity {

    Button adminButton;
    Button signOutButton;
    Button productsButton;

    Button checkoutButton;

    Button shoppingCartButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_acitivity);

        adminButton = findViewById(R.id.admin);
        signOutButton = findViewById(R.id.sign_out);
        productsButton = findViewById(R.id.products);
//        searchButton = findViewById(R.id.search_message);
        checkoutButton = findViewById(R.id.checkout);
        shoppingCartButton = findViewById(R.id.shopping_cart);


        boolean isAdmin = getIntent().getBooleanExtra("isAdmin", true);
        if(!isAdmin) {
            adminButton.setVisibility(View.GONE);
        }
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Code to execute when button is clicked
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

//        searchButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Code to execute when button is clicked
//                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
//                startActivity(intent);
//            }
//        });
        productsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Code to execute when button is clicked
                Intent intent = new Intent(getApplicationContext(), ProductsActivity.class);
                startActivity(intent);
            }
        });

        adminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Code to execute when button is clicked
                Intent intent = new Intent(getApplicationContext(), AdminActivity.class);
                startActivity(intent);
            }
        });
            checkoutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), CheckoutActivity.class);
                    startActivity(intent);
                }
            });

        shoppingCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ShoppingCartActivity.class);
                startActivity(intent);
            }
        });
    }
}