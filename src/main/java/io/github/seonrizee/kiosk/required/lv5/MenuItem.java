package io.github.seonrizee.kiosk.required.lv5;

public class MenuItem {

    private final String name;
    private final int price;
    private final String desc;

    public MenuItem(String name, int price, String desc) {
        this.name = name;
        this.price = price;
        this.desc = desc;
    }

    public String getName() {
        return name;
    }


    public int getPrice() {
        return price;
    }


    public String getDesc() {
        return desc;
    }

}
