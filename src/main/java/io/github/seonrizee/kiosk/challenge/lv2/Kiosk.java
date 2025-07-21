package io.github.seonrizee.kiosk.challenge.lv2;

import java.util.List;
import java.util.Scanner;

public class Kiosk {

    private final List<Menu> menuList;

    public Kiosk(List<Menu> menuList) {
        this.menuList = menuList;
    }

    public void start() {

        Cart cart = new Cart();
        try (Scanner sc = new Scanner(System.in)) {
            while (true) {

                printNewLine();
                printLine();
                printInfo("Welcome to Five Guys");
                printTitle("MAIN MENU");
                displayMenuList(menuList);

                int orderMinValidIdx = menuList.size();
                int orderMaxValidIdx = orderMinValidIdx + 2;
                if (!cart.isCartEmpty()) {
                    showOrderMenu(orderMinValidIdx);
                }
                printInfo("0. 종료");
                printLine();
                printInput("원하는 카테고리 또는 기능의 번호를 입력해주세요.: ");

                // TODO 카트가 비어있을 떄와 그렇지 않을 때의 입력범위 정리하기
                int selectedMenuIdx = getUserInput(sc, 0, orderMaxValidIdx);
                if (selectedMenuIdx == 0) {
                    printInfo("키오스크를 종료합니다.");
                    break;
                } else {
                    if (!cart.isCartEmpty() && selectedMenuIdx == 4) {
                        handleCheckoutOrder(sc, cart);
                    } else if (!cart.isCartEmpty() && selectedMenuIdx == 5) {
                        showCartUpdateMenu(sc, cart);
                    } else if (selectedMenuIdx <= orderMinValidIdx) {
                        showSubMenu(sc, selectedMenuIdx, cart);
                    }
                }

            }

        }
    }


    private void showOrderMenu(int minValidIdx) {
        printTitle("ORDER MENU");
        printInfo(minValidIdx + 1 + ". Orders   | 장바구니를 확인 후 주문합니다.");
        printInfo(minValidIdx + 2 + ". Edit     | 장바구니를 확인 후 수정합니다.");
    }

    private void handleCheckoutOrder(Scanner sc, Cart cart) {
        while (true) {
            if (cart.isCartEmpty()) {
                return;
            }

            showCartStatus(cart);
            printInfo("1. 주문");
            printInfo("0. 뒤로 가기");
            printInput("선택하신 메뉴를 확인하시고 번호를 입력해주세요.: ");

            int selectedIdx = getUserInput(sc, 0, 1);

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

    private void showCartUpdateMenu(Scanner sc, Cart cart) {
        while (true) {
            if (cart.isCartEmpty()) {
                printInfo("장바구니에 남은 메뉴가 없어 처음으로 돌아갑니다.");
                return;
            }

            showCartStatus(cart);
            printInfo("0. 뒤로 가기");
            printInput("선택하신 메뉴를 확인하시고 수정하고 싶은 메뉴의 번호를 입력해주세요.: ");

            int selectedIdx = getUserInput(sc, 0, cart.getCartItemList().size());
            if (selectedIdx == 0) {
                return;
            }

            updateCart(sc, cart, selectedIdx, cart.getCartItemList().get(selectedIdx - 1));
        }
    }

    private void updateCart(Scanner sc, Cart cart, int selection, CartItem cartItem) {

        displayCartItem(selection, cartItem);
        printInfo("1. 메뉴 수량 증가");
        printInfo("2. 메뉴 수량 감소");
        printInfo("3. 메뉴 삭제");
        printInfo("0. 뒤로 가기");
        printInput("원하는 기능의 메뉴를 고르세요.: ");

        int selectedIdx = getUserInput(sc, 0, 3);
        if (selectedIdx == 0) {
            return;
        }

        MenuItem selectedItem = cartItem.getItem();
        if (selectedItem == null) {
            return;
        }

        if (selectedIdx == 1) {
            cart.addItem(selectedItem);
        } else if (selectedIdx == 2) {
            cart.decreaseItem(selectedItem);
        } else if (selectedIdx == 3) {
            cart.removeItem(selectedItem);
        }


    }

    private void operateOrder(Cart cart, Discount dcType) {
        double discountedPrice = cart.getTotalPrice() -
                cart.getTotalPrice() * dcType.getDcRate();
        int oneEliminatedPrice = (int) (Math.floor(discountedPrice / 10) * 10);
        String formattedOriginalPrice = String.format("%,d", cart.getTotalPrice());
        String formattedPrice = String.format("%,d", oneEliminatedPrice);
        printInfo("할일 전 가격: " + formattedOriginalPrice + "원");
        printInfo("할인 후 가격: " + formattedPrice + "원");
        printInfo("주문이 완료되었습니다. " + formattedPrice + "원이 결제되었습니다. 감사합니다.");
        cart.clearCart();
    }

    private Discount confirmDiscount(Scanner sc) {
        int idx = 1;
        List<Discount> availableDiscounts = Discount.getAvailableDiscounts();
        for (Discount discount : availableDiscounts) {
            String formattedDiscountRate = String.format("%d. %-6s: %.0f%% 할인", idx++, discount.getDesc(),
                    discount.getDcRate() * 100);
            printInfo(formattedDiscountRate);
        }
        printInfo("0. 뒤로 가기");
        printInput("할인 정보를 확인하시고 번호를 입력해주세요.: ");

        int selectedDiscountIdx = getUserInput(sc, 0, availableDiscounts.size());
        if (selectedDiscountIdx == 0) {
            return null;
        }
        return Discount.findDiscount(selectedDiscountIdx);
    }

    private void showSubMenu(Scanner sc, int selectedMenuIdx, Cart cart) {

        Menu selectedMenu = menuList.get(selectedMenuIdx - 1);
        List<MenuItem> menuItemList = selectedMenu.getMenuItems();

        while (true) {
            printNewLine();
            printLine();
            printTitle(selectedMenu.getCategory() + " MENU");
            displayMenuItems(menuItemList);

            printInfo("0. 뒤로 가기");
            printLine();
            printInput("원하는 메뉴의 번호를 입력해주세요.: ");

            int selectedItemIdx = getUserInput(sc, 0, menuItemList.size());
            if (selectedItemIdx == 0) {
                printInfo("이전 화면으로 돌아갑니다.");
                return;
            }

            MenuItem selectedItem = menuItemList.get(selectedItemIdx - 1);
            boolean isConfirmed = confirmAddToCart(sc, selectedItemIdx, selectedItem);
            if (isConfirmed) {
                addItemToCart(cart, selectedItem);
            }
        }
    }

    private void showCartStatus(Cart cart) {
        printTitle("CART STATUS");
        displayCartItems(cart.getCartItemList());
        String formattedPrice = String.format("%,d", cart.getTotalPrice());
        printLine();
        printInfo("총 가격: " + formattedPrice + "원");
    }

    private boolean confirmAddToCart(Scanner sc, int selectedItemIdx, MenuItem selectedItem) {
        displayMenuItem(selectedItemIdx, selectedItem);
        printInfo("1. 확인");
        printInfo("2. 취소");
        printInput("선택하신 메뉴를 확인하시고 번호를 입력해주세요.: ");

        int selection = getUserInput(sc, 1, 2);
        return selection == 1;
    }

    private void addItemToCart(Cart cart, MenuItem selectedItem) {
        printInfo(selectedItem.getName() + "이(가) 장바구니에 추가되었습니다.");
        cart.addItem(selectedItem);
        showCartStatus(cart);
    }

    private int getUserInput(Scanner sc, int minValidIdx, int maxValidIdx) {
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

    private void displayCartItems(List<CartItem> cartItems) {
        for (int idx = 0; idx < cartItems.size(); idx++) {
            displayCartItem(idx + 1, cartItems.get(idx));
        }
    }

    private void displayCartItem(int idx, CartItem cartItem) {
        String format = "KIOSK:::: %d. %-16s | %3d개 | %,6d원\n";
        System.out.printf(format, idx, cartItem.getItem().getName(), cartItem.getQuantity(),
                cartItem.getSumItemPrice());
    }

    private void displayMenuList(List<Menu> menuList) {
        for (int idx = 0; idx < menuList.size(); idx++) {
            printInfo(idx + 1 + ". " + menuList.get(idx).getCategory());
        }
    }

    private void displayMenuItems(List<MenuItem> menuItems) {
        for (int idx = 0; idx < menuItems.size(); idx++) {
            displayMenuItem(idx + 1, menuItems.get(idx));
        }
    }

    private void displayMenuItem(int idx, MenuItem menuItem) {
        String format = "KIOSK:::: %d. %-16s | %,6d원 | %s\n";
        System.out.printf(format, idx, menuItem.getName(), menuItem.getPrice(), menuItem.getDesc());
    }

    private void printTitle(String msg) {
        System.out.println("KIOSK:::: [ " + msg + " ] ");
    }

    private void printInfo(String msg) {
        System.out.println("KIOSK:::: " + msg);
    }

    private void printError(String msg) {
        System.out.print("KIOSK_ERROR:::: " + msg + ":");
    }

    private void printInput(String msg) {
        System.out.print("KIOSK:::: " + msg);
    }

    private void printLine() {
        System.out.println("KIOSK:::: ====================================");
    }

    private void printNewLine() {
        System.out.println();
    }
}
