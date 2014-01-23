package org.localstorm.mcc.ejb.gtd.impl;

import org.localstorm.mcc.ejb.gtd.entity.FlightPlan;
import org.localstorm.mcc.ejb.gtd.entity.FlightPlanToTask;
import java.util.Collection;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.localstorm.mcc.ejb.AbstractSingletonManager;
import org.localstorm.mcc.ejb.except.ObjectNotFoundException;
import org.localstorm.mcc.ejb.gtd.entity.Context;
import org.localstorm.mcc.ejb.gtd.entity.Task;
import org.localstorm.mcc.ejb.memcached.MemcachedFacade;
import org.localstorm.mcc.ejb.users.User;

/**
 * @author Alexey Kuznetsov
 */
@Stateless 
public  class FlightPlanManagerBean extends AbstractSingletonManager<FlightPlan, User> 
                                    implements FlightPlanManagerLocal
{

    public FlightPlanManagerBean() {
        super(FlightPlan.class);
    }


    @Override
    public void addTaskToFlightPlan(Task t, FlightPlan fp) {

        Query uq = em.createNamedQuery(FlightPlanToTask.Queries.FIND_CONNECTORS_BY_TASK_AND_PLAN);
        {
            uq.setParameter(FlightPlanToTask.Properties.FLIGHT_PLAN, fp);
            uq.setParameter(FlightPlanToTask.Properties.TASK, t);
        }

        List<FlightPlanToTask> list = uq.getResultList();
        if (list.isEmpty()) {
            FlightPlanToTask fp2t = new FlightPlanToTask(fp, t);
            em.persist(fp2t);
        }
    }


    @Override
    public void removeTaskFromFlightPlan(Task t, FlightPlan fp) {
        Query uq = em.createNamedQuery(FlightPlanToTask.Queries.FIND_CONNECTORS_BY_TASK_AND_PLAN);
        {
            uq.setParameter(FlightPlanToTask.Properties.FLIGHT_PLAN, fp);
            uq.setParameter(FlightPlanToTask.Properties.TASK, t);
        }
        
        List<FlightPlanToTask> list = uq.getResultList();
        for (FlightPlanToTask fp2t: list) {
            em.remove(fp2t);
        }
    }

    @Override
    public Collection<Task> getTasksFromFlightPlan(FlightPlan fp, Context context) {
        Query uq;

        if (context==null) {
            uq = em.createNamedQuery(FlightPlanToTask.Queries.FIND_TASKS_BY_PLAN);
            {
                uq.setParameter(FlightPlanToTask.Properties.FLIGHT_PLAN, fp);
            }
            return uq.getResultList();
        } else {
            uq = em.createNamedQuery(FlightPlanToTask.Queries.FIND_TASKS_BY_PLAN_AND_CTX);
            {
                uq.setParameter(FlightPlanToTask.Properties.FLIGHT_PLAN, fp);
                uq.setParameter(FlightPlanToTask.Properties.CONTEXT, context);
            }
            return uq.getResultList();
        }
    }

    @Override
    public Collection<Task> getTasksFromFlightPlan(FlightPlan fp) {
        return this.getTasksFromFlightPlan(fp, null);
    }

    @Override
    public FlightPlan findByUser(User owner) {
        try {
            return this.findByUser(owner, true);
        } catch(ObjectNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public FlightPlan findByUser(User u, boolean createIfNone) throws ObjectNotFoundException {

        FlightPlan fp = this.findCurrentFlightPlan(u);

        if (fp==null) {
            if (createIfNone) {
                fp = this.create(u);
            } else {
                throw new ObjectNotFoundException();
            }
        }

        return fp;
    }

    @Override
    protected FlightPlan create(User u) {
        
        try 
        {
            FlightPlan result = new FlightPlan(u);
            em.persist(result);

            MemcachedFacade mf = MemcachedFacade.getInstance();
            mf.put(super.keyGen(u), result);

            return result;
            
        } catch(EntityExistsException e) 
        {
            throw new RuntimeException(e);
        }
    }

    private FlightPlan findCurrentFlightPlan(User u) {
        MemcachedFacade mf = MemcachedFacade.getInstance();
        String key = super.keyGen(u);

        FlightPlan fp = (FlightPlan) mf.get(key);
        if (fp==null) {
            fp = this.findCurrentFlightPlanInDb(u);
            if (fp!=null) {
                mf.put(key, fp);
            }
        }

        return fp;
    }

    private FlightPlan findCurrentFlightPlanInDb(User u) {
        try {
            Query uq = em.createNamedQuery(FlightPlan.Queries.FIND_CURRENT_BY_USER);
            uq.setParameter(FlightPlan.Properties.OWNER, u);
            return (FlightPlan) uq.getSingleResult();
        } catch(NoResultException e) {
            return null;
        }
    }
}
