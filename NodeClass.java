class NodeClass implements Comparable<NodeClass> {
    boolean RED = true;
    boolean BLACK = false;
    Data data;
    Integer key;
    NodeClass parent;
    NodeClass left;
    NodeClass right;
    boolean color; // black = False red = True

    public NodeClass(Integer rideNumber, Integer ridCost, Integer tripDuration) {
        this.data = new Data(rideNumber, ridCost, tripDuration);
        this.key = rideNumber;
        this.color = RED;

    }

    public void set(NodeClass node) {
        this.key = node.key;
        this.data = node.data;
    }

    public int getKey() {
        return this.key;
    }

    public int compareTo(NodeClass obj) {
        if (this.key.equals(obj.key)) {
            return this.data.compareTo(obj.data);
        }
        return this.key.compareTo(obj.key);
    }
}