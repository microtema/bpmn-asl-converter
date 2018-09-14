package de.seven.converter.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum StateType {

    TASK("Task"),

    PASS("Pass"),

    CHOICE("Choice"),

    FAIL("Fail"),

    SUCCEED("Succeed"),

    PARALLEL("Parallel"),

    WAIT("Wait");

    private final String value;

    private StateType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
