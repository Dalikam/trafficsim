package kamil_dalidowicz.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = AddVehicleCommand.class, name = "addVehicle"),
        @JsonSubTypes.Type(value = StepCommand.class, name = "step")
})
public abstract class Command {
    public abstract CommandType getType();
}