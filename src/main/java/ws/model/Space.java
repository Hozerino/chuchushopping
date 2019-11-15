package ws.model;

public class Space {
    private Space topOf;
    private Space bottomOf;
    private Space leftOf;
    private Space rightOf;
    private String label;
    private String belongsTo;
    private Space connects;
    private boolean isObstacle = false;
    private boolean isWalkable = false;

    public String getLabel() {
        return label;
    }

    public Space(String label, String type) {
        this.label = label;

        if (type.equals("Obstacle")) {
            isObstacle = true;
        } else {
            isWalkable = true;
        }
    }

    final Space downNeighbor() {
        return topOf;
    }

    final Space topNeighbor() {
        return bottomOf;
    }

    final Space leftNeighbor() {
        return rightOf;
    }

    final Space rightNeighbor() {
        return leftOf;
    }


    public void setSpaceProperties(String property, Space neighbor) {
        switch (property) {
            case "topOf":
                this.topOf = (neighbor);
                break;
            case "bottomOf":
                this.bottomOf = (neighbor);
                break;
            case "leftOf":
                this.leftOf = (neighbor);
                break;
            case "rightOf":
                this.rightOf = (neighbor);
                break;
            case "connects":
                this.connects = (neighbor);
                break;
            case "belongsTo":
                this.belongsTo = neighbor.label;
                break;
        }
    }

}
