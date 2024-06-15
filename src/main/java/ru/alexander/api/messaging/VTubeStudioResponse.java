package ru.alexander.api.messaging;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

@Getter
public class VTubeStudioResponse extends VTubeStudioRequest {

    @SerializedName("timestamp")
    private long timestamp;
}
