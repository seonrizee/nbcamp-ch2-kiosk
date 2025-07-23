package io.github.seonrizee.kiosk.challenge.lv2.domain;

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
        if (this.quantity > 0) {
            this.quantity -= 1;
        } else {
            throw new IllegalStateException("수량은 0보다 작을 수 없습니다.");
        }
    }

    public MenuItem getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getItemTotalPrice() {
        return item.getPrice() * quantity;
    }
}
