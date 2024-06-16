package ru.alexander.api.messaging;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.UUID;

public class VTubeStudioRequest {
    @SerializedName("apiName")
    private String name = "VTubeStudioPublicAPI";
    @SerializedName("apiVersion")
    private String version = "1.0";

    @Getter
    @SerializedName("requestID")
    private String id;

    @Getter
    @SerializedName("messageType")
    private String messageType;

    @Getter
    @SerializedName("data")
    private LinkedHashMap<String, Object> data;

    public VTubeStudioRequest() {}

    protected VTubeStudioRequest(String id, String messageType, LinkedHashMap<String, Object> data) {
        this.id = id;
        this.messageType = messageType;
        this.data = data;
    }

    public VTubeStudioRequest(String messageType, LinkedHashMap<String, Object> data) {
        this(UUID.randomUUID().toString(), messageType, data);
    }

}
