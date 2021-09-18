package com.github.kittenslab.pomodoro_timer;

import javafx.scene.media.AudioClip;

import java.net.URISyntaxException;
import java.util.Objects;

public class AudioModel {

    private AudioClip clip;

    public AudioModel() {
        try {
            clip = new AudioClip(Objects.requireNonNull(App.class.getResource("MusMus-BGM-072.mp3")).toURI().toString());
            clip.setVolume(0.25);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void play() {
        clip.play();
    }

    public void stop() {
        clip.stop();
    }
}
