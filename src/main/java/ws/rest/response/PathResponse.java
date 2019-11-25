package ws.rest.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import ws.domain.space.Space;

import java.util.ArrayList;
import java.util.List;

public class PathResponse {
    @JsonProperty
    private String name;

    @JsonProperty
    private String floor;

    @JsonProperty("belongs_to")
    private String belongsTo;

    private PathResponse(String name, String floor, String belongsTo) {
        this.name = name.replace(".", "_");
        this.floor = floor;
        this.belongsTo = belongsTo;
    }

    public static List<PathResponse> convertSpaceListToPathList(List<Space> spaces) {
        List<PathResponse> path = new ArrayList<>();

        spaces.forEach(space -> path.add(new PathResponse(space.getLabel(), space.getFloor(), space.getBelongsTo())));

        return path;
    }
}
