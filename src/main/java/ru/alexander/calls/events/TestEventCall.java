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

@Builder(builderMethodName = "hiddenBuilder")
public class TestEventCall implements APICall {
    private final String message;
    private final ResponseListener<SubscriptionResponse> responseListener;
    private final EventListener<Response> eventListener;

    public TestEventCall(
            String message,
            ResponseListener<SubscriptionResponse> responseListener,
            EventListener<Response> eventListener
    ) {
        this.message = message;
        this.responseListener = responseListener;
        this.eventListener = eventListener;
    }

    @Override
    public void execute(VTubeStudioAPI api) {
        Gson gson = api.getGson();
        LinkedTreeMap<String, Object> map = new LinkedTreeMap<>();
        map.put("testMessageForEvent", message);
        api.subscribe(
                "TestEvent",
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
        @SerializedName("yourTestMessage")
        private String message;
        @SerializedName("counter")
        private int counter;

        private Response(String message, int counter) {
            this.message = message;
            this.counter = counter;
        }
    }
    public static TestEventCallBuilder builder(String message) {
        return hiddenBuilder().message(message);
    }
}
