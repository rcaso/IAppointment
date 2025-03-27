package com.shavatech.management.infraestructure.websocket;

import java.util.HashMap;
import java.util.Map;

public class ThreadLocalContextHolder {

    /** La Constante THREAD_WITH_CONTEXT. */
    private static final ThreadLocal<Map<String,String>> THREAD_WITH_CONTEXT = new ThreadLocal<Map<String,String>>();

    /**
     * Put.
     *
     * @param key el key
     * @param payload el payload
     */
    public static void put(String key, String payload) {
        if(THREAD_WITH_CONTEXT.get() == null){
            THREAD_WITH_CONTEXT.set(new HashMap<String, String>());
        }
        THREAD_WITH_CONTEXT.get().put(key, payload);
    }

    /**
     * Gets the.
     *
     * @param key el key
     * @return the object
     */
    public static String get(String key) {
        if(checkInitialited()){
            return THREAD_WITH_CONTEXT.get().get(key);
        } else {
            return null;
        }

    }

    /**
     * Cleanup thread.
     */
    public static void cleanupThread(){
        THREAD_WITH_CONTEXT.remove();
    }

    /**
     * Check initialited.
     *
     * @return true, en caso de exito
     */
    public static boolean checkInitialited() {
        return THREAD_WITH_CONTEXT.get() != null;
    }

    /**
     * Instancia un nuevo thread local context holder.
     */
    private ThreadLocalContextHolder() {
        // TODO Auto-generated constructor stub
    }
}
