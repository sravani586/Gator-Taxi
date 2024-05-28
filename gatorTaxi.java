import java.util.ArrayList;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class gatorTaxi {
    private MinimumHeap<Data> minHeap;
    private RedBlackTree tree;

    // initialising the gatorTaxi class
    gatorTaxi() {
        this.minHeap = new MinimumHeap<>();
        this.tree = new RedBlackTree();
    }

    // prints the ride number using ridenumber
    public NodeClass print(Integer rideNumber) {
        NodeClass node = tree.search(rideNumber);
        if (node == null || node == tree.nodeClassNode) {
            node = new NodeClass(0, 0, 0);
        }
        return node;
    }
    // prints all rides in the given range (ride1,ride2)

    public ArrayList<NodeClass> print(Integer ride1, Integer ride2) {
        ArrayList<NodeClass> arr = tree.range(ride1, ride2);
        if (arr.isEmpty()) {
            NodeClass node = new NodeClass(0, 0, 0);
            arr.add(node);
        }
        return arr;
    }

    // insert into Redblacktree using ridenumber,ridecost,tripduration
    public Boolean insert(Integer rideNumber, Integer rideCost, Integer tripDuration) {
        NodeClass node = new NodeClass(rideNumber, rideCost, tripDuration);
        if (tree.search(node.data.rideNumber) == tree.nodeClassNode) {
            minHeap.insert(node.data);
            tree.insert(node);
            return true;
        } else {
            return false;
        }
    }

    // gets the next ride with lowest node value and remove the min value
    public Data getNextRide() {
        Data data = minHeap.removeMin();
        NodeClass node;
        if (data != null) {
            node = tree.search(data.rideNumber);
            tree.delete(node.data.rideNumber);
            return data;
        } else {
            // handle if node == null => "No Active ride requests" should be printed
            return null;
        }
    }

    // cancels the ride (delete the node from minheap,tree)
    public void cancelRide(Integer rideNumber) {
        NodeClass node = tree.search(rideNumber);
        if (node != null) {
            minHeap.delete(node.data);
            tree.delete(rideNumber);
        }

    }

    // update the trip with ridenumber and newtripduration
    public void updateTrip(Integer rideNumber, Integer newTripDuration) {
        NodeClass node = tree.search(rideNumber);
        if (node == null) {
            return;
        }
        minHeap.delete(node.data);
        if (node.data.tripDuration >= newTripDuration) {
            node.data.tripDuration = newTripDuration;
            minHeap.insert(node.data);
        } else if (newTripDuration <= node.data.tripDuration * 2) {
            node.data.tripDuration = newTripDuration;
            node.data.rideCost += 10;
            minHeap.insert(node.data);
        } else {
            tree.delete(rideNumber);
        }
    }

    public static void main(String[] args) {
        // write your code here
        if (args.length == 0) {
            System.out.println("Error: input file not specified.");
            return;
        }
        String inputFileName = args[0];
        gatorTaxi gatorTaxi = new gatorTaxi();
        try {
            File inputFile = new File(inputFileName);
            Scanner scanner = new Scanner(inputFile);
            FileWriter outputFile = new FileWriter("output_file.txt");

            while (scanner.hasNextLine()) {
                String inputLine = scanner.nextLine();
                String[] in = inputLine.substring(inputLine.indexOf("(", 0) + 1, inputLine.indexOf(")", 3)).split(",");
                // splitting the string for the input
                if (inputLine.startsWith("Insert")) {
                    Integer rideNumber = Integer.parseInt(in[0]);
                    Integer rideCost = Integer.parseInt(in[1]);
                    Integer tripDuration = Integer.parseInt(in[2]);

                    Boolean node = gatorTaxi.insert(rideNumber, rideCost, tripDuration);
                    if (node == false) {
                        String out = "Duplicate RideNumber"; // check for the duplicate ridenumber
                        outputFile.write(out + "\n");
                        break;
                    }
                } else if (inputLine.contains("UpdateTrip")) { // calling for updatetrip method
                    Integer rideNumber = Integer.parseInt(in[0]);
                    Integer newTripDuration = Integer.parseInt(in[1]);
                    gatorTaxi.updateTrip(rideNumber, newTripDuration);
                } else if (inputLine.contains("GetNextRide")) { // calling for getnextride method
                    Data node = gatorTaxi.getNextRide();
                    if (node == null) {
                        String out = "No active ride requests"; // checking if have a active ride requests
                        outputFile.write(out);
                    } else {
                        outputFile.write("(" + node.rideNumber + ", " + node.rideCost + ", " + node.tripDuration + ")");
                    }
                    outputFile.write("\n");
                } else if (inputLine.contains("Print")) { // calling for print method
                    if (in.length == 1) {
                        Integer rideNumber = Integer.parseInt(in[0]);
                        NodeClass node = gatorTaxi.print(rideNumber);

                        outputFile.write("(" + node.data.rideNumber + "," + node.data.rideCost + ","
                                + node.data.tripDuration + ")");
                        outputFile.write("\n");
                    } else {
                        Integer rideNumber1 = Integer.parseInt(in[0]);
                        Integer rideNumber2 = Integer.parseInt(in[1]);
                        ArrayList<NodeClass> arr = gatorTaxi.print(rideNumber1, rideNumber2);

                        for (int i = 0; i < arr.size(); i++) {
                            NodeClass node = arr.get(i);
                            outputFile.write("(" + node.data.rideNumber + "," + node.data.rideCost + ","
                                    + node.data.tripDuration + ")");
                            if (i + 1 != arr.size()) {
                                outputFile.write(",");
                            } else {
                                outputFile.write("\n");
                            }

                        }
                    }
                } else if (inputLine.contains("CancelRide")) { // calling for cancelride method
                    Integer rideNumber = Integer.parseInt(in[0]);
                    gatorTaxi.cancelRide(rideNumber);
                }
            }

            scanner.close();
            outputFile.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
