package org.localstorm.mcc.ejb.gtd.impl;

import org.localstorm.mcc.ejb.gtd.entity.GTDList;
import javax.ejb.Stateless;
import javax.persistence.Query;
import org.localstorm.mcc.ejb.AbstractManager;
import org.localstorm.mcc.ejb.gtd.entity.Context;
import java.util.*;
/**
 *
 * @author localstorm
 */
@Stateless
public class ListManagerBean extends AbstractManager<GTDList>
                             implements ListManagerLocal
{
    public ListManagerBean() {
        super(GTDList.class);
    }

    
    @SuppressWarnings("unchecked")
    @Override
    public Collection<GTDList> findByContext(Context ctx)
    {
        Query lq = em.createNamedQuery(GTDList.Queries.FIND_BY_CTX);
        lq.setParameter(GTDList.Properties.CONTEXT, ctx);

        return (List<GTDList>) lq.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Collection<GTDList> findByContextArchived(Context ctx) {
        Query lq = em.createNamedQuery(GTDList.Queries.FIND_BY_CTX_ARCHIVED);
        lq.setParameter(GTDList.Properties.CONTEXT, ctx);

        return (List<GTDList>) lq.getResultList();
    }
    
}
