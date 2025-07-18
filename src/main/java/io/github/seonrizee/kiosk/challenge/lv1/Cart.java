package io.github.seonrizee.kiosk.challenge.lv1;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Cart {

    private final Map<String, CartItem> cartItems = new LinkedHashMap<>();
    private int sumCartPrice;


    public void addItem(MenuItem menuItem) {

        String itemName = menuItem.getName();
        CartItem cartItem = cartItems.get(itemName);
        if (cartItem != null) {
            cartItem.increaseQuantity();
        } else {
            cartItem = new CartItem(menuItem);
            cartItems.put(itemName, cartItem);
        }

    }

    public int getSumCartPrice() {
        sumCartPrice = cartItems.values()
                .stream()
                .mapToInt(CartItem::getSumItemPrice)
                .sum();
        return sumCartPrice;
    }

    public void clearCart() {
        cartItems.clear();
    }


    public List<CartItem> getCartItemList() {
        return List.copyOf(cartItems.values().stream().toList());
    }

    public boolean isCartEmpty() {
        return cartItems.isEmpty();
    }

    @Override
    public String toString() {
        return "Cart{" +
                "cartItems=" + cartItems +
                ", sumCartPrice=" + sumCartPrice +
                '}';
    }
}
