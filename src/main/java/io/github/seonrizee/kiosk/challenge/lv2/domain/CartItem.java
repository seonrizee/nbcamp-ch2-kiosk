package io.github.seonrizee.kiosk.challenge.lv2.domain;

/**
 * 장바구니에 담긴 개별 항목을 나타내는 클래스로 수량을 관리합니다.
 */
public class CartItem {

    private final MenuItem item;
    private int quantity;

    /**
     * 지정된 {@link MenuItem}으로 새로운 CartItem 인스턴스를 생성합니다. 초기 수량은 1로 설정됩니다.
     *
     * @param item 장바구니에 추가할 메뉴 아이템
     */
    public CartItem(MenuItem item) {
        this.item = item;
        this.quantity = 1;
    }

    /**
     * 이 아이템의 수량을 1 증가시킵니다.
     */
    public void increaseQuantity() {
        this.quantity += 1;
    }

    /**
     * 이 아이템의 수량을 1 감소시킵니다.
     *
     * @throws IllegalStateException 수량이 0인 상태에서 감소를 시도할 경우 발생합니다.
     */
    public void decreaseQuantity() {
        if (this.quantity > 0) {
            this.quantity -= 1;
        } else {
            throw new IllegalStateException("수량은 0보다 작을 수 없습니다.");
        }
    }

    /**
     * 이 장바구니 항목에 포함된 원본 {@link MenuItem} 객체를 반환합니다.
     *
     * @return 메뉴 아이템 객체
     */
    public MenuItem getItem() {
        return item;
    }

    /**
     * 현재 장바구니에 담긴 이 아이템의 수량을 반환합니다.
     *
     * @return 아이템 수량 (int)
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * 이 아이템의 총 가격(단가 * 수량)을 계산하여 반환합니다.
     *
     * @return 이 아이템의 총 가격 (int)
     */
    public int getItemTotalPrice() {
        return item.getPrice() * quantity;
    }
}