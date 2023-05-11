package com.example.project2;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Product {

    @PrimaryKey(autoGenerate = true)
    public int prodId;

    @ColumnInfo(name = "Product")
    public String Product;

    @ColumnInfo(name = "price")
    public int price;

    @ColumnInfo(name = "Quantity")
    public int Quantity;

    public Product(int prodId, String product, int price, int quantity) {
        this.prodId = prodId;
        this.Product = product;
        this.price = price;
        this.Quantity = quantity;
    }

    public int getProdId() {
        return prodId;
    }

    public void setProdId(int prodId) {
        this.prodId = prodId;
    }

    public String getProduct() {
        return Product;
    }

    public void setProduct(String product) {
        Product = product;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }
}
