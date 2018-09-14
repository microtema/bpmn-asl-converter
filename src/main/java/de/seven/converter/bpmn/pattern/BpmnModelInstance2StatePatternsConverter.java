package de.seven.converter.bpmn.pattern;

import de.seven.converter.Converter;
import de.seven.converter.bpmn.model.StatePatternData;
import de.seven.converter.bpmn.util.ModelElementInstanceUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.FlowNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

public class BpmnModelInstance2StatePatternsConverter implements Converter<BpmnModelInstance, List<StatePatternData>> {

    private final Set<Converter<? extends FlowNode, StatePatternData>> converters = new HashSet<>();

    public BpmnModelInstance2StatePatternsConverter(CatchEvent2StatePatternDataConverter catchEvent2StatePatternDataConverter,
                                                    Task2CamelStatePatternDataConverter task2CamelStatePatternDataConverter) {
        this.converters.add(catchEvent2StatePatternDataConverter);
        this.converters.add(task2CamelStatePatternDataConverter);
    }

    @Override
    public List<StatePatternData> convert(BpmnModelInstance orig) {
        if (orig == null) {
            return Collections.emptyList();
        }

        List<StatePatternData> list = createInstance();

        List<FlowNode> startElements = ModelElementInstanceUtil.getStartElements(orig);

        for (FlowNode flowNode : startElements) {

            Converter<FlowNode, StatePatternData> converter = getConverter(flowNode);

            StatePatternData groupPatternData = converter.convert(flowNode);

            CollectionUtils.addIgnoreNull(list, groupPatternData);
        }

        return list;
    }

    @Override
    public List<StatePatternData> createInstance() {

        return new ArrayList<>();
    }

    @SuppressWarnings("unchecked")
    private Converter<FlowNode, StatePatternData> getConverter(FlowNode flowNode) {
        assert flowNode != null;

        Class<? extends FlowNode> flowNodeClass = flowNode.getClass();

        return (Converter<FlowNode, StatePatternData>) converters.stream()
                .filter(it -> it.getSourceType().isAssignableFrom(flowNodeClass))
                .findAny()
                .orElseThrow(() -> new NoSuchElementException("Unable to find Converter for given Type: " + flowNodeClass + " within EIP Type: " + ModelElementInstanceUtil.findEipType(flowNode)));
    }
}
