package com.looper_api.constants;

public class ExceptionsMessages {

    public static final String CSV_INPUT_INVALID_ARRAY_ELEMENTS_TYPE = String.format(
            "Invalid array data type. Property 'numbers' must be of type integer array"
    );

    public static final String PALINDROME_STRING_CANNOT_BE_EMPTY = String.format(
            "Palindrome string must be at least one character long"
    );

    public static final String THREAD_WITH_ID_NOT_FOUND = "Thread with Id %d not found";

    public static final String THREAD_IS_ALREADY_TERMINATED = "Thread with Id %d is already terminated";

    public static final String KILL_ALL_LOOPERS_INTERNAL_EXCEPTION = String.format(
            "Exception appeared through the process of killing all loopers"
    );

    public static final String FIND_ALL_LOGS_INTERNAL_EXCEPTION = String.format(
            "Exception appeared through the process of finding all log records"
    );

    public static final String LOOPER_STARTING_INVALID_INPUT_SCHEMA = String.format(
            "Invalid JSON input schema on starting new looper"
    );

    public static final String LOOPER_KILL_THREAD_INVALID_INPUT_TYPE = "Thread Id must be of type (Long | Integer)";

}
