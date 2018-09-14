package de.seven.converter.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.seven.converter.enums.StateType;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class TaskStateDefinition extends StateDefinition {

    /**
     * A URI, especially an Amazon Resource Name (ARN) that uniquely identifies the specific task to execute.
     */
    @NotNull
    @JsonProperty("Resource")
    private String resource;

    /**
     * Specifies where (in the input) to place the results of executing the task specified in Resource.
     * The input is then filtered as prescribed by the OutputPath field (if present)
     * before being used as the state's output. For more information, see path.
     */
    @JsonProperty("ResultPath")
    private String resultPath;

    /**
     * An array of objects, called Retriers, that define a retryStateDefinitions policy in case the state encounters runtime errors.
     * For more information, see Retrying After an Error.
     */
    @JsonProperty("Retry")
    private List<RetryStateDefinition> retryStateDefinitions;

    /**
     * An array of objects, called Catchers, that define a fallback state which is executed in case the state encounters
     * runtime errors and its retryStateDefinitions policy has been exhausted or is not defined.
     * For more information, see Fallback States.
     */
    @JsonProperty("Catch")
    private List<CatchStateDefinition> catchStateDefinitions;

    /**
     * If the task runs longer than the specified seconds, then this state fails with a States.Timeout Error Name.
     * Must be a positive, non-zero integer. If not provided, the default value is 99999999.
     */
    @JsonProperty("TimeoutSeconds")
    private Integer timeoutSeconds;

    /**
     * If more time than the specified seconds elapses between heartbeats from the task, then this state fails
     * with an States.Timeout Error Name. Must be a positive, non-zero integer less than
     * the number of seconds specified in the TimeoutSeconds field. If not provided, the default value is 99999999.
     */
    @JsonProperty("HeartbeatSeconds")
    private Integer heartbeatSeconds;


    public TaskStateDefinition() {
        super(StateType.TASK);
    }
}
