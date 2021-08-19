package org.charlie.template.framework.constants.io.http;


/**
 * To define constants used for http.
 *
 * @author Charlie
 */
public class HttpConstants {

    public final static String CONTENT_TYPE_JSON_VALUE = "application/json; charset=utf-8";

    public final static String CHARSET_UTF8 = "UTF-8";

    // FIXME: please modify to proper value.
    // Connections
    public final static int HTTP_CLIENT_CONN_MANAGER_TTL = 60000;
    public final static int POOL_MANAGER_MAX_TOTAL_DEFAULT_CONNECTIONS = 200; // 200 connections
    public final static int POOL_MANAGER_MAX_ROUTE_DEFAULT_CONNECTIONS = 200; // default 1 route, all connections of all routes should better be total

    // FIXME: please modify to proper value.
    // Retry
    public final static int HTTP_CLIENT_RETRY = 3;

    // FIXME: please modify to proper value.
    // Keep alive
    public final static int DEFAULT_KEEP_ALIVE_TIME = 20 * 1000; // unit: ms

    // FIXME: please modify to proper value.
    // Timeouts
    public final static int CONNECTION_TIMEOUT = 500; // 0.5 sec, the time for waiting until a connection is established
    public final static int REQUEST_TIMEOUT    = 500; // 0.5 sec, the time for waiting for a connection from connection pool
    public final static int SOCKET_TIMEOUT     = 1000; // 1 sec, the time for waiting for data

    // Idle connection monitor
    public final static String IDLE_CONNECTION_MONITOR_THREAD_NAME = "idle-conn-mon";
    public final static int IDLE_CONNECTION_MONITOR_THREAD_NUM = 3;

    // FIXME: please modify to proper value.
    public final static int IDLE_CONNECTION_WAIT_SECOND = 1 * 1000; // unit: ms

    // FIXME: please modify to proper value.
    public final static int IDLE_CONNECTION_CLOSE_INTERVAL = 1; // unit: second

    // FIXME: please modify to proper value.
    public final static int CONNECTION_MONITOR_INTERVAL = 1; // unit: second
}
