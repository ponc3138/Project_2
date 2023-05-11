package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.IpSecManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class New_account_activity extends AppCompatActivity {

    Button createUserButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);

        createUserButton = findViewById(R.id.create_account_page_button);
        createUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText usernameEditText = findViewById(R.id.username);
                EditText passwordEditText = findViewById(R.id.password);
                EditText retypePasswordEditText = findViewById(R.id.retype_password);
                final String username = usernameEditText.getText().toString();
                final String password = passwordEditText.getText().toString();
                final String retypedPassword = retypePasswordEditText.getText().toString();

                //TODO: UPDATE CODE
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        AppDatabase db = AppDatabase.getDatabase(getApplicationContext());
                        UserDAO userDao = db.userDao();
                        final User existingUser = userDao.getUserByUsername(username);

                        if (existingUser != null) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(New_account_activity.this, "Username already in use", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else if (!password.equals(retypedPassword)) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(New_account_activity.this, "Passwords don't match", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            User newUser = new User(username, password);
                            userDao.insert(newUser);
                            Runnable startMainActivity = new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(New_account_activity.this, "User created successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(New_account_activity.this, MainActivity.class);
                                    startActivity(intent);
                                }
                            };
                            runOnUiThread(startMainActivity);
                        }
                    }
                }).start();
            }
        });
    }
}

