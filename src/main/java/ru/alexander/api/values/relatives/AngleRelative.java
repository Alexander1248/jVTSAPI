package ru.alexander.api.values.relatives;

import lombok.Getter;

@Getter
public enum AngleRelative {
    ToWorld("RelativeToWorld"),
    ToCurrentItemRotation("RelativeToCurrentItemRotation"),
    ToModel("RelativeToModel"),
    ToPinPosition("RelativeToPinPosition");

    private final String value;

    AngleRelative(String value) {
        this.value = value;
    }
}
