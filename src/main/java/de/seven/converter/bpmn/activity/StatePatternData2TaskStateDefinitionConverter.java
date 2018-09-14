package de.seven.converter.bpmn.activity;

import de.seven.converter.bpmn.enums.PatternType;
import de.seven.converter.bpmn.model.StateBuilderContext;
import de.seven.converter.bpmn.model.StatePatternData;
import de.seven.converter.model.TaskStateDefinition;
import org.apache.commons.lang3.Validate;
import org.camunda.bpm.model.bpmn.instance.FlowNode;

import java.util.Optional;

import static de.seven.converter.bpmn.constants.Constants.MAY_NOT_BE_NULL;

public class StatePatternData2TaskStateDefinitionConverter implements StatePatternData2StateDefinitionConverter<TaskStateDefinition> {

    @Override
    public void update(StatePatternData source, TaskStateDefinition target, StateBuilderContext context) {
        Validate.notNull(source, MAY_NOT_BE_NULL, "source");
        Validate.notNull(target, MAY_NOT_BE_NULL, "target");
        Validate.notNull(context, MAY_NOT_BE_NULL, "context");

        FlowNode flowNode = source.getFlowNode();

        target.setName(Optional.ofNullable(flowNode.getName()).orElse(flowNode.getId()));
    }

    @Override
    public TaskStateDefinition createInstance() {

        return new TaskStateDefinition();
    }

    @Override
    public PatternType getPatternType(){

        return PatternType.START;
    }
}
