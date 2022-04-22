package org.charlie.example.common.utils.bean;


import org.charlie.example.common.entities.Session;

/**
 * Session Context to hold request information for each request.
 * <p>
 * Using InheritableThreadLocal for multi-thread occasion.
 *
 * @author Charlie
 */
public class SessionContext {

    private final static InheritableThreadLocal<Session> threadLocal = new InheritableThreadLocal<>();

    public static void setSession(Session session) {
        threadLocal.set(session);
    }

    public static Session getSession() {
        return threadLocal.get();
    }

    public static void clearSession() {
        threadLocal.remove();
    }
}
