package kamil_dalidowicz.model;

public class Vehicle {
    private final String id;
    private final Road start;
    private final Road destination;
    private final LaneType laneType;
    private final long seq;

    public Vehicle(String id, Road start, Road dest, long seq) {
        this.id = id;
        this.start = start;
        this.destination = dest;
        this.laneType = determineLaneType(start, dest);
        this.seq = seq;
    }

    public long getSeq() {
        return seq;
    }

    private LaneType determineLaneType(Road s, Road d) {
        if ((s == Road.north && d == Road.south) ||
                (s == Road.south && d == Road.north) ||
                (s == Road.east && d == Road.west) ||
                (s == Road.west && d == Road.east)) {
            return LaneType.STRAIGHT;
        }
        // lewoskręty
        if ((s == Road.north && d == Road.west) ||
                (s == Road.east && d == Road.north) ||
                (s == Road.south && d == Road.east) ||
                (s == Road.west && d == Road.south)) {
            return LaneType.LEFT;
        }
        // reszta to prawoskręty
        return LaneType.RIGHT;
    }

    public String getId() {
        return id;
    }

    public Road getStart() {
        return start;
    }

    public Road getDestination() {
        return destination;
    }

    public LaneType getLaneType() {
        return laneType;
    }
}
