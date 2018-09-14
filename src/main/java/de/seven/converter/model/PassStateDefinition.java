package de.seven.converter.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.seven.converter.enums.StateType;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
public class PassStateDefinition extends StateDefinition {

    /**
     * Treated as the output of a virtual task to be passed on to the next state,
     * and filtered as prescribed by the ResultPath field (if present).
     */
    @JsonProperty("Result")
    private Object result;

    /**
     * Specifies where (in the input) to place the results of executing the task specified in Resource.
     * The input is then filtered as prescribed by the OutputPath field (if present)
     * before being used as the state's output. For more information, see path.
     */
    @JsonProperty("ResultPath")
    private String resultPath;

    public PassStateDefinition() {
        super(StateType.PASS);
    }
}
