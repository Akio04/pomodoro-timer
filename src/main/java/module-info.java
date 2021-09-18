module com.github.kittenslab.pomodoro_timer {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    opens com.github.kittenslab.pomodoro_timer to javafx.fxml;
    exports com.github.kittenslab.pomodoro_timer;
}