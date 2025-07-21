package io.github.seonrizee.kiosk.challenge.lv2;

public enum DiscountType {
    GENERAL(1, 0, "일반"),
    SENIOR(2, 0.03, "노인"),
    STUDENT(3, 0.05, "학생"),
    SOLDIER(4, 0.1, "군인"),
    ;

    private final int dcCode;
    private final double dcRate;
    private final String desc;

    DiscountType(int dcCode, double dcRate, String desc) {
        this.dcCode = dcCode;
        this.dcRate = dcRate;
        this.desc = desc;

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
