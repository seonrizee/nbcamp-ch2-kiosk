package io.github.seonrizee.kiosk.challenge.lv1;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        List<Menu> menuList = setupMenuList();
        Kiosk kiosk = new Kiosk(menuList);
        kiosk.start();
    }


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
                new MenuItem("LITTLE", 6900, "작은 사이즈"),
                new MenuItem("REGULAR", 8900, "중간 사이즈"),
                new MenuItem("LARGE", 10900, "큰 사이즈")
        ));

        return List.of(burgerMenu, drinkMenu, frieMenu);
    }
}
