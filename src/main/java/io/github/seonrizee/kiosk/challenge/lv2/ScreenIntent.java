package io.github.seonrizee.kiosk.challenge.lv2;

public class ScreenIntent {

    private Screen nextScreen;
    private Object data;

    public ScreenIntent(Screen nextScreen, Object data) {
        this.nextScreen = nextScreen;
        this.data = data;
    }

    public ScreenIntent(Screen nextScreen) {
        this(nextScreen, null);
    }

    public Screen getNextScreen() {
        return nextScreen;
    }

    public void setNextScreen(Screen nextScreen) {
        this.nextScreen = nextScreen;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
