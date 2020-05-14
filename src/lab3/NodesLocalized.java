package lab3;

public class NodesLocalized {
    double localized;
    Node[] nodes;

    public NodesLocalized() {}

    public NodesLocalized(double localized, Node[] nodes) {
        this.localized = localized;
        this.nodes = nodes;
    }

    public double getLocalized() {
        return localized;
    }

    public void setLocalized(double localized) {
        this.localized = localized;
    }

    public Node[] getNodes() {
        return nodes;
    }

    public void setNodes(Node[] nodes) {
        this.nodes = nodes;
    }
}