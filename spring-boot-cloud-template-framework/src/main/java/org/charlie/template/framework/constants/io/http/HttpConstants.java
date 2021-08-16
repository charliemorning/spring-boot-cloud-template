package org.charlie.template.framework.constants.io.http;

import java.nio.charset.Charset;

public class HttpConstants {

    public final static int HTTP_CLIENT_RETRY = 3;

    public final static String ENTITY_CHARSET = "UTF-8";
    public static final Charset CHAR_SET = Charset.forName("utf-8");

    public final static int HTTP_CLIENT_CONN_MANAGER_TTL = 60000;
    public final static int HTTP_CLIENT_MAX_CONN = 200;
    public final static int POOL_MANAGER_MAX_TOTAL_DEFAULT_CONNECTIONS = 200;
    public final static int POOL_MANAGER_MAX_ROUTE_DEFAULT_CONNECTIONS = 100;

    public final static int HTTP_CLIENT_MAX_PER_ROUTE = 100;
    public final static int HTTP_CLIENT_TIMEOUT = 1000; // ms

    // Keep alive
    public final static int DEFAULT_KEEP_ALIVE_TIME = 20 * 1000; // 20 sec

    // Timeouts
    public final static int CONNECTION_TIMEOUT = 30 * 1000; // 30 sec, the time for waiting until a connection is established
    public final static int REQUEST_TIMEOUT    = 30 * 1000; // 30 sec, the time for waiting for a connection from connection pool
    public final static int SOCKET_TIMEOUT     = 60 * 1000; // 60 sec, the time for waiting for data

    // Idle connection monitor
    public final static int IDLE_CONNECTION_WAIT_TIME = 30 * 1000; // 30 sec
}
