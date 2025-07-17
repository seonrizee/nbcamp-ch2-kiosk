package io.github.seonrizee.kiosk.required.lv3;

import java.util.List;
import java.util.Scanner;

public class Kiosk {

    private final List<MenuItem> menuItems;

    public Kiosk(List<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    public void start() {
        try (Scanner sc = new Scanner(System.in)) {
            boolean isRunning = true;
            while (isRunning) {

                System.out.println();
                displayLine();
                displayInfo("Welcome to Five Guys");
                for (int idx = 0; idx < menuItems.size(); idx++) {
                    displayMenuItem(idx + 1, menuItems.get(idx));
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
                        MenuItem selectedItem = menuItems.get(cmd - 1);
                        processOrder(cmd, selectedItem);
                    }
                } catch (NumberFormatException e) {
                    displayError("숫자 형식으로 입력해주세요. ");
                } catch (IndexOutOfBoundsException e) {
                    displayError("잘못된 번호를 입력하셨습니다. 키오스크의 번호를 입력해주세요.");
                }

            }

        }
    }

    public void processOrder(int cmd, MenuItem item) {
        displayInfo(cmd + "번 " + item.getName() + "를 주문하셨습니다. 결제 완료됐습니다. 맛있게 드세요!");
    }

    public void displayMenuItem(int idx, MenuItem menuItem) {
        String format = "KIOSK:::: %d. %-12s | %,6d원 | %s\n";
        System.out.printf(format, idx, menuItem.getName(), menuItem.getPrice(), menuItem.getDesc());
    }

    public void displayInfo(String msg) {
        System.out.println("KIOSK:::: " + msg);
    }

    public void displayError(String msg) {
        System.out.println("KIOSK_ERROR:::: " + msg);
    }

    public void displayInput(String msg) {
        System.out.print("KIOSK:::: " + msg);
    }

    public void displayLine() {
        System.out.println("KIOSK:::: ====================================");
    }
}
