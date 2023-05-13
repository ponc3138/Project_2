package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartActivity extends AppCompatActivity {

    List<ShoppingCart> cartItems;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoppingcart);

        SharedPreferences prefs = getSharedPreferences("MyApp", MODE_PRIVATE);
        userId = prefs.getString("UserId", "defaultUserId");

//        cartItems = getCartItemsDb(userId);

        ListView listView = findViewById(R.id.cart_items);

        Button checkoutButton = findViewById(R.id.checkout);
        Button returnButton = findViewById(R.id.return_button);

        String username = getIntent().getStringExtra("username");

        new Thread(new Runnable() {
            @Override
            public void run() {
                AppDatabase db = AppDatabase.getDatabase(getApplicationContext());
                UserDAO userDao = db.userDao();
                User currentUser = userDao.getUserByUsername(username);
                int currentUserId = currentUser.getId();
//                cartItems = db.shoppingCartDAO().getAllCartItems(currentUserId);
                cartItems = db.shoppingCartDAO().getAllCartItems();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ArrayAdapter<ShoppingCart> adapter = new ArrayAdapter<>(ShoppingCartActivity.this, android.R.layout.simple_list_item_1, cartItems);
                        listView.setAdapter(adapter);
                    }
                });
            }
        }).start();

//        cartItems.add(new Product("Product 1", 9.99,5,"Details"));
//        cartItems.add(new Product("Product 2", 8.99,4,"Details"));
//        cartItems.add(new Product("Product 2", 7.99,3,"Details"));



        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShoppingCartActivity.this, CheckoutActivity.class);
                startActivity(intent);
            }
        });

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

//    private ArrayList<ShoppingCart> getCartItemsDb(String userId){
//
//    }
}