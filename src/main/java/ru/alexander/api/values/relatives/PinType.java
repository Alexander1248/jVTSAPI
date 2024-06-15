package ru.alexander.api.values.relatives;

import lombok.Getter;

@Getter
public enum PinType {
    Center("Center"),
    Random("Random");
    private final String value;

    PinType(String value) {
        this.value = value;
    }
}
