package Services;
import java.util.Objects;

public final class TripCost {

    public static double CANCELLED_TRIP_COST = 0;
    public static double calculateCost(String fromStopId, String toStopId) {
        if(fromStopId == null || toStopId == null) {
            String stopId = fromStopId == null ? toStopId : fromStopId;
            return switch (stopId) {
                case "Stop1", "Stop3" -> 7.30;
                case "Stop2" -> 5.50;
                default -> 0;
            };
        }

        if((Objects.equals(fromStopId, "Stop1") && Objects.equals(toStopId, "Stop2")) ||
            (Objects.equals(fromStopId, "Stop2") && Objects.equals(toStopId, "Stop1"))
        ) {
            return 3.25;
        }
        if((Objects.equals(fromStopId, "Stop1") && Objects.equals(toStopId, "Stop3")) ||
                (Objects.equals(fromStopId, "Stop3") && Objects.equals(toStopId, "Stop1"))
        ) {
            return 7.30;
        }

        if((Objects.equals(fromStopId, "Stop2") && Objects.equals(toStopId, "Stop3")) ||
                (Objects.equals(fromStopId, "Stop3") && Objects.equals(toStopId, "Stop2"))
        ) {
            return 5.50;
        }

        System.out.println("Unable to find stop cost information");
        return 0;
    }
}
