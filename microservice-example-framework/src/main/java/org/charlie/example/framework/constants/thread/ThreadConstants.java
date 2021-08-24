package org.charlie.example.framework.constants.thread;

/**
 * To define constants used by thread pool.
 * Modify values of each constants to adapt the specific context.
 *
 * @author Charlie
 */
public class ThreadConstants {

    // FIXME: please modify to proper value
    public final static int THREAD_POOL_DEFAULT_NUM = 100;

    // FIXME: please modify to proper value
    public final static int THREAD_POOL_DEFAULT_MAX_NUM = 100;

    // FIXME: please modify to proper value
    public final static int THREAD_KEEP_ALIVE_SECOND = 60;

    public final static String THREAD_NAME_FORMAT = "framework-pool-%d";

    // FIXME: please modify to proper value
    public final static int THREAD_POOL_MONITOR_MS_INTERVAL = 60; // unit: s
}
