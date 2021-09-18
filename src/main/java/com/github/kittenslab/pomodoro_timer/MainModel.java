package com.github.kittenslab.pomodoro_timer;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.*;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Window;
import javafx.util.Duration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MainModel {

    private final DoubleProperty x;

    private final DoubleProperty y;

    private final DoubleProperty mouseX;

    private final DoubleProperty mouseY;

    private final ObjectProperty<LocalDateTime> date;

    private final IntegerProperty sessionCount;

    private final IntegerProperty currentMinutes;

    private final IntegerProperty currentSeconds;

    private final IntegerProperty calculatedMinute;

    private final StringProperty currentTime;

    private final BooleanProperty inWork;

    private final ObjectProperty<Color> color;

    private static final double oneCycleSeconds = 1800;

    private static final Color inWorkColor = Color.SLATEGRAY;

    private static final Color inRestColor = Color.STEELBLUE;

    public MainModel(){
        this.x = new SimpleDoubleProperty(Double.NaN);
        this.y = new SimpleDoubleProperty(Double.NaN);
        this.mouseX = new SimpleDoubleProperty(Double.NaN);
        this.mouseY = new SimpleDoubleProperty(Double.NaN);
        this.date = new SimpleObjectProperty<>(LocalDateTime.now());
        this.sessionCount = new SimpleIntegerProperty(0);
        this.currentMinutes = new SimpleIntegerProperty(-1);
        this.currentSeconds = new SimpleIntegerProperty(-1);
        this.calculatedMinute = new SimpleIntegerProperty(-1);
        this.inWork = new SimpleBooleanProperty(true);
        this.color = new SimpleObjectProperty<>(inWorkColor);
        this.currentTime = new SimpleStringProperty("");

        date.addListener(e -> {
            int minute = date.get().getMinute();
            if (minute != currentSeconds.get())
                currentMinutes.set(minute);

            int second = date.get().getSecond();
            if (second != currentSeconds.get())
                currentSeconds.set(second);

            checkTime();
        });

        currentSeconds.addListener(e -> currentTime.set(date.get().format(DateTimeFormatter.ofPattern("HH:mm:ss"))));

        currentMinutes.addListener(e -> calculatedMinute.set(currentMinutes.get() % 30));

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(50), e -> {
            date.set(LocalDateTime.now());
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public double calcArcLength() {

        int minute = getCalculatedMinute();
        int second = getCurrentSeconds();

        double rate = (minute * 60 + second) / oneCycleSeconds;

        return 360 * rate;
    }

    private void checkTime() {
        int minute = getCalculatedMinute();
        if (minute < 25) {
            if (!isInWork()) {
                setInWork(true);
                setColor(inWorkColor);
            }
        } else {
            if (isInWork()) {
                setInWork(false);
                setColor(inRestColor);
            }
        }
    }

    public Background createBackground() {
        return new Background(new BackgroundFill(getColor(), CornerRadii.EMPTY, Insets.EMPTY));
    }

    public void setMouseCoordinate(double mouseX, double mouseY) {
        setMouseX(mouseX);
        setMouseY(mouseY);
    }

    public void calcCoordinate(Window window, double x, double y) {

        if (Double.isNaN(getX()) || Double.isNaN(getY())) {
            setX(x);
            setY(y);
            return;
        }

        double dx = getX() - x;
        double dy = getY() - y;

        setX(x);
        setY(y);

        window.setX(window.getX() - dx);
        window.setY(window.getY() - dy);
    }

    public void resetCoordinate() {

        setX(Double.NaN);
        setY(Double.NaN);

        setMouseX(Double.NaN);
        setMouseY(Double.NaN);
    }

    public double getX() {
        return x.get();
    }

    public DoubleProperty xProperty() {
        return x;
    }

    public void setX(double x) {
        this.x.set(x);
    }

    public double getY() {
        return y.get();
    }

    public DoubleProperty yProperty() {
        return y;
    }

    public void setY(double y) {
        this.y.set(y);
    }

    public double getMouseX() {
        return mouseX.get();
    }

    public DoubleProperty mouseXProperty() {
        return mouseX;
    }

    public void setMouseX(double mouseX) {
        this.mouseX.set(mouseX);
    }

    public double getMouseY() {
        return mouseY.get();
    }

    public DoubleProperty mouseYProperty() {
        return mouseY;
    }

    public void setMouseY(double mouseY) {
        this.mouseY.set(mouseY);
    }

    public LocalDateTime getDate() {
        return date.get();
    }

    public ObjectProperty<LocalDateTime> dateProperty() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date.set(date);
    }

    public int getSessionCount() {
        return sessionCount.get();
    }

    public IntegerProperty sessionCountProperty() {
        return sessionCount;
    }

    public void setSessionCount(int sessionCount) {
        this.sessionCount.set(sessionCount);
    }

    public String getCurrentTime() {
        return currentTime.get();
    }

    public StringProperty currentTimeProperty() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime.set(currentTime);
    }

    public int getCurrentMinutes() {
        return currentMinutes.get();
    }

    public IntegerProperty currentMinutesProperty() {
        return currentMinutes;
    }

    public void setCurrentMinutes(int currentMinutes) {
        this.currentMinutes.set(currentMinutes);
    }

    public int getCurrentSeconds() {
        return currentSeconds.get();
    }

    public IntegerProperty currentSecondsProperty() {
        return currentSeconds;
    }

    public void setCurrentSeconds(int currentSeconds) {
        this.currentSeconds.set(currentSeconds);
    }

    public boolean isInWork() {
        return inWork.get();
    }

    public BooleanProperty inWorkProperty() {
        return inWork;
    }

    public void setInWork(boolean inWork) {
        this.inWork.set(inWork);
    }

    public int getCalculatedMinute() {
        return calculatedMinute.get();
    }

    public IntegerProperty calculatedMinuteProperty() {
        return calculatedMinute;
    }

    public void setCalculatedMinute(int calculatedMinute) {
        this.calculatedMinute.set(calculatedMinute);
    }

    public Color getColor() {
        return color.get();
    }

    public ObjectProperty<Color> colorProperty() {
        return color;
    }

    public void setColor(Color color) {
        this.color.set(color);
    }
}
