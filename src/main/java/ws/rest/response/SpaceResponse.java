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

    @JsonProperty
    private int floor;

    @JsonProperty("store")
    private String storeLabel;

    public SpaceResponse(String name, String type) {
        this.name = name.replace(".", "_");
        this.type = type;
    }

    public void setSpaceProperties(String property, String sideStructureName) {
        switch (property) {
            case "topOf":
                this.topOf = (sideStructureName.replace(".", "_"));
                break;
            case "bottomOf":
                this.bottomOf = (sideStructureName.replace(".", "_"));
                break;
            case "leftOf":
                this.leftOf = (sideStructureName.replace(".", "_"));
                break;
            case "rightOf":
                this.rightOf = (sideStructureName.replace(".", "_"));
                break;
            case "connects":
                this.connects = (sideStructureName.replace(".", "_"));
                break;
            case "belongsTo":
                this.storeLabel = sideStructureName.replace(".", "_");
                break;
            case "floor":
                this.floor = Integer.parseInt(sideStructureName);
                break;
        }
    }
}
