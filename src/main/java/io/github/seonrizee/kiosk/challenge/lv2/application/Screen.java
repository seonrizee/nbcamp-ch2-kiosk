package io.github.seonrizee.kiosk.challenge.lv2.application;

import java.util.Scanner;

/**
 * 키오스크의 각 화면 상태를 정의하는 Enum.
 */
public enum Screen {
    /**
     * 메인 메뉴 화면. 상품 카테고리 및 주문/수정 옵션을 표시합니다.
     */
    MAIN((kiosk, sc, intent) -> kiosk.handleMainMenu(sc)),
    /**
     * 특정 메뉴 카테고리의 상세 상품 목록을 표시하는 화면.
     */
    MENU_DETAIL((kiosk, sc, intent) -> kiosk.handleSelectMenu(sc, intent)),
    /**
     * 장바구니 내용을 확인하고 결제를 진행하는 화면.
     */
    ORDER_CHECKOUT((kiosk, sc, intent) -> kiosk.handleCheckoutOrder(sc)),
    /**
     * 장바구니에 담긴 상품의 수량을 조절하거나 삭제하는 화면.
     */
    ORDER_UPDATE((kiosk, sc, intent) -> kiosk.handleUpdateOrder(sc)),
    /**
     * 애플리케이션을 종료하는 상태.
     */
    EXIT((kiosk, sc, intent) -> intent);

    /**
     * 각 화면 상태에 대한 구체적인 동작을 정의하는 핸들러.
     */
    private final ScreenHandler screenHandler;

    /**
     * 새로운 Screen 상수 인스턴스를 생성합니다.
     *
     * @param screenHandler 이 화면 상태가 실행할 로직을 담고 있는 {@link ScreenHandler}
     */
    Screen(ScreenHandler screenHandler) {
        this.screenHandler = screenHandler;
    }

    /**
     * 현재 화면에 해당하는 로직을 실행하고, 다음으로 전환할 화면 정보를 담은 {@link ScreenIntent}를 반환합니다.
     *
     * @param kiosk  애플리케이션의 메인 로직을 처리하는 {@link Kiosk} 인스턴스
     * @param sc     사용자 입력을 받기 위한 {@link Scanner} 인스턴스
     * @param intent 현재 화면으로 전환될 때 전달받은 데이터와 상태 정보를 담은 {@link ScreenIntent}
     * @return 다음으로 전환될 화면의 정보와 데이터를 담은 {@link ScreenIntent}
     */
    public ScreenIntent executeScreen(Kiosk kiosk, Scanner sc, ScreenIntent intent) {
        return screenHandler.handle(kiosk, sc, intent);
    }

    /**
     * 각 화면의 동작을 람다식을 활용하여 처리하기 위해 구현한 함수형 인터페이스.
     */
    @FunctionalInterface
    public interface ScreenHandler {
        /**
         * 특정 화면의 로직을 처리합니다.
         *
         * @param kiosk  애플리케이션의 메인 로직을 처리하는 {@link Kiosk} 인스턴스
         * @param sc     사용자 입력을 받기 위한 {@link Scanner} 인스턴스
         * @param intent 현재 화면으로 전환될 때 전달받은 데이터와 상태 정보를 담은 {@link ScreenIntent}
         * @return 다음으로 전환될 화면의 정보와 데이터를 담은 {@link ScreenIntent}
         */
        ScreenIntent handle(Kiosk kiosk, Scanner sc, ScreenIntent intent);
    }
}