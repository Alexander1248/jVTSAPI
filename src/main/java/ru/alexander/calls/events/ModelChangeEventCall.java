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
public class ModelChangeEventCall implements APICall {
    private final String[] filter;
    private final ResponseListener<SubscriptionResponse> responseListener;
    private final EventListener<Response> eventListener;

    public ModelChangeEventCall(
            String[] filter,
            ResponseListener<SubscriptionResponse> responseListener,
            EventListener<Response> eventListener
    ) {
        this.filter = filter;
        this.responseListener = responseListener;
        this.eventListener = eventListener;
    }

    @Override
    public void execute(VTubeStudioAPI api) {
        Gson gson = api.getGson();
        LinkedTreeMap<String, Object> map = new LinkedTreeMap<>();
        map.put("modelID", filter);
        api.subscribe(
                "ModelLoadedEvent",
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
        private boolean loaded;
        @SerializedName("modelName")
        private String modelName;
        @SerializedName("modelID")
        private String modelID;
    }
}
