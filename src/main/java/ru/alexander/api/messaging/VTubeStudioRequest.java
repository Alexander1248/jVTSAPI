package ru.alexander.api.messaging;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.LinkedTreeMap;
import lombok.Getter;

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
    private LinkedTreeMap<String, Object> data;

    public VTubeStudioRequest() {}

    protected VTubeStudioRequest(String id, String messageType, LinkedTreeMap<String, Object> data) {
        this.id = id;
        this.messageType = messageType;
        this.data = data;
    }

    public VTubeStudioRequest(String messageType, LinkedTreeMap<String, Object> data) {
        this(UUID.randomUUID().toString(), messageType, data);
    }

}
