package io.github.seonrizee.kiosk.challenge.lv2.application;

import io.github.seonrizee.kiosk.challenge.lv2.domain.Cart;
import io.github.seonrizee.kiosk.challenge.lv2.domain.CartItem;
import io.github.seonrizee.kiosk.challenge.lv2.domain.Discount;
import io.github.seonrizee.kiosk.challenge.lv2.domain.Menu;
import io.github.seonrizee.kiosk.challenge.lv2.domain.MenuItem;
import io.github.seonrizee.kiosk.challenge.lv2.view.KioskConsole;
import java.util.List;
import java.util.Scanner;

public class Kiosk {

    private final List<Menu> menuList;
    private final KioskConsole console;
    private final Cart cart;

    public Kiosk(List<Menu> menuList) {
        this.menuList = menuList;
        this.console = new KioskConsole();
        this.cart = new Cart();
    }

    public void start() {
        try (Scanner sc = new Scanner(System.in)) {
            ScreenIntent curIntent = new ScreenIntent(Screen.MAIN);

            while (curIntent.getNextScreen() != Screen.EXIT) {
                curIntent = switch (curIntent.getNextScreen()) {
                    case MAIN -> handleMainMenu(sc);
                    case MENU_DETAIL -> handleSelectMenu(sc, curIntent);
                    case ORDER_CHECKOUT -> handleCheckoutOrder(sc);
                    case ORDER_UPDATE -> handleUpdateOrder(sc);
                    case EXIT -> curIntent;
                };

            }

        }

    }

    private ScreenIntent handleMainMenu(Scanner sc) {

        final int MENU_SIZE = menuList.size();
        final int EXIT_INDEX = 0;
        final int CHECKOUT_INDEX = MENU_SIZE + 1;
        final int UPDATE_INDEX = CHECKOUT_INDEX + 1;
        final int LIMIT_INDEX = UPDATE_INDEX;

        console.displayMainOpening(CHECKOUT_INDEX, cart, menuList);
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

        if (selectedIdx > 0 && selectedIdx <= MENU_SIZE) {
            return new ScreenIntent(Screen.MENU_DETAIL, selectedIdx);
        }

        console.printInfo("키오스크를 종료합니다.");
        return new ScreenIntent(Screen.EXIT);
    }


    private ScreenIntent handleCheckoutOrder(Scanner sc) {
        ScreenIntent nextScreen = new ScreenIntent(Screen.MAIN);
        if (cart.isCartEmpty()) {
            return nextScreen;
        }

        console.printNewLine();
        console.displayCartStatus(cart);
        console.displayTwoOptions();

        int selectedIdx = console.getUserInput(sc, 0, 1);

        if (selectedIdx == 1) {
            Discount selectedDcType = confirmDiscount(sc);
            if (selectedDcType == null) {
                return nextScreen;
            }
            operateOrder(selectedDcType);
        }

        return nextScreen;
    }


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

        int selectedDiscountIdx = console.getUserInput(sc, 0, availableDiscounts.size());
        if (selectedDiscountIdx == 0) {
            return null;
        }
        return Discount.findDiscount(selectedDiscountIdx);
    }

    private void operateOrder(Discount dcType) {
        int cartTotalPrice = cart.getCartTotalPrice();
        double discountedPrice = cartTotalPrice - cartTotalPrice * dcType.getDcRate();
        int oneEliminatedPrice = (int) (Math.floor(discountedPrice / 10) * 10);

        console.displayOrderCompletion(dcType, cartTotalPrice, oneEliminatedPrice);
        cart.clearCart();
    }


    private ScreenIntent handleUpdateOrder(Scanner sc) {

        if (cart.isCartEmpty()) {
            console.printInfo("장바구니에 남은 메뉴가 없어 메인 화면으로 돌아갑니다.");
            return new ScreenIntent(Screen.MAIN);
        }

        console.printNewLine();
        console.displayCartStatus(cart);
        console.printInfo("0. 뒤로 가기");
        console.printInput("선택하신 메뉴를 확인하시고 수정하고 싶은 메뉴의 번호를 입력해주세요.: ");

        int selectedIdx = console.getUserInput(sc, 0, cart.getCartItems().size());
        if (selectedIdx == 0) {
            return new ScreenIntent(Screen.MAIN);
        }

        updateCart(sc, selectedIdx, cart.getCartItems().get(selectedIdx - 1));
        return new ScreenIntent(Screen.ORDER_UPDATE);
    }

    private void updateCart(Scanner sc, int selectedItemIdx, CartItem cartItem) {

        final int UPDATE_SIZE = 3;

        console.printNewLine();
        console.displayCartItem(selectedItemIdx, cartItem);
        console.displayUpdateCartMenu();

        int selectedIdx = console.getUserInput(sc, 0, UPDATE_SIZE);
        if (selectedIdx == 0) {
            return;
        }

        MenuItem selectedItem = cartItem.getItem();

        if (selectedIdx == 1) {
            if (cart.addItem(selectedItem)) {
                console.printInfo(selectedItem.getName() + "이(가) 의 수량을 1개 추가했습니다.");
            }
        } else if (selectedIdx == 2) {
            if (cart.decreaseItem(selectedItem)) {
                console.printInfo(selectedItem.getName() + "이(가) 의 수량을 1개 감소했습니다.");
            }
        } else if (selectedIdx == 3) {
            if (cart.removeItem(selectedItem)) {
                console.printInfo(selectedItem.getName() + "을(를) 장바구니에서 삭제했습니다.");
            }
        }

    }

    private ScreenIntent handleSelectMenu(Scanner sc, ScreenIntent curIntent) {
        ScreenIntent nextScreen = new ScreenIntent(Screen.MAIN);

        Menu selectedMenu = menuList.get(curIntent.getData() - 1);
        List<MenuItem> menuItemList = selectedMenu.getMenuItems();

        console.displaySelectMenu(selectedMenu, menuItemList);

        int selectedItemIdx = console.getUserInput(sc, 0, menuItemList.size());
        if (selectedItemIdx == 0) {
            console.printInfo("메인 화면으로 돌아갑니다.");
            return nextScreen;
        }

        MenuItem selectedItem = menuItemList.get(selectedItemIdx - 1);
        boolean isConfirmed = confirmAddToCart(sc, selectedItemIdx, selectedItem);
        if (isConfirmed) {
            addItemToCart(selectedItem);
        }
        return new ScreenIntent(Screen.MENU_DETAIL, curIntent.getData());
    }


    private boolean confirmAddToCart(Scanner sc, int selectedItemIdx, MenuItem selectedItem) {
        console.printNewLine();
        console.displayMenuItem(selectedItemIdx, selectedItem);
        console.displayTwoOptions();

        int selectedIdx = console.getUserInput(sc, 0, 1);
        return selectedIdx == 1;
    }

    private void addItemToCart(MenuItem selectedItem) {
        console.printNewLine();
        console.printInfo(selectedItem.getName() + "을(를) 장바구니에 추가했습니다.");
        cart.addItem(selectedItem);
        console.displayCartStatus(cart);
    }


}
