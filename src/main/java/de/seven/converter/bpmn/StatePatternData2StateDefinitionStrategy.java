package de.seven.converter.bpmn;

import de.seven.converter.Converter;
import de.seven.converter.ExtendedConverter;
import de.seven.converter.bpmn.activity.StatePatternData2EndStateDefinitionConverter;
import de.seven.converter.bpmn.activity.StatePatternData2PipelineStateDefinitionConverter;
import de.seven.converter.bpmn.activity.StatePatternData2StartStateDefinitionConverter;
import de.seven.converter.bpmn.activity.StatePatternData2StateDefinitionConverter;
import de.seven.converter.bpmn.activity.StatePatternData2TaskStateDefinitionConverter;
import de.seven.converter.bpmn.enums.PatternType;
import de.seven.converter.bpmn.model.StateBuilderContext;
import de.seven.converter.bpmn.model.StatePatternData;
import de.seven.converter.model.StateDefinition;
import org.apache.commons.lang3.Validate;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

import static de.seven.converter.bpmn.constants.Constants.MAY_NOT_BE_NULL;

public class StatePatternData2StateDefinitionStrategy implements ExtendedConverter<StatePatternData, StateDefinition, StateBuilderContext> {

    private Set<StatePatternData2StateDefinitionConverter> converters = new HashSet<>();

    public StatePatternData2StateDefinitionStrategy(StatePatternData2StartStateDefinitionConverter statePatternData2StartStateDefinitionConverter,
                                                    StatePatternData2PipelineStateDefinitionConverter statePatternData2PipelineStateDefinitionConverter,
                                                    StatePatternData2EndStateDefinitionConverter statePatternData2EndStateDefinitionConverter) {
        this.converters.add(statePatternData2StartStateDefinitionConverter);
        this.converters.add(statePatternData2PipelineStateDefinitionConverter);
        this.converters.add(statePatternData2EndStateDefinitionConverter);
    }

    @Override
    @SuppressWarnings("unchecked")
    public StateDefinition convert(StatePatternData source, StateBuilderContext context) {
        Validate.notNull(source, MAY_NOT_BE_NULL, "source");

        PatternType patternType = Validate.notNull(source.getPatternType(), MAY_NOT_BE_NULL, "patternType");

        StatePatternData2StateDefinitionConverter<StateDefinition> converter = getConverter(patternType);

        StateDefinition stateDefinition = converter.convert(source, context);

        convertToList(source.getChildren(), context);

        return stateDefinition;
    }

    private StatePatternData2StateDefinitionConverter getConverter(PatternType patternType) {
        assert patternType != null;

        return converters.stream()
                .filter(it -> it.getPatternType() == patternType)
                .findAny()
                .orElseThrow(() -> new NoSuchElementException("Unable to find converter by PatternType: " + patternType));
    }
}
