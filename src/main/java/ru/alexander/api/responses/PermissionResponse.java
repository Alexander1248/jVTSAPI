package ru.alexander.api.responses;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

public class PermissionResponse {
    @SerializedName("grantSuccess")
    public boolean status;
    @Getter
    @SerializedName("requestedPermission")
    public boolean requestedPermission;

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
