package io.github.seonrizee.kiosk.challenge.lv2.domain;

/**
 * 키오스크에서 판매하는 개별 메뉴 항목을 나타내는 클래스.
 */
public class MenuItem {


    private final String name;
    private final int price;
    private final String desc;

    /**
     * 새로운 MenuItem 인스턴스를 생성합니다.
     *
     * @param name  메뉴 항목의 이름 (예: "CHEESEBURGER")
     * @param price 메뉴 항목의 가격 (예: 14900)
     * @param desc  메뉴 항목에 대한 설명
     */
    public MenuItem(String name, int price, String desc) {
        this.name = name;
        this.price = price;
        this.desc = desc;
    }

    /**
     * 메뉴 항목의 이름을 반환합니다.
     *
     * @return 메뉴 이름 (String)
     */
    public String getName() {
        return name;
    }

    /**
     * 메뉴 항목의 가격을 반환합니다.
     *
     * @return 메뉴 가격 (int)
     */
    public int getPrice() {
        return price;
    }

    /**
     * 메뉴 항목의 설명을 반환합니다.
     *
     * @return 메뉴 설명 (String)
     */
    public String getDesc() {
        return desc;
    }
}
