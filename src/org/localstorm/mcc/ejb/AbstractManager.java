package org.localstorm.mcc.ejb;

import java.util.Collection;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.localstorm.mcc.ejb.except.DuplicateException;
import org.localstorm.mcc.ejb.except.ObjectNotFoundException;
import org.localstorm.mcc.ejb.memcached.MemcachedFacade;


/**
 *
 * @author Alexey Kuznetsov
 */
public abstract class AbstractManager<T extends Identifiable> implements BaseManager<T>
{

    public AbstractManager(Class<T> c) {
        this.cl = c;
    }
    
    @Override
    public void update( T o ) 
    {
        int id = o.getId();
        
        em.merge( o );

        MemcachedFacade mf = MemcachedFacade.getInstance();
        mf.put(this.keyGen(id), o);
    }
    
    
    @Override
    public T create(T o) throws DuplicateException
    {
        try 
        {
            em.persist(o);
            this.flush();

            MemcachedFacade mf = MemcachedFacade.getInstance();
            mf.put(this.keyGen(o.getId()), o);

            return o;
        } catch(EntityExistsException e) 
        {
            throw new DuplicateException(e);
        }
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public T findById( int id ) throws ObjectNotFoundException
    {
        MemcachedFacade mf = MemcachedFacade.getInstance();
        String key = this.keyGen(id);

        T t = (T) mf.get(key);
        if (t==null) {
            t = em.find(cl, id);
            if (t!=null) {
                mf.put(key, t);
            }
        }
        
        if (t==null) {
            throw new ObjectNotFoundException();
        }
        
        return t;
    }

    @Override
    public void flush() {
        em.flush();
    }

    @Override
    public void remove(T obj) {
        int id = obj.getId();

        obj = em.getReference( cl, id );
        em.remove(obj);

        MemcachedFacade mf = MemcachedFacade.getInstance();
        mf.remove(this.keyGen(id));
    }

    protected final void updateObjectsMap(Collection<T> list) {
        MemcachedFacade mf = MemcachedFacade.getInstance();
        
        for (T t: list) {
            mf.put(this.keyGen(t.getId()), t);
        }
    }

    protected final String keyGen(int id) {
        StringBuilder sb = new StringBuilder(this.cl.getName());
        sb.append('#');
        sb.append(id);
        return sb.toString();
    }
    
    @PersistenceContext(unitName=Constants.DEFAULT_PU)
    protected EntityManager em;
    
    protected Class<T> cl;
}
