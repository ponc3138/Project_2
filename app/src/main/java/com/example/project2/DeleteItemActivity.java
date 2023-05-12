package com.example.project2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DeleteItemActivity extends AppCompatActivity {

    private List<Product> productList;
    private ArrayAdapter<Product> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_item);

        ListView listView = findViewById(R.id.products_in_inventory);


        new Thread(new Runnable() {
            @Override
            public void run() {
                productList = new ArrayList<>();
                productList = AppDatabase.getDatabase(DeleteItemActivity.this).ProductDao().getAllProducts();
                adapter = new ArrayAdapter<>(DeleteItemActivity.this, android.R.layout.simple_list_item_1, productList);
                listView.setAdapter(adapter);
            }
        }).start();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Product productSelected = productList.get(i);
                AlertDialog.Builder builder = new AlertDialog.Builder(DeleteItemActivity.this);
                builder.setMessage("Are you sure you want to delete " + productSelected.getProductName() + "?")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        AppDatabase db = AppDatabase.getDatabase(getApplicationContext());
                                        ProductDAO productDao = db.ProductDao();
                                        productDao.delete(productSelected);
                                        productList.remove(productSelected);
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                adapter.notifyDataSetChanged();
                                                Toast.makeText(DeleteItemActivity.this, productSelected.getProductName() + " no longer in inventory.", Toast.LENGTH_SHORT).show();
                                            }
                                        });

                                    }
                                }).start();
                            }
                        })
                        .setNegativeButton("Cancel", null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }
}