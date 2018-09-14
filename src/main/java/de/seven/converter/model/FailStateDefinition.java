package de.seven.converter.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.seven.converter.enums.StateType;
import lombok.Data;

/**
 * A Fail state ("Type": "Fail") stops the execution of the state machine and marks it as a failure.
 * <p>
 * The Fail state only allows the use of Type and Comment fields from the set of common state fields.
 */
@Data
public class FailStateDefinition extends StateDefinition {

    /**
     * Provides a custom failure string that can be used for operational or diagnostic purposes.
     */
    @JsonProperty("Cause")
    private String cause;

    /**
     * Provides an error name that can be used for error handling (Retry/Catch), operational or diagnostic purposes.
     */
    @JsonProperty("Error")
    private String error;

    public FailStateDefinition() {
        super(StateType.FAIL);
    }
}
