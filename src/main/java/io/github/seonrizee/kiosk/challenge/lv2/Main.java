package io.github.seonrizee.kiosk.challenge.lv2;

import io.github.seonrizee.kiosk.challenge.lv2.application.Kiosk;
import io.github.seonrizee.kiosk.challenge.lv2.domain.Menu;
import io.github.seonrizee.kiosk.challenge.lv2.domain.MenuItem;
import java.util.List;

public class Main {

    /**
     * Kiosk 프로젝트의 메인 메서드.
     *
     * @param
     */
    public static void main(String[] args) {

        List<Menu> menuList = setupMenuList();
        Kiosk kiosk = new Kiosk(menuList);
        kiosk.start();
    }

    /**
     * 키오스크에서 사용할 메뉴와 메뉴 아이템 목록을 초기화하고 설정합니다.
     *
     * @return 초기화된 {@link Menu} 객체의 리스트
     */
    private static List<Menu> setupMenuList() {
        Menu burgerMenu = new Menu("BURGERS", List.of(
                new MenuItem("HAMBURGER", 13400, "부드러운 참깨 번 사이에 갓 구운 신선한 수제 패티 2장을 얹어 제공합니다."),
                new MenuItem("CHEESEBURGER", 14900, "부드러운 참깨 번 사이에 아메리칸 스타일 치즈를 녹여 넣은 수제 패티 2장을 얹어 제공합니다."),
                new MenuItem("BACON BURGER", 15900, "부드러운 참깨 번 사이에 바삭한 애플우드 베이컨과 수제 패티 2장을 얹어 제공합니다.")
        ));

        Menu drinkMenu = new Menu("DRINKS", List.of(
                new MenuItem("SODA", 3900, "코카콜라 제품을 제공합니다."),
                new MenuItem("SPARKLING WATER", 2500, "탄산수"),
                new MenuItem("BUDWEISER", 6000, "버드와이저 맥주")
        ));

        Menu frieMenu = new Menu("FRIES", List.of(
                new MenuItem("FRIES LITTLE", 6900, "파이브가이즈 스타일 프라이즈 작은 사이즈"),
                new MenuItem("FRIES REGULAR", 8900, "파이브가이즈 스타일 프라이즈 중간 사이즈"),
                new MenuItem("FRIES LARGE", 10900, "파이브가이즈 스타일 프라이즈 큰 사이즈")
        ));

        return List.of(burgerMenu, drinkMenu, frieMenu);
    }
}
