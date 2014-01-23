package org.localstorm.mcc.ejb;

import org.localstorm.mcc.ejb.except.ObjectNotFoundException;

/**
 *
 * @author localstorm
 */
public class NullResultGuard {
    
    public static <T> T checkNotNull(T o) throws ObjectNotFoundException {
        if (o==null) {
            throw new ObjectNotFoundException();
        }
        return o;
    }
}
