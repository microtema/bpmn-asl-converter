package de.seven.converter.bpmn.pattern;

import de.seven.converter.Converter;
import de.seven.converter.bpmn.model.StatePatternData;
import de.seven.converter.util.ListUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.camunda.bpm.model.bpmn.instance.FlowNode;
import org.camunda.bpm.model.bpmn.instance.SequenceFlow;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static de.seven.converter.bpmn.constants.Constants.MAY_NOT_BE_NULL;
import static org.apache.commons.lang3.Validate.notNull;

public class SequenceFlows2StatePatternDataConverter implements Converter<Collection<SequenceFlow>, List<StatePatternData>> {

    private final FlowNode2StatePatternDataConverter flowNode2StatePatternDataConverter;

    public SequenceFlows2StatePatternDataConverter(FlowNode2StatePatternDataConverter flowNode2StatePatternDataConverter) {
        this.flowNode2StatePatternDataConverter = flowNode2StatePatternDataConverter;
    }

    @Override
    public void update(Collection<SequenceFlow> orig, List<StatePatternData> target) {
        notNull(target, MAY_NOT_BE_NULL, "target");

        if (CollectionUtils.isEmpty(orig)) {
            return;
        }

        for (SequenceFlow sequenceFlow : orig) {

            FlowNode flowTarget = sequenceFlow.getTarget();

            StatePatternData groupPatternData = flowNode2StatePatternDataConverter.convert(flowTarget);

            if (groupPatternData == null) {
                continue;
            }

            target.add(groupPatternData);

            //NOTE: Start next Group Search
            FlowNode flowNode = ListUtil.last(groupPatternData.getFlowNodes());

            Optional.ofNullable(flowNode).ifPresent(it -> groupPatternData.getChildren().addAll(convert(it.getOutgoing())));
        }
    }

    @Override
    public List<StatePatternData> createInstance() {

        return new ArrayList<>();
    }
}
