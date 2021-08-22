package org.charlie.template.framework.utils.bean;


public class SessionContext {

    private final static InheritableThreadLocal<Session> threadLocal = new InheritableThreadLocal<>();

    public static void setSession(Session session) {
        threadLocal.set(session);
    }

    public static Session getSession() {
        return threadLocal.get();
    }
}
