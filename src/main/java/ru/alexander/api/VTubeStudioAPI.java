package ru.alexander.api;

import com.google.gson.Gson;
import org.jetbrains.annotations.NotNull;
import ru.alexander.api.listeners.EventListener;
import ru.alexander.api.listeners.ResponseListener;
import ru.alexander.api.values.ArtMeshMatcher;
import ru.alexander.api.messaging.VTubeStudioRequest;
import ru.alexander.api.values.ItemTransform;
import ru.alexander.api.values.Parameter;
import ru.alexander.api.values.PhysicsParameter;
import ru.alexander.api.values.relatives.AngleRelative;
import ru.alexander.api.values.relatives.PinType;
import ru.alexander.api.values.relatives.SizeRelative;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.List;


@SuppressWarnings({"unused"})
public class VTubeStudioAPI {
    private final VTubeStudioWS ws;

    public VTubeStudioAPI() {
        this(8001);
    }

    public VTubeStudioAPI(int port) {
        ws = new VTubeStudioWS(port);
        ws.connect();
    }
    public Gson getGson() {
        return ws.getGson();
    }

    public void state(ResponseListener<
            LinkedHashMap<String, Object>
            > response) {
        VTubeStudioRequest request = new VTubeStudioRequest(
                "APIStateRequest",
                null
        );
        ws.send(request, response);
    }

    public void requestToken(
            @NotNull String pluginName,
            @NotNull String developer,
            InputStream iconStream,
            ResponseListener<LinkedHashMap<String, Object>> response
    ) throws IOException {
        LinkedHashMap<String, Object> object = new LinkedHashMap<>();
        object.put("pluginName", pluginName);
        object.put("pluginDeveloper", developer);

        if (iconStream != null)
            object.put("pluginIcon", Base64.getEncoder().encodeToString(iconStream.readAllBytes()));

        VTubeStudioRequest request = new VTubeStudioRequest(
                "AuthenticationTokenRequest",
                object
        );
        ws.send(request, response);
    }

    public void requestToken(
            @NotNull String pluginName,
            @NotNull String developer,
            ResponseListener<LinkedHashMap<String, Object>> response
    ) {
        try {
            requestToken(pluginName, developer, null, response);
        } catch (IOException ignored) {
        }
    }

    public void auth(
            @NotNull String pluginName,
            @NotNull String developer,
            @NotNull String token,
            ResponseListener<LinkedHashMap<String, Object>> response
    ) {
        LinkedHashMap<String, Object> object = new LinkedHashMap<>();
        object.put("pluginName", pluginName);
        object.put("pluginDeveloper", developer);
        object.put("authenticationToken", token);
        VTubeStudioRequest request = new VTubeStudioRequest(
                "AuthenticationRequest",
                object
        );
        ws.send(request, response);
    }

    public void subscribe(
            @NotNull String eventName,
            LinkedHashMap<String, Object> config,
            ResponseListener<LinkedHashMap<String, Object>> responseListener,
            EventListener<LinkedHashMap<String, Object>> eventListener
    ) {
        LinkedHashMap<String, Object> object = new LinkedHashMap<>();
        object.put("eventName", eventName);
        object.put("subscribe", true);
        object.put("config", config);
        VTubeStudioRequest request = new VTubeStudioRequest(
                "EventSubscriptionRequest",
                object
        );
        ws.subscribe(eventName, eventListener);
        ws.send(request, responseListener);
    }

    public void unsubscribe(
            @NotNull String eventName,
            ResponseListener<LinkedHashMap<String, Object>> listener
    ) {
        LinkedHashMap<String, Object> object = new LinkedHashMap<>();
        object.put("eventName", eventName);
        object.put("subscribe", false);
        VTubeStudioRequest request = new VTubeStudioRequest(
                "EventSubscriptionRequest",
                object
        );
        ws.unsubscribe(eventName);
        ws.send(request, listener);
    }

    public void requestPermission(
            @NotNull String permission,
            ResponseListener<LinkedHashMap<String, Object>> response
    ) {
        LinkedHashMap<String, Object> object = new LinkedHashMap<>();
        object.put("requestedPermission", permission);
        VTubeStudioRequest request = new VTubeStudioRequest(
                "PermissionRequest",
                object
        );
        ws.send(request, response);
    }

    public void statistics(ResponseListener<
            LinkedHashMap<String, Object>
            > response) {
        VTubeStudioRequest request = new VTubeStudioRequest(
                "StatisticsRequest",
                null
        );
        ws.send(request, response);
    }

    public void folderInfo(ResponseListener<
            LinkedHashMap<String, Object>
            > response) {
        VTubeStudioRequest request = new VTubeStudioRequest(
                "VTSFolderInfoRequest",
                null
        );
        ws.send(request, response);
    }

    public void availableModels(ResponseListener<
            LinkedHashMap<String, Object>
            > response) {
        VTubeStudioRequest request = new VTubeStudioRequest(
                "AvailableModelsRequest",
                null
        );
        ws.send(request, response);
    }

    public void currentModel(ResponseListener<
            LinkedHashMap<String, Object>
            > response) {
        VTubeStudioRequest request = new VTubeStudioRequest(
                "CurrentModelRequest",
                null
        );
        ws.send(request, response);
    }

    public void loadModel(
            @NotNull String modelID,
            ResponseListener<LinkedHashMap<String, Object>> response) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("modelID", modelID);
        VTubeStudioRequest request = new VTubeStudioRequest(
                "ModelLoadRequest",
                map
        );
        ws.send(request, response);
    }

    public void moveModel(
            float timeInSeconds,
            boolean valuesAreRelativeToModel,
            Float x,
            Float y,
            Float rotation,
            Float size,
            ResponseListener<LinkedHashMap<String, Object>> response
    ) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("timeInSeconds", timeInSeconds);
        map.put("valuesAreRelativeToModel", valuesAreRelativeToModel);
        map.put("positionX", x);
        map.put("positionY", y);
        map.put("rotation", rotation);
        map.put("size", size);
        VTubeStudioRequest request = new VTubeStudioRequest(
                "MoveModelRequest",
                map
        );
        ws.send(request, response);
    }

    public void modelHotkeys(
            @NotNull String modelID,
            ResponseListener<LinkedHashMap<String, Object>> response) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("modelID", modelID);
        VTubeStudioRequest request = new VTubeStudioRequest(
                "HotkeysInCurrentModelRequest",
                map
        );
        ws.send(request, response);
    }

    public void triggerModelHotkey(
            @NotNull String hotkeyID,
            ResponseListener<LinkedHashMap<String, Object>> response) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("hotkeyID", hotkeyID);
        VTubeStudioRequest request = new VTubeStudioRequest(
                "HotkeyTriggerRequest",
                map
        );
        ws.send(request, response);
    }

    public void itemHotkeys(
            @NotNull String itemFileName,
            ResponseListener<LinkedHashMap<String, Object>> response) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("live2DItemFileName", itemFileName);
        VTubeStudioRequest request = new VTubeStudioRequest(
                "HotkeysInCurrentModelRequest",
                map
        );
        ws.send(request, response);
    }

    public void triggerItemHotkeys(
            @NotNull String hotkeyID,
            @NotNull String itemInstanceID,
            ResponseListener<LinkedHashMap<String, Object>> response) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("hotkeyID", hotkeyID);
        map.put("itemInstanceID", itemInstanceID);
        VTubeStudioRequest request = new VTubeStudioRequest(
                "HotkeyTriggerRequest",
                map
        );
        ws.send(request, response);
    }

    public void expressions(
            boolean detailed,
            String expressionFile,
            ResponseListener<LinkedHashMap<String, Object>> response) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("details", detailed);
        map.put("expressionFile", expressionFile);
        VTubeStudioRequest request = new VTubeStudioRequest(
                "ExpressionStateRequest",
                map
        );
        ws.send(request, response);
    }

    public void activateExpression(
            @NotNull String expressionFile,
            ResponseListener<LinkedHashMap<String, Object>> response) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("expressionFile", expressionFile);
        map.put("active", true);
        VTubeStudioRequest request = new VTubeStudioRequest(
                "ExpressionActivationRequest",
                map
        );
        ws.send(request, response);
    }

    public void deactivateExpression(
            @NotNull String expressionFile,
            ResponseListener<LinkedHashMap<String, Object>> response) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("expressionFile", expressionFile);
        map.put("active", false);
        VTubeStudioRequest request = new VTubeStudioRequest(
                "ExpressionActivationRequest",
                map
        );
        ws.send(request, response);
    }

    public void artMeshes(ResponseListener<
            LinkedHashMap<String, Object>
            > response) {
        VTubeStudioRequest request = new VTubeStudioRequest(
                "ArtMeshListRequest",
                null
        );
        ws.send(request, response);
    }

    public void tintArtMeshes(
            @NotNull Color color,
            Float mixWithSceneLightingColor,
            @NotNull ArtMeshMatcher matcher,
            ResponseListener<LinkedHashMap<String, Object>> response) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        LinkedHashMap<String, Object> colorTint = new LinkedHashMap<>();
        colorTint.put("colorR", color.getRed());
        colorTint.put("colorG", color.getGreen());
        colorTint.put("colorB", color.getBlue());
        colorTint.put("colorA", color.getAlpha());
        colorTint.put("mixWithSceneLightingColor", mixWithSceneLightingColor);
        map.put("colorTint", colorTint);
        map.put("artMeshMatcher", matcher);

        VTubeStudioRequest request = new VTubeStudioRequest(
                "ColorTintRequest",
                map
        );
        ws.send(request, response);
    }

    public void getSceneLightningOverlay(ResponseListener<
            LinkedHashMap<String, Object>
            > response) {
        VTubeStudioRequest request = new VTubeStudioRequest(
                "SceneColorOverlayInfoRequest",
                null
        );
        ws.send(request, response);
    }

    public void isFaceFound(ResponseListener<
            LinkedHashMap<String, Object>
            > response) {
        VTubeStudioRequest request = new VTubeStudioRequest(
                "FaceFoundRequest",
                null
        );
        ws.send(request, response);
    }

    public void inputParameters(ResponseListener<
            LinkedHashMap<String, Object>
            > response) {
        VTubeStudioRequest request = new VTubeStudioRequest(
                "InputParameterListRequest",
                null
        );
        ws.send(request, response);
    }

    public void modelParameters(ResponseListener<
            LinkedHashMap<String, Object>
            > response) {
        VTubeStudioRequest request = new VTubeStudioRequest(
                "Live2DParameterListRequest",
                null
        );
        ws.send(request, response);
    }

    public void getParameter(
            @NotNull String name,
            ResponseListener<LinkedHashMap<String, Object>> response) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("name", name);
        VTubeStudioRequest request = new VTubeStudioRequest(
                "ParameterValueRequest",
                map
        );
        ws.send(request, response);
    }

    public void createParameter(
            @NotNull String name,
            @NotNull String explanation,
            float min,
            float max,
            float defaultValue,
            ResponseListener<LinkedHashMap<String, Object>> response) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("parameterName", name);
        map.put("explanation", explanation);
        map.put("min", min);
        map.put("max", max);
        map.put("defaultValue", defaultValue);
        VTubeStudioRequest request = new VTubeStudioRequest(
                "ParameterCreationRequest",
                map
        );
        ws.send(request, response);
    }

    public void deleteParameter(
            @NotNull String name,
            ResponseListener<LinkedHashMap<String, Object>> response) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("parameterName", name);

        VTubeStudioRequest request = new VTubeStudioRequest(
                "ParameterDeletionRequest",
                map
        );
        ws.send(request, response);
    }

    public void setParameter(
            Boolean faceFound,
            List<Parameter> parameters,
            ResponseListener<LinkedHashMap<String, Object>> response) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("faceFound", faceFound);
        map.put("mode", "set");
        map.put("parameterValues", parameters);

        VTubeStudioRequest request = new VTubeStudioRequest(
                "InjectParameterDataRequest",
                map
        );
        ws.send(request, response);
    }

    public void editParameter(
            Boolean faceFound,
            List<Parameter> parameters,
            ResponseListener<LinkedHashMap<String, Object>> response) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("faceFound", faceFound);
        map.put("mode", "add");
        map.put("parameterValues", parameters);

        VTubeStudioRequest request = new VTubeStudioRequest(
                "InjectParameterDataRequest",
                map
        );
        ws.send(request, response);
    }

    public void getPhysics(ResponseListener<
            LinkedHashMap<String, Object>
            > response) {
        VTubeStudioRequest request = new VTubeStudioRequest(
                "GetCurrentModelPhysicsRequest",
                null
        );
        ws.send(request, response);
    }

    public void setPhysics(
            List<PhysicsParameter> strength,
            List<PhysicsParameter> wind,
            ResponseListener<LinkedHashMap<String, Object>> response) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("strengthOverrides", strength);
        map.put("windOverrides", wind);

        VTubeStudioRequest request = new VTubeStudioRequest(
                "SetCurrentModelPhysicsRequest",
                map
        );
        ws.send(request, response);
    }

    public void getNetConfig(
            ResponseListener<LinkedHashMap<String, Object>> response
    ) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("setNewConfig", false);

        VTubeStudioRequest request = new VTubeStudioRequest(
                "NDIConfigRequest",
                map
        );
        ws.send(request, response);
    }

    public void setNetConfig(
            boolean ndiActive,
            boolean useNDI5,
            boolean useCustomResolution,
            int customWidthNDI,
            int customHeightNDI,
            ResponseListener<LinkedHashMap<String, Object>> response
    ) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("setNewConfig", true);
        map.put("ndiActive", ndiActive);
        map.put("useNDI5", useNDI5);
        map.put("useCustomResolution", useCustomResolution);
        map.put("customWidthNDI", customWidthNDI);
        map.put("customHeightNDI", customHeightNDI);

        VTubeStudioRequest request = new VTubeStudioRequest(
                "NDIConfigRequest",
                map
        );
        ws.send(request, response);
    }

    public void itemsInScene(
            boolean includeAvailableSpots,
            boolean includeItemInstancesInScene,
            boolean includeAvailableItemFiles,
            String onlyItemsWithFileName,
            String onlyItemsWithInstanceID,
            ResponseListener<LinkedHashMap<String, Object>> response) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("includeAvailableSpots", includeAvailableSpots);
        map.put("includeItemInstancesInScene", includeItemInstancesInScene);
        map.put("includeAvailableItemFiles", includeAvailableItemFiles);
        map.put("onlyItemsWithFileName", onlyItemsWithFileName);
        map.put("onlyItemsWithInstanceID", onlyItemsWithInstanceID);

        VTubeStudioRequest request = new VTubeStudioRequest(
                "ItemListRequest",
                map
        );
        ws.send(request, response);
    }

    public void loadItem(
            @NotNull String fileName,
            float x,
            float y,
            float size,
            float rotation,
            float fadeTime,
            int order,
            boolean failIfOrderTaken,
            float smoothing,
            boolean censored,
            boolean flipped,
            boolean locked,
            boolean unloadWhenPluginDisconnects,
            ResponseListener<LinkedHashMap<String, Object>> response) {
       LinkedHashMap<String, Object> map = configureItemLoading(
               fileName, x, y, size, rotation,
               fadeTime, order, failIfOrderTaken,
               smoothing, censored, flipped, locked,
               unloadWhenPluginDisconnects);
        VTubeStudioRequest request = new VTubeStudioRequest(
                "ItemLoadRequest",
                map
        );
        ws.send(request, response);
    }
    public void loadCustomItem(
            @NotNull InputStream imageStream,
            boolean askUserFirst,
            boolean skipAskingUserIfWhitelisted,
            int askTimer,

            @NotNull String fileName,
            float x,
            float y,
            float size,
            float rotation,
            float fadeTime,
            int order,
            boolean failIfOrderTaken,
            float smoothing,
            boolean censored,
            boolean flipped,
            boolean locked,
            boolean unloadWhenPluginDisconnects,
            ResponseListener<LinkedHashMap<String, Object>> response) throws IOException {
        LinkedHashMap<String, Object> map = configureItemLoading(
                fileName, x, y, size, rotation,
                fadeTime, order, failIfOrderTaken,
                smoothing, censored, flipped, locked,
                unloadWhenPluginDisconnects);

        map.put("customDataBase64", Base64.getEncoder().encodeToString(imageStream.readAllBytes()));
        map.put("customDataAskUserFirst", askUserFirst);
        map.put("customDataSkipAskingUserIfWhitelisted", skipAskingUserIfWhitelisted);
        map.put("customDataAskTimer", askTimer);
        VTubeStudioRequest request = new VTubeStudioRequest(
                "ItemLoadRequest",
                map
        );
        ws.send(request, response);
    }

    private LinkedHashMap<String, Object> configureItemLoading(@NotNull String fileName, float x, float y, float size, float rotation, float fadeTime, int order, boolean failIfOrderTaken, float smoothing, boolean censored, boolean flipped, boolean locked, boolean unloadWhenPluginDisconnects) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("fileName", fileName);
        map.put("positionX", x);
        map.put("positionY", y);
        map.put("size", size);
        map.put("rotation", rotation);
        map.put("fadeTime", fadeTime);
        map.put("order", order);
        map.put("failIfOrderTaken", failIfOrderTaken);
        map.put("smoothing", smoothing);
        map.put("censored", censored);
        map.put("flipped", flipped);
        map.put("locked", locked);
        map.put("unloadWhenPluginDisconnects", unloadWhenPluginDisconnects);
        return map;
    }


    public void removeAllItems(
            boolean onlyThisPlugin,
            ResponseListener<LinkedHashMap<String, Object>> response) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        if (onlyThisPlugin)
            map.put("unloadAllLoadedByThisPlugin", true);
        else
            map.put("unloadAllInScene", true);

        VTubeStudioRequest request = new VTubeStudioRequest(
                "ItemUnloadRequest",
                map
        );
        ws.send(request, response);
    }

    public void removeItems(
            List<String> instanceIDs,
            List<String> fileNames,
            boolean onlyThisPlugin,
            ResponseListener<LinkedHashMap<String, Object>> response) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("instanceIDs", instanceIDs);
        map.put("fileNames", fileNames);
        map.put("allowUnloadingItemsLoadedByUserOrOtherPlugins", !onlyThisPlugin);

        VTubeStudioRequest request = new VTubeStudioRequest(
                "ItemUnloadRequest",
                map
        );
        ws.send(request, response);
    }

    public void itemAnimationControl(
            @NotNull String instanceID,
            int framerate,
            int frame,
            float brightness,
            float opacity,
            boolean useAutoStop,
            int[] autoStopFrames,
            boolean changePlayState,
            boolean playState,
            ResponseListener<LinkedHashMap<String, Object>> response
    ) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("itemInstanceID", instanceID);
        map.put("framerate", framerate);
        map.put("frame", frame);
        map.put("brightness", brightness);
        map.put("opacity", opacity);

        map.put("setAutoStopFrames", useAutoStop);
        map.put("autoStopFrames", autoStopFrames);

        map.put("setAnimationPlayState", changePlayState);
        map.put("animationPlayState", playState);
        VTubeStudioRequest request = new VTubeStudioRequest(
                "ItemAnimationControlRequest",
                map
        );
        ws.send(request, response);
    }

    public void moveItems(
            List<ItemTransform> transforms,
            ResponseListener<LinkedHashMap<String, Object>> response
    ) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("itemsToMove", transforms);
        VTubeStudioRequest request = new VTubeStudioRequest(
                "ItemMoveRequest",
                map
        );
        ws.send(request, response);
    }

    public void askArtMeshSelection(
            @NotNull String text,
            @NotNull String help,
            int requestedCount,
            List<String> activeArtMeshes,
            ResponseListener<LinkedHashMap<String, Object>> response
    ) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("textOverride", text);
        map.put("helpOverride", help);
        map.put("requestedArtMeshCount", requestedCount);
        map.put("activeArtMeshes", activeArtMeshes);
        VTubeStudioRequest request = new VTubeStudioRequest(
                "ArtMeshSelectionRequest",
                map
        );
        ws.send(request, response);
    }

    public void pinItemToModel(
            @NotNull String itemInstanceID,
            @NotNull AngleRelative angleRelative,
            @NotNull SizeRelative sizeRelative,
            @NotNull PinType pinType,
            @NotNull String modelID,
            @NotNull String artMeshID,
            float angle,
            float size,
            ResponseListener<LinkedHashMap<String, Object>> response
    ) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("pin", true);
        map.put("itemInstanceID", itemInstanceID);
        map.put("angleRelativeTo", angleRelative.getValue());
        map.put("sizeRelativeTo", sizeRelative.getValue());
        map.put("vertexPinType", pinType.getValue());
        LinkedHashMap<String, Object> pinInfo = new LinkedHashMap<>();
        pinInfo.put("modelID", modelID);
        pinInfo.put("artMeshID", artMeshID);
        pinInfo.put("angle", angle);
        pinInfo.put("size", size);
        map.put("pinInfo", pinInfo);

        VTubeStudioRequest request = new VTubeStudioRequest(
                "ItemPinRequest",
                map
        );
        ws.send(request, response);
    }

    public void providedPinItemToModel(
            @NotNull String itemInstanceID,
            @NotNull AngleRelative angleRelative,
            @NotNull SizeRelative sizeRelative,
            @NotNull String modelID,
            @NotNull String artMeshID,
            float angle,
            float size,
            int vertexID1,
            float vertexWeight1,
            int vertexID2,
            float vertexWeight2,
            int vertexID3,
            float vertexWeight3,
            ResponseListener<LinkedHashMap<String, Object>> response
    ) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("pin", true);
        map.put("itemInstanceID", itemInstanceID);
        map.put("angleRelativeTo", angleRelative.getValue());
        map.put("sizeRelativeTo", sizeRelative.getValue());
        map.put("vertexPinType", "Provided");
        LinkedHashMap<String, Object> pinInfo = new LinkedHashMap<>();
        pinInfo.put("modelID", modelID);
        pinInfo.put("artMeshID", artMeshID);
        pinInfo.put("angle", angle);
        pinInfo.put("size", size);

        pinInfo.put("vertexID1", vertexID1);
        pinInfo.put("vertexID2", vertexID2);
        pinInfo.put("vertexID3", vertexID3);

        pinInfo.put("vertexWeight1", vertexWeight1);
        pinInfo.put("vertexWeight2", vertexWeight2);
        pinInfo.put("vertexWeight3", vertexWeight3);
        map.put("pinInfo", pinInfo);

        VTubeStudioRequest request = new VTubeStudioRequest(
                "ItemPinRequest",
                map
        );
        ws.send(request, response);
    }

    public void unpinItemFromModel(
            ResponseListener<LinkedHashMap<String, Object>> response
    ) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("pin", false);
        VTubeStudioRequest request = new VTubeStudioRequest(
                "ItemPinRequest",
                map
        );
        ws.send(request, response);
    }

    // TODO: Post-Processing is in beta

    public void call(APICall call) {
        call.execute(this);
    }

    public void close() {
        ws.close();
    }
}
