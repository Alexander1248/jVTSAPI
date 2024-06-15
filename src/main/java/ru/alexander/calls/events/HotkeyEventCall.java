package ru.alexander.calls.events;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.LinkedTreeMap;
import lombok.Builder;
import lombok.Getter;
import ru.alexander.api.APICall;
import ru.alexander.api.HotkeyAction;
import ru.alexander.api.VTubeStudioAPI;
import ru.alexander.api.listeners.EventListener;
import ru.alexander.api.listeners.ResponseAdapter;
import ru.alexander.api.listeners.ResponseListener;
import ru.alexander.api.responses.ErrorResponse;
import ru.alexander.api.responses.SubscriptionResponse;

@Builder
public class HotkeyEventCall implements APICall {
    private final HotkeyAction action;
    private final boolean ignoreTriggeredByAPI;
    private final ResponseListener<SubscriptionResponse> responseListener;
    private final EventListener<Response> eventListener;

    public HotkeyEventCall(
            HotkeyAction action,
            boolean ignoreTriggeredByAPI,
            ResponseListener<SubscriptionResponse> responseListener,
            EventListener<Response> eventListener
    ) {
        this.action = action;
        this.ignoreTriggeredByAPI = ignoreTriggeredByAPI;
        this.responseListener = responseListener;
        this.eventListener = eventListener;
    }

    @Override
    public void execute(VTubeStudioAPI api) {
        Gson gson = api.getGson();
        LinkedTreeMap<String, Object> map = new LinkedTreeMap<>();
        map.put("onlyForAction", action.toString());
        map.put("ignoreHotkeysTriggeredByAPI", ignoreTriggeredByAPI);
        api.subscribe(
                "HotkeyTriggeredEvent",
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

    public static class Response {
        @Getter
        @SerializedName("hotkeyID")
        private String hotkeyID;
        @Getter
        @SerializedName("hotkeyName")
        private String hotkeyName;
        @SerializedName("hotkeyAction")
        private String hotkeyAction;
        @Getter
        @SerializedName("hotkeyFile")
        private String hotkeyFile;
        @Getter
        @SerializedName("hotkeyTriggeredByAPI")
        private boolean hotkeyTriggeredByAPI;

        @Getter
        @SerializedName("modelID")
        private String modelID;
        @Getter
        @SerializedName("modelName")
        private String modelName;
        @Getter
        @SerializedName("isLive2DItem")
        private boolean isLive2DItem;


        public HotkeyAction getHotkeyAction() {
            return HotkeyAction.valueOf(hotkeyAction);
        }
    }
}