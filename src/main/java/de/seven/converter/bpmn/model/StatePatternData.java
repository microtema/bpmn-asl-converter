package de.seven.converter.bpmn.model;

import de.seven.converter.bpmn.enums.PatternType;
import lombok.Data;
import org.camunda.bpm.model.bpmn.instance.FlowNode;

import java.util.ArrayList;
import java.util.List;

@Data
public class StatePatternData {

    private FlowNode flowNode;

    private List<FlowNode> flowNodes = new ArrayList<>();

    private PatternType patternType;

    private List<StatePatternData> children = new ArrayList<>();
}
