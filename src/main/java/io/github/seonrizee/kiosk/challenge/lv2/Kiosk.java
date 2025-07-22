package io.github.seonrizee.kiosk.challenge.lv2;

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
            while (true) {

                console.displayMainOpening();
                console.displayMenuList(menuList);

                int orderMinValidIdx = menuList.size();
                int orderMaxValidIdx = orderMinValidIdx + 2;
                if (!cart.isCartEmpty()) {
                    console.displayOrderMenu(orderMinValidIdx);
                }
                console.printInfo("0. 종료");
                console.printLine();
                console.printInput("원하는 카테고리 또는 기능의 번호를 입력해주세요.: ");

                // TODO 카트가 비어있을 떄와 그렇지 않을 때의 입력범위 정리하기
                int selectedMenuIdx = console.getUserInput(sc, 0, orderMaxValidIdx);
                if (selectedMenuIdx == 0) {
                    console.printInfo("키오스크를 종료합니다.");
                    break;
                } else {
                    if (!cart.isCartEmpty() && selectedMenuIdx == 4) {
                        handleCheckoutOrder(sc, cart);
                    } else if (!cart.isCartEmpty() && selectedMenuIdx == 5) {
                        handleUpdateCart(sc, cart);
                    } else if (selectedMenuIdx <= orderMinValidIdx) {
                        handleSelectMenu(sc, selectedMenuIdx, cart);
                    }
                }

            }

        }
    }


    private void handleCheckoutOrder(Scanner sc, Cart cart) {
        while (true) {
            if (cart.isCartEmpty()) {
                return;
            }

            console.displayCartStatus(cart);
            console.printInfo("1. 주문");
            console.printInfo("0. 뒤로 가기");
            console.printInput("선택하신 메뉴를 확인하시고 번호를 입력해주세요.: ");

            int selectedIdx = console.getUserInput(sc, 0, 1);

            if (selectedIdx == 1) {
                Discount selectedDcType = confirmDiscount(sc);
                if (selectedDcType == null) {
                    return;
                }
                operateOrder(cart, selectedDcType);
            } else if (selectedIdx == 0) {
                return;
            }
        }
    }

    private Discount confirmDiscount(Scanner sc) {
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

    private void operateOrder(Cart cart, Discount dcType) {
        double discountedPrice = cart.getTotalPrice() -
                cart.getTotalPrice() * dcType.getDcRate();
        int oneEliminatedPrice = (int) (Math.floor(discountedPrice / 10) * 10);
        String formattedOriginalPrice = String.format("%,d", cart.getTotalPrice());
        String formattedPrice = String.format("%,d", oneEliminatedPrice);
        console.printInfo("할일 전 가격: " + formattedOriginalPrice + "원");
        console.printInfo("할인 후 가격: " + formattedPrice + "원");
        console.printInfo("주문이 완료되었습니다. " + formattedPrice + "원이 결제되었습니다. 감사합니다.");
        cart.clearCart();
    }

    private void handleUpdateCart(Scanner sc, Cart cart) {
        while (true) {
            if (cart.isCartEmpty()) {
                console.printInfo("장바구니에 남은 메뉴가 없어 처음으로 돌아갑니다.");
                return;
            }

            console.displayCartStatus(cart);
            console.printInfo("0. 뒤로 가기");
            console.printInput("선택하신 메뉴를 확인하시고 수정하고 싶은 메뉴의 번호를 입력해주세요.: ");

            int selectedIdx = console.getUserInput(sc, 0, cart.getCartItemList().size());
            if (selectedIdx == 0) {
                return;
            }

            updateCart(sc, cart, selectedIdx, cart.getCartItemList().get(selectedIdx - 1));
        }
    }

    private void updateCart(Scanner sc, Cart cart, int selection, CartItem cartItem) {

        console.displayCartItem(selection, cartItem);
        console.printInfo("1. 메뉴 수량 증가");
        console.printInfo("2. 메뉴 수량 감소");
        console.printInfo("3. 메뉴 삭제");
        console.printInfo("0. 뒤로 가기");
        console.printInput("원하는 기능의 메뉴를 고르세요.: ");

        int selectedIdx = console.getUserInput(sc, 0, 3);
        if (selectedIdx == 0) {
            return;
        }

        MenuItem selectedItem = cartItem.getItem();
        try {
            if (selectedIdx == 1) {
                cart.addItem(selectedItem);
            } else if (selectedIdx == 2) {
                cart.decreaseItem(selectedItem);
            } else if (selectedIdx == 3) {
                cart.removeItem(selectedItem);
            }
        } catch (NullPointerException e) {
            console.printError("장바구니에 없는 메뉴입니다.");
        }
    }


    private void handleSelectMenu(Scanner sc, int selectedMenuIdx, Cart cart) {

        Menu selectedMenu = menuList.get(selectedMenuIdx - 1);
        List<MenuItem> menuItemList = selectedMenu.getMenuItems();

        while (true) {
            console.printNewLine();
            console.printLine();
            console.printTitle(selectedMenu.getCategory() + " MENU");
            console.displayMenuItems(menuItemList);

            console.printInfo("0. 뒤로 가기");
            console.printLine();
            console.printInput("원하는 메뉴의 번호를 입력해주세요.: ");

            int selectedItemIdx = console.getUserInput(sc, 0, menuItemList.size());
            if (selectedItemIdx == 0) {
                console.printInfo("이전 화면으로 돌아갑니다.");
                return;
            }

            MenuItem selectedItem = menuItemList.get(selectedItemIdx - 1);
            boolean isConfirmed = confirmAddToCart(sc, selectedItemIdx, selectedItem);
            if (isConfirmed) {
                addItemToCart(cart, selectedItem);
            }
        }
    }

    private boolean confirmAddToCart(Scanner sc, int selectedItemIdx, MenuItem selectedItem) {
        console.displayMenuItem(selectedItemIdx, selectedItem);
        console.printInfo("1. 확인");
        console.printInfo("2. 취소");
        console.printInput("선택하신 메뉴를 확인하시고 번호를 입력해주세요.: ");

        int selection = console.getUserInput(sc, 1, 2);
        return selection == 1;
    }

    private void addItemToCart(Cart cart, MenuItem selectedItem) {
        console.printInfo(selectedItem.getName() + "이(가) 장바구니에 추가되었습니다.");
        cart.addItem(selectedItem);
        console.displayCartStatus(cart);
    }


}
