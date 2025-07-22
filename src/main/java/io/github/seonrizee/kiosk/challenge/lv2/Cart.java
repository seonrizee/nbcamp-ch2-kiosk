package io.github.seonrizee.kiosk.challenge.lv2;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Cart {

    private final Map<String, CartItem> cartItems = new LinkedHashMap<>();

    public boolean addItem(MenuItem menuItem) {

        String itemName = menuItem.getName();
        CartItem cartItem = cartItems.get(itemName);

        if (cartItem == null) {
            cartItem = new CartItem(menuItem);
            cartItems.put(itemName, cartItem);
            return true;
        }

        cartItem.increaseQuantity();
        return true;
    }

    public boolean removeItem(MenuItem menuItem) {
        String itemName = menuItem.getName();
        return cartItems.remove(itemName) != null;
    }

    public boolean decreaseItem(MenuItem menuItem) {
        String itemName = menuItem.getName();
        CartItem cartItem = cartItems.get(itemName);

        cartItem.decreaseQuantity();
        if (cartItem.getQuantity() == 0) {
            removeItem(menuItem);
        }

        return true;
    }

    public int getCartTotalPrice() {
        return cartItems.values()
                .stream()
                .mapToInt(CartItem::getItemTotalPrice)
                .sum();

    }

    public void clearCart() {
        cartItems.clear();
    }

    public List<CartItem> getCartItems() {
        return List.copyOf(cartItems.values());
    }

    public boolean isCartEmpty() {
        return cartItems.isEmpty();
    }
}
