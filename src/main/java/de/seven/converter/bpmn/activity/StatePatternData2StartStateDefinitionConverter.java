package de.seven.converter.bpmn.activity;

import de.seven.converter.bpmn.enums.PatternType;
import de.seven.converter.bpmn.model.StateBuilderContext;
import de.seven.converter.bpmn.model.StatePatternData;
import de.seven.converter.model.StateDefinition;

import java.util.List;

public class StatePatternData2StartStateDefinitionConverter implements StatePatternData2StateDefinitionConverter<StateDefinition> {

    @Override
    public StateDefinition convert(StatePatternData source, StateBuilderContext context) {

        return null;
    }

    @Override
    public PatternType getPatternType(){

        return PatternType.START;
    }
}
