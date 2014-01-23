package org.localstorm.mcc.ejb;

import org.localstorm.mcc.ejb.except.ObjectNotFoundException;


/**
 *
 * @author Alexey Kuznetsov
 */
public interface BaseSingletonManager<T, E> 
{
    public void update( T o, E e );
    
    public T findByUser( E owner );

    public T findByUser( E owner, boolean createIfNone ) throws ObjectNotFoundException;
    
    public void utilizeCurrent( E owner );
}
