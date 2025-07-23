package io.github.seonrizee.kiosk.challenge.lv2.domain;

import java.util.List;

/**
 * 키오스크의 메뉴 카테고리와 상세 메뉴 아이템를 가지는 클래스.
 */
public class Menu {

    private final String category;
    private final List<MenuItem> menuItems;

    /**
     * 새로운 Menu 인스턴스를 생성합니다.
     *
     * @param category  메뉴의 카테고리 이름 (예: "BURGERS")
     * @param menuItems 이 카테고리에 속하는 {@link MenuItem}의 리스트
     */
    public Menu(String category, List<MenuItem> menuItems) {
        this.category = category;
        this.menuItems = menuItems;
    }

    /**
     * 메뉴의 카테고리 이름을 반환합니다.
     *
     * @return 카테고리 이름 (String)
     */
    public String getCategory() {
        return category;
    }

    /**
     * 이 메뉴에 속한 메뉴 아이템들의 수정 불가능한 리스트를 반환합니다.
     *
     * @return 수정 불가능한 {@link MenuItem} 리스트
     */
    public List<MenuItem> getMenuItems() {
        return List.copyOf(menuItems);
    }
}
