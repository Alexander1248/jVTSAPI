package ru.alexander.calls.events;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.LinkedTreeMap;
import lombok.Builder;
import lombok.Getter;
import ru.alexander.api.VTubeStudioAPI;
import ru.alexander.api.listeners.EventListener;
import ru.alexander.api.listeners.ResponseAdapter;
import ru.alexander.api.listeners.ResponseListener;
import ru.alexander.api.responses.ErrorResponse;
import ru.alexander.api.responses.SubscriptionResponse;
import ru.alexander.api.APICall;

@Builder
public class ModelClickedEventCall implements APICall {
    private final boolean onlyClicksOnModel;
    private final ResponseListener<SubscriptionResponse> responseListener;
    private final EventListener<Response> eventListener;

    public ModelClickedEventCall(
            boolean onlyClicksOnModel,
            ResponseListener<SubscriptionResponse> responseListener,
            EventListener<Response> eventListener
    ) {
        this.onlyClicksOnModel = onlyClicksOnModel;
        this.responseListener = responseListener;
        this.eventListener = eventListener;
    }

    @Override
    public void execute(VTubeStudioAPI api) {
        Gson gson = api.getGson();
        LinkedTreeMap<String, Object> map = new LinkedTreeMap<>();
        map.put("onlyClicksOnModel", onlyClicksOnModel);
        api.subscribe(
                "ModelClickedEvent",
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
        @SerializedName("modelLoaded")
        private boolean modelLoaded;
        @SerializedName("loadedModelID")
        private String loadedModelID;
        @SerializedName("loadedModelName")
        private String loadedModelName;
        @SerializedName("modelWasClicked")
        private boolean modelWasClicked;
        @SerializedName("mouseButtonID")
        private int mouseButtonID;
        @SerializedName("clickPosition")
        private Point clickPosition;
        @SerializedName("windowSize")
        private Resolution windowSize;
        @SerializedName("clickedArtMeshCount")
        private int clickedArtMeshCount;
        @SerializedName("artMeshHits")
        private ArtMeshHit[] artMeshHits;
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
    @Getter
    public static class ArtMeshHit {
        @SerializedName("artMeshOrder")
        private int artMeshOrder;
        @SerializedName("isMasked")
        private boolean isMasked;
        @SerializedName("hitInfo")
        private HitInfo hitInfo;
    }
    @Getter
    public static class HitInfo {
        @SerializedName("modelID")
        private String modelID;
        @SerializedName("artMeshID")
        private String artMeshID;
        @SerializedName("angle")
        private float angle;
        @SerializedName("size")
        private float size;

        @SerializedName("vertexID1")
        private int vertexID1;
        @SerializedName("vertexWeight1")
        private float vertexWeight1;

        @SerializedName("vertexID2")
        private int vertexID2;
        @SerializedName("vertexWeight2")
        private float vertexWeight2;

        @SerializedName("vertexID3")
        private int vertexID3;
        @SerializedName("vertexWeight3")
        private float vertexWeight3;
    }
}