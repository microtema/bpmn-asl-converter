package de.seven.converter.bpmn.pattern;

import de.seven.converter.Converter;
import de.seven.converter.bpmn.enums.PatternType;
import de.seven.converter.bpmn.model.StatePatternData;
import de.seven.converter.bpmn.util.ModelElementInstanceUtil;
import org.camunda.bpm.model.bpmn.instance.EndEvent;
import org.camunda.bpm.model.bpmn.instance.FlowElement;
import org.camunda.bpm.model.bpmn.instance.FlowNode;
import org.camunda.bpm.model.bpmn.instance.IntermediateCatchEvent;
import org.camunda.bpm.model.bpmn.instance.IntermediateThrowEvent;
import org.camunda.bpm.model.bpmn.instance.SequenceFlow;
import org.camunda.bpm.model.bpmn.instance.Task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class FlowNode2StatePatternDataConverter implements Converter<FlowNode, StatePatternData> {

    @Override
    public StatePatternData convert(FlowNode orig) {
        if (orig == null) {
            return null;
        }

        StatePatternData dest = createInstance();

        dest.setFlowNode(orig);

        if (ModelElementInstanceUtil.isStartElement(orig)) {

            dest.setPatternType(PatternType.TEMPORARY_END);

        } else if (orig instanceof EndEvent) {

            dest.setPatternType(PatternType.END);
            dest.setFlowNodes(getFlowNodes(orig, IntermediateCatchEvent.class));

        } else if (orig instanceof IntermediateThrowEvent) {

            dest.setPatternType(PatternType.THROW);

            List<FlowNode> flowNodes = getFlowNodes(orig.getOutgoing(), IntermediateCatchEvent.class);

            if (flowNodes.isEmpty()) {

                flowNodes = Collections.singletonList(orig);
            }

            dest.setFlowNodes(flowNodes);

        } else if (orig instanceof IntermediateCatchEvent) {

            return dest;

        } else if (orig instanceof Task) {

            dest.setPatternType(PatternType.PIPELINE);
            dest.setFlowNodes(getFlowNodesAfterTask(orig));

        } else {

            return null;
        }

        return dest;
    }

    @Override
    public StatePatternData createInstance() {

        return new StatePatternData();
    }

    private List<FlowNode> getFlowNodesAfterTask(FlowNode orig) {

        if (orig == null || !(orig instanceof Task) || ModelElementInstanceUtil.isStartElement(orig)) {

            return Collections.emptyList();
        }

        List<FlowNode> processes = new ArrayList<>();

        processes.add(orig);

        Collection<SequenceFlow> outgoing = orig.getOutgoing();

        for (SequenceFlow sequenceFlow : outgoing) {
            processes.addAll(getFlowNodesAfterTask(sequenceFlow.getTarget()));
        }

        return processes;
    }

    private List<FlowNode> getFlowNodes(Collection<SequenceFlow> outgoing, Class<? extends FlowElement> searchType) {

        List<FlowNode> processes = new ArrayList<>();

        for (SequenceFlow sequenceFlow : outgoing) {
            processes.addAll(getFlowNodes(sequenceFlow.getTarget(), searchType));
        }

        return processes;
    }

    private List<FlowNode> getFlowNodes(FlowNode orig, Class<? extends FlowElement> searchType) {

        if (orig == null || !searchType.isAssignableFrom(orig.getClass())) {

            return Collections.emptyList();
        }

        List<FlowNode> processes = new ArrayList<>();

        processes.add(orig);

        Collection<SequenceFlow> outgoing = orig.getOutgoing();

        for (SequenceFlow sequenceFlow : outgoing) {
            processes.addAll(getFlowNodes(sequenceFlow.getTarget(), searchType));
        }

        return processes;
    }
}
