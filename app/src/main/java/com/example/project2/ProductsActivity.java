package com.example.project2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ProductsActivity extends AppCompatActivity {

    private List<Product> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        String username = getIntent().getStringExtra("username");

        ListView listView = findViewById(R.id.products_in_inventory);

        new Thread(new Runnable() {
            @Override
            public void run() {
                productList = new ArrayList<>();
                productList = AppDatabase.getDatabase(ProductsActivity.this).ProductDao().getAllProducts();
                ArrayAdapter<Product> adapter = new ArrayAdapter<>(ProductsActivity.this, android.R.layout.simple_list_item_1, productList);
                listView.setAdapter(adapter);
            }
        }).start();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Product productSelected = productList.get(i);
                int productSelectedId = productSelected.getId();
                AlertDialog.Builder builder = new AlertDialog.Builder(ProductsActivity.this);
                builder.setMessage(productSelected.toString())
                        .setPositiveButton("Add to cart", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        AppDatabase db = AppDatabase.getDatabase(getApplicationContext());
                                        ShoppingCartDAO shoppingCartDAO = db.shoppingCartDAO();
                                        Product product = db.ProductDao().getProductById(productSelectedId);
                                        UserDAO userDao = db.userDao();
                                        User currentUser = userDao.getUserByUsername(username);
                                        int currentUserId = currentUser.getId();
                                        if(db.shoppingCartDAO().userExists(currentUserId)) {
                                            ShoppingCart shoppingCartItem = new ShoppingCart(productSelected.getProductName(), productSelected.getProductQuantity(), productSelected.getProductPrice());
                                            shoppingCartDAO.insert(shoppingCartItem);
                                        } else {
                                            return;
                                        }
//                                        ShoppingCart shoppingCartItem = new ShoppingCart(productSelected.getProductName(), productSelected.getProductQuantity(), productSelected.getProductPrice());
//                                        shoppingCartDAO.insert(shoppingCartItem);
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(ProductsActivity.this, productSelected.getProductName() + " added to shopping cart.", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                }).start();
                            }
                        })
                        .setNegativeButton("Return", null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }
}