package com.example.project2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class UpdateItemActivity extends AppCompatActivity {

    private List<Product> productList;
    private ArrayAdapter<Product> adapter;

    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_item);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        ListView listView = findViewById(R.id.products_in_inventory);


        new Thread(new Runnable() {
            @Override
            public void run() {
                productList = new ArrayList<>();
                productList = AppDatabase.getDatabase(UpdateItemActivity.this).ProductDao().getAllProducts();
                adapter = new ArrayAdapter<>(UpdateItemActivity.this, android.R.layout.simple_list_item_1, productList);
                listView.setAdapter(adapter);
            }
        }).start();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Product productSelected = productList.get(i);
                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateItemActivity.this);
                builder.setMessage("Are you sure you want to Update " + productSelected.getProductName() + "?")
                        .setPositiveButton("Update", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialog = new Dialog(UpdateItemActivity.this);

                                dialog.setContentView(R.layout.add_item_dialog);

                                EditText editTextProductName = dialog.findViewById(R.id.edit_text_name);
                                EditText editTextPrice = dialog.findViewById(R.id.edit_text_price);
                                EditText editTextQuantity = dialog.findViewById(R.id.edit_text_quantity);
                                EditText editTextDetails = dialog.findViewById(R.id.edit_text_details);

                                String productName = editTextProductName.getText().toString();
                                String priceString = editTextPrice.getText().toString();
                                final Double price;
                                try {
                                    price = Double.parseDouble(priceString);
                                } catch (NumberFormatException e) {
                                    return;
                                }
                                String quantityString = editTextQuantity.getText().toString();
                                final int quantity;
                                try {
                                    quantity = Integer.parseInt(quantityString);
                                } catch(NumberFormatException e) {
                                    return;
                                }
                                String details = editTextDetails.getText().toString();

                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        AppDatabase db = AppDatabase.getDatabase(getApplicationContext());
                                        ProductDAO productDao = db.ProductDao();

                                        final Product existingProduct = productDao.getProductByName(productName);
                                        if(existingProduct != null) {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Toast.makeText(UpdateItemActivity.this, productName + " already in inventory! Update quantity instead.", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        } else {
                                            Product newProduct = new Product(productName, price, quantity, details);
                                            productDao.insert(newProduct);
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Toast.makeText(UpdateItemActivity.this, productName + " added successfully!", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }
                                    }
                                }).start();
                                dialog.dismiss();
                            }
                        });
                dialog.show();
            }
        });
    }
}

