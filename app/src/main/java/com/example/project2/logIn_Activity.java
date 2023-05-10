package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class logIn_Activity extends AppCompatActivity {

    Button adminButton;
    Button signOutButton;
    Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_acitivity);

        adminButton = findViewById(R.id.admin);
        signOutButton = findViewById(R.id.sign_out);
        searchButton = findViewById(R.id.search_message);

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

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Code to execute when button is clicked
                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(intent);
            }
        });
    }
}