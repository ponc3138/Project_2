package com.example.project2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AdminActivity extends AppCompatActivity {

    Button addItem;

    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        addItem = findViewById(R.id.add_item_button);

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // create a new Dialog object
                dialog = new Dialog(AdminActivity.this);

                dialog.setContentView(R.layout.add_item_dialog);

                EditText editTextProductName = dialog.findViewById(R.id.edit_text_name);
                EditText editTextPrice = dialog.findViewById(R.id.edit_text_price);
                EditText editTextQuantity = dialog.findViewById(R.id.edit_text_quantity);
                EditText editTextDetails = dialog.findViewById(R.id.edit_text_details);

                Button buttonCancel = dialog.findViewById(R.id.button_cancel);
                Button buttonAdd = dialog.findViewById(R.id.button_add);

                buttonCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // dismiss the dialog if the Cancel button is clicked
                        dialog.dismiss();
                    }
                });

                // set an OnClickListener on the Add button
                buttonAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // get the text entered by the user in the EditText views
                        String productName = editTextProductName.getText().toString();
                        String price = editTextPrice.getText().toString();
                        String quantity = editTextQuantity.getText().toString();
                        String details = editTextDetails.getText().toString();

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                AppDatabase db = AppDatabase.getDatabase(getApplicationContext());
                                ProductDao productDao = db.ProductDao();

                                final Product existingProduct = productDao.getProductByName(productName);
                                if(existingProduct != null) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(AdminActivity.this, productName + " already in inventory! Update quantity instead.", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                } else {
                                    Product newProduct = new Product(productName, price, quantity, details);
                                    productDao.insert(newProduct);

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(AdminActivity.this, productName + " added successfully!", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                }

                            }
                        });
                        // dismiss the dialog after the user has added the item
                        dialog.dismiss();
                    }
                });
                // show the dialog
                dialog.show();
            }
        });
    }
}