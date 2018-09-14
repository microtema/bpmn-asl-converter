package de.seven.converter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import de.seven.converter.enums.StateType;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"type", "comment", "resource", "inputPath", "outputPath", "result", "resultPath",
        "timeoutSeconds", "heartbeatSeconds", "seconds", "timestamp", "timestampPath", "secondsPath", "cause", "error",
        "branches", "next", "end"})
public abstract class StateDefinition {

    /*
     * State Definition unique name
     */
    @NotNull
    @JsonIgnore
    private String name;

    /**
     * State Type
     */
    @NotNull
    @JsonProperty("Type")
    private final StateType type;

    /**
     * Holds a human-readable description of the state.
     */
    @JsonProperty("Comment")
    private String comment;

    /**
     * A path that selects a portion of the state's input to be passed to the state's task for processing.
     * If omitted, it has the value $ which designates the entire input. For more information,
     * see Input and Output Processing).
     */
    @JsonProperty("InputPath")
    private String inputPath;

    /**
     * A path that selects a portion of the state's input to be passed to the state's output.
     * If omitted, it has the value $ which designates the entire input.
     * For more information, see Input and Output Processing.
     */
    @JsonProperty("OutputPath")
    private String outputPath;

    /**
     * The name of the next state that will be run when the current state finishes.
     * Some state types, such as Choice, allow multiple transition states.
     */
    @JsonProperty("Next")
    private String next;

    /**
     * Designates this state as a terminal state (it ends the execution) if set to true.
     * There can be any number of terminal states per state machine.
     * Only one of Next or End can be used in a state. Some state types,
     * such as Choice, do not support or use the End field.
     */
    @JsonProperty("END")
    private Boolean end;
}
