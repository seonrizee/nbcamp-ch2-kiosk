package io.github.seonrizee.kiosk.challenge.lv2.domain;

import java.util.Arrays;
import java.util.List;

/**
 * 키오스크 주문 시 적용할 수 있는 할인 유형을 정의하는 Enum으로 코드, 할인율, 설명을 가집니다.
 */
public enum Discount {
    /**
     * 일반 결제 (할인 없음)
     */
    GENERAL(1, 0, "일반"),
    /**
     * 노인 할인 (3%)
     */
    SENIOR(2, 0.03, "노인"),
    /**
     * 학생 할인 (5%)
     */
    STUDENT(3, 0.05, "학생"),
    /**
     * 군인 할인 (10%)
     */
    SOLDIER(4, 0.1, "군인"),
    ;

    private final int dcCode;
    private final double dcRate;
    private final String desc;

    /**
     * 새로운 Discount 상수 인스턴스를 생성합니다.
     *
     * @param dcCode 할인 유형을 식별하는 고유 코드
     * @param dcRate 적용될 할인율 (예: 0.03은 3%를 의미)
     * @param desc   할인 유형에 대한 설명 (예: "노인")
     */
    Discount(int dcCode, double dcRate, String desc) {
        this.dcCode = dcCode;
        this.dcRate = dcRate;
        this.desc = desc;
    }

    /**
     * 주어진 할인 코드(dcCode)에 해당하는 {@code Discount} 상수를 찾습니다.
     *
     * @param dcCode 찾고자 하는 할인 유형의 고유 코드
     * @return 주어진 코드와 일치하는 {@code Discount} 상수
     * @throws IllegalArgumentException 주어진 코드가 유효한 할인 유형에 해당하지 않을 경우 발생
     */
    public static Discount findDiscount(int dcCode) {
        return Arrays.stream(Discount.values())
                .filter(discount -> discount.dcCode == dcCode)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 번호를 입력하셨습니다. 올바른 번호를 입력해주세요."));
    }

    /**
     * 사용 가능한 모든 할인 유형의 리스트를 반환합니다.
     *
     * @return 수정 불가능한 {@code Discount} 상수의 리스트
     */
    public static List<Discount> getAvailableDiscounts() {
        return List.of(values());
    }

    /**
     * 할인 유형의 고유 코드를 반환합니다.
     *
     * @return 할인 코드 (int)
     */
    public int getDcCode() {
        return dcCode;
    }

    /**
     * 할인 유형의 할인율을 반환합니다.
     *
     * @return 할인율 (double)
     */
    public double getDcRate() {
        return dcRate;
    }

    /**
     * 할인 유형의 설명을 반환합니다.
     *
     * @return 할인 설명 (String)
     */
    public String getDesc() {
        return desc;
    }
}