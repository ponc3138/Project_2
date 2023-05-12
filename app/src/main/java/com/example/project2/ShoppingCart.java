package com.example.project2;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

//@Entity(tableName = "shopping_cart",
//        foreignKeys = @ForeignKey(entity = User.class,
//                parentColumns = "id",
//                childColumns = "user_id",
//                onDelete = ForeignKey.CASCADE))
@Entity(tableName = "shopping_cart")
public class ShoppingCart {

    @PrimaryKey(autoGenerate = true)
    private int id;
//    @ColumnInfo(name = "product_name")
    private String name;
    private int quantity;
    private double price;

//    @ColumnInfo(name = "user_id")
//    private int userId;

    public ShoppingCart(String name, int quantity, double price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name + "  " + "   $" + price;
    }

//    public int getUserId() {
//        return userId;
//    }
//
//    public void setUserId(int userId) {
//        this.userId = userId;
//    }


}
