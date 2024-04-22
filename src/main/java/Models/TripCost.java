package Models;

public class TripCost {
    private final Stop startStop;
    private final Stop endStop;
    private final Double cost;

    public TripCost(Stop startStop, Stop endStop, Double cost) {
        this.startStop = startStop;
        this.endStop = endStop;
        this.cost = cost;
    }

    public Double getCost() {
        return cost;
    }
}
