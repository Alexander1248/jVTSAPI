package ru.alexander.calls.events;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.LinkedTreeMap;
import lombok.Builder;
import lombok.Getter;
import ru.alexander.api.APICall;
import ru.alexander.api.VTubeStudioAPI;
import ru.alexander.api.listeners.EventListener;
import ru.alexander.api.listeners.ResponseAdapter;
import ru.alexander.api.listeners.ResponseListener;
import ru.alexander.api.responses.ErrorResponse;
import ru.alexander.api.responses.SubscriptionResponse;

@Builder
public class ModelOutlineEventCall implements APICall {
    private final Boolean draw;
    private final ResponseListener<SubscriptionResponse> responseListener;
    private final EventListener<Response> eventListener;

    public ModelOutlineEventCall(
            Boolean draw,
            ResponseListener<SubscriptionResponse> responseListener,
            EventListener<Response> eventListener
    ) {
        this.draw = draw;
        this.responseListener = responseListener;
        this.eventListener = eventListener;
    }

    @Override
    public void execute(VTubeStudioAPI api) {
        Gson gson = api.getGson();
        LinkedTreeMap<String, Object> map = new LinkedTreeMap<>();
        map.put("draw", draw);
        api.subscribe(
                "ModelOutlineEvent",
                map,
                responseListener == null ? null : new ResponseAdapter<>() {
                    @Override
                    public void onSuccess(LinkedTreeMap<String, Object> value) {
                        responseListener.onSuccess(gson.fromJson(gson.toJsonTree(value), SubscriptionResponse.class));
                    }

                    @Override
                    public void onFailure(ErrorResponse error) {
                        responseListener.onFailure(error);
                    }
                },
                eventListener == null ? null : value ->
                        eventListener.onEvent(gson.fromJson(gson.toJsonTree(value), Response.class))
        );
    }

    @Getter
    public static class Response {
        @SerializedName("modelName")
        private String modelName;
        @SerializedName("modelID")
        private String modelID;
        @SerializedName("convexHull")
        private Point[] convexHull;
        @SerializedName("convexHullCenter")
        private Point center;
        @SerializedName("windowSize")
        private Resolution windowSize;
    }

    @Getter
    public static class Point {
        @SerializedName("x")
        private float x;
        @SerializedName("y")
        private float y;
    }

    @Getter
    public static class Resolution {
        @SerializedName("x")
        private int x;
        @SerializedName("y")
        private int y;
    }
}