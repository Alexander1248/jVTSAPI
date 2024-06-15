package ru.alexander.state;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import ru.alexander.api.VTubeStudioAPI;
import ru.alexander.api.VTubeStudioWS;
import ru.alexander.api.listeners.ResponseListener;
import ru.alexander.api.messaging.VTubeStudioRequest;
import ru.alexander.api.responses.ErrorResponse;

import java.util.Map;
import java.util.TreeMap;

public class StateMachine {
    @Getter(AccessLevel.PACKAGE)
    private final VTubeStudioWS api;
    private final Map<String, State> states = new TreeMap<>();
    public StateMachine(int port) {
        this.api = new VTubeStudioWS(port);
    }
    public StateMachine() {
        this(8001);
    }
    public Gson getGson() {
        return api.getGson();
    }

    public boolean addState(String name, State state) {
        if (states.containsKey(name)) return false;
        states.put(name, state);
        return true;
    }
    <T, D> void transit(State<T> from, String to, String messageType, D data) {
        Gson gson = getGson();
        api.send(new VTubeStudioRequest(
                        messageType,
                        gson.fromJson(gson.toJsonTree(data), LinkedTreeMap.class)
                ),
                new ResponseListener<>() {
                    @Override
                    public void onSuccess(LinkedTreeMap<String, Object> value) {
                        State state = states.get(to);
                        state.setValue(gson.fromJson(gson.toJsonTree(data),
                                state.getClass().getGenericInterfaces()[0]));
                    }

                    @Override
                    public void onFailure(ErrorResponse error) {
                        from.onError(error);
                    }
                }
        );
    }
}
