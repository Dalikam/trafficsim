package kamil_dalidowicz.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class TrafficResult {
    @JsonProperty("stepStatuses")
    private List<StepStatus> stepStatuses;

    public TrafficResult() {
    }

    public TrafficResult(List<StepStatus> stepStatuses) {
        this.stepStatuses = stepStatuses;
    }

    public List<StepStatus> getStepStatuses() {
        return stepStatuses;
    }

    public void setStepStatuses(List<StepStatus> stepStatuses) {
        this.stepStatuses = stepStatuses;
    }
}
