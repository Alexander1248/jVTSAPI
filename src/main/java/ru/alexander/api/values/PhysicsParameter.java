package ru.alexander.api.values;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

@Getter
public class PhysicsParameter {

    @SerializedName("id")
    private String id;
    @SerializedName("value")
    private float value;
    @SerializedName("setBaseValue")
    private boolean baseValue;
    @SerializedName("overrideSeconds")
    private float changeTime;

    public PhysicsParameter(String id, float value, boolean baseValue, float changeTime) {
        this.id = id;
        this.value = value;
        this.baseValue = baseValue;
        this.changeTime = changeTime;
    }
}
