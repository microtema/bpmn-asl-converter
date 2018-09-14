package de.seven.converter.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.seven.converter.enums.StateType;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * The Parallel state ("Type": "Parallel") can be used to create parallel branches of execution in your state machine.
 */
@Data
public class ParallelStateDefinition extends StateDefinition {

    /**
     * An array of objects that specify state machines to execute in parallel.
     * Each such state machine object must have fields named States and StartAt whose meanings are exactly like
     * those in the top level of a state machine.
     */
    @JsonProperty("Branches")
    @NotNull
    @Size(min = 0)
    private List<StateBuilder> branches = new ArrayList<>();

    /**
     * Specifies where (in the input) to place the output of the branches.
     * The input is then filtered as prescribed by the OutputPath field (if present)
     * before being used as the state's output. For more information, see Input and Output Processing.
     */
    @JsonProperty("ResultPath")
    private String resultPath;

    /**
     * An array of objects, called Retriers, that define a retryStateDefinitions policy in case the state encounters runtime errors.
     * For more information, see Retrying After an Error.
     */
    @JsonProperty("Retry")
    private List<RetryStateDefinition> retryStateDefinitions;

    /**
     * An array of objects, called Catchers, that define a fallback state which is executed in case the state encounters
     * runtime errors and its retryStateDefinitions policy has been exhausted or is not defined.
     * For more information, see Fallback States.
     */
    @JsonProperty("Catch")
    private List<CatchStateDefinition> catchStateDefinitions;

    public ParallelStateDefinition() {
        super(StateType.PARALLEL);
    }
}
