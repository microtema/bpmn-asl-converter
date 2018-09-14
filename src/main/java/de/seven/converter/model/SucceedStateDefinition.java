package de.seven.converter.model;

import de.seven.converter.enums.StateType;
import lombok.Data;

/**
 * A Succeed state ("Type": "Succeed") stops an execution successfully. The Succeed state is a useful target
 * for Choice state branches that don't do anything but stop the execution.
 * <p>
 * Because Succeed states are terminal states, they have no Next field, nor do they have need of an End field.
 */
@Data
public class SucceedStateDefinition extends StateDefinition {

    public SucceedStateDefinition() {
        super(StateType.SUCCEED);
    }
}
