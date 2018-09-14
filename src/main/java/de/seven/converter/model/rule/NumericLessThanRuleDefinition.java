package de.seven.converter.model.rule;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.seven.converter.model.ChoiceRuleDefinition;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * A Choice state must have a Choices field whose value is a non-empty array,
 * whose every element is a object called a Choice Rule.
 */
@Data
public class NumericLessThanRuleDefinition extends ChoiceRuleDefinition<Integer> {

    /**
     * Numeric Input variable to compared.
     */
    @JsonProperty("NumericLessThan")
    @NotNull
    private Integer comparison;
}
