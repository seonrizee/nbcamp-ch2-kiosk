package io.github.seonrizee.kiosk.challenge.lv2;

import java.util.List;
import java.util.Scanner;

public class KioskConsole {

    public void printInfo(String msg) {
        System.out.println("KIOSK:::: " + msg);
    }

    public void printError(String msg) {
        System.out.print("KIOSK_ERROR:::: " + msg + ":");
    }

    public void printInput(String msg) {
        System.out.print("KIOSK:::: " + msg);
    }

    public void printTitle(String msg) {
        System.out.println("KIOSK:::: [ " + msg + " ] ");
    }

    public void printLine() {
        System.out.println("KIOSK:::: ====================================");
    }

    public void printNewLine() {
        System.out.println();
    }

    public void displayMainOpening() {
        printNewLine();
        printLine();
        printInfo("Welcome to Five Guys");
        printTitle("MAIN MENU");
    }

    public void displayCartItems(List<CartItem> cartItems) {
        for (int idx = 0; idx < cartItems.size(); idx++) {
            displayCartItem(idx + 1, cartItems.get(idx));
        }
    }

    public void displayCartItem(int idx, CartItem cartItem) {
        String format = "KIOSK:::: %d. %-16s | %3d개 | %,6d원\n";
        System.out.printf(format, idx, cartItem.getItem().getName(), cartItem.getQuantity(),
                cartItem.getSumItemPrice());
    }

    public void displayMenuList(List<Menu> menuList) {
        for (int idx = 0; idx < menuList.size(); idx++) {
            printInfo(idx + 1 + ". " + menuList.get(idx).getCategory());
        }
    }

    public void displayMenuItems(List<MenuItem> menuItems) {
        for (int idx = 0; idx < menuItems.size(); idx++) {
            displayMenuItem(idx + 1, menuItems.get(idx));
        }
    }

    public void displayMenuItem(int idx, MenuItem menuItem) {
        String format = "KIOSK:::: %d. %-16s | %,6d원 | %s\n";
        System.out.printf(format, idx, menuItem.getName(), menuItem.getPrice(), menuItem.getDesc());
    }

    public void displayOrderMenu(int minValidIdx) {
        printTitle("ORDER MENU");
        printInfo(minValidIdx + 1 + ". Orders   | 장바구니를 확인 후 주문합니다.");
        printInfo(minValidIdx + 2 + ". Edit     | 장바구니를 확인 후 수정합니다.");
    }

    public void displayCartStatus(Cart cart) {
        printTitle("CART STATUS");
        displayCartItems(cart.getCartItemList());
        String formattedPrice = String.format("%,d", cart.getTotalPrice());
        printLine();
        printInfo("총 가격: " + formattedPrice + "원");
    }

    public int getUserInput(Scanner sc, int minValidIdx, int maxValidIdx) {
        while (true) {
            try {
                int input = Integer.parseInt(sc.nextLine());
                if (input < minValidIdx || input > maxValidIdx) {
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
}
