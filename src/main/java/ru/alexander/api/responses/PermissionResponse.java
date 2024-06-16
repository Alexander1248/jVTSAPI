package ru.alexander.api.responses;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

public class PermissionResponse {
    @SerializedName("grantSuccess")
    private boolean status;
    @Getter
    @SerializedName("requestedPermission")
    private boolean requestedPermission;
    @Getter
    @SerializedName("permissions")
    private Permission[] permissions;

    public PermissionStatus getStatus() {
        return status ? PermissionStatus.Granted : PermissionStatus.Denied;
    }

    public static class Permission {

        @Getter
        @SerializedName("name")
        public String name;
        @SerializedName("granted")
        public boolean status;

        public PermissionStatus getStatus() {
            return status ? PermissionStatus.Granted : PermissionStatus.Denied;
        }
    }
    public enum PermissionStatus {
        Denied,
        Granted
    }
}
