package io.github.seonrizee.kiosk.required.lv1;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        try (Scanner sc = new Scanner(System.in)) {
            boolean isRunning = true;
            while (isRunning) {

                System.out.println("롯데리아");
                System.out.println("메뉴를 선택해주세요.");
                System.out.println("0. 종료");

                int cmd = Integer.parseInt(sc.nextLine());
                if (cmd == 0) {
                    System.out.println("키오스크를 종료합니다.");
                    isRunning = false;
                } else {
                    operateMenu(cmd);
                }
            }

        }

    }

    public static void operateMenu(int cmd) {
        System.out.println(cmd + "번 메뉴를 선택했습니다.");
    }

}
