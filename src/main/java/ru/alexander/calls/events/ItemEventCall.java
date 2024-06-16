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

import java.util.LinkedHashMap;

@Builder
public class ItemEventCall implements APICall {
    private final String[] itemInstanceIDs;
    private final String[] itemFileNames;
    private final ResponseListener<SubscriptionResponse> responseListener;
    private final EventListener<Response> eventListener;

    public ItemEventCall(
            String[] itemInstanceIDs,
            String[] itemFileNames,
            ResponseListener<SubscriptionResponse> responseListener,
            EventListener<Response> eventListener
    ) {
        this.itemInstanceIDs = itemInstanceIDs;
        this.itemFileNames = itemFileNames;
        this.responseListener = responseListener;
        this.eventListener = eventListener;
    }

    @Override
    public void execute(VTubeStudioAPI api) {
        Gson gson = api.getGson();
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("itemInstanceIDs", itemInstanceIDs);
        map.put("itemFileNames", itemFileNames);
        api.subscribe(
                "ItemEvent",
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
        @SerializedName("itemEventType")
        private String eventType;
        @Getter
        @SerializedName("itemInstanceID")
        private String instanceID;
        @Getter
        @SerializedName("itemFileName")
        private String fileName;
        @Getter
        @SerializedName("itemPosition")
        private Point position;


        public EventType getEventType() {
            return EventType.valueOf(eventType);
        }
    }

    @Getter
    public static class Point {
        @SerializedName("x")
        private float x;
        @SerializedName("y")
        private float y;
    }
    public enum EventType {
        Added,
        Removed,
        DroppedPinned,
        DroppedUnpinned,
        Clicked,
        Locked,
        Unlocked
    }
}