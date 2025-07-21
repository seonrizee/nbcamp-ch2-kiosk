package io.github.seonrizee.kiosk.challenge.lv2;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Cart {

    private final Map<String, CartItem> cartItems = new LinkedHashMap<>();

    public void addItem(MenuItem menuItem) {

        String itemName = menuItem.getName();
        CartItem cartItem = cartItems.get(itemName);

        if (cartItem == null) {
            cartItem = new CartItem(menuItem);
            cartItems.put(itemName, cartItem);
            return;
        }

        cartItem.increaseQuantity();
    }

    public void removeItem(MenuItem menuItem) {
        String itemName = menuItem.getName();

        if (cartItems.remove(itemName) == null) {
            System.out.println("장바구니에 없는 메뉴입니다.");
        }

    }

    public void decreaseItem(MenuItem menuItem) {
        String itemName = menuItem.getName();
        CartItem cartItem = cartItems.get(itemName);

        if (cartItem == null) {
            System.out.println("장바구니에 없는 메뉴입니다.");
            return;
        }

        cartItem.decreaseQuantity();
        if (cartItem.getQuantity() == 0) {
            removeItem(menuItem);
        }

    }

    public int getTotalPrice() {
        return cartItems.values()
                .stream()
                .mapToInt(CartItem::getSumItemPrice)
                .sum();

    }

    public void clearCart() {
        cartItems.clear();
    }

    public List<CartItem> getCartItemList() {
        return List.copyOf(cartItems.values());
    }

    public boolean isCartEmpty() {
        return cartItems.isEmpty();
    }
}
