package io.github.seonrizee.kiosk.challenge.lv2.application;

import io.github.seonrizee.kiosk.challenge.lv2.domain.Cart;
import io.github.seonrizee.kiosk.challenge.lv2.domain.CartItem;
import io.github.seonrizee.kiosk.challenge.lv2.domain.Discount;
import io.github.seonrizee.kiosk.challenge.lv2.domain.Menu;
import io.github.seonrizee.kiosk.challenge.lv2.domain.MenuItem;
import io.github.seonrizee.kiosk.challenge.lv2.view.KioskConsole;
import java.util.List;
import java.util.Scanner;

/**
 * 키오스크의 컨트롤러 역할을 하는 클래스.
 */
public class Kiosk {

    /**
     * 사용자 입력에서 '종료' 또는 '뒤로 가기'를 나타내는 상수 인덱스.
     */
    private static final int EXIT_INDEX = 0;

    private final List<Menu> menuList;
    private final KioskConsole console;
    private final Cart cart;

    /**
     * Kiosk 인스턴스를 생성합니다.
     *
     * @param menuList 키오스크에서 사용할 초기 메뉴 데이터 리스트
     */
    public Kiosk(List<Menu> menuList) {
        this.menuList = menuList;
        this.console = new KioskConsole();
        this.cart = new Cart();
    }

    /**
     * 키오스크 상태 머신 루프를 시작하여 사용자로부터 입력을 받습니다.
     * </p>
     */
    public void start() {
        try (Scanner sc = new Scanner(System.in)) {
            ScreenIntent curIntent = new ScreenIntent(Screen.MAIN);

            while (curIntent.getNextScreen() != Screen.EXIT) {
                curIntent = curIntent.getNextScreen()
                        .executeScreen(this, sc, curIntent);
            }
        }
    }

    /**
     * 메인 메뉴 화면으로 메뉴 카테고리와 주문 옵션을 표시하고 사용자로부터 입력을 받아 로직을 처리합니다.
     *
     * @param sc 사용자 입력을 받기 위한 {@link Scanner} 객체
     * @return 다음 화면 상태와 데이터를 담은 {@link ScreenIntent}
     */
    public ScreenIntent handleMainMenu(Scanner sc) {
        final int MENU_SIZE = menuList.size();
        final int CHECKOUT_INDEX = MENU_SIZE + 1;
        final int UPDATE_INDEX = CHECKOUT_INDEX + 1;
        final int LIMIT_INDEX = UPDATE_INDEX;

        console.displayMainOpening(CHECKOUT_INDEX, menuList, !cart.isCartEmpty());
        int selectedIdx;
        if (!cart.isCartEmpty()) {
            selectedIdx = console.getUserInput(sc, EXIT_INDEX, LIMIT_INDEX);
        } else {
            selectedIdx = console.getUserInput(sc, EXIT_INDEX, MENU_SIZE);
        }

        if (!cart.isCartEmpty()) {
            if (selectedIdx == CHECKOUT_INDEX) {
                return new ScreenIntent(Screen.ORDER_CHECKOUT);
            }
            if (selectedIdx == UPDATE_INDEX) {
                return new ScreenIntent(Screen.ORDER_UPDATE);
            }
        }

        if (selectedIdx > EXIT_INDEX && selectedIdx <= MENU_SIZE) {
            return new ScreenIntent(Screen.MENU_DETAIL, selectedIdx);
        }

        console.printInfo("키오스크를 종료합니다.");
        return new ScreenIntent(Screen.EXIT);
    }

    /**
     * 장바구의 상태를 확인하고 결제 화면의 로직을 처리합니다.
     *
     * @param sc 사용자 입력을 받기 위한 {@link Scanner} 객체
     * @return 메인 화면으로 돌아가기 위한 {@link ScreenIntent}
     */
    public ScreenIntent handleCheckoutOrder(Scanner sc) {
        ScreenIntent nextScreen = new ScreenIntent(Screen.MAIN);
        if (cart.isCartEmpty()) {
            return nextScreen;
        }

        console.printNewLine();
        console.displayCartStatus(cart);
        console.displayTwoOptions();

        int selectedIdx = console.getUserInput(sc, EXIT_INDEX, 1);

        if (selectedIdx == 1) {
            Discount selectedDcType = confirmDiscount(sc);
            if (selectedDcType == null) {
                return nextScreen;
            }
            operateOrder(selectedDcType);
        }
        return nextScreen;
    }

    /**
     * 사용자에게 적용할 할인을 선택받는 로직을 처리합니다.
     *
     * @param sc 사용자 입력을 받기 위한 {@link Scanner} 객체
     * @return 사용자가 선택한 {@link Discount} 타입. 뒤로 가기를 선택하면 {@code null}을 반환.
     */
    private Discount confirmDiscount(Scanner sc) {
        console.printNewLine();

        int idx = 1;
        List<Discount> availableDiscounts = Discount.getAvailableDiscounts();
        for (Discount discount : availableDiscounts) {
            String formattedDiscountRate = String.format("%d. %-6s: %.0f%% 할인", idx++, discount.getDesc(),
                    discount.getDcRate() * 100);
            console.printInfo(formattedDiscountRate);
        }

        console.printInfo("0. 뒤로 가기");
        console.printInput("할인 정보를 확인하시고 번호를 입력해주세요.: ");

        int selectedDiscountIdx = console.getUserInput(sc, EXIT_INDEX, availableDiscounts.size());
        if (selectedDiscountIdx == EXIT_INDEX) {
            return null;
        }
        return Discount.findDiscount(selectedDiscountIdx);
    }

    /**
     * 할인을 반영한 가격을 계산하고 최종 주문을 처리합니다.
     *
     * @param dcType 적용할 할인 타입
     */
    private void operateOrder(Discount dcType) {
        int cartTotalPrice = cart.getCartTotalPrice();
        double discountedPrice = cartTotalPrice - cartTotalPrice * dcType.getDcRate();
        int oneEliminatedPrice = (int) (Math.floor(discountedPrice / 10) * 10);

        console.displayOrderCompletion(dcType, cartTotalPrice, oneEliminatedPrice);
        cart.clearCart();
    }

    /**
     * 장바구니의 상태를 춢력하고 사용자가 입력한 장바구니 수정 화면의 로직을 처리합니다.
     *
     * @param sc 사용자 입력을 받기 위한 {@link Scanner} 객체
     * @return 다음 화면 상태를 담은 {@link ScreenIntent}. 수정 후 다시 수정 화면을 표시하거나 메인으로 돌아감.
     */
    public ScreenIntent handleUpdateOrder(Scanner sc) {
        if (cart.isCartEmpty()) {
            console.printInfo("장바구니에 남은 메뉴가 없어 메인 화면으로 돌아갑니다.");
            return new ScreenIntent(Screen.MAIN);
        }

        console.printNewLine();
        console.displayCartStatus(cart);
        console.printInfo("0. 뒤로 가기");
        console.printInput("선택하신 메뉴를 확인하시고 수정하고 싶은 메뉴의 번호를 입력해주세요.: ");

        int selectedIdx = console.getUserInput(sc, EXIT_INDEX, cart.getCartItems().size());
        if (selectedIdx == EXIT_INDEX) {
            return new ScreenIntent(Screen.MAIN);
        }

        updateCart(sc, selectedIdx, cart.getCartItems().get(selectedIdx - 1));
        return new ScreenIntent(Screen.ORDER_UPDATE);
    }

    /**
     * 선택된 장바구니 아이템을 수정하는 세부 로직을 처리합니다.
     *
     * @param sc              사용자 입력을 받기 위한 {@link Scanner} 객체
     * @param selectedItemIdx 사용자가 선택한 아이템의 화면상 번호
     * @param cartItem        수정할 대상 {@link CartItem} 객체
     */
    private void updateCart(Scanner sc, int selectedItemIdx, CartItem cartItem) {
        final int UPDATE_SIZE = 3;

        console.printNewLine();
        console.displayCartItem(selectedItemIdx, cartItem);
        console.displayUpdateCartMenu();

        int selectedIdx = console.getUserInput(sc, EXIT_INDEX, UPDATE_SIZE);
        if (selectedIdx == EXIT_INDEX) {
            return;
        }

        MenuItem selectedItem = cartItem.getItem();

        try {
            if (selectedIdx == 1) {
                cart.addItem(selectedItem);
                console.printInfo(selectedItem.getName() + "의 수량을 1개 추가했습니다.");
            } else if (selectedIdx == 2) {
                cart.decreaseItem(selectedItem);
                console.printInfo(selectedItem.getName() + "의 수량을 1개 감소했습니다.");
            } else if (selectedIdx == 3) {
                cart.removeItem(selectedItem);
                console.printInfo(selectedItem.getName() + "을(를) 장바구니에서 삭제했습니다.");
            }
        } catch (IllegalArgumentException | IllegalStateException e) {
            console.printError(e.getMessage());
        }
    }

    /**
     * 사용자가 선택한 카테고리의 모든 상품을 출력하고, 사용자가 상품을 선택하여 장바구니에 담는 로직을 처리합니다.
     *
     * @param sc        사용자 입력을 받기 위한 {@link Scanner} 객체
     * @param curIntent 현재 화면으로 전환 시 전달받은 데이터(선택된 메뉴 카테고리의 인덱스사용을 위해 전달)
     * @return 다음 화면 상태를 담은 {@link ScreenIntent}
     */
    public ScreenIntent handleSelectMenu(Scanner sc, ScreenIntent curIntent) {
        ScreenIntent nextScreen = new ScreenIntent(Screen.MAIN);

        Menu selectedMenu = menuList.get(curIntent.getIdxData() - 1);
        List<MenuItem> menuItemList = selectedMenu.getMenuItems();

        console.displaySelectMenu(selectedMenu, menuItemList);

        int selectedItemIdx = console.getUserInput(sc, EXIT_INDEX, menuItemList.size());
        if (selectedItemIdx == EXIT_INDEX) {
            console.printInfo("메인 화면으로 돌아갑니다.");
            return nextScreen;
        }

        MenuItem selectedItem = menuItemList.get(selectedItemIdx - 1);
        boolean isConfirmed = confirmAddToCart(sc, selectedItemIdx, selectedItem);
        if (isConfirmed) {
            addItemToCart(selectedItem);
        }
        // 아이템 추가 후에도 같은 메뉴 상세 화면에 머물도록 설정
        return new ScreenIntent(Screen.MENU_DETAIL, curIntent.getIdxData());
    }

    /**
     * 상품을 장바구니에 담기 전 사용자에게 확인을 받는 로직을 처리합니다.
     *
     * @param sc              사용자 입력을 받기 위한 {@link Scanner} 객체
     * @param selectedItemIdx 사용자가 선택한 아이템의 화면상 번호
     * @param selectedItem    사용자가 선택한 {@link MenuItem} 객체
     * @return 사용자가 확인을 선택하면 {@code true}, 아니면 {@code false}
     */
    private boolean confirmAddToCart(Scanner sc, int selectedItemIdx, MenuItem selectedItem) {
        console.printNewLine();
        console.displayMenuItem(selectedItemIdx, selectedItem);
        console.displayTwoOptions();

        int selectedIdx = console.getUserInput(sc, EXIT_INDEX, 1);
        return selectedIdx == 1;
    }

    /**
     * 선택된 상품을 장바구니에 추가하고, 추가된 후의 장바구니 상태를 출력합니다.
     *
     * @param selectedItem 장바구니에 추가할 {@link MenuItem} 객체
     */
    private void addItemToCart(MenuItem selectedItem) {
        console.printNewLine();
        console.printInfo(selectedItem.getName() + "을(를) 장바구니에 추가했습니다.");
        cart.addItem(selectedItem);
        console.displayCartStatus(cart);
    }
}