package de.seven.converter.bpmn.model;

import de.seven.converter.model.StateBuilder;
import de.seven.converter.model.StateDefinition;
import de.seven.converter.model.TaskStateDefinition;
import lombok.Data;
import org.apache.commons.lang3.Validate;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.Process;

import static de.seven.converter.bpmn.constants.Constants.MAY_NOT_BE_NULL;

@Data
public class StateBuilderContext {

    private BpmnModelInstance bpmnModelInstance;
    private Process process;
    private StateBuilder stateBuilder;

    public void addStateDefinition(StateDefinition stateDefinition) {
        Validate.notNull(stateDefinition, MAY_NOT_BE_NULL, "stateDefinition");

        stateBuilder.addState(stateDefinition);
    }
}
