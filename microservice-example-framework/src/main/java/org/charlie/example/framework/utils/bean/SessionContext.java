package org.charlie.example.framework.utils.bean;


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
}
