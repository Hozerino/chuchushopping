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

    public SpaceResponse(String name) {
        this.name = name;
    }

    public void setTopOf(String topOf) {
        this.topOf = topOf;
    }

    public void setLeftOf(String leftOf) {
        this.leftOf = leftOf;
    }

    public void setRightOf(String rightOf) {
        this.rightOf = rightOf;
    }

    public void setBottomOf(String bottomOf) {
        this.bottomOf = bottomOf;
    }

    public void setConnects(String connects) {
        this.connects = connects;
    }


    public void setSpaceSide(String side, String sideStructureName) {
        switch (side) {
            case "topOf":
                this.setTopOf(sideStructureName);
                break;
            case "bottomOf":
                this.setBottomOf(sideStructureName);
                break;
            case "leftOf":
                this.setLeftOf(sideStructureName);
                break;
            case "rightOf":
                this.setRightOf(sideStructureName);
                break;
            case "connects":
                this.setConnects(sideStructureName);
                break;
        }
    }
}
