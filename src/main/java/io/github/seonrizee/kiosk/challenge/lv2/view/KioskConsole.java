package io.github.seonrizee.kiosk.challenge.lv2.view;

import io.github.seonrizee.kiosk.challenge.lv2.domain.Cart;
import io.github.seonrizee.kiosk.challenge.lv2.domain.CartItem;
import io.github.seonrizee.kiosk.challenge.lv2.domain.Discount;
import io.github.seonrizee.kiosk.challenge.lv2.domain.Menu;
import io.github.seonrizee.kiosk.challenge.lv2.domain.MenuItem;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

/**
 * 키오스크의 콘솔 입출력을 담당하는 뷰(View) 클래스.
 */
public class KioskConsole {
    /**
     * 콘솔 출력 메시지 앞에 붙는 기본 prefix 문자열.
     */
    private static final String PROMPT = "KIOSK:::: ";

    /**
     * 일반 정보 메시지를 콘솔에 출력합니다.
     *
     * @param msg 출력할 메시지 문자열
     */
    public void printInfo(String msg) {
        System.out.println(PROMPT + msg);
    }

    /**
     * 오류 메시지를 콘솔에 출력합니다. 메시지 끝에 콜론(:)이 추가됩니다.
     *
     * @param msg 출력할 오류 메시지 문자열
     */
    public void printError(String msg) {
        System.out.print(PROMPT + msg + ":");
    }

    /**
     * 사용자 입력을 요청하는 프롬프트 메시지를 콘솔에 출력합니다. (줄바꿈 없음)
     *
     * @param msg 출력할 입력 요청 메시지 문자열
     */
    public void printInput(String msg) {
        System.out.print(PROMPT + msg);
    }

    /**
     * 화면의 제목을 형식에 맞게 콘솔에 출력합니다.
     *
     * @param msg 출력할 제목 문자열
     */
    public void printTitle(String msg) {
        System.out.println(PROMPT + "[ " + msg + " ] ");
    }

    /**
     * 구분선을 콘솔에 출력합니다.
     */
    public void printLine() {
        System.out.println(PROMPT + "====================================");
    }

    /**
     * 빈 줄을 하나 출력하여 가독성을 높입니다.
     */
    public void printNewLine() {
        System.out.println();
    }


    /**
     * 키오스크의 메인 시작 화면을 표시합니다.
     *
     * @param CHECKOUT_INDEX   주문 메뉴의 시작 인덱스
     * @param menuList         표시할 메뉴 카테고리 리스트
     * @param displayOrderMenu 주문/수정 메뉴를 표시할지 여부
     */
    public void displayMainOpening(int CHECKOUT_INDEX, List<Menu> menuList, boolean displayOrderMenu) {
        printNewLine();
        printLine();
        printInfo("Welcome to Five Guys");
        printTitle("MAIN MENU");
        displayMenuList(menuList);
        if (displayOrderMenu) {
            displayOrderMenu(CHECKOUT_INDEX);
        }
        printInfo("0. 종료");
        printLine();
        printInput("원하는 카테고리 또는 기능의 번호를 입력해주세요.: ");
    }

    /**
     * 장바구니에 담긴 모든 아이템 리스트를 화면에 표시합니다.
     *
     * @param cartItems 표시할 {@link CartItem} 리스트
     */
    public void displayCartItems(List<CartItem> cartItems) {
        IntStream.range(0, cartItems.size())
                .forEach(idx -> displayCartItem(idx + 1, cartItems.get(idx)));
    }

    /**
     * 장바구니의 단일 아이템을 형식에 맞게 화면에 표시합니다.
     *
     * @param idx      화면에 표시될 아이템 번호
     * @param cartItem 표시할 {@link CartItem} 객체
     */
    public void displayCartItem(int idx, CartItem cartItem) {
        String format = PROMPT + "%d. %-16s | %3d개 | %,6d원\n";
        System.out.printf(format, idx, cartItem.getItem().getName(), cartItem.getQuantity(),
                cartItem.getItemTotalPrice());
    }

    /**
     * 메인 메뉴의 카테고리 리스트를 화면에 표시합니다.
     *
     * @param menuList 표시할 {@link Menu} 리스트
     */
    public void displayMenuList(List<Menu> menuList) {
        IntStream.range(0, menuList.size())
                .forEach(idx -> printInfo(idx + 1 + ". " + menuList.get(idx).getCategory()));
    }

    /**
     * 특정 카테고리에 속한 모든 메뉴 아이템 리스트를 화면에 표시합니다.
     *
     * @param menuItems 표시할 {@link MenuItem} 리스트
     */
    public void displayMenuItems(List<MenuItem> menuItems) {
        IntStream.range(0, menuItems.size())
                .forEach(idx -> displayMenuItem(idx + 1, menuItems.get(idx)));
    }

    /**
     * 단일 메뉴 아이템을 형식에 맞게 화면에 표시합니다.
     *
     * @param idx      화면에 표시될 아이템 번호
     * @param menuItem 표시할 {@link MenuItem} 객체
     */
    public void displayMenuItem(int idx, MenuItem menuItem) {
        String format = PROMPT + "%d. %-16s | %,6d원 | %s\n";
        System.out.printf(format, idx, menuItem.getName(), menuItem.getPrice(), menuItem.getDesc());
    }

    /**
     * 상세 메뉴 선택 화면을 표시합니다.
     *
     * @param selectedMenu 선택된 메뉴 카테고리
     * @param menuItemList 표시할 해당 카테고리의 메뉴 아이템 리스트
     */
    public void displaySelectMenu(Menu selectedMenu, List<MenuItem> menuItemList) {
        printNewLine();
        printLine();
        printTitle(selectedMenu.getCategory() + " MENU");
        displayMenuItems(menuItemList);

        printInfo("0. 뒤로 가기");
        printLine();
        printInput("원하는 메뉴의 번호를 입력해주세요.: ");
    }

    /**
     * 주문 및 수정 옵션 메뉴를 화면에 표시합니다.
     *
     * @param CHECKOUT_INDEX 주문 메뉴의 시작 인덱스
     */
    public void displayOrderMenu(int CHECKOUT_INDEX) {
        printTitle("ORDER MENU");
        printInfo(CHECKOUT_INDEX + ". Orders   | 장바구니를 확인 후 주문합니다.");
        printInfo(CHECKOUT_INDEX + 1 + ". Edit     | 장바구니를 확인 후 수정합니다.");
    }

    /**
     * 현재 장바구니의 전체 상태(아이템 목록 및 총액)를 화면에 표시합니다.
     *
     * @param cart 표시할 {@link Cart} 객체
     */
    public void displayCartStatus(Cart cart) {
        printTitle("CART STATUS");
        displayCartItems(cart.getCartItems());
        String formattedPrice = String.format("%,d", cart.getCartTotalPrice());
        printLine();
        printInfo("총 가격: " + formattedPrice + "원");
    }

    /**
     * 주문 완료 및 결제 내역을 화면에 표시합니다.
     *
     * @param dcType             적용된 할인 타입
     * @param cartTotalPrice     할인 전 총 금액
     * @param oneEliminatedPrice 할인 후 원 단위 절사된 최종 결제 금액
     */
    public void displayOrderCompletion(Discount dcType, int cartTotalPrice, int oneEliminatedPrice) {
        String formattedOriginalPrice = String.format("%,d", cartTotalPrice);
        String formattedPrice = String.format("%,d", oneEliminatedPrice);
        printNewLine();
        printInfo("할일 이전 금액: " + formattedOriginalPrice + "원");
        printInfo("할인 적용 금액: " + formattedPrice + "원 | 할인율: " + (dcType.getDcRate() * 100) + "% (원 단위 절사)");
        printInfo("주문이 완료되었습니다. " + formattedPrice + "원이 결제되었습니다. 감사합니다.");
    }

    /**
     * 장바구니 아이템 수정 메뉴(수량 증가/감소, 삭제)를 화면에 표시합니다.
     */
    public void displayUpdateCartMenu() {
        printInfo("1. 메뉴 수량 증가");
        printInfo("2. 메뉴 수량 감소");
        printInfo("3. 메뉴 삭제");
        printInfo("0. 뒤로 가기");
        printInput("원하는 기능의 메뉴를 고르세요.: ");
    }


    /**
     * "1. 주문/확인"과 "0. 뒤로 가기/취소" 두 가지 선택지를 화면에 표시합니다.
     */
    public void displayTwoOptions() {
        printInfo("1. 주문");
        printInfo("0. 뒤로 가기");
        printInput("선택하신 메뉴를 확인하시고 번호를 입력해주세요.: ");
    }

    /**
     * 사용자로부터 정수 입력을 받고, 유효한 범위 내의 값인지 검증합니다.
     *
     * @param sc          사용자 입력을 받기 위한 {@link Scanner} 객체
     * @param minValidIdx 입력 가능한 최소값
     * @param maxValidIdx 입력 가능한 최대값
     * @return 유효성이 검증된 사용자 입력 정수값
     */
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