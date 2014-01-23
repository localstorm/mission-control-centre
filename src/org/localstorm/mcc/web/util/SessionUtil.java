package org.localstorm.mcc.web.util;


import javax.servlet.http.HttpSession;
import org.localstorm.mcc.web.ExpirableValuesCache;

/**
 * @author Alexey Kuznetsov
 */
public class SessionUtil 
{
    private static final String EXPIRABLE_MAP = "$expAttributes$";
    private static final int RPB_MAP_CAPACITY = 100;

    public static void clear(HttpSession sess, String key) {
        sess.removeAttribute(key);
    }

    public static void fill(HttpSession sess, String key, Object o) {
        sess.setAttribute(key, o);
    }

    public static boolean isEmpty(HttpSession sess, String key) {
        return sess.getAttribute(key)==null;
    }
    
    public static Object getValue(HttpSession sess, String key) {
        return sess.getAttribute(key);
    }

    @SuppressWarnings("unchecked")
    public static void fillExpirable(HttpSession sess, String key, Object o) {
        
        ExpirableValuesCache evc = (ExpirableValuesCache) sess.getAttribute(EXPIRABLE_MAP);
        if (evc==null) {
            evc = new ExpirableValuesCache(RPB_MAP_CAPACITY);
            sess.setAttribute(EXPIRABLE_MAP, evc);
        }
        
        evc.put(key, o);
    }

    @SuppressWarnings("unchecked")
    public static Object getExpirableValue(HttpSession sess, String key)
    {
        ExpirableValuesCache evc = (ExpirableValuesCache) sess.getAttribute(EXPIRABLE_MAP);
        if (evc==null) {
            evc = new ExpirableValuesCache(RPB_MAP_CAPACITY);
            sess.setAttribute(EXPIRABLE_MAP, evc);
        }
        return evc.get(key);
    }

}
