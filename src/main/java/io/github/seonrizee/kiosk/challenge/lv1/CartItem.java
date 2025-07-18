package io.github.seonrizee.kiosk.challenge.lv1;

public class CartItem {

    private final MenuItem item;
    private int quantity;
    private int sumItemPrice;

    public CartItem(MenuItem item) {
        this.item = item;
        this.quantity = 1;
        this.sumItemPrice = item.getPrice();
    }

    public void increaseQuantity() {
        this.quantity += 1;
        this.sumItemPrice += item.getPrice();
    }

    public void decreaseQuantity() {
        this.quantity -= 1;
        this.sumItemPrice -= item.getPrice();
    }

    public int getTotalPrice() {
        return sumItemPrice;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "item=" + item +
                ", quantity=" + quantity +
                ", totalPrice=" + sumItemPrice +
                '}';
    }
}
