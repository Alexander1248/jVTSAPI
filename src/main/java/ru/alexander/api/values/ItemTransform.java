package ru.alexander.api.values;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
public class ItemTransform {
    @NotNull
    @SerializedName("itemInstanceID")
    private String instanceID;
    @SerializedName("timeInSeconds")
    private float time;
    @SerializedName("fadeMode")
    private String mode;
    @SerializedName("positionX")
    private Float x;
    @SerializedName("positionY")
    private Float y;
    @SerializedName("size")
    private Float size;
    @SerializedName("rotation")
    private Float rotation;
    @SerializedName("order")
    private Integer order;
    @SerializedName("setFlip")
    private boolean setFlip;
    @SerializedName("flip")
    private Boolean flip;
    @SerializedName("userCanStop")
    private boolean canStop;

    public ItemTransform(@NotNull String instanceID, float time, FadeMode mode,
                         Float x, Float y, Float size, Float rotation, Integer order,
                         boolean canStop) {
        this.instanceID = instanceID;
        this.time = time;
        this.mode = mode.getValue();
        this.x = x;
        this.y = y;
        this.size = size;
        this.rotation = rotation;
        this.order = order;
        this.setFlip = false;
        this.canStop = canStop;
    }
    public ItemTransform(@NotNull String instanceID, float time, FadeMode mode,
                         Float x, Float y, Float size, Float rotation, Integer order,
                         boolean canStop, boolean flip) {
        this.instanceID = instanceID;
        this.time = time;
        this.mode = mode.getValue();
        this.x = x;
        this.y = y;
        this.size = size;
        this.rotation = rotation;
        this.order = order;
        this.setFlip = true;
        this.flip = flip;
        this.canStop = canStop;
    }
}
