package org.localstorm.mcc.ejb.gtd.impl;

import java.util.Collection;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;
import org.localstorm.mcc.ejb.AbstractManager;
import org.localstorm.mcc.ejb.gtd.entity.Agent;
import org.localstorm.mcc.ejb.users.User;

/**
 * @author localstorm
 */
@Stateless
public class AgentManagerBean extends AbstractManager<Agent>
                              implements AgentManagerLocal
{

    public AgentManagerBean()
    {
        super(Agent.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<Agent> getAgents(User u)
    {
        Query uq = em.createNamedQuery(Agent.Queries.FIND_BY_OWNER);
        uq.setParameter(Agent.Properties.OWNER, u);

        return (List<Agent>) uq.getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<Agent> getAgents()
    {
        Query uq = em.createNamedQuery(Agent.Queries.FIND_ALL);
        return (List<Agent>) uq.getResultList();
    }
    
}
