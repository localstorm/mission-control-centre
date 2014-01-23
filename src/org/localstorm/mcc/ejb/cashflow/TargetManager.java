package org.localstorm.mcc.ejb.cashflow;

import java.util.Collection;

import org.localstorm.mcc.ejb.cashflow.entity.Cost;
import org.localstorm.mcc.ejb.cashflow.entity.Target;
import org.localstorm.mcc.ejb.except.ObjectNotFoundException;
import org.localstorm.mcc.ejb.users.User;



/**
 *
 * @author localstorm
 */

public interface TargetManager
{
    public static final String BEAN_NAME = "TargetManagerBean";
    
    public void create(Target newTarget, Cost targetCost);

    
    public Target find(int targetId) throws ObjectNotFoundException;

    public Collection<Target> find(User user);

    public Collection<Target> findArchived(User user);

    public void remove(Target target);

    public void update(Target target);
}
