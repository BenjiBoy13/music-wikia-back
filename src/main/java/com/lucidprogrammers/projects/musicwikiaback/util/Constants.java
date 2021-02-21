package com.lucidprogrammers.projects.musicwikiaback.util;

/**
 * Application constants for general use
 *
 * @author Benjamin Gil Flores
 * @since 1.0.0
 */
public class Constants {

    /** General usage **/
    public static final boolean TRUE = true;
    public static final boolean FALSE = false;

    /** User information **/
    public static final String ROLE_USER = "ROL_USER";
    public static final String ROLE_SEPARATOR = ";";
    public static final String ROLE_STARTER = "ROLE_";
    public static final Character USER_STATUS_ACTIVE = 'A';

    /** Restful information **/
    public static final int STATUS_CODE_ACCEPTED = 200;
    public static final int STATUS_CODE_CREATED = 201;
    public static final int STATUS_CODE_UNAUTHORIZED = 401;
    public static final int STATUS_CODE_FORBIDDEN = 403;
    public static final int STATUS_CODE_SERVER_ERROR = 500;
    public static final String STATUS_TYPE_SUCCESS = "Success";
    public static final String STATUS_TYPE_FAILURE = "Failure";
    public static final String STATUS_TYPE_FATAL = "Fatal";
    public static final String STATUS_MESSAGE_FAILURE = "Expected conditions were not met";
    public static final String STATUS_MESSAGE_FATAL = "Unexpected error";

}
