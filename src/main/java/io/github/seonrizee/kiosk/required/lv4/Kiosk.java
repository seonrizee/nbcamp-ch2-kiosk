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
            while (true) {

                System.out.println();
                printLine();
                printInfo("Welcome to Five Guys");
                printTitle("MAIN");
                displayMenuList(menuList);

                printInfo("0. 종료");
                printLine();
                printInput("원하는 카테고리 또는 기능의 번호를 입력해주세요.: ");

                int selectedMenuIdx = getUserInput(sc, menuList.size());
                if (selectedMenuIdx == 0) {
                    printInfo("키오스크를 종료합니다.");
                    break;
                } else {
                    showSubMenu(sc, selectedMenuIdx);
                }

            }

        }
    }

    private int getUserInput(Scanner sc, int maxValidIdx) {
        while (true) {
            try {
                int input = Integer.parseInt(sc.nextLine());
                if (input < 0 || input > maxValidIdx) {
                    throw new IndexOutOfBoundsException();
                }
                return input;
            } catch (NumberFormatException e) {
                printError("숫자 형식으로 입력해주세요.");
            } catch (IndexOutOfBoundsException e) {
                printError("잘못된 번호를 입력하셨습니다. 키오스크의 번호를 입력해주세요.");
            }
        }
    }

    private void showSubMenu(Scanner sc, int selectedMenuIdx) {

        Menu selectedMenu = menuList.get(selectedMenuIdx - 1);
        List<MenuItem> menuItemList = selectedMenu.getMenuItems();

        while (true) {
            System.out.println();
            printLine();
            printTitle(selectedMenu.getCategory());
            displayMenuItems(menuItemList);

            printInfo("0. 뒤로 가기");
            printLine();
            printInput("원하는 메뉴의 번호를 입력해주세요.: ");

            int selectedItemIdx = getUserInput(sc, menuItemList.size());
            if (selectedItemIdx == 0) {
                printInfo("이전 화면으로 돌아갑니다.");
                break;
            } else {
                MenuItem selectedItem = menuItemList.get(selectedItemIdx - 1);
                processOrder(selectedItemIdx, selectedItem);
            }
        }
    }

    public void processOrder(int menuItemIdx, MenuItem menuItem) {
        printInfo(menuItemIdx + "번 " + menuItem.getName() + "를 주문하셨습니다. 결제 완료됐습니다. 맛있게 드세요!");
    }

    public void displayMenuList(List<Menu> menuList) {
        for (int idx = 0; idx < menuList.size(); idx++) {
            printInfo(idx + 1 + ". " + menuList.get(idx).getCategory());
        }
    }

    private void displayMenuItems(List<MenuItem> menuItems) {
        for (int idx = 0; idx < menuItems.size(); idx++) {
            displayMenuItem(idx + 1, menuItems.get(idx));
        }
    }

    public void displayMenuItem(int idx, MenuItem menuItem) {
        String format = "KIOSK:::: %d. %-16s | %,6d원 | %s\n";
        System.out.printf(format, idx, menuItem.getName(), menuItem.getPrice(), menuItem.getDesc());
    }

    public void printTitle(String msg) {
        System.out.println("KIOSK:::: [ " + msg + " MENU ] ");
    }

    public void printInfo(String msg) {
        System.out.println("KIOSK:::: " + msg);
    }

    public void printError(String msg) {
        System.out.print("KIOSK_ERROR:::: " + msg + ":");
    }

    public void printInput(String msg) {
        System.out.print("KIOSK:::: " + msg);
    }

    public void printLine() {
        System.out.println("KIOSK:::: ====================================");
    }
}
