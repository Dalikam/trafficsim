package kamil_dalidowicz.model;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("step")
public class StepCommand extends Command {
    @Override
    public CommandType getType() {
        return CommandType.step;
    }
}