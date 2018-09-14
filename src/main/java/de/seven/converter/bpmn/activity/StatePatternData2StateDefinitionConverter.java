package de.seven.converter.bpmn.activity;

import de.seven.converter.ExtendedConverter;
import de.seven.converter.bpmn.enums.PatternType;
import de.seven.converter.bpmn.model.StateBuilderContext;
import de.seven.converter.bpmn.model.StatePatternData;
import de.seven.converter.model.StateDefinition;

public interface StatePatternData2StateDefinitionConverter<T extends StateDefinition> extends ExtendedConverter<StatePatternData, T, StateBuilderContext> {

    PatternType getPatternType();
}
