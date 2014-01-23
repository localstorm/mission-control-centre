package org.localstorm.mcc.ejb;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.localstorm.mcc.ejb.except.ObjectNotFoundException;
import org.localstorm.mcc.ejb.memcached.MemcachedFacade;

/**
 *
 * @author Alexey Kuznetsov
 */
public abstract class AbstractSingletonManager <T extends Identifiable, E extends Identifiable>
                                               implements BaseSingletonManager<T, E>
{
    public AbstractSingletonManager(Class<T> cl) {
        this.cl = cl;
    }

    protected abstract T create(E owner);

    @Override
    public void update(T o, E e)
    {
        em.merge( o );

        MemcachedFacade mf = MemcachedFacade.getInstance();
        mf.put(this.keyGen(e), o);
    }

    @Override
    public void utilizeCurrent( E e ) {
        try {
            T current = this.findByUser(e, false);
            em.remove(current);
            em.flush();

            MemcachedFacade mf = MemcachedFacade.getInstance();
            mf.remove(this.keyGen(e));

        } catch(ObjectNotFoundException ex) {
           /* ignoring */
        }
        this.create( e );
    }

    protected final String keyGen(E e) {
        StringBuilder sb = new StringBuilder(this.cl.getName());
        sb.append("##");
        sb.append(e.getId());
        return sb.toString();
    }
    
    @PersistenceContext(unitName=Constants.DEFAULT_PU)
    protected EntityManager em;

    protected Class<T> cl;
}
