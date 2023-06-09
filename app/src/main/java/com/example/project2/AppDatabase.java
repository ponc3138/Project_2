package com.example.project2;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.Executors;

@Database(entities = {User.class, Product.class, ShoppingCart.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDAO userDao();

    public abstract ProductDAO ProductDao();

    public abstract ShoppingCartDAO shoppingCartDAO();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "my-database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback() {
                @Override
                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                    super.onCreate(db);
                    Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {

                        @Override
                        public void run() {
                            // Predefined users
                            User testUser = new User("testuser1", "testuser1");
                            User adminUser = new User("admin2", "admin2");

                            // Insert users
                            INSTANCE.userDao().insert(testUser);
                            INSTANCE.userDao().insert(adminUser);

                            //Predefined product
                            Product product = new Product("Apple TV", 100, 1, "10 inch TV from Apple");

                            //Insert Product
                            INSTANCE.ProductDao().insert(product);
                        }
                    });
                }
            };
}
