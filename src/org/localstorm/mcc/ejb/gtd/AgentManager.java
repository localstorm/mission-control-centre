package org.localstorm.mcc.ejb.gtd;

import java.util.Collection;
import org.localstorm.mcc.ejb.BaseManager;

import org.localstorm.mcc.ejb.gtd.entity.Agent;
import org.localstorm.mcc.ejb.users.User;

/**
 *
 * @author localstorm
 */

public interface AgentManager extends BaseManager<Agent>
{
    public static final String BEAN_NAME="AgentManagerBean";
    
    public Collection<Agent> getAgents(User u);

    public Collection<Agent> getAgents();
}
