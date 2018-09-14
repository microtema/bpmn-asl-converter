package de.seven.converter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import org.apache.commons.lang3.Validate;

import javax.validation.constraints.NotNull;
import java.util.LinkedHashMap;
import java.util.Map;

import static de.seven.converter.bpmn.constants.Constants.MAY_NOT_BE_EMPTY;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"description", "StartAt", "version", "timeoutSeconds", "heartbeatSeconds", "states"})
@JsonIgnoreProperties({"name"})
public class StateBuilder {

    /*
     * Unique name
     */
    @NotNull
    @JsonIgnore
    private String name;

    /*
     * A human-readable description of the state machine.
     */
    @JsonProperty("Comment")
    private String description;

    /*
     * The maximum number of seconds an execution of the state machine may run;
     * if it runs longer than the specified time, then the execution fails with an States.Timeout
     */
    @JsonProperty("TimeoutSeconds")
    private Integer timeoutSeconds;

    /**
     * The version of Amazon States Language used in the state machine, default is "1.0".
     */
    @JsonProperty("Version")
    private String version;

    /*
     * List of States
     */
    @NotNull
    @JsonProperty("States")
    private Map<String, StateDefinition> states = new LinkedHashMap<>();

    /**
     * A string that must exactly match (case-sensitive) the name of one of the state objects.
     *
     * @return State name
     */
    @NotNull
    @JsonProperty("StartAt")
    public String getStartAt() {
        Validate.notEmpty(states, MAY_NOT_BE_EMPTY, "states");

        return states.values().stream().findAny().get().getName();
    }

    @JsonIgnore
    public void addState(StateDefinition stateDefinition) {

        states.put(stateDefinition.getName(), stateDefinition);
    }

}
