package de.seven.converter.bpmn.pattern;

import de.seven.converter.Converter;
import de.seven.converter.bpmn.enums.PatternType;
import de.seven.converter.bpmn.model.StatePatternData;
import org.camunda.bpm.model.bpmn.instance.CatchEvent;

import static de.seven.converter.bpmn.constants.Constants.MAY_NOT_BE_NULL;
import static org.apache.commons.lang3.Validate.notNull;

public class CatchEvent2StatePatternDataConverter implements Converter<CatchEvent, StatePatternData> {

    private final SequenceFlows2StatePatternDataConverter sequenceFlows2StatePatternDataConverter;

    public CatchEvent2StatePatternDataConverter(SequenceFlows2StatePatternDataConverter sequenceFlows2StatePatternDataConverter) {
        this.sequenceFlows2StatePatternDataConverter = sequenceFlows2StatePatternDataConverter;
    }

    @Override
    public void update(CatchEvent source, StatePatternData target) {
        notNull(source, MAY_NOT_BE_NULL, "source");
        notNull(target, MAY_NOT_BE_NULL, "target");

        target.setFlowNode(source);
        target.setPatternType(PatternType.START);
        target.setChildren(sequenceFlows2StatePatternDataConverter.convert(source.getOutgoing()));
    }

    @Override
    public StatePatternData createInstance() {

        return new StatePatternData();
    }

    @Override
    public Class<CatchEvent> getSourceType() {

        return CatchEvent.class;
    }
}
