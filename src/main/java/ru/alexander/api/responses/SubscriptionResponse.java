package ru.alexander.api.responses;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

@Getter
public class SubscriptionResponse {
    @SerializedName("subscribedEventCount")
    private int count;
    @SerializedName("subscribedEvents")
    private String[] events;
}
