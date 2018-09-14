package de.seven.converter.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.seven.converter.enums.StateType;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * A Choice state ("Type": "Choice") adds branching logic to a state machine.
 */
@Data
public class ChoiceStateDefinition extends StateDefinition {

    /**
     * An array of Choice Rules that determines which state the state machine transitions to next.
     */
    @JsonProperty("Choices")
    private List<ChoiceRuleDefinition> choices = new ArrayList<>();

    /**
     * The name of the state to transition to if none of the transitions in Choices is taken.
     *
     * Important: Choice states do not support the End field. In addition, they use Next only inside their Choices field.
     */
    @JsonProperty("Default")
    private String defaultState;

    public ChoiceStateDefinition() {
        super(StateType.CHOICE);
    }
}
