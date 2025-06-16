package kamil_dalidowicz.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AddVehicleCommand extends Command {
    private final String vehicleId;
    private final Road startRoad;
    private final Road endRoad;

    @JsonCreator
    public AddVehicleCommand(
            @JsonProperty("vehicleId") String vehicleId,
            @JsonProperty("startRoad") Road startRoad,
            @JsonProperty("endRoad") Road endRoad) {
        this.vehicleId = vehicleId;
        this.startRoad = startRoad;
        this.endRoad = endRoad;
    }

    @Override
    public CommandType getType() {
        return CommandType.addVehicle;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public Road getStartRoad() {
        return startRoad;
    }

    public Road getEndRoad() {
        return endRoad;
    }
}