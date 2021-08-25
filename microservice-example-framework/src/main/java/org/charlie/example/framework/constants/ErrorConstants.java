package org.charlie.example.framework.constants;


/**
 * To define error code and message.
 * Error Code design a four decimal digit to represent error.
 *
 *
 * 0000-0999: represents success, with conditions
 *   0000: success.
 *   0001: success, but fallback
 *   0002: success, but failed
 *   patially success,
 * ----------------------------------------------------------
 * 1000-2000: common error
 *   1000: unknown error
 *   1001:
 * 1000-2000: persistent error
 *   1001: database connection error
 *   1002: database
 *   --------------------------------
 *   1101:
 * ---------------------------------------------------------
 * 2000-3000: network error
 *   2000:
 *   2001: cannot fetch connection from pool.
 *   2002: connection refused.
 *   2003:
 * 3000-8000: reserved
 * 8000-    : Application Error
 *
 * @author Charlie
 */
public class ErrorConstants {

    public static String ERROR_CODE = "";
    public static String ERROR_MESSAGE = "";
}
