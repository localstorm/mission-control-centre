package org.localstorm.mcc.ejb.cashflow.impl;

import org.localstorm.mcc.ejb.cashflow.entity.Cost;
import org.localstorm.mcc.ejb.cashflow.entity.Asset;
import org.localstorm.mcc.ejb.cashflow.entity.ValuableObject;
import org.localstorm.mcc.ejb.cashflow.entity.Target;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.localstorm.mcc.ejb.Constants;
import org.localstorm.mcc.ejb.except.ObjectNotFoundException;
import org.localstorm.mcc.ejb.users.User;

/**
 *
 * @author localstorm
 */
@Stateless
public class TargetManagerBean implements TargetManagerLocal
{
   
    @Override
    public void create(Target newTarget, Cost targetCost) {
       ValuableObject vo = newTarget.getValuable();
       em.persist(vo);
       em.persist(newTarget);

       targetCost.setValuable(vo);
       em.persist(targetCost);
    }

    @Override
    public void remove(Target target) {
        target = em.getReference(Target.class, target.getId());
        em.remove(target);
    }

    @Override
    public void update(Target target) {
        em.merge(target);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<Target> find(User user) {
        Query uq = em.createNamedQuery(Target.Queries.FIND_BY_OWNER);
        uq.setParameter(Asset.Properties.OWNER, user);
        
        List<Target> list = uq.getResultList();
        return list;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<Target> findArchived(User user)
    {
        return Collections.EMPTY_LIST;
    }

    @Override
    public Target find(int targetId) throws ObjectNotFoundException {
        Target t = em.find(Target.class, targetId);
        if (t==null)
        {
            throw new ObjectNotFoundException();
        }

        return t;
    }



    @PersistenceContext(unitName=Constants.DEFAULT_PU)
    protected EntityManager em;
}
