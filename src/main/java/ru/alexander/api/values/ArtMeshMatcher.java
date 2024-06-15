package ru.alexander.api.values;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ArtMeshMatcher {
    @Setter
    @SerializedName("tintAll")
    private boolean all;
    @SerializedName("artMeshNumber")
    private List<Integer> indexes;
    @SerializedName("nameExact")
    private List<String> nameEqual;
    @SerializedName("nameContains")
    private List<String> containsInName;
    @SerializedName("tagExact")
    private List<String> tagEqual;
    @SerializedName("tagContains")
    private List<String> containsInTag;

    public ArtMeshMatcher(
            boolean all, List<Integer> indexes,
            List<String> nameEqual, List<String> containsInName,
            List<String> tagEqual, List<String> containsInTag) {
        this.all = all;
        this.indexes = indexes;
        this.nameEqual = nameEqual;
        this.containsInName = containsInName;
        this.tagEqual = tagEqual;
        this.containsInTag = containsInTag;
    }

    public ArtMeshMatcher() {
        this(
                false,
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>()
        );
    }
}
