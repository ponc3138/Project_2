package com.example.project2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ThemedSpinnerAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class RemoveUserActivity extends AppCompatActivity {

    private List<User> userList;
    private ArrayAdapter<User> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_user);

        ListView listView = findViewById(R.id.users_in_database);

        new Thread(new Runnable() {
            @Override
            public void run() {
                userList = new ArrayList<>();
                userList = AppDatabase.getDatabase(RemoveUserActivity.this).userDao().getAllUsers();
                adapter = new ArrayAdapter<>(RemoveUserActivity.this, android.R.layout.simple_list_item_1, userList);
                listView.setAdapter(adapter);
            }
        }).start();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                User userSelected = userList.get(i);
                AlertDialog.Builder builder = new AlertDialog.Builder(RemoveUserActivity.this);
                builder.setMessage("Are you sure you want to remove " + userSelected.getUsername() + " from the database?")
                        .setPositiveButton("Remove", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        AppDatabase db = AppDatabase.getDatabase(getApplicationContext());
                                        UserDAO userDao = db.userDao();
                                        userDao.delete(userSelected);
                                        userList.remove(userSelected);

                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                adapter.notifyDataSetChanged();
                                                Toast.makeText(RemoveUserActivity.this, userSelected.getUsername() + " no longer in database.", Toast.LENGTH_SHORT).show();
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