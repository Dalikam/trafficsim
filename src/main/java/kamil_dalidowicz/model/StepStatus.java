package kamil_dalidowicz.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

public class StepStatus {
    @JsonProperty("leftVehicles")
    public List<String> leftVehicles = new ArrayList<>();

    public StepStatus() {
    }

    public StepStatus(List<String> leftVehicles) {
        this.leftVehicles = leftVehicles;
    }

    public List<String> getLeftVehicles() {
        return leftVehicles;
    }
}
