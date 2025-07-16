package io.github.seonrizee.kiosk.required.lv1;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        // 임시 메뉴아이템 배열
        String[] menuItemNames = {"HAMBURGER", "CHEESEBURGER", "BACON BURGER"};
        int[] menuItemPrices = {13400, 14900, 15900};
        String[] menuItemDesc = {
                "부드러운 참깨 번 사이에 갓 구운 신선한 수제 패티 2장을 얹어 제공합니다.",
                "부드러운 참깨 번 사이에 아메리칸 스타일 치즈를 녹여 넣은 수제 패티 2장을 얹어 제공합니다.",
                "부드러운 참깨 번 사이에 바삭한 애플우드 베이컨과 수제 패티 2장을 얹어 제공합니다."};

        try (Scanner sc = new Scanner(System.in)) {
            boolean isRunning = true;
            while (isRunning) {

                System.out.println();
                displayLine();
                displayInfo("Welcome to Five Guys");
                for (int idx = 0; idx < menuItemNames.length; idx++) {
                    displayMenuItem(idx + 1, menuItemNames[idx], menuItemPrices[idx], menuItemDesc[idx]);
                }
                displayInfo("0. 종료");
                displayLine();
                displayInput("원하는 메뉴 또는 기능의 번호를 입력해주세요.: ");

                try {
                    int cmd = Integer.parseInt(sc.nextLine());
                    if (cmd == 0) {
                        displayInfo("키오스크를 종료합니다.");
                        isRunning = false;
                    } else {
                        processOrder(cmd, menuItemNames[cmd - 1]);
                    }
                } catch (NumberFormatException e) {
                    displayError("숫자 형식으로 입력해주세요. ");
                } catch (ArrayIndexOutOfBoundsException e) {
                    displayError("잘못된 번호를 입력하셨습니다. 메뉴판의 번호를 입력해주세요.");
                }

            }

        }

    }

    public static void processOrder(int cmd, String name) {
        displayInfo(cmd + "번 " + name + "를 주문하셨습니다. 결제 완료됐습니다. 맛있게 드세요!");
    }

    public static void displayInfo(String msg) {
        System.out.println("KIOSK:::: " + msg);
    }

    public static void displayMenuItem(int idx, String name, int price, String description) {
        String format = "KIOSK:::: %d. %-12s | %,6d원 | %s\n";
        System.out.printf(format, idx, name, price, description);
    }

    public static void displayError(String msg) {
        System.out.println("KIOSK_ERROR:::: " + msg);
    }

    public static void displayInput(String msg) {
        System.out.print("KIOSK:::: " + msg);
    }

    public static void displayLine() {
        System.out.println("KIOSK:::: ====================================");
    }
}
