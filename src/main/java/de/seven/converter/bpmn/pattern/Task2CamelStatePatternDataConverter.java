package de.seven.converter.bpmn.pattern;

import de.seven.converter.Converter;
import de.seven.converter.bpmn.model.StatePatternData;
import org.camunda.bpm.model.bpmn.instance.CatchEvent;
import org.camunda.bpm.model.bpmn.instance.Task;

public class Task2CamelStatePatternDataConverter implements Converter<Task, StatePatternData> {

    @Override
    public Class<Task> getSourceType() {

        return Task.class;
    }
}
