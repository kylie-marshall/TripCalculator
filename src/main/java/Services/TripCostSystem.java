package Services;

import Models.Stop;
import Models.TripCost;

import java.util.HashMap;
import java.util.Map;

public class TripCostSystem {
    public static final double CANCELLED_TRIP_COST = 0;
    private static final double UNKNOWN_TRIP_COST = 0;

    private final Map<String, TripCost> tripCosts;
    private final Map<String, Double> highestCostForStop;

    public TripCostSystem() {
        this.tripCosts = new HashMap<>();
        this.highestCostForStop = new HashMap<>();
    }

    public Stop addStop(String stopId){
        Stop stop = new Stop(stopId);
        highestCostForStop.put(stop.toString(), (double) -1);
        return stop;
    }

    public void addTrip(Stop from, Stop to, double cost) {
        TripCost tripCost = new TripCost(from, to, cost);
        tripCosts.putIfAbsent(from + "_" + to, tripCost);
        tripCosts.putIfAbsent(to + "_" + from, tripCost);

        //set the highest cost for the stop if greater than existing highest cost
        if(cost > highestCostForStop.get(from.toString())) {
            highestCostForStop.put(from.toString(), cost);
        }
        if(cost > highestCostForStop.get(to.toString())) {
            highestCostForStop.put(to.toString(), cost);
        }
    }

    public double calculateCostBetweenStops(Stop fromStopId) {
        return calculateCostBetweenStops(fromStopId, null);
    }

    public double calculateCostBetweenStops(Stop fromStopId, Stop toStopId) {
        if(fromStopId == null && toStopId != null) {
            return getHighestTripCost(toStopId);
        }
        if(toStopId == null && fromStopId != null) {
            return getHighestTripCost(fromStopId);
        }

        TripCost trip = tripCosts.get(fromStopId + "_" + toStopId);
        if (trip == null) {
            System.out.println("Unable to get trip cost as trip does not exist");
            return UNKNOWN_TRIP_COST;
        }
        return trip.getCost();
    }

    private double getHighestTripCost(Stop stop) {
        Double value = highestCostForStop.get(stop.toString());
        if (value == null) {
            System.out.println("Unable to get trip cost as stop does not exist");
            return UNKNOWN_TRIP_COST;
        }
        return value;
    }
}
