package de.seven.converter.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class RetryStateDefinition {

    /**
     *
     */
    @JsonProperty("ErrorEquals")
    private List<String> errorNames;

    /**
     *
     */
    @JsonProperty("IntervalSeconds")
    private Integer intervalSeconds;

    /**
     *
     */
    @JsonProperty("MaxAttempts")
    private Integer maxAttempts;

    /**
     *
     */
    @JsonProperty("BackoffRate")
    private Double backoffRate;
}
