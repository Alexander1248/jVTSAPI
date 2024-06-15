package ru.alexander.api.values;

import lombok.Getter;

@Getter
public enum FadeMode {
    Linear("linear"),
    EaseIn("easeIn"),
    EaseOut("easeOut"),
    EaseBoth("easeBoth"),
    Overshoot("overshoot"),
    Zip("zip");
    private final String value;

    FadeMode(String value) {
        this.value = value;
    }
}
