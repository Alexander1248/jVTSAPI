package ru.alexander.api.values.relatives;

import lombok.Getter;

@Getter
public enum SizeRelative {
    ToWorld("RelativeToWorld"),
    ToCurrentItemSize("RelativeToCurrentItemSize");
    private final String value;

    SizeRelative(String value) {
        this.value = value;
    }
}
