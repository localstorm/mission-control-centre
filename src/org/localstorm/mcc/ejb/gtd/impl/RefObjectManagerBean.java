package org.localstorm.mcc.ejb.gtd.impl;

import org.localstorm.mcc.ejb.gtd.entity.ReferencedObject;
import java.util.Collection;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;
import org.localstorm.mcc.ejb.AbstractManager;
import org.localstorm.mcc.ejb.users.User;

/**
 *
 * @author Alexey Kuznetsov
 */
@Stateless
public class RefObjectManagerBean extends AbstractManager<ReferencedObject>
                                  implements RefObjectManagerLocal
{

    public RefObjectManagerBean() {
        super(ReferencedObject.class);
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public Collection<ReferencedObject> findOperativeByOwner(User user, boolean sortByContext) {
        String queryName;
        if (sortByContext) {
            queryName = ReferencedObject.Queries.FIND_OPERATIVE_BY_OWNER;
        } else {
            queryName = ReferencedObject.Queries.FIND_OPERATIVE_BY_OWNER_NO_CTX_SORT;
        }
        Query lq = em.createNamedQuery(queryName);
        lq.setParameter(ReferencedObject.Properties.OWNER, user);
        
        List<ReferencedObject> list = lq.getResultList();
        return list;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<ReferencedObject> findAllByOwner(User user) {
        Query lq = em.createNamedQuery(ReferencedObject.Queries.FIND_BY_OWNER);
        lq.setParameter(ReferencedObject.Properties.OWNER, user);
        
        List<ReferencedObject> list = lq.getResultList();
        return list;
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public Collection<ReferencedObject> findAllArchivedByOwner(User user) {
        Query lq = em.createNamedQuery(ReferencedObject.Queries.FIND_ARCHIVED_BY_OWNER);
        lq.setParameter(ReferencedObject.Properties.OWNER, user);
        
        List<ReferencedObject> list = lq.getResultList();
        return list;
    }
    

}
