package io.github.seonrizee.kiosk.required.lv4;

import java.util.List;
import java.util.Scanner;

public class Kiosk {

    private final List<Menu> menuList;

    public Kiosk(List<Menu> menuList) {
        this.menuList = menuList;
    }

    public void start() {

        try (Scanner sc = new Scanner(System.in)) {
            boolean isRunning = true;
            while (isRunning) {

                System.out.println();
                displayLine();
                displayInfo("Welcome to Five Guys");
                displayTitle("MAIN");
                // 상위 메뉴 출력
                displayMenu(menuList);
                displayInfo("0. 종료");
                displayLine();
                displayInput("원하는 카테고리 또는 기능의 번호를 입력해주세요.: ");

                try {
                    int selMenuIdx = Integer.parseInt(sc.nextLine());
                    if (selMenuIdx == 0) {
                        displayInfo("키오스크를 종료합니다.");
                        isRunning = false;
                    } else {
                        showSubMenu(sc, selMenuIdx);
                    }
                } catch (NumberFormatException e) {
                    displayError("숫자 형식으로 입력해주세요. ");
                } catch (IndexOutOfBoundsException e) {
                    displayError("잘못된 번호를 입력하셨습니다. 키오스크의 번호를 입력해주세요.");
                }

            }

        }
    }

    private void showSubMenu(Scanner sc, int selMenuIdx) {

        Menu selectedMenu = menuList.get(selMenuIdx - 1);
        List<MenuItem> menuItemList = selectedMenu.getMenuItems();

        System.out.println();
        displayLine();
        displayTitle(selectedMenu.getCategory());
        // 상위 메뉴 출력
        displayMenuItems(menuItemList);

        displayInfo("0. 뒤로 가기");
        displayLine();
        displayInput("원하는 메뉴의 번호를 입력해주세요.: ");

        boolean isRunning = true;
        while (isRunning) {
            try {
                int selectedMenuItemIdx = Integer.parseInt(sc.nextLine());
                if (selectedMenuItemIdx == 0) {
                    displayInfo("이전 화면으로 돌아갑니다.");
                    isRunning = false;
                } else {
                    MenuItem selectedItem = menuItemList.get(selectedMenuItemIdx - 1);
                    processOrder(selectedMenuItemIdx, selectedItem);
                    isRunning = false;
                }
            } catch (NumberFormatException e) {
                displayError("숫자 형식으로 입력해주세요. ");
            } catch (IndexOutOfBoundsException e) {
                displayError("잘못된 번호를 입력하셨습니다. 키오스크의 번호를 입력해주세요.");
            }
        }

    }

    private void displayMenuItems(List<MenuItem> menuItems) {
        for (int idx = 0; idx < menuItems.size(); idx++) {
            displayMenuItem(idx + 1, menuItems.get(idx));
        }
    }


    public void processOrder(int selItemIdx, MenuItem selectedItem) {
        displayInfo(selItemIdx + "번 " + selectedItem.getName() + "를 주문하셨습니다. 결제 완료됐습니다. 맛있게 드세요!");
    }


    public void displayMenu(List<Menu> menuList) {
        for (int idx = 0; idx < menuList.size(); idx++) {
            displayInfo(idx + 1 + ". " + menuList.get(idx).getCategory());
        }
    }

    public void displayMenuItem(int idx, MenuItem menuItem) {
        String format = "KIOSK:::: %d. %-16s | %,6d원 | %s\n";
        System.out.printf(format, idx, menuItem.getName(), menuItem.getPrice(), menuItem.getDesc());
    }

    public void displayTitle(String msg) {
        System.out.println("KIOSK:::: [ " + msg + " MENU ] ");
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
