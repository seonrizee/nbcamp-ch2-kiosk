package io.github.seonrizee.kiosk.challenge.lv2.domain;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Cart {

    private final Map<String, CartItem> cartItems = new LinkedHashMap<>();

    public void addItem(MenuItem menuItem) {

        String itemName = menuItem.getName();
        CartItem cartItem = cartItems.computeIfAbsent(itemName, name -> new CartItem(menuItem));
        cartItem.increaseQuantity();
    }

    public void removeItem(MenuItem menuItem) {
        String itemName = menuItem.getName();
        if (cartItems.remove(itemName) == null) {
            throw new IllegalArgumentException("장바구니에 해당 아이템이 없습니다: " + itemName);
        }
    }

    public void decreaseItem(MenuItem menuItem) {
        String itemName = menuItem.getName();
        CartItem cartItem = cartItems.get(itemName);
        if (cartItem == null) {
            throw new IllegalArgumentException("장바구니에 해당 아이템이 없습니다: " + itemName);
        }

        cartItem.decreaseQuantity();
        if (cartItem.getQuantity() == 0) {
            removeItem(menuItem);
        }
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
