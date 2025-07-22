package io.github.seonrizee.kiosk.challenge.lv2.application;

public class ScreenIntent {

    private final Screen nextScreen;
    private final int idxData;

    public ScreenIntent(Screen nextScreen, int idxData) {
        this.nextScreen = nextScreen;
        this.idxData = idxData;
    }

    public ScreenIntent(Screen nextScreen) {
        this(nextScreen, 0);
    }

    public Screen getNextScreen() {
        return nextScreen;
    }


    public int getData() {
        return idxData;
    }

}
