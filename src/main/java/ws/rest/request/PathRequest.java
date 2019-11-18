package ws.rest.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PathRequest {
    @JsonProperty("store_to_be_found")
    private String storeToBeFound;

    @JsonProperty
    private String floor;

    public String getStoreToBeFound() {
        return storeToBeFound;
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


}
