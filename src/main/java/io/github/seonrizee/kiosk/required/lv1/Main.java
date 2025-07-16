package io.github.seonrizee.kiosk.required.lv1;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        try (Scanner sc = new Scanner(System.in)) {
            boolean isRunning = true;
            while (isRunning) {

                displayInfo("롯데리아");
                displayInfo("메뉴를 선택해주세요.");
                displayInfo("0. 종료");

                int cmd = Integer.parseInt(sc.nextLine());
                if (cmd == 0) {
                    displayInfo("키오스크를 종료합니다.");
                    isRunning = false;
                } else {
                    operateMenu(cmd);
                }
            }

        }

    }

    public static void operateMenu(int cmd) {
        displayInfo(cmd + "번 메뉴를 선택했습니다.");
    }

    public static void displayInfo(String str) {
        System.out.println("KIOSK:::: " + str);
    }

}
