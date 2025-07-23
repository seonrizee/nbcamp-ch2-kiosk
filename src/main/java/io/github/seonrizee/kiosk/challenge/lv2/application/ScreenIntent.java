package io.github.seonrizee.kiosk.challenge.lv2.application;

/**
 * 키오스크의 다른 화면으로 전환할 때 필요한 Intent를 나타내는 클래스.
 */
public class ScreenIntent {

    /**
     * 다음에 표시될 화면의 상태를 나타내는 {@link Screen} 상수.
     */
    private final Screen nextScreen;

    /**
     * 화면 전환 시 전달할 정수형 데이터. 예를 들어, 특정 메뉴를 선택했을 때 해당 메뉴의 인덱스를 전달하는 데 사용됩니다.
     */
    private final int idxData;

    /**
     * 다음에 표시할 화면과 함께 전달할 데이터를 사용하여 새로운 ScreenIntent 인스턴스를 생성합니다.
     *
     * @param nextScreen 다음에 표시할 {@link Screen}
     * @param idxData    전달할 정수형 데이터
     */
    public ScreenIntent(Screen nextScreen, int idxData) {
        this.nextScreen = nextScreen;
        this.idxData = idxData;
    }

    /**
     * 다음에 표시할 화면 정보만으로 새로운 ScreenIntent 인스턴스를 생성합니다.
     *
     * @param nextScreen 다음에 표시할 {@link Screen}
     */
    public ScreenIntent(Screen nextScreen) {
        this(nextScreen, 0);
    }

    /**
     * 이 인텐트가 가리키는 다음 화면({@link Screen})을 반환합니다.
     *
     * @return 다음 화면의 {@link Screen} 상수
     */
    public Screen getNextScreen() {
        return nextScreen;
    }


    /**
     * 이 인텐트에 포함된 정수형 데이터를 반환합니다.
     *
     * @return 전달된 정수형 데이터
     */
    public int getIdxData() {
        return idxData;
    }

}