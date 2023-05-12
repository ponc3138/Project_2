package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SearchActivity extends AppCompatActivity {

    Button searchButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchButton = findViewById(R.id.search_message);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText searchEditText = findViewById(R.id.search_bar);
                final String productSearched = searchEditText.getText().toString();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        AppDatabase db = AppDatabase.getDatabase(getApplicationContext());
                        ProductDAO productDao = db.ProductDao();
                        final Product product = productDao.getProductByName(productSearched);

                        //TODO: SHOW PRODUCT DETAILS ON SCREEN
                        if(product != null) {

                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(SearchActivity.this, productSearched + " not in inventory!", Toast.LENGTH_SHORT).show();

                                }
                            });
                        }

                    }
                }).start();
            }
        });

    }
}