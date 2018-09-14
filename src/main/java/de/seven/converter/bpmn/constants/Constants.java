package de.seven.converter.bpmn.constants;

public final class Constants {

    public static final String MAY_NOT_BE_NULL = "The given %s may not be null!";
    public static final String MAY_NOT_BE_EMPTY = "The given %s may not be empty!";

    private Constants() {
        throw new UnsupportedOperationException(getClass().getName() + " should not be called with new!");
    }

}