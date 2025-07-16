package io.github.seonrizee.kiosk.required.lv1;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        try (Scanner sc = new Scanner(System.in)) {
            boolean isRunning = true;
            while (isRunning) {

                System.out.println();
                displayLine();
                displayInfo("롯데리아");
                displayInfo("0. 종료");
                displayLine();
                displayInput("원하는 메뉴의 번호를 입력해주세요.: ");

                try {
                    int cmd = Integer.parseInt(sc.nextLine());
                    if (cmd == 0) {
                        displayInfo("키오스크를 종료합니다.");
                        isRunning = false;
                    } else {
                        operateMenu(cmd);
                    }
                } catch (NumberFormatException e) {
                    displayError("숫자 형식으로 입력해주세요. ");
                }

            }

        }

    }

    public static void operateMenu(int cmd) {
        displayInfo(cmd + "번 메뉴를 선택했습니다.");
    }

    public static void displayInfo(String msg) {
        System.out.println("KIOSK:::: " + msg);
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
