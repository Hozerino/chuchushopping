package ws.model;

public abstract class Space {
    Space topOf;
    Space bottomOf;
    Space leftOf;
    Space rightOf;

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

}
