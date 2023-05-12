package com.example.project2;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ShoppingCartDAO {

//    @Insert
//    void insert(ShoppingCart item);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ShoppingCart item);

    @Update
    void update(ShoppingCart item);

    @Delete
    void delete(ShoppingCart item);

    @Query("SELECT * FROM shopping_cart WHERE user_id = :userId")
    List<ShoppingCart> getAllCartItems(int userId);

    @Query("SELECT EXISTS(SELECT 1 FROM users WHERE id = :userId)")
    boolean userExists(int userId);
}
