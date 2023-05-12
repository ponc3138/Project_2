package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartActivity extends AppCompatActivity {

    private List<Product> cartItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoppingcart);

        ListView listView = findViewById(R.id.cart_items);

        Button checkoutButton = findViewById(R.id.checkout);

        cartItems = new ArrayList<>();

        cartItems.add(new Product("Product 1", 9.99,5,"Details"));
        cartItems.add(new Product("Product 2", 8.99,4,"Details"));
        cartItems.add(new Product("Product 2", 7.99,3,"Details"));

        ArrayAdapter<Product> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, cartItems);
        listView.setAdapter(adapter);

        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShoppingCartActivity.this, CheckoutActivity.class);
                startActivity(intent);
            }
        });




    }
}