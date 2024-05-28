class Data implements Comparable<Data> {

    Integer rideNumber;
    Integer rideCost;
    Integer tripDuration;

    public Data(Integer rideNumber, Integer rideCost, Integer tripDuration) {
        this.rideNumber = rideNumber;
        this.rideCost = rideCost;
        this.tripDuration = tripDuration;
    }

    @Override
    public int compareTo(Data o) {
        int costCompare = this.rideCost.compareTo(o.rideCost);
        if (costCompare == 0) {
            return this.tripDuration.compareTo(o.tripDuration);
        }
        return costCompare;
    }
}