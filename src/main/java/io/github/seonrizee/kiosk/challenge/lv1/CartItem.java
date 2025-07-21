package io.github.seonrizee.kiosk.challenge.lv1;

public class CartItem {

    private final MenuItem item;
    private int quantity;

    public CartItem(MenuItem item) {
        this.item = item;
        this.quantity = 1;
    }

    public void increaseQuantity() {
        this.quantity += 1;
    }

    public void decreaseQuantity() {
        this.quantity -= 1;
    }

    public MenuItem getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getSumItemPrice() {
        return item.getPrice() * quantity;
    }
}
