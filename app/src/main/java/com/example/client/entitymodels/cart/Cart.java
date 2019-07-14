package com.example.client.entitymodels.cart;

import java.util.Date;

public class Cart {
    private int CartId;
    private Date PurchaseDate;

    public Cart(int cartId, Date purchaseDate) {
        CartId = cartId;
        PurchaseDate = purchaseDate;
    }

    public int getCartId() {
        return CartId;
    }

    public void setCartId(int cartId) {
        CartId = cartId;
    }

    public Date getPurchaseDate() {
        return PurchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        PurchaseDate = purchaseDate;
    }
}
