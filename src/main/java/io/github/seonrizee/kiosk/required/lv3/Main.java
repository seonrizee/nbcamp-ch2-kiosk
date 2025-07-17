package io.github.seonrizee.kiosk.required.lv3;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        // 메뉴아이템 배열 생성
        List<MenuItem> menuItems = List.of(
                new MenuItem("HAMBURGER", 13400, "부드러운 참깨 번 사이에 갓 구운 신선한 수제 패티 2장을 얹어 제공합니다."),
                new MenuItem("CHEESEBURGER", 14900, "부드러운 참깨 번 사이에 아메리칸 스타일 치즈를 녹여 넣은 수제 패티 2장을 얹어 제공합니다."),
                new MenuItem("BACON BURGER", 15900, "부드러운 참깨 번 사이에 바삭한 애플우드 베이컨과 수제 패티 2장을 얹어 제공합니다.")
        );
        
        Kiosk kiosk = new Kiosk(menuItems);
        kiosk.start();
    }
}
