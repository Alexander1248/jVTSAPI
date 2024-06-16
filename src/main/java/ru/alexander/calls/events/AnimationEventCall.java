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

import java.util.LinkedHashMap;

@Builder
public class AnimationEventCall implements APICall {
    private final boolean ignoreLive2DItems;
    private final boolean ignoreIdleAnimations;
    private final ResponseListener<SubscriptionResponse> responseListener;
    private final EventListener<Response> eventListener;

    public AnimationEventCall(
            boolean ignoreLive2DItems,
            boolean ignoreIdleAnimations,
            ResponseListener<SubscriptionResponse> responseListener,
            EventListener<Response> eventListener
    ) {
        this.ignoreLive2DItems = ignoreLive2DItems;
        this.ignoreIdleAnimations = ignoreIdleAnimations;
        this.responseListener = responseListener;
        this.eventListener = eventListener;
    }

    @Override
    public void execute(VTubeStudioAPI api) {
        Gson gson = api.getGson();
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("ignoreLive2DItems", ignoreLive2DItems);
        map.put("ignoreIdleAnimations", ignoreIdleAnimations);
        api.subscribe(
                "ModelAnimationEvent",
                map,
                responseListener == null ? null : new ResponseAdapter<>() {
                    @Override
                    public void onSuccess(LinkedHashMap<String, Object> value) {
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

    public static class Response {
        @SerializedName("animationEventType")
        private String eventType;
        @Getter
        @SerializedName("animationEventTime")
        private float eventTime;
        @Getter
        @SerializedName("animationEventData")
        private String eventData;
        @Getter
        @SerializedName("animationName")
        private String animationName;
        @Getter
        @SerializedName("animationLength")
        private float animationLength;
        @Getter
        @SerializedName("isIdleAnimation")
        private boolean isIdleAnimation;

        @Getter
        @SerializedName("modelID")
        private String modelID;
        @Getter
        @SerializedName("modelName")
        private String modelName;
        @Getter
        @SerializedName("isLive2DItem")
        private boolean isLive2DItem;


        public EventType getEventType() {
            return EventType.valueOf(eventType);
        }
    }

    public enum EventType {
        Start,
        End,
        Custom
    }
}