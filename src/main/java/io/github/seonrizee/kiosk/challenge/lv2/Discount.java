package io.github.seonrizee.kiosk.challenge.lv2;

import java.util.Arrays;
import java.util.List;

public enum Discount {
    GENERAL(1, 0, "일반"),
    SENIOR(2, 0.03, "노인"),
    STUDENT(3, 0.05, "학생"),
    SOLDIER(4, 0.1, "군인"),
    ;

    private final int dcCode;
    private final double dcRate;
    private final String desc;

    Discount(int dcCode, double dcRate, String desc) {
        this.dcCode = dcCode;
        this.dcRate = dcRate;
        this.desc = desc;
    }

    public static Discount findDiscount(int dcCode) {
        return Arrays.stream(Discount.values())
                .filter(discount -> discount.dcCode == dcCode)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 번호를 입력하셨습니다. 올바른 번호를 입력해주세요."));
    }

    public static List<Discount> getAvailableDiscounts() {
        return List.of(values());
    }

    public int getDcCode() {
        return dcCode;
    }

    public double getDcRate() {
        return dcRate;
    }

    public String getDesc() {
        return desc;
    }
}
