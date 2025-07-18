package io.github.seonrizee.kiosk.challenge.lv1;

import java.util.LinkedHashMap;
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

    public void removeItem(MenuItem menuItem) {
        cartItems.remove(menuItem.getName());
    }

//    public int updateItemQuantity(MenuItem menuItem, int quantityDifference) {
//        String itemName = menuItem.getName();
//        if (!cartItems.containsKey(itemName)) {
//            // TODO
//            System.out.println("카트에 존재하지 않는 상품입니다.");
//        }
//        CartItem cartItem = cartItems.get(itemName);
//        cartItem.setQuantity(cartItem.getQuantity() + quantityDifference);
//        return cartItems.get(itemName).getQuantity();
//    }


    public int getSumCartPrice() {
        sumCartPrice = cartItems.values()
                .stream()
                .mapToInt(CartItem::getTotalPrice)
                .sum();
        return sumCartPrice;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "cartItems=" + cartItems +
                ", sumCartPrice=" + sumCartPrice +
                '}';
    }
}
