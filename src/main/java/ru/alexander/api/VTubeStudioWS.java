package ru.alexander.api;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import lombok.Getter;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import ru.alexander.api.listeners.EventListener;
import ru.alexander.api.listeners.ResponseListener;
import ru.alexander.api.messaging.VTubeStudioRequest;
import ru.alexander.api.messaging.VTubeStudioResponse;
import ru.alexander.api.responses.ErrorResponse;

import java.net.URI;
import java.util.Map;
import java.util.TreeMap;

public class VTubeStudioWS extends WebSocketClient {
    @Getter
    private final Gson gson;
    private final Map<String, ResponseListener<LinkedTreeMap<String, Object>>> responseListeners = new TreeMap<>();
    private final Map<String, EventListener<LinkedTreeMap<String, Object>>> eventListeners = new TreeMap<>();

    public VTubeStudioWS(int port) {
        super(URI.create(String.format("ws://localhost:%d", port)));
        gson = new Gson();
    }
    public void send(VTubeStudioRequest request,
                     ResponseListener<LinkedTreeMap<String, Object>> response) {
        responseListeners.put(request.getId(), response);
        send(gson.toJson(request));
    }
    public void subscribe(
            String channel,
            EventListener<LinkedTreeMap<String, Object>> listener
    ) {
        eventListeners.put(channel, listener);
    }
    public void unsubscribe(
            String channel
    ) {
        eventListeners.remove(channel);
    }


    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        System.out.println("Server opened!");
    }

    @Override
    public void onMessage(String s) {
        VTubeStudioResponse response = gson.fromJson(s, VTubeStudioResponse.class);
        if (responseListeners.containsKey(response.getId())) {
            ResponseListener<LinkedTreeMap<String, Object>> listener = responseListeners.remove(response.getId());
            if (listener != null) {
            if (response.getMessageType().equals("APIError"))
                listener.onFailure(gson.fromJson(gson.toJsonTree(response.getData()), ErrorResponse.class));
            else
                listener.onSuccess(response.getData());
            }
        }
        if (response.getMessageType().contains("Event") && eventListeners.containsKey(response.getMessageType())) {
            EventListener<LinkedTreeMap<String, Object>> listener = eventListeners.get(response.getMessageType());
            if (listener != null) listener.onEvent(response.getData());
        }
    }

    @Override
    public void onClose(int i, String s, boolean b) {
        System.out.println("Server closed!");
    }

    @Override
    public void onError(Exception e) {
        e.printStackTrace();
    }
}
