package ru.alexander.api.responses;

import com.google.gson.annotations.SerializedName;
import ru.alexander.api.ErrorID;

public class ErrorResponse {
    @SerializedName("errorID")
    private int code;
    @SerializedName("message")
    private String message;

    @Override
    public String toString() {
        return code + " " + ErrorID.byCode(code) + "\n" + message;
    }

    public ErrorResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
