package kamil_dalidowicz.model;

import java.util.*;
import java.util.stream.Collectors;
import kamil_dalidowicz.strategy.LightControlStrategy;

public class Intersection {
    private static final int MIN_GREEN = 1;
    private static final int MAX_GREEN = 5;
    private static final int YELLOW_DURATION = 1;
    private static final int PRIORITY_BONUS = 2;
    private int currentGroup = 0; // 0 = [north,south], 1 = [east,west]
    private int greenStreak = 1;
    private long stepCount = 0;

    private final List<List<Road>> groups = List.of(
            List.of(Road.north, Road.south),
            List.of(Road.east, Road.west));


    private LightPhase phase = LightPhase.GREEN;
    private int timer = 0;
    private int greenDuration = MIN_GREEN;

    private long nextSeq = 0;
    private final Set<Road> priorityRoads = new HashSet<>();
    private final Map<Road, Map<LaneType, Queue<Vehicle>>> lanes = new EnumMap<>(Road.class);

    public Intersection() {
        for (Road r : Road.values()) {
            Map<LaneType, Queue<Vehicle>> map = new EnumMap<>(LaneType.class);
            for (LaneType lt : LaneType.values()) {
                map.put(lt, new LinkedList<>());
            }
            lanes.put(r, map);
        }
    }

    public void addVehicle(String id, Road start, Road end) {
        Vehicle v = new Vehicle(id, start, end, nextSeq++);
        lanes.get(start).get(v.getLaneType()).add(v);
    }

    private int totalQueueSize(Road road) {
        return lanes.get(road).values().stream()
                .mapToInt(Queue::size)
                .sum();
    }

    private int computeGreenDuration(List<Road> grp) {
        int sum = grp.stream().mapToInt(this::totalQueueSize).sum();
        int base = Math.min(Math.max(sum, MIN_GREEN), MAX_GREEN);
        boolean hasPriority = grp.stream().anyMatch(priorityRoads::contains);
        if (hasPriority && sum > 0) {
            return Math.min(base + PRIORITY_BONUS, MAX_GREEN);
        }
        return base;
    }

    public List<String> step(LightControlStrategy strat) {
        stepCount++;

        // 0) No cars: the lights don't matter.
        boolean any = Arrays.stream(Road.values())
                .anyMatch(r -> totalQueueSize(r) > 0);
        if (!any) {
            currentGroup = (int) (stepCount % 2);
            phase = LightPhase.GREEN;
            timer = greenStreak = 1;
            return List.of();
        }

        List<Road> g1 = groups.get(0);
        List<Road> g2 = groups.get(1);
        int sum1 = g1.stream().mapToInt(this::totalQueueSize).sum();
        int sum2 = g2.stream().mapToInt(this::totalQueueSize).sum();
        boolean empty1 = sum1 == 0;
        boolean empty2 = sum2 == 0;

        // 1) Cars only on one road: they get green ligts.
        if (empty1 ^ empty2) {
            int target = empty1 ? 1 : 0;
            if (currentGroup != target) {
                switchToGroup(target);
            }
        }
        else if (greenStreak >= 5) {
            switchToGroup((currentGroup + 1) % 2);
        }
        // 2) Bigger traffic gets green.
        else if (!empty1 && !empty2) {
            int target = sum1 >= sum2 ? 0 : 1;
            if (currentGroup != target) {
                switchToGroup(target);
            }
        }
        // 3) Optional malicious or helpful twist.
        strat.apply(this, stepCount);

        if (phase == LightPhase.GREEN && timer == 0) {
            greenDuration = computeGreenDuration(groups.get(currentGroup));
        }

        // 4) We can release up to two cars.
        List<Vehicle> leftVs = new ArrayList<>();
        if (phase == LightPhase.GREEN) {
            for (Road r : groups.get(currentGroup)) {
                for (LaneType lt : List.of(LaneType.STRAIGHT, LaneType.RIGHT, LaneType.LEFT)) {
                    Queue<Vehicle> q = lanes.get(r).get(lt);
                    if (!q.isEmpty()) {
                        leftVs.add(q.poll());
                        break;
                    }
                }
            }
        }

        
        leftVs.sort(Comparator.comparingLong(Vehicle::getSeq));

        // 5) We switch the phases.
        timer++;
        if (phase == LightPhase.GREEN) {
            greenStreak++;
            if (timer >= greenDuration) {
                phase = LightPhase.YELLOW;
                timer = 1;
            }
        } else if (phase == LightPhase.YELLOW) {
            if (timer >= YELLOW_DURATION) {
                phase = LightPhase.GREEN;
                timer = 0;
                greenStreak = 1;
            }
        }

        return leftVs.stream()
                .map(Vehicle::getId)
                .collect(Collectors.toList());
    }

    private void switchToGroup(int targetGroup) {
        currentGroup = targetGroup;
        phase = LightPhase.GREEN;
        timer = 0;
        greenStreak = 1;
    }

    public void setPriority(Road road, boolean isPriority) {
        if (isPriority)
            priorityRoads.add(road);
        else
            priorityRoads.remove(road);
    }

    public boolean isPriority(Road road) {
        return priorityRoads.contains(road);
    }
    
    public void switchToNextGroup() {
        currentGroup = (currentGroup + 1) % groups.size();
        phase = LightPhase.GREEN;
        timer = 0;
        greenStreak = 1;
    }

    public int getGreenStreak() {
        return greenStreak;
    }

}
