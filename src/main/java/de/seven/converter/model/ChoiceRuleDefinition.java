package de.seven.converter.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * A Choice state must have a Choices field whose value is a non-empty array,
 * whose every element is a object called a Choice Rule.
 */
@Data
public class ChoiceRuleDefinition<T> {

    /**
     * Input variable to compared.
     */
    @JsonProperty("Variable")
    @NotNull
    private String variable;

    private T comparison;

    /**
     * The name of the next state that will be run when the current state finishes.
     * Some state types, such as Choice, allow multiple transition states.
     */
    @JsonProperty("Next")
    private String next;
}
