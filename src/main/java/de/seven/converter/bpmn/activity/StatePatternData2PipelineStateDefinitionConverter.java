package de.seven.converter.bpmn.activity;

import de.seven.converter.bpmn.enums.PatternType;
import de.seven.converter.bpmn.enums.PropertyType;
import de.seven.converter.bpmn.model.StateBuilderContext;
import de.seven.converter.bpmn.model.StatePatternData;
import de.seven.converter.bpmn.util.ModelElementInstanceUtil;
import de.seven.converter.model.TaskStateDefinition;
import org.apache.commons.lang3.Validate;
import org.camunda.bpm.model.bpmn.instance.FlowNode;
import org.camunda.bpm.model.bpmn.instance.StartEvent;
import org.camunda.bpm.model.bpmn.instance.Task;

import java.util.List;
import java.util.Optional;

import static de.seven.converter.bpmn.constants.Constants.MAY_NOT_BE_NULL;

public class StatePatternData2PipelineStateDefinitionConverter implements StatePatternData2StateDefinitionConverter<TaskStateDefinition> {

    private final StatePatternData2TaskStateDefinitionConverter statePatternData2TaskStateDefinitionConverter;

    public StatePatternData2PipelineStateDefinitionConverter(StatePatternData2TaskStateDefinitionConverter statePatternData2TaskStateDefinitionConverter) {
        this.statePatternData2TaskStateDefinitionConverter = statePatternData2TaskStateDefinitionConverter;
    }

    @Override
    public TaskStateDefinition convert(StatePatternData source, StateBuilderContext context) {
        Validate.notNull(source, MAY_NOT_BE_NULL, "source");
        Validate.notNull(context, MAY_NOT_BE_NULL, "context");

        List<FlowNode> flowNodes = source.getFlowNodes();

        for (int index = 0; index < flowNodes.size(); index++) {

            FlowNode flowNode = flowNodes.get(index);

            TaskStateDefinition taskStateDefinition = new TaskStateDefinition();
            taskStateDefinition.setName(flowNode.getId());

            FlowNode nextFlowNode = nextFlowNode(flowNodes, index);

            Optional.ofNullable(nextFlowNode).ifPresent(it -> taskStateDefinition.setNext(it.getId()));
            taskStateDefinition.setEnd(nextFlowNode == null ? Boolean.TRUE : null);

            taskStateDefinition.setResource(ModelElementInstanceUtil.getContentText(flowNode));
            taskStateDefinition.setResultPath(ModelElementInstanceUtil.findExtensionPropertyValue(flowNode, PropertyType.RESULT_PATH, String.class));

            context.addStateDefinition(taskStateDefinition);
        }

        return null;
    }

    private FlowNode nextFlowNode(List<FlowNode> flowNodes, int currentIndex) {
        assert flowNodes != null;

        int index = currentIndex + 1;

        if (index < flowNodes.size()) {

            return flowNodes.get(index);
        }

        return null;
    }

    @Override
    public PatternType getPatternType() {

        return PatternType.PIPELINE;
    }
}
