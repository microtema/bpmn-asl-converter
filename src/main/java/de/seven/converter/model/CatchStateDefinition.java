package de.seven.converter.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class CatchStateDefinition {

    @JsonProperty("ErrorEquals")
    private List<String> errorNames;

    /**
     * The name of the next state that will be run when the current state finishes.
     * Some state types, such as Choice, allow multiple transition states.
     */
    @JsonProperty("Next")
    private String next;
}
