package org.localstorm.mcc.ejb;

import org.localstorm.mcc.ejb.except.DuplicateException;
import org.localstorm.mcc.ejb.except.ObjectNotFoundException;

/**
 *
 * @author Alexey Kuznetsov
 */
public interface BaseManager<T>
{
    public void update( T o );
    public T create(T o) throws DuplicateException;
    public T findById( int id ) throws ObjectNotFoundException;
    public void remove( T o );
    public void flush();
}
