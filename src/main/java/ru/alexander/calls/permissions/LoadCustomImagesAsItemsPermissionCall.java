package ru.alexander.calls.permissions;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import ru.alexander.api.APICall;
import ru.alexander.api.VTubeStudioAPI;
import ru.alexander.api.listeners.ResponseListener;
import ru.alexander.api.responses.ErrorResponse;
import ru.alexander.api.responses.PermissionResponse;

public class LoadCustomImagesAsItemsPermissionCall implements APICall {
    private final  ResponseListener<PermissionResponse> listener;

    public LoadCustomImagesAsItemsPermissionCall(ResponseListener<PermissionResponse> listener) {
        this.listener = listener;
    }

    @Override
    public void execute(VTubeStudioAPI api) {
        Gson gson = api.getGson();
        api.requestPermission("LoadCustomImagesAsItems",
                new ResponseListener<>() {
                    @Override
                    public void onSuccess(LinkedTreeMap<String, Object> value) {
                        listener.onSuccess(gson.fromJson(gson.toJsonTree(value), PermissionResponse.class));
                    }

                    @Override
                    public void onFailure(ErrorResponse error) {
                        listener.onFailure(error);
                    }
                });
    }
}
