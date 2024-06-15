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
public class TrackingChangeEventCall implements APICall {
    private final ResponseListener<SubscriptionResponse> responseListener;
    private final EventListener<Response> eventListener;

    public TrackingChangeEventCall(
            ResponseListener<SubscriptionResponse> responseListener,
            EventListener<Response> eventListener
    ) {
        this.responseListener = responseListener;
        this.eventListener = eventListener;
    }

    @Override
    public void execute(VTubeStudioAPI api) {
        Gson gson = api.getGson();
        api.subscribe(
                "TrackingStatusChangedEvent",
                null,
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
        @SerializedName("faceFound")
        private boolean faceFound;
        @SerializedName("leftHandFound")
        private boolean leftHandFound;
        @SerializedName("rightHandFound")
        private boolean rightHandFound;
    }
}
