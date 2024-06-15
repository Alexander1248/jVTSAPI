package ru.alexander.api.values;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
public class Parameter {
    @NotNull
    @SerializedName("id")
    private String id;
    @SerializedName("weight")
    private Float weight;
    @SerializedName("value")
    private float value;

    public Parameter(@NotNull String id, Float weight, float value) {
        this.id = id;
        this.weight = weight;
        this.value = value;
    }
}
