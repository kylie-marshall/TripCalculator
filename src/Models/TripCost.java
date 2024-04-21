package Models;

public class TripCost {
    Stop startStop;
    Stop endStop;
    Double cost;

    public TripCost(Stop startStop, Stop endStop, Double cost) {
        this.startStop = startStop;
        this.endStop = endStop;
        this.cost = cost;
    }

    public Double getCost() {
        return cost;
    }
}
