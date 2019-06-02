package com.example.client.entitymodels.product;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Product implements Serializable {

    @SerializedName("ProductId")
    private long productId;

    @SerializedName("ProductName")
    public String productName;

    @SerializedName("ManufacturerName")
    public String manufacturerName;

    @SerializedName("CategoryName")
    public String categoryName;

    @SerializedName("Price")
    public float price;

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    @SerializedName("Quantity")
    public int Quantity;


    public Product() {

        productId=1;
       productName="productTest";
        manufacturerName="manufacturerTest";
        categoryName="cateogoryTest";
         price=1;
    }


    public Product(long productId, String productName, String manufacturerName, String categoryName, float price) {
        this.productId = productId;
        this.productName = productName;
        this.manufacturerName = manufacturerName;
        this.categoryName = categoryName;
        this.price = price;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", manufacturerName='" + manufacturerName + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", price=" + price +
                '}';
    }
}
