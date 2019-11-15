package ws.rest.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SpaceResponse {
    @JsonProperty
    private String name;

    @JsonProperty("top_of")
    private String topOf;

    @JsonProperty("left_of")
    private String leftOf;

    @JsonProperty("right_of")
    private String rightOf;

    @JsonProperty("bottom_of")
    private String bottomOf;

    @JsonProperty
    private String connects;

    @JsonProperty
    private String type;

    @JsonProperty("store")
    private String storeLabel;

    public SpaceResponse(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public void setSpaceProperties(String property, String sideStructureName) {
        switch (property) {
            case "topOf":
                this.topOf = (sideStructureName);
                break;
            case "bottomOf":
                this.bottomOf = (sideStructureName);
                break;
            case "leftOf":
                this.leftOf = (sideStructureName);
                break;
            case "rightOf":
                this.rightOf = (sideStructureName);
                break;
            case "connects":
                this.connects = (sideStructureName);
                break;
            case "belongsTo":
                this.storeLabel = sideStructureName;
                break;
        }
    }
}
