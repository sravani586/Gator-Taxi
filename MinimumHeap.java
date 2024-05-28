import java.util.ArrayList;

class MinimumHeap<T extends Comparable<T>> {
    private ArrayList<T> minheap;

    public MinimumHeap() {
        this.minheap = new ArrayList<>();
    }

    public void insert(T node) {
        // Add new Element to the end of the minheap
        minheap.add(node);
        int currentIndex = minheap.size() - 1;

        // Reordering the minheap to place the newly add element to its right position
        while (currentIndex > 0) {
            int parentIndex = (currentIndex - 1) / 2;
            T parent = minheap.get(parentIndex);
            if (node.compareTo(parent) < 0) {
                minheap.set(currentIndex, parent);
                minheap.set(parentIndex, node);
                currentIndex = parentIndex;
            } else {
                break;
            }
        }
    }

    // Removing Smallest Element
    public T removeMin() {
        if (minheap.size() == 0) {
            return null;
        }
        T minNode = this.getMin();
        T lastNode = minheap.remove(minheap.size() - 1);
        // If more the one element exists in the minheap
        if (minheap.size() != 0) {

            // setting last element of minheap as first element
            minheap.set(0, lastNode);

            int currentIndex = 0;

            // reordering to find right position of last element in new minheap
            while (true) {
                int leftIdx = 2 * currentIndex + 1;
                int rightIdx = 2 * currentIndex + 2;

                if (leftIdx >= minheap.size()) {
                    break;
                }
                int minChildIdx = leftIdx;

                if (rightIdx < minheap.size()) {
                    T left = minheap.get(leftIdx);
                    T right = minheap.get(rightIdx);

                    if (left.compareTo(right) > 0) {
                        minChildIdx = rightIdx;
                    }
                }

                T minChild = minheap.get(minChildIdx);

                if (lastNode.compareTo(minChild) > 0) {
                    minheap.set(currentIndex, minChild);
                    minheap.set(minChildIdx, lastNode);
                    currentIndex = minChildIdx;
                } else {
                    break;
                }

            }
        }

        return minNode;
    }

    public void delete(T node) { // deleting the element from the minheap
        if (minheap.size() == 0) {
            return;
        }

        int idx = minheap.indexOf(node);

        if (idx == -1) {
            return;
        }

        T lastNode = minheap.remove(minheap.size() - 1);

        if (idx != minheap.size()) {
            minheap.set(idx, lastNode);
            int currentIndex = idx;

            while (true) {
                int leftIdx = 2 * currentIndex + 1;
                int rightIdx = 2 * currentIndex + 2;

                if (leftIdx >= minheap.size()) {
                    break;
                }

                int minChildIdx = leftIdx;

                if (rightIdx < minheap.size()) {
                    T left = minheap.get(leftIdx);
                    T right = minheap.get(rightIdx);
                    if (left.compareTo(right) > 0) {
                        minChildIdx = rightIdx;
                    }
                }

                T minChild = minheap.get(minChildIdx);

                if (lastNode.compareTo(minChild) > 0) {
                    minheap.set(currentIndex, minChild);
                    minheap.set(minChildIdx, lastNode);
                    currentIndex = minChildIdx;
                } else {
                    break;
                }
            }
            int parentIdx = (currentIndex - 1) / 2;

            T parent = minheap.get(parentIdx);

            if (currentIndex > 0 && lastNode.compareTo(parent) < 0) {
                while (currentIndex > 0) {
                    parentIdx = (currentIndex - 1) / 2;
                    parent = minheap.get(parentIdx);

                    if (lastNode.compareTo(parent) < 0) {
                        minheap.set(currentIndex, parent);
                        minheap.set(parentIdx, lastNode);
                        currentIndex = parentIdx;
                    } else {
                        break;
                    }
                }
            }
        }
    }

    public boolean isEmpty() { // checking if the minheap is empty
        return minheap.isEmpty();
    }

    public T getMin() { // returning the minimum from the minheap
        if (minheap.size() == 0) {
            return null;
        }
        return minheap.get(0);
    }

}
