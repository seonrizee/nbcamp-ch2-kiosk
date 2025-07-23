package io.github.seonrizee.kiosk.challenge.lv2.application;

import java.util.Scanner;

public enum Screen {
    MAIN((kiosk, sc, intent) -> kiosk.handleMainMenu(sc)),                  // 메인 화면
    MENU_DETAIL((kiosk, sc, intent) -> kiosk.handleSelectMenu(sc, intent)), // 선택한 Menu의 MenuItem 선택 화면
    ORDER_CHECKOUT((kiosk, sc, intent) -> kiosk.handleCheckoutOrder(sc)),   // 주문 확인 및 결제 화면
    ORDER_UPDATE((kiosk, sc, intent) -> kiosk.handleUpdateOrder(sc)),     // 주문 수정 화면
    EXIT((kiosk, sc, intent) -> intent);                                   // 종료

    private final ScreenHandler screenHandler;

    Screen(ScreenHandler screenHandler) {
        this.screenHandler = screenHandler;
    }

    public ScreenIntent executeScreen(Kiosk kiosk, Scanner sc, ScreenIntent intent) {
        return screenHandler.handle(kiosk, sc, intent);
    }

    @FunctionalInterface
    public interface ScreenHandler {
        ScreenIntent handle(Kiosk kiosk, Scanner sc, ScreenIntent intent);
    }
}
