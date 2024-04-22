package Models;

import java.util.Objects;

public class Stop {
    private final String stopId;

    public Stop(String stopId) {
        this.stopId = stopId;
    }

    @Override
    public String toString() {
        return stopId;
    }

    // Override equals method
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if(obj instanceof String) {
            return Objects.equals(this.stopId, obj);
        }

        if (obj instanceof Stop otherStop) {
            return this.stopId.equals(otherStop.stopId);
        }

        return false;
    }
}
