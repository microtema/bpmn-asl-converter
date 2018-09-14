package de.seven.converter.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.seven.converter.enums.StateType;
import lombok.Data;

import java.util.Date;

@Data
public class WaitStateDefinition extends StateDefinition {

    /**
     * A time, in seconds, to wait before beginning the state specified in the Next field.
     */
    @JsonProperty("Seconds")
    private Integer seconds;

    /**
     * An absolute time to wait until before beginning the state specified in the Next field.
     * <p>
     * Timestamps must conform to the RFC3339 profile of ISO 8601, with the further restrictions
     * that an uppercase T must separate the date and time portions, and an uppercase Z must denote
     * that a numeric time zone offset is not present, for example, 2016-08-18T17:33:00Z.
     */
    @JsonProperty("Timestamp")
    private Date timestamp;

    /**
     * A time, in seconds, to wait before beginning the state specified in the Next field,
     * specified using a path from the state's input data.
     */
    @JsonProperty("SecondsPath")
    private Integer secondsPath;

    /**
     * An absolute time to wait until before beginning the state specified in the Next field, specified using a path from the state's input data.
     * <p>
     * Note
     * <p>
     * You must specify exactly one of Seconds, Timestamp, SecondsPath, or TimestampPath.
     */
    @JsonProperty("TimestampPath")
    private String timestampPath;

    public WaitStateDefinition() {
        super( StateType.WAIT);
    }
}
