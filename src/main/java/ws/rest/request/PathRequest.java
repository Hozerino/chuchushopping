package ws.rest.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PathRequest {
    @JsonProperty
    String storeToBeFound;

    public String getStoreToBeFound() {
        return storeToBeFound;
    }

    public void setStoreToBeFound(String storeToBeFound) {
        this.storeToBeFound = storeToBeFound;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public PathRequest(String storeToBeFound, String floor) {
        this.storeToBeFound = storeToBeFound;
        this.floor = floor;
    }

    @JsonProperty
    String floor;
}
