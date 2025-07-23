package io.github.seonrizee.kiosk.challenge.lv2.domain;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 사용자의 장바구니를 나타내는 클래스로 상품 추가, 제거, 수량 조절, 총액 계산 등의 기능을 제공합니다.
 */
public class Cart {

    /**
     * 장바구니에 담긴 아이템들을 저장하는 맵. Key는 아이템의 이름(String), Value는 {@link CartItem} 객체입니다.
     */
    private final Map<String, CartItem> cartItems = new LinkedHashMap<>();

    /**
     * 장바구니에 메뉴 아이템을 추가합니다.
     *
     * @param menuItem 장바구니에 추가할 {@link MenuItem}
     */
    public void addItem(MenuItem menuItem) {
        String itemName = menuItem.getName();
        cartItems.compute(itemName, (curName, curItem) -> {
            if (curItem == null) {
                return new CartItem(menuItem);
            } else {
                curItem.increaseQuantity();
                return curItem;
            }
        });
    }

    /**
     * 장바구니에서 특정 메뉴 아이템을 완전히 제거합니다.
     *
     * @param menuItem 장바구니에서 제거할 {@link MenuItem}
     * @throws IllegalArgumentException 장바구니에 해당 아이템이 존재하지 않을 경우 발생
     */
    public void removeItem(MenuItem menuItem) {
        String itemName = menuItem.getName();
        if (cartItems.remove(itemName) == null) {
            throw new IllegalArgumentException("장바구니에 해당 아이템이 없습니다: " + itemName);
        }
    }

    /**
     * 장바구니에 담긴 특정 아이템의 수량을 1 감소시킵니다.
     *
     * @param menuItem 수량을 감소시킬 {@link MenuItem}
     * @throws IllegalArgumentException 장바구니에 해당 아이템이 존재하지 않을 경우 발생
     */
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

    /**
     * 장바구니에 담긴 모든 아이템의 총 가격을 계산하여 반환합니다.
     *
     * @return 장바구니의 총 가격 (int)
     */
    public int getCartTotalPrice() {
        return cartItems.values()
                .stream()
                .mapToInt(CartItem::getItemTotalPrice)
                .sum();
    }

    /**
     * 장바구니의 모든 아이템을 비웁니다.
     */
    public void clearCart() {
        cartItems.clear();
    }

    /**
     * 장바구니에 담긴 모든 아이템을 수정 불가능한 리스트로 반환합니다.
     *
     * @return 수정 불가능한 {@link CartItem} 리스트
     */
    public List<CartItem> getCartItems() {
        return List.copyOf(cartItems.values());
    }

    /**
     * 장바구니가 비어있는지 여부를 확인합니다.
     *
     * @return 장바구니가 비어있으면 {@code true}, 그렇지 않으면 {@code false}
     */
    public boolean isCartEmpty() {
        return cartItems.isEmpty();
    }
}