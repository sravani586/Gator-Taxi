import java.util.ArrayList;

class RedBlackTree {
    NodeClass root;
    NodeClass nodeClassNode;
    boolean RED = true;
    boolean BLACK = false;

    RedBlackTree() { // redblacktree initialization
        this.nodeClassNode = new NodeClass(null, null, null);
        this.nodeClassNode.left = null;
        this.nodeClassNode.right = null;
        this.nodeClassNode.color = BLACK;
        this.root = this.nodeClassNode;
    }

    public void rotateLeft(NodeClass node) { // method for tree -> rotating to the left
        if (node == nodeClassNode || node == null) {
            return;
        }
        NodeClass rightNode = node.right;

        node.right = rightNode.left;
        if (rightNode.left != nodeClassNode) {
            rightNode.left.parent = node;
        }

        rightNode.parent = node.parent;
        if (node.parent == null) {
            this.root = rightNode;
        } else if (node.parent.left == node) {
            node.parent.left = rightNode;
        } else {
            node.parent.right = rightNode;
        }
        rightNode.left = node;
        node.parent = rightNode;
    }

    public void rotateRight(NodeClass node) { // method for tree -> rotating to the right
        if (node == nodeClassNode || node == null) {
            return;
        }
        NodeClass leftNode = node.left;

        node.left = leftNode.right;
        if (leftNode.right != nodeClassNode && leftNode.right != null) {
            leftNode.right.parent = node;
        }

        leftNode.parent = node.parent;
        if (node.parent == null) {
            this.root = leftNode;
        }

        else if (node.parent.left == node) {
            node.parent.left = leftNode;
        } else {
            node.parent.right = leftNode;
        }

        leftNode.right = node;
        node.parent = leftNode;

    }

    public boolean insert(NodeClass node) { // inserting to the red black tree

        node.parent = null;
        node.left = nodeClassNode;
        node.right = nodeClassNode;
        node.color = RED;

        NodeClass searchNode = this.root;
        NodeClass serachNodeParent = null;
        while (searchNode != nodeClassNode) {
            serachNodeParent = searchNode;
            if (searchNode.key > node.key) {
                searchNode = searchNode.left;
            } else if (searchNode.key < node.key) {
                searchNode = searchNode.right;
            } else {
                return false;
            }
        }
        node.parent = serachNodeParent;
        if (serachNodeParent == null) {
            root = node;
        } else if (node.key < serachNodeParent.key) {
            serachNodeParent.left = node;
        } else {
            serachNodeParent.right = node;
        }

        if (node.parent == null) {
            return true;
        }
        if (node.parent.parent == null) {
            return true;
        }

        insertFixUp(node);

        return true;

    }

    public Boolean getColor(NodeClass node) { // method to give colour to the nodes
        if (node == null || node == nodeClassNode) {
            return BLACK;
        }
        return node.color;
    }

    public void flipColor(NodeClass node) { // method for the fliping the colour
        if (node == null || node == nodeClassNode) {
            return;
        }
        node.color = RED;
        if (node.left != null) {
            node.left.color = BLACK;
        }
        if (node.right != null) {
            node.right.color = BLACK;
        }
    }

    public void insertFixUp(NodeClass node) { // balancing the tree after the insertion to the tree
        NodeClass nodeUncle;
        while (node.parent.color == RED) {
            if (node.parent == node.parent.parent.right) {
                nodeUncle = node.parent.parent.left;
                if (nodeUncle.color == RED) {
                    nodeUncle.color = BLACK;
                    node.parent.color = BLACK;
                    node.parent.parent.color = RED;
                    node = node.parent.parent;
                } else {
                    if (node == node.parent.left) {
                        node = node.parent;
                        rotateRight(node);
                    }
                    node.parent.color = BLACK;
                    node.parent.parent.color = RED;
                    rotateLeft(node.parent.parent);
                }
            } else {
                nodeUncle = node.parent.parent.right;

                if (nodeUncle.color == RED) {
                    nodeUncle.color = BLACK;
                    node.parent.color = BLACK;
                    node.parent.parent.color = RED;
                    node = node.parent.parent;
                } else {
                    if (node == node.parent.right) {
                        node = node.parent;
                        rotateLeft(node);
                    }
                    node.parent.color = BLACK;
                    node.parent.parent.color = RED;
                    rotateRight(node.parent.parent);
                }
            }
            if (node == root) {
                break;
            }
        }
        root.color = BLACK;

    }

    public void transplant(NodeClass replacingNode, NodeClass newReplacement) {
        if (replacingNode.parent == null) {
            this.root = newReplacement;
        } else if (replacingNode == replacingNode.parent.left) {
            replacingNode.parent.left = newReplacement;
        } else {
            replacingNode.parent.right = newReplacement;
        }
        newReplacement.parent = replacingNode.parent;

    }

    public NodeClass minimum(NodeClass node) { // method to return minumum node from the tree
        while (node.left != nodeClassNode) {
            node = node.left;
        }
        return node;
    }

    public void delete(Integer key) { // deleting the node from the reb black tree.
        NodeClass node = search(key);

        if (node == nodeClassNode || node == null) {
            return;
        }
        NodeClass nodeTobeReplace = node;
        boolean nodeTobeReplace_originalColor = node.color;
        NodeClass fixUpNode;
        // case 1 //deleting left node
        if (node.left == nodeClassNode) {
            fixUpNode = node.right;
            transplant(node, node.right);
        }
        // case 2 //deleting right node
        else if (node.right == nodeClassNode) {
            fixUpNode = node.left;
            transplant(node, node.left);
        }
        // case 3
        else {
            nodeTobeReplace = minimum(node.right);
            nodeTobeReplace_originalColor = nodeTobeReplace.color;
            fixUpNode = nodeTobeReplace.right;
            if (nodeTobeReplace.parent == node) {
                fixUpNode.parent = nodeTobeReplace;
            } else {
                transplant(nodeTobeReplace, nodeTobeReplace.right);
                nodeTobeReplace.right = node.right;
                nodeTobeReplace.right.parent = nodeTobeReplace;
            }
            transplant(node, nodeTobeReplace);
            nodeTobeReplace.left = node.left;
            nodeTobeReplace.left.parent = nodeTobeReplace;
            nodeTobeReplace.color = node.color;
        }

        if (nodeTobeReplace_originalColor == BLACK) {
            deleteFixUp(fixUpNode);
        }
    }

    // black = 0 red = 1
    public void deleteFixUp(NodeClass nodeToFix) { // method for balancing the RBT after deleting from the tree
        NodeClass node;
        while (nodeToFix != this.root && nodeToFix.color == BLACK) {
            if (nodeToFix == nodeToFix.parent.left) {
                node = nodeToFix.parent.right;
                if (node.color == RED) {
                    node.color = BLACK;
                    nodeToFix.parent.color = RED;
                    rotateLeft(nodeToFix.parent);
                    node = nodeToFix.parent.right;
                }

                if (node.left.color == BLACK && node.right.color == BLACK) {
                    node.color = RED;
                    nodeToFix = nodeToFix.parent;
                } else {
                    if (node.right.color == BLACK) {
                        node.left.color = BLACK;
                        node.color = RED;
                        rotateRight(node);
                        node = nodeToFix.parent.right;
                    }

                    node.color = nodeToFix.parent.color;
                    nodeToFix.parent.color = BLACK;
                    node.right.color = BLACK;
                    rotateLeft(nodeToFix.parent);
                    nodeToFix = root;
                }
            } else {
                node = nodeToFix.parent.left;
                if (node.color == RED) {
                    node.color = BLACK;
                    nodeToFix.parent.color = RED;
                    rotateRight(nodeToFix.parent);
                    node = nodeToFix.parent.left;
                }
                if (node.right.color == BLACK && node.right.color == BLACK) {
                    node.color = RED;
                    nodeToFix = nodeToFix.parent;
                } else {
                    if (node.left.color == BLACK) {
                        node.right.color = BLACK;
                        node.color = RED;
                        rotateLeft(node);
                        node = nodeToFix.parent.left;
                    }

                    node.color = nodeToFix.parent.color;
                    nodeToFix.parent.color = BLACK;
                    node.left.color = BLACK;
                    rotateRight(nodeToFix.parent);
                    nodeToFix = root;

                }
            }
        }

        nodeToFix.color = BLACK;
    }

    public NodeClass search(Integer key) { // method to search in redblacktree
        NodeClass temp = this.root;
        while (temp != nodeClassNode) {
            if (temp.key == key) {
                return temp;
            } else if (temp.key > key) {
                temp = temp.left;
            } else {
                temp = temp.right;
            }
        }
        return temp;
    }

    private void rangeHelper(NodeClass node, Integer key1, Integer key2, ArrayList<NodeClass> arr) {
        if (node == null || node == nodeClassNode) { // this method checks if it is in range of key1 and key2
            return;
        }
        if (node.key > key2) {
            rangeHelper(node.left, key1, key2, arr);
        } else if (node.key >= key1 && node.key <= key2) {

            rangeHelper(node.left, key1, key2, arr);
            arr.add(node);
            rangeHelper(node.right, key1, key2, arr);

        } else {
            rangeHelper(node.right, key1, key2, arr);

        }
    }

    public ArrayList<NodeClass> range(Integer key1, Integer key2) {
        ArrayList<NodeClass> arr = new ArrayList<>();
        rangeHelper(root, key1, key2, arr);// calling of rangehelper function
        return arr;
    }

}