package com.github.kittenslab.pomodoro_timer;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class MainViewModel implements Initializable {

    @FXML
    public StackPane root, userInterface;

    @FXML
    public Pane background;

    @FXML
    public Arc arc;

    @FXML
    public Circle circle;

    @FXML
    public Label minute;

    @FXML
    public Label time;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        MainModel model = new MainModel();

        AudioModel audio = new AudioModel();

        model.colorProperty().addListener(e -> root.setBackground(model.createBackground()));

        model.dateProperty().addListener(e -> arc.setLength(-model.calcArcLength()));

        model.calculatedMinuteProperty().addListener(e -> minute.setText(model.getCalculatedMinute() + "min"));

        model.inWorkProperty().addListener(e -> {if (model.isInWork()) audio.play(); else audio.stop();});

        root.setBackground(model.createBackground());

        circle.fillProperty().bind(model.colorProperty());

        time.textProperty().bind(model.currentTimeProperty());

        userInterface.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
            FadeTransition fadeTransitionUI = new FadeTransition(Duration.millis(500), userInterface);
            fadeTransitionUI.setFromValue(0);
            fadeTransitionUI.setToValue(1);

            FadeTransition fadeTransitionMinute = new FadeTransition(Duration.millis(500), minute);
            fadeTransitionMinute.setFromValue(1);
            fadeTransitionMinute.setToValue(0);

            ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(300), circle);
            scaleTransition.setFromX(0.5);
            scaleTransition.setFromY(0.5);
            scaleTransition.setToX(0.9);
            scaleTransition.setToY(0.9);

            ParallelTransition transition =
                    new ParallelTransition(fadeTransitionUI, fadeTransitionMinute, scaleTransition);
            transition.play();
        });

        userInterface.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
            FadeTransition fadeTransitionUI = new FadeTransition(Duration.millis(500), userInterface);
            fadeTransitionUI.setFromValue(1);
            fadeTransitionUI.setToValue(0);

            FadeTransition fadeTransitionMinute = new FadeTransition(Duration.millis(500), minute);
            fadeTransitionMinute.setFromValue(0);
            fadeTransitionMinute.setToValue(1);

            ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(300), circle);
            scaleTransition.setFromX(0.9);
            scaleTransition.setFromY(0.9);
            scaleTransition.setToX(0.5);
            scaleTransition.setToY(0.5);

            ParallelTransition transition =
                    new ParallelTransition(fadeTransitionUI, fadeTransitionMinute, scaleTransition);
            transition.play();
        });

        userInterface.addEventHandler(MouseEvent.DRAG_DETECTED, e ->
                model.setMouseCoordinate(e.getScreenX(), e.getScreenY()));

        userInterface.addEventHandler(MouseDragEvent.MOUSE_DRAGGED, e ->
                model.calcCoordinate(root.getScene().getWindow(), e.getScreenX(), e.getScreenY()));

        userInterface.addEventHandler(MouseDragEvent.MOUSE_DRAG_RELEASED, e -> model.resetCoordinate());
    }

    public void onAction() {
        ((Stage)root.getScene().getWindow()).close();
    }
}