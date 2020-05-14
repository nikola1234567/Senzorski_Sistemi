package lab3;

public class NodeNeighbor {
    double distance;
    Node node;

    public NodeNeighbor() {}

    public NodeNeighbor(double distance, Node node) {
        this.distance=distance;
        this.node=node;
    }

    public double getDistance() {
        return distance;
    }

    public Node getNode() {
        return node;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public void setNode(Node node) {
        this.node = node;
    }
}