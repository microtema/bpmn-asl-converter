package de.seven.converter.bpmn.enums;

import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.model.bpmn.instance.EndEvent;
import org.camunda.bpm.model.bpmn.instance.ExclusiveGateway;
import org.camunda.bpm.model.bpmn.instance.InclusiveGateway;
import org.camunda.bpm.model.bpmn.instance.IntermediateCatchEvent;
import org.camunda.bpm.model.bpmn.instance.IntermediateThrowEvent;
import org.camunda.bpm.model.bpmn.instance.ParallelGateway;
import org.camunda.bpm.model.bpmn.instance.Process;
import org.camunda.bpm.model.bpmn.instance.ServiceTask;
import org.camunda.bpm.model.bpmn.instance.StartEvent;
import org.camunda.bpm.model.bpmn.instance.SubProcess;
import org.camunda.bpm.model.bpmn.instance.Task;
import org.camunda.bpm.model.xml.instance.ModelElementInstance;

public enum EIPType {

    PROCESS("BpmnProcess", Process.class),

    BPMN_SUB_PROCESS("BpmnSubProcess", Process.class),

    SUB_PROCESS("SubProcess", SubProcess.class),

    MESSAGE_START_EVENT("StartEventMessageQueue", StartEvent.class),

    MESSAGE_CATCH_EVENT("CatchEventMessageQueue", IntermediateCatchEvent.class),

    MESSAGE_ACK_CATCH_EVENT("MessageAcknowledgementCatchEvent", IntermediateCatchEvent.class),

    MESSAGE_ACK_THROW_EVENT("MessageAcknowledgementThrowEvent", IntermediateThrowEvent.class),

    START_EVENT("StartEvent", StartEvent.class),

    MESSAGE_THROW_EVENT("ThrowEventMessageQueue", IntermediateThrowEvent.class),

    END_EVENT("EndEvent", EndEvent.class),

    BOUNDARY_END_EVENT("BoundaryEndEvent", EndEvent.class),

    TERMINATE_END_EVENT("EndEventTerminate", EndEvent.class),

    TIME_CYCLE_START_EVENT("TimeCycle", StartEvent.class),

    TIME_DURATION_START_EVENT("TimeDuration", StartEvent.class),

    SERVICE_TASK("ServiceTask", ServiceTask.class),

    PARALLEL_GATEWAY("ParallelGateway", ParallelGateway.class),

    MESSAGE_FILTER("MessageFilter", ServiceTask.class),

    CONTENT_FILTER("ContentFilter", ServiceTask.class),

    MESSAGE_SPLITTER("MessageSplitter", ServiceTask.class),

    MESSAGE_COMPOSER("MessageComposer", ServiceTask.class),

    MESSAGE_NORMALIZER("MessageNormalizer", ServiceTask.class),

    MESSAGE_TRANSLATOR("MessageTranslator", ServiceTask.class),

    TASK("Task", Task.class),

    EXCLUSIVE_GATEWAY("ExclusiveGateway", ExclusiveGateway.class),

    INCLUSIVE_GATEWAY("InclusiveGateway", InclusiveGateway.class),

    CONDITIONAL_TIMEOUT_INCLUSIVE_GATEWAY("ConditionalTimeoutInclusiveGateway", InclusiveGateway.class),

    CONDITIONAL_COMPLETION_SIZE_INCLUSIVE_GATEWAY("ConditionalCompletionSizeInclusiveGateway", InclusiveGateway.class);


    private final String eipTypeName;
    private final Class<? extends ModelElementInstance> bpmnComponentType;
    private final String bpmnElementType;

    private EIPType(String eipTypeName, Class<? extends ModelElementInstance> bpmnComponentType) {
        this.eipTypeName = eipTypeName;
        this.bpmnComponentType = bpmnComponentType;
        this.bpmnElementType = "bpmn:" + bpmnComponentType.getSimpleName();
    }

    public String getName() {
        return eipTypeName;
    }

    public Class<? extends ModelElementInstance> getBpmnComponentType() {
        return bpmnComponentType;
    }

    public String getEipTypeName() {
        return eipTypeName;
    }

    public String getBpmnElementType() {
        return bpmnElementType;
    }

    public static EIPType findByEipTypeName(String eipTypeName) {

        if(StringUtils.isBlank(eipTypeName)){
            return null;
        }

        for (EIPType camelDslType : EIPType.values()) {
            if (camelDslType.eipTypeName.equalsIgnoreCase(eipTypeName)) {
                return camelDslType;
            }
        }

        throw new IllegalArgumentException("Unable to find CamelDslType by given eipTypeName: " + eipTypeName);
    }
}
