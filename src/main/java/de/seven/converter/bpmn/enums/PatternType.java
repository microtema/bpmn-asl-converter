package de.seven.converter.bpmn.enums;

/**
 * Slice BPMN Process Flow in small state Pattern
 */
public enum PatternType {

    /**
     * Message, File, Event, Scheduled
     */
    START,

    /**
     * Create temporary from endpoint.
     */
    TEMPORARY_START,

    /**
     * Create temporary to endpoint.
     */
    TEMPORARY_END,

    /**
     * Collect all processes that are chained together
     */
    PIPELINE,

    GATEWAY,

    /**
     * Create new Route for each outgoing sequence
     */
    MULTICAST,

    /**
     * How can you decouple individual processing steps so that messages can be passed
     * to different filters depending on a set of conditions?
     * Choice EIP.
     * Create new Route for each outgoing filtered sequence
     */
    CHOICE,

    /**
     * Receive multiple sequence
     */
    AGGREGATE,

    /**
     * The Splitter from the EIP patterns allows you split
     * a message into a number of pieces and process them individually
     * Create new Route for each outgoing filtered sequence with InOut MEP
     */
    SPLIT,

    /**
     * In Multicast pattern we have to create temporary route for each multiplex
     */
    CHILD_ROUTE,

    /**
     * In Sub Process pattern we have to create temporary route
     */
    SUB_PROCESS,

    /**
     * Message, File, Event
     */
    THROW,

    /**
     * Message, File, Event
     */
    END
}
